package com.fej1fun.potentials.fabric.transactions;

import com.fej1fun.potentials.transactions.DummySnapshotJournal;
import com.fej1fun.potentials.transactions.SnapshotJournal;
import com.fej1fun.potentials.transactions.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class FabricSnapshotJournal<T extends @NonNull Object> implements DummySnapshotJournal<T> {

    private final SnapshotJournal<T> journal;
    private final SnapshotParticipant<T> participant;

    public FabricSnapshotJournal(SnapshotJournal<T> journal) {
        this.journal = journal;

        this.participant = new SnapshotParticipant<T>() {

            @Override
            protected T createSnapshot() {
                return null;
            }

            @Override
            protected void readSnapshot(T snapshot) {
                
            }
        };
    }

    @Override
    public void updateSnapshots(TransactionContext transaction) {
        TransactionWrapper transactionWrapper = (TransactionWrapper) transaction;
        participant.updateSnapshots(transactionWrapper.getUnderlyingTransaction());
    }

    @Override
    public SnapshotJournal<T> getSnapshotJournal() {
        return journal;
    }
}
