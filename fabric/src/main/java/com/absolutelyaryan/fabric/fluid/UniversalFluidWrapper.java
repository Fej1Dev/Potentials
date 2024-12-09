package com.absolutelyaryan.fabric.fluid;

import com.absolutelyaryan.fabric.utils.ConversionHelper;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.level.material.Fluid;

public class UniversalFluidWrapper implements UniversalFluidTank {
    private final SingleVariantStorage<FluidVariant> storage;

    public UniversalFluidWrapper(SingleVariantStorage<FluidVariant> storage) {
        this.storage = storage;
    }


    @Override
    public Fluid getBaseFluid() {
        return storage.variant.getFluid();
    }

    @Override
    public long getFluidValue() {
        return ConversionHelper.dropletsToMilliBuckets(storage.amount);
    }

    @Override
    public FluidStack getFluidStack() {
        return FluidStackHooksFabric.fromFabric(storage);
    }

    @Override
    public long getMaxAmount() {
        return ConversionHelper.dropletsToMilliBuckets(storage.getCapacity());
    }

    @Override
    public boolean isValid(FluidStack stack) {
        return stack.isFluidEqual(getFluidStack());
    }

    @Override
    public long fillFluid(FluidStack stack, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            long inserted = storage.insert(FluidStackHooksFabric.toFabric(stack), ConversionHelper.milliBucketsToDroplets(stack.getAmount()), transaction);
            if (!simulate) {
                transaction.commit();
            }
            return inserted;
        }
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            long extracted = storage.extract(FluidStackHooksFabric.toFabric(stack), ConversionHelper.milliBucketsToDroplets(stack.getAmount()), transaction);
            if (!simulate) {
                transaction.commit();
            }
            return extracted;
        }
    }
}
