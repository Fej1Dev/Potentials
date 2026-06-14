package com.fej1fun.potentials.transactions;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface TransactionContext {
    int depth();
    Transaction openNested();
}
