package com.fej1fun.fabric.fluid;

import com.fej1fun.fabric.utils.ConversionHelper;
import com.fej1fun.fluid.UniversalFluidTank;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.level.material.Fluid;

@Deprecated(forRemoval = true)
public class FluidStorageWrapper implements UniversalFluidTank {
    private final Storage<FluidVariant> storage;
    private final StorageView<FluidVariant> view;

    public FluidStorageWrapper(Storage<FluidVariant> storage) {
        this.storage = storage;
        this.view = storage.iterator().next();
    }

    @Override
    public Fluid getBaseFluid() {
        return view.getResource().getFluid();
    }

    @Override
    public long getFluidValue() {
        return ConversionHelper.dropletsToMilliBuckets(view.getAmount());
    }

    @Override
    public FluidStack getFluidStack() {
        return FluidStackHooksFabric.fromFabric(view);
    }

    @Override
    public long getMaxAmount() {
        return ConversionHelper.dropletsToMilliBuckets(view.getCapacity());
    }

    @Override
    public boolean isValid(FluidStack stack) {
        return stack.isFluidEqual(getFluidStack());
    }

    @Override
    public long fillFluid(FluidStack stack, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            long inserted = storage.insert(FluidStackHooksFabric.toFabric(stack), stack.getAmount(), transaction);
            if (!simulate) transaction.commit();
            return ConversionHelper.dropletsToMilliBuckets(inserted);
        }
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            long extracted = storage.extract(FluidStackHooksFabric.toFabric(stack), stack.getAmount(), transaction);
            if (!simulate) transaction.commit();
            return ConversionHelper.dropletsToMilliBuckets(extracted);
        }
    }

}
