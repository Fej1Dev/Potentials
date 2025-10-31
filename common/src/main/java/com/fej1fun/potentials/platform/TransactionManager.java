package com.fej1fun.potentials.platform;

import com.fej1fun.potentials.transaction.Transaction;
import dev.architectury.injectables.annotations.ExpectPlatform;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class TransactionManager {
    @ExpectPlatform
    public static Transaction open(@Nullable Transaction parent) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static Transaction.Lifecycle getLifecycle() {
        throw new NotImplementedException();
    }
}
