package com.fej1fun.potentials.neoforge.transactions;

import com.fej1fun.potentials.transactions.Transaction;

public class TransactionWrapper implements Transaction {

    private final net.neoforged.neoforge.transfer.transaction.Transaction transaction;

    public TransactionWrapper(net.neoforged.neoforge.transfer.transaction.Transaction transaction) {
        this.transaction = transaction;
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
        return transaction.depth();
    }

    @Override
    public Transaction openNested() {
        return new TransactionWrapper(net.neoforged.neoforge.transfer.transaction.Transaction.open(transaction));
    }
}
