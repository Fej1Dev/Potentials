package com.fej1fun.potentials.platform;

import com.fej1fun.potentials.transactions.DummySnapshotJournal;
import com.fej1fun.potentials.transactions.SnapshotJournal;
import com.fej1fun.potentials.transactions.Transaction;
import com.fej1fun.potentials.transactions.TransactionContext;
import dev.architectury.injectables.annotations.ExpectPlatform;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@ApiStatus.Internal
public class TransactionManager {

    @ExpectPlatform
    public static Transaction openRoot() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static Transaction open(@Nullable TransactionContext parent) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static Transaction.Lifecycle getLifecycle() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <T extends @NonNull Object> DummySnapshotJournal<T> createSnapshotJournal(SnapshotJournal<T> reference) {
        throw new NotImplementedException();
    }
}
