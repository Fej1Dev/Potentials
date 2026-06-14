package com.fej1fun.potentials.transactions;

import com.fej1fun.potentials.platform.TransactionManager;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;


@ApiStatus.NonExtendable
public interface Transaction extends AutoCloseable, TransactionContext {
    static Transaction openRoot() {
        return TransactionManager.openRoot();
    }

    static Transaction open(@Nullable TransactionContext parent) {
        return TransactionManager.open(parent);
    }

    static Lifecycle getLifecycle() {
        return TransactionManager.getLifecycle();
    }

    void commit();

    void close();

    enum Lifecycle {
        NONE,
        OPEN,
        CLOSING,
        ROOT_CLOSING;

    }
}
