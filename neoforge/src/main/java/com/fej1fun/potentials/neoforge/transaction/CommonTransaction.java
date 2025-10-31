package com.fej1fun.potentials.neoforge.transaction;

import com.fej1fun.potentials.transaction.Transaction;


public class CommonTransaction implements Transaction {

    private final net.neoforged.neoforge.transfer.transaction.Transaction transaction;

    public CommonTransaction(net.neoforged.neoforge.transfer.transaction.Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public Transaction openNested() {
        return new CommonTransaction(net.neoforged.neoforge.transfer.transaction.Transaction.open(transaction));
    }

    @Override
    public int nestingDepth() {
        return transaction.depth();
    }

    @Override
    public void abort() {
        transaction.close();
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
    public Object getInternalTransaction() {
        return this.transaction;
    }
}
