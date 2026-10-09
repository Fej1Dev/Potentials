package com.fej1fun.potentials.neoforge.energy;

import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

@ApiStatus.Internal
public class ItemAccessEnergyStorage implements UniversalEnergyStorage {
    private final ItemAccess itemAccess;

    public ItemAccessEnergyStorage(ItemAccess itemAccess) {
        this.itemAccess = itemAccess;
    }

    private static @Nullable UniversalEnergyStorage getStorage(ItemStack stack) {
        return stack.getItem() instanceof EnergyProvider.ITEM provider ? provider.getEnergy(stack) : null;
    }

    private int transfer(boolean simulate, ToIntFunction<UniversalEnergyStorage> operation) {
        ItemStack stack = this.itemAccess.getResource().toStack();
        UniversalEnergyStorage storage = getStorage(stack);
        if (storage == null)
            return 0;
        try (Transaction transaction = Transaction.open(Transaction.getCurrentOpenedTransaction())) {
            int moved = operation.applyAsInt(storage);
            if (moved <= 0 || this.itemAccess.exchange(ItemResource.of(stack), 1, transaction) != 1)
                return 0;
            if (!simulate)
                transaction.commit();
            return moved;
        }
    }

    @Override
    public int getEnergy() {
        UniversalEnergyStorage storage = getStorage(this.itemAccess.getResource().toStack());
        return storage == null ? 0 : storage.getEnergy();
    }

    @Override
    public int getMaxEnergy() {
        UniversalEnergyStorage storage = getStorage(this.itemAccess.getResource().toStack());
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
        UniversalEnergyStorage storage = getStorage(this.itemAccess.getResource().toStack());
        return storage != null && storage.canInsertEnergy();
    }

    @Override
    public boolean canExtractEnergy() {
        UniversalEnergyStorage storage = getStorage(this.itemAccess.getResource().toStack());
        return storage != null && storage.canExtractEnergy();
    }
}
