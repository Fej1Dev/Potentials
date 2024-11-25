package com.absolutelyaryan.fabric.fluid;

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
        return storage.amount;
    }

    @Override
    public FluidStack getFluidStack() {
        return FluidStackHooksFabric.fromFabric(storage);
    }

    @Override
    public long getMaxAmount() {
        return storage.getCapacity();
    }

    @Override
    public boolean isValid(FluidStack stack) {
        return stack.isFluidEqual(getFluidStack());
    }

    @Override
    public long fillFluid(FluidStack stack, boolean simulate) {
        if(simulate){
            return stack.getAmount() + getFluidValue() > getMaxAmount() ? 0 : stack.getAmount();
        }
        return storage.insert(FluidStackHooksFabric.toFabric(stack), stack.getAmount(), Transaction.openOuter());
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        if(simulate){
            return stack.getAmount() - getFluidValue() < 0 ? 0 : stack.getAmount();
        }
        return storage.extract(FluidStackHooksFabric.toFabric(stack), stack.getAmount(), Transaction.openOuter());
    }

    @Override
    public void setFluidValue(long amount) {
        storage.amount = amount;
    }

    @Override
    public void setMaxAmount(long amount) {
        //not supported
    }
}
