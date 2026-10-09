package com.fej1fun.potentials.fabric.utils;

import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fabric.energy.FabricEnergyStorage;
import com.fej1fun.potentials.fabric.energy.UniversalEnergyWrapper;
import com.fej1fun.potentials.fabric.fluid.FabricFluidStorage;
import com.fej1fun.potentials.fabric.fluid.UniversalFluidVariantStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class FabricTransactionHelper {

    @SuppressWarnings("deprecation")
    public static Transaction open() {
        if (Transaction.getLifecycle() == Transaction.Lifecycle.OUTER_CLOSING)
            throw new IllegalStateException("Fabric storages can't be used while the outer transaction is closing. A storage with deferUntilCommit() = true must not move resources through other storages on Fabric");
        return Transaction.openNested(Transaction.getCurrentUnsafe());
    }

    public static @Nullable UniversalFluidStorage wrap(@Nullable UniversalFluidStorage storage) {
        if (storage == null || storage instanceof UniversalFluidVariantStorage || Transaction.getLifecycle() != Transaction.Lifecycle.OPEN)
            return storage;
        return new UniversalFluidVariantStorage(new FabricFluidStorage(storage));
    }

    public static @Nullable UniversalEnergyStorage wrap(@Nullable UniversalEnergyStorage storage) {
        if (storage == null || storage instanceof UniversalEnergyWrapper || Transaction.getLifecycle() != Transaction.Lifecycle.OPEN)
            return storage;
        return new UniversalEnergyWrapper(new FabricEnergyStorage(storage));
    }

    private FabricTransactionHelper() {

    }
}
