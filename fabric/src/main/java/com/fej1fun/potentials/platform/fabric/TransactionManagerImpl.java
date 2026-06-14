package com.fej1fun.potentials.platform.fabric;

import com.fej1fun.potentials.fabric.transactions.TransactionWrapper;
import com.fej1fun.potentials.transactions.Transaction;

public class TransactionManagerImpl {

    public static Transaction openRoot() {
        return new TransactionWrapper(net.fabricmc.fabric.api.transfer.v1.transaction.Transaction.openOuter());
    }

    public static Transaction.Lifecycle getLifecycle() {
        return switch (net.fabricmc.fabric.api.transfer.v1.transaction.Transaction.getLifecycle()) {
            case NONE -> Transaction.Lifecycle.NONE;
            case OPEN -> Transaction.Lifecycle.OPEN;
            case CLOSING -> Transaction.Lifecycle.CLOSING;
            case OUTER_CLOSING -> Transaction.Lifecycle.ROOT_CLOSING;
        };
    }
}
