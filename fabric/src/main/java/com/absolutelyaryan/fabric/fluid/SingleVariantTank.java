package com.absolutelyaryan.fabric.fluid;

import com.absolutelyaryan.fabric.utils.ConversionHelper;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

public class SingleVariantTank implements Storage<FluidVariant> {
    private final UniversalFluidTank baseFluidTank;

    public SingleVariantTank(@NotNull UniversalFluidTank baseFluidTank) {
        this.baseFluidTank = baseFluidTank;
    }


    @Override
    public boolean supportsInsertion() {
        return baseFluidTank.getFluidValue() < baseFluidTank.getMaxAmount();
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
    public boolean supportsExtraction() {
        return baseFluidTank.getFluidValue() > 0;
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
    public @NotNull Iterator<StorageView<FluidVariant>> iterator() {
        return List.of(new FluidStorageViewWrapper().getUnderlyingView()).iterator();
    }

}
