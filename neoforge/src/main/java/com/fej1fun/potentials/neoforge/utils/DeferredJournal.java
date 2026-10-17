package com.fej1fun.potentials.neoforge.utils;

import com.google.common.collect.MapMaker;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ApiStatus.Internal
public class DeferredJournal extends SnapshotJournal<Integer> {
    private static final Map<Object, DeferredJournal> JOURNALS = new MapMaker().weakKeys().makeMap();
    private final List<Operation> operations = new ArrayList<>();

    public static DeferredJournal of(Object storage) {
        return JOURNALS.computeIfAbsent(storage, key -> new DeferredJournal());
    }

    public void defer(TransactionContext transaction, Runnable operation) {
        defer(transaction, null, false, 0, operation);
    }

    public void defer(TransactionContext transaction, @Nullable Object resource, boolean extract, long amount, Runnable operation) {
        this.updateSnapshots(transaction);
        this.operations.add(new Operation(resource, extract, amount, operation));
    }

    public long getPending(@Nullable Object resource, boolean extract) {
        long pending = 0;
        for (Operation operation : this.operations)
            if (operation.extract() == extract && Objects.equals(operation.resource(), resource))
                pending += operation.amount();
        return pending;
    }

    @Override
    protected Integer createSnapshot() {
        return this.operations.size();
    }

    @Override
    protected void revertToSnapshot(Integer snapshot) {
        this.operations.subList(snapshot, this.operations.size()).clear();
    }

    @Override
    protected void onRootCommit(Integer originalState) {
        List<Operation> toRun = new ArrayList<>(this.operations);
        this.operations.clear();
        toRun.forEach(operation -> operation.operation().run());
    }

    private record Operation(@Nullable Object resource, boolean extract, long amount, Runnable operation) {}
}
