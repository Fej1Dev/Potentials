package com.fej1fun.potentials.fabric.transaction;

import com.fej1fun.potentials.transaction.Transaction;
import org.jetbrains.annotations.ApiStatus;

public class CommonTransaction implements Transaction {

    private final net.fabricmc.fabric.api.transfer.v1.transaction.Transaction transaction;

    public CommonTransaction(net.fabricmc.fabric.api.transfer.v1.transaction.Transaction context) {
        this.transaction = context;
    }

    @Override
    public Transaction openNested() {
        return new CommonTransaction(transaction.openNested());
    }

    @Override
    public int nestingDepth() {
        return transaction.nestingDepth();
    }

    @Override
    public void abort() {
        transaction.abort();
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
    @ApiStatus.Internal
    public Object getInternalTransaction() {
        return this.transaction;
    }

}
