package com.fej1fun.potentials.fabric.transactions;

import com.fej1fun.potentials.transactions.Transaction;

public class TransactionWrapper implements Transaction {

    private final net.fabricmc.fabric.api.transfer.v1.transaction.Transaction transaction;

    public TransactionWrapper(net.fabricmc.fabric.api.transfer.v1.transaction.Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public Transaction openNested() {
        return new TransactionWrapper(transaction.openNested());
    }

    @Override
    public void commit() {
        transaction.commit();
    }

    @Override
    public void close() {
        transaction.close();
    }

    @Override
    public int depth() {
        return transaction.nestingDepth();
    }
}
