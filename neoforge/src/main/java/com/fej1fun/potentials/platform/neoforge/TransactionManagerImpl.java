package com.fej1fun.potentials.platform.neoforge;

import com.fej1fun.potentials.neoforge.transaction.CommonTransaction;
import com.fej1fun.potentials.transaction.Transaction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class TransactionManagerImpl {
    public static Transaction open(@Nullable Transaction parent) {
        if (parent == null)
            return new CommonTransaction(net.neoforged.neoforge.transfer.transaction.Transaction.open(null));

        return parent.openNested();
    }

    public static Transaction.Lifecycle getLifecycle() {
        return switch (net.neoforged.neoforge.transfer.transaction.Transaction.getLifecycle()) {
            case NONE -> Transaction.Lifecycle.NONE;
            case OPEN -> Transaction.Lifecycle.OPEN;
            case CLOSING -> Transaction.Lifecycle.CLOSING;
            case ROOT_CLOSING -> Transaction.Lifecycle.OUTER_CLOSING;
        };
    }
}
