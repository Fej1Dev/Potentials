package com.fej1fun.potentials.transaction;

import org.jetbrains.annotations.ApiStatus;

/**
 * A subset of a {@link Transaction} that lets participants properly take part in transactions, manage their state,
 * or open nested transactions, but does not allow them to close the transaction they are passed.
 *
 * <p>
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
@ApiStatus.NonExtendable
public interface TransactionContext {
    /**
     * Open a new nested transaction.
     *
     * @throws IllegalStateException If this function is not called on the thread this transaction was opened in.
     * @throws IllegalStateException If this transaction is not the current transaction.
     * @throws IllegalStateException If this transaction was closed.
     */
    Transaction openNested();

    /**
     * @return The nesting depth of this transaction: 0 if it was opened with {@link Transaction#openRoot},
     * 1 if its parent was opened with {@link Transaction#openRoot}, and so on...
     * @throws IllegalStateException If this function is not called on the thread this transaction was opened in.
     */
    int nestingDepth();

    @ApiStatus.Internal
    Object getInternalTransaction();
}
