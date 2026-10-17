package com.fej1fun.potentials.platform.fabric;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class TransactionHelperImpl {

    public static boolean isOpen() {
        return Transaction.isOpen();
    }
}
