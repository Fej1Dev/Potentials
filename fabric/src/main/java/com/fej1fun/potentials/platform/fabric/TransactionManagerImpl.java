package com.fej1fun.potentials.platform.fabric;

import com.fej1fun.potentials.fabric.transaction.CommonTransaction;
import com.fej1fun.potentials.transaction.Transaction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class TransactionManagerImpl {
    public static Transaction open(@Nullable Transaction parent) {
        if (parent == null)
            return new CommonTransaction(net.fabricmc.fabric.api.transfer.v1.transaction.Transaction.openOuter());

        return parent.openNested();
    }

    public static Transaction.Lifecycle getLifecycle() {
        return switch (net.fabricmc.fabric.api.transfer.v1.transaction.Transaction.getLifecycle()) {
            case NONE -> Transaction.Lifecycle.NONE;
            case OPEN -> Transaction.Lifecycle.OPEN;
            case CLOSING -> Transaction.Lifecycle.CLOSING;
            case OUTER_CLOSING -> Transaction.Lifecycle.OUTER_CLOSING;
        };
    }
}
