package com.fej1fun.potentials.fabric.utils;

import dev.architectury.fluid.FluidStack;
import dev.architectury.fluid.fabric.FluidStackImpl;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;

@Deprecated
public class ConversionHelper {

    private static final int DROPLETS_PER_BUCKET = 81000;
    private static final int MILLIBUCKETS_PER_BUCKET = 1000;

    public static long dropletsToMilliBuckets(long droplets) {
        return (droplets * MILLIBUCKETS_PER_BUCKET) / DROPLETS_PER_BUCKET;
    }

    public static long milliBucketsToDroplets(long milliBuckets) {
        return (milliBuckets * DROPLETS_PER_BUCKET) / MILLIBUCKETS_PER_BUCKET;
    }
    public static FluidStack fromFabric(FluidVariant variant, long amount) {
        return FluidStackImpl.fromValue.apply(new FluidStackImpl.Pair(variant.getFluid(), variant.getComponents(), amount));
    }

    public static FluidStack fromFabric(StorageView<FluidVariant> storageView) {
        return fromFabric(storageView.getResource(), storageView.getAmount());
    }


}
