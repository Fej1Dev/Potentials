package com.fej1fun.potentials.neoforge.utils;

import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class DeferredJournal extends SnapshotJournal<Integer> {
    private final List<Runnable> operations = new ArrayList<>();

    public void defer(TransactionContext transaction, Runnable operation) {
        this.updateSnapshots(transaction);
        this.operations.add(operation);
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
        List<Runnable> toRun = new ArrayList<>(this.operations);
        this.operations.clear();
        toRun.forEach(Runnable::run);
    }
}
