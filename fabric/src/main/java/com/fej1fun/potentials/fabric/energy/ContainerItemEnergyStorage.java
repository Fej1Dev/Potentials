package com.fej1fun.potentials.fabric.energy;

import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fabric.utils.FabricTransactionHelper;
import com.fej1fun.potentials.providers.EnergyProvider;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

@ApiStatus.Internal
public class ContainerItemEnergyStorage implements UniversalEnergyStorage {
    private final ContainerItemContext context;

    public ContainerItemEnergyStorage(ContainerItemContext context) {
        this.context = context;
    }

    private static @Nullable UniversalEnergyStorage getStorage(ItemStack stack) {
        return stack.getItem() instanceof EnergyProvider.ITEM provider ? provider.getEnergy(stack) : null;
    }

    private int transfer(boolean simulate, ToIntFunction<UniversalEnergyStorage> operation) {
        ItemStack stack = this.context.getItemVariant().toStack();
        UniversalEnergyStorage storage = getStorage(stack);
        if (storage == null)
            return 0;
        try (Transaction transaction = FabricTransactionHelper.open()) {
            int moved = operation.applyAsInt(storage);
            if (moved <= 0 || this.context.exchange(ItemVariant.of(stack), 1, transaction) != 1)
                return 0;
            if (!simulate)
                transaction.commit();
            return moved;
        }
    }

    @Override
    public int getEnergy() {
        UniversalEnergyStorage storage = getStorage(this.context.getItemVariant().toStack());
        return storage == null ? 0 : storage.getEnergy();
    }

    @Override
    public int getMaxEnergy() {
        UniversalEnergyStorage storage = getStorage(this.context.getItemVariant().toStack());
        return storage == null ? 0 : storage.getMaxEnergy();
    }

    @Override
    public void setEnergyStored(int amount) {
        transfer(false, storage -> {
            storage.setEnergyStored(amount);
            return 1;
        });
    }

    @Override
    public int insert(int amount, boolean simulate) {
        return transfer(simulate, storage -> storage.insert(amount, false));
    }

    @Override
    public int extract(int amount, boolean simulate) {
        return transfer(simulate, storage -> storage.extract(amount, false));
    }

    @Override
    public boolean canInsertEnergy() {
        UniversalEnergyStorage storage = getStorage(this.context.getItemVariant().toStack());
        return storage != null && storage.canInsertEnergy();
    }

    @Override
    public boolean canExtractEnergy() {
        UniversalEnergyStorage storage = getStorage(this.context.getItemVariant().toStack());
        return storage != null && storage.canExtractEnergy();
    }
}
