package com.fej1fun.potentials.fabric.fluid;

import com.fej1fun.potentials.fabric.utils.FabricTransactionHelper;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import dev.architectury.fluid.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;

@ApiStatus.Internal
public class ContainerItemFluidStorage implements UniversalFluidItemStorage {
    private final ContainerItemContext context;

    public ContainerItemFluidStorage(ContainerItemContext context) {
        this.context = context;
    }

    private @Nullable UniversalFluidItemStorage getStorage() {
        ItemStack stack = this.context.getItemVariant().toStack();
        return stack.getItem() instanceof FluidProvider.ITEM provider ? provider.getFluidTank(stack) : null;
    }

    @Override
    public int getTanks() {
        UniversalFluidItemStorage storage = getStorage();
        return storage == null ? 0 : storage.getTanks();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        UniversalFluidItemStorage storage = getStorage();
        return storage == null ? FluidStack.empty() : storage.getFluidInTank(tank);
    }

    @Override
    public long getTankCapacity(int tank) {
        UniversalFluidItemStorage storage = getStorage();
        return storage == null ? 0 : storage.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        UniversalFluidItemStorage storage = getStorage();
        return storage != null && storage.isFluidValid(tank, stack);
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        UniversalFluidItemStorage storage = getStorage();
        if (storage == null)
            return 0;
        try (Transaction transaction = FabricTransactionHelper.open()) {
            long filled = storage.fill(stack, false);
            if (filled <= 0 || this.context.exchange(ItemVariant.of(storage.getContainer()), 1, transaction) != 1)
                return 0;
            if (!simulate)
                transaction.commit();
            return filled;
        }
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        UniversalFluidItemStorage storage = getStorage();
        if (storage == null)
            return FluidStack.empty();
        try (Transaction transaction = FabricTransactionHelper.open()) {
            FluidStack drained = storage.drain(stack, false);
            if (drained.isEmpty() || this.context.exchange(ItemVariant.of(storage.getContainer()), 1, transaction) != 1)
                return FluidStack.empty();
            if (!simulate)
                transaction.commit();
            return drained;
        }
    }

    @Override
    public @NotNull Iterator<FluidStack> iterator() {
        UniversalFluidItemStorage storage = getStorage();
        return storage == null ? Collections.emptyIterator() : storage.iterator();
    }

    @Override
    public ItemStack getContainer() {
        return this.context.getItemVariant().toStack();
    }
}
