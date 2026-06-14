package com.fej1fun.potentials.platform.neoforge;

import com.fej1fun.potentials.neoforge.transactions.TransactionWrapper;
import com.fej1fun.potentials.transactions.Transaction;

public class TransactionManagerImpl {
    public static Transaction openRoot() {
        return new TransactionWrapper(net.neoforged.neoforge.transfer.transaction.Transaction.openRoot());
    }

    public static Transaction.Lifecycle getLifecycle() {
        return switch (net.neoforged.neoforge.transfer.transaction.Transaction.getLifecycle()) {
            case NONE -> Transaction.Lifecycle.NONE;
            case OPEN -> Transaction.Lifecycle.OPEN;
            case CLOSING -> Transaction.Lifecycle.CLOSING;
            case ROOT_CLOSING -> Transaction.Lifecycle.ROOT_CLOSING;
        };
    }
}
