package com.absolutelyaryan.fluid;

import com.absolutelyaryan.fabric.utils.ConversionHelper;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;

public class SingleVariantTank extends SingleVariantStorage<FluidVariant> {
    private final UniversalFluidTank baseFluidTank;

    public SingleVariantTank(UniversalFluidTank baseFluidTank) {
        this.baseFluidTank = baseFluidTank;
    }

    @Override
    protected FluidVariant getBlankVariant() {
        return FluidVariant.blank();
    }

    @Override
    protected long getCapacity(FluidVariant variant) {
        return baseFluidTank.getMaxAmount();
    }



    @Override
    public long insert(FluidVariant insertedVariant, long maxAmount, TransactionContext transaction) {
        if (insertedVariant.isBlank()) {
            return 0;
        }

        if (baseFluidTank.isValid(ConversionHelper.fromFabric(insertedVariant, maxAmount))) {
            return baseFluidTank.fillFluid(FluidStackHooksFabric.fromFabric(insertedVariant, maxAmount), false);
        }

        return 0;
    }

    @Override
    public long extract(FluidVariant extractedVariant, long maxAmount, TransactionContext transaction) {
        if (extractedVariant.isBlank()) {
            return 0;
        }

        if (baseFluidTank.isValid(ConversionHelper.fromFabric(extractedVariant, maxAmount))) {
            return baseFluidTank.drainFluid(ConversionHelper.fromFabric(extractedVariant, maxAmount), false);
        }

        return 0;
    }

    @Override
    public boolean isResourceBlank() {
        return baseFluidTank.getFluidStack().isEmpty();
    }

    @Override
    public FluidVariant getResource() {
        return FluidVariant.of(baseFluidTank.getFluidStack().getFluid());
    }

    @Override
    public long getAmount() {
        return baseFluidTank.getFluidValue();
    }

    @Override
    public long getCapacity() {
        return baseFluidTank.getMaxAmount();
    }

    public SingleVariantStorage<FluidVariant> getFluidTank(Direction direction) {
        return this;
    }

    @Override
    public String toString() {
        return "FluidTankToSingleVariantStorage[Fluid: " + getResource() + ", Amount: " + getAmount() + "]";
    }
}
