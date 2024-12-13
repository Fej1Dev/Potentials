package com.fej1fun.potentials.fabric.fluid;

import com.fej1fun.potentials.fabric.utils.ConversionHelper;
import com.fej1fun.potentials.fluid.UniversalFluidTank;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

public class SingleVariantTank extends SingleVariantStorage<FluidVariant> {

    private final UniversalFluidTank baseFluidTank;

    public SingleVariantTank(@NotNull UniversalFluidTank baseFluidTank) {
        this.baseFluidTank = baseFluidTank;
    }

    @Override
    protected FluidVariant getBlankVariant() {
        return FluidVariant.blank();
    }

    @Override
    protected long getCapacity(FluidVariant variant) {
        return ConversionHelper.milliBucketsToDroplets(baseFluidTank.getMaxAmount());
    }

   @Override
    public long insert(FluidVariant insertedVariant, long maxAmount, TransactionContext transaction) {
        if (insertedVariant.isBlank() || !supportsInsertion()) {
            return 0;
        }
        long availableCapacity = getCapacity() - getAmount();

       long insertedAmount;
       if (maxAmount > availableCapacity) {
           insertedAmount = baseFluidTank.fillFluid(
                   FluidStackHooksFabric.fromFabric(insertedVariant, ConversionHelper.dropletsToMilliBuckets(availableCapacity)),
                   false
           );
       } else {
           insertedAmount = baseFluidTank.fillFluid(
                   FluidStackHooksFabric.fromFabric(insertedVariant, ConversionHelper.dropletsToMilliBuckets(maxAmount)),
                   false
           );
       }
       return maxAmount - ConversionHelper.milliBucketsToDroplets(insertedAmount);
   }



    @Override
    public long extract(FluidVariant extractedVariant, long maxAmount, TransactionContext transaction) {
        if (extractedVariant.isBlank()) {
            return 0;
        }
        long availableAmount = getAmount();

        long extractedAmount;
        if (maxAmount > availableAmount) {
            extractedAmount = baseFluidTank.drainFluid(
                    ConversionHelper.fromFabric(extractedVariant, ConversionHelper.dropletsToMilliBuckets(availableAmount)),
                    false
            );
        } else {
            extractedAmount = baseFluidTank.drainFluid(
                    ConversionHelper.fromFabric(extractedVariant, ConversionHelper.dropletsToMilliBuckets(maxAmount)),
                    false
            );
        }
        return ConversionHelper.milliBucketsToDroplets(extractedAmount);
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
        return ConversionHelper.milliBucketsToDroplets(baseFluidTank.getFluidValue());
    }

    @Override
    public long getCapacity() {
        return ConversionHelper.milliBucketsToDroplets(baseFluidTank.getMaxAmount());
    }

    public SingleVariantStorage<FluidVariant> getFluidTank(Direction direction) {
        return this;
    }

    @Override
    public boolean supportsInsertion() {
        return baseFluidTank.getFluidValue() < baseFluidTank.getMaxAmount();
    }

    @Override
    public boolean supportsExtraction() {
        return baseFluidTank.getFluidValue() > 0;
    }

    @Override
    public String toString() {
        return "FluidTankToSingleVariantStorage[Fluid: " + getResource() + ", Amount: " + getAmount() + "]";
    }
}
