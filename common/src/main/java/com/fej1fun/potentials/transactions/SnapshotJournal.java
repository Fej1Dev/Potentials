package com.fej1fun.potentials.transactions;

import com.fej1fun.potentials.platform.TransactionManager;
import org.jspecify.annotations.NonNull;

public abstract class SnapshotJournal<T extends @NonNull Object> {
    private final DummySnapshotJournal<T> journal;

    public SnapshotJournal() {
        journal = TransactionManager.createSnapshotJournal(this);
    }

    protected abstract T createSnapshot();

    protected abstract void revertToSnapshot(T snapshot);

    protected void releaseSnapshot(T snapshot) {

    }

    protected void onRootCommit(T originalState) {
    }

    public void updateSnapshots(TransactionContext transaction) {

    }

}
