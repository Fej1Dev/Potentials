package com.absolutelyaryan.fabric.fluid;

import com.absolutelyaryan.fabric.utils.ConversionHelper;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.level.material.Fluid;

public class StorageViewWrapper implements UniversalFluidTank {

    private final StorageView<FluidVariant> view;
    private final Storage<FluidVariant> storage;

    public StorageViewWrapper(Storage<FluidVariant> storage, StorageView<FluidVariant> view) {
        this.view = view;
        this.storage = storage;
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
        if(FluidStackHooksFabric.fromFabric(view).getFluid()==stack.getFluid() || FluidStackHooksFabric.fromFabric(view).isEmpty()) {
            try (Transaction transaction = Transaction.openOuter()) {
                long inserted = storage.insert(FluidStackHooksFabric.toFabric(stack), stack.getAmount(), transaction);
                if (!simulate) transaction.commit();
                return ConversionHelper.dropletsToMilliBuckets(inserted);
            }
        }
        return 0;
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        if(FluidStackHooksFabric.fromFabric(view).getFluid()==stack.getFluid()) {
            try (Transaction transaction = Transaction.openOuter()) {
                long extracted = storage.extract(FluidStackHooksFabric.toFabric(stack), stack.getAmount(), transaction);
                if (!simulate) transaction.commit();
                return ConversionHelper.dropletsToMilliBuckets(extracted);
            }
        }
        return 0;
    }
}
