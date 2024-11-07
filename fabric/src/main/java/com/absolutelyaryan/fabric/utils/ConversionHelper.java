package com.absolutelyaryan.fabric.utils;

import dev.architectury.fluid.FluidStack;
import dev.architectury.fluid.fabric.FluidStackImpl;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;

public class ConversionHelper {
    public static FluidStack fromFabric(FluidVariant variant, long amount) {
        return FluidStackImpl.fromValue.apply(new FluidStackImpl.Pair(variant.getFluid(), variant.getComponents(), amount));
    }

    public static FluidStack fromFabric(StorageView<FluidVariant> storageView) {
        return fromFabric(storageView.getResource(), storageView.getAmount());
    }

}
