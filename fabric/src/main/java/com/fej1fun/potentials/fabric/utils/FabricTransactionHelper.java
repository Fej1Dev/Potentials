package com.fej1fun.potentials.fabric.utils;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class FabricTransactionHelper {

    @SuppressWarnings("deprecation")
    public static Transaction open() {
        if (Transaction.getLifecycle() == Transaction.Lifecycle.OUTER_CLOSING)
            throw new IllegalStateException("Fabric storages can't be used while the outer transaction is closing. A storage with deferUntilCommit() = true must not move resources through other storages on Fabric");
        return Transaction.openNested(Transaction.getCurrentUnsafe());
    }

    private FabricTransactionHelper() {

    }
}
