package com.fej1fun.potentials.fabric.utils;

import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class DeferredParticipant extends SnapshotParticipant<Integer> {
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
    protected void readSnapshot(Integer snapshot) {
        this.operations.subList(snapshot, this.operations.size()).clear();
    }

    @Override
    protected void onFinalCommit() {
        List<Runnable> toRun = new ArrayList<>(this.operations);
        this.operations.clear();
        toRun.forEach(Runnable::run);
    }
}
