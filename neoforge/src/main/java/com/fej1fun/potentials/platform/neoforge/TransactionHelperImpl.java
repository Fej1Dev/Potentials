package com.fej1fun.potentials.platform.neoforge;

import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class TransactionHelperImpl {

    public static boolean isOpen() {
        return Transaction.getCurrentOpenedTransaction() != null;
    }
}
