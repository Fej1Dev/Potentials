package com.fej1fun.potentials.transactions;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@ApiStatus.Internal
public interface DummySnapshotJournal<T extends @NonNull Object> {
    void updateSnapshots(TransactionContext transaction);
    SnapshotJournal<T> getSnapshotJournal();
}
