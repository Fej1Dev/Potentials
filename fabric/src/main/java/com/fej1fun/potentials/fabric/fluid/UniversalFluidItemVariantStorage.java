package com.fej1fun.potentials.fabric.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.world.item.ItemStack;

public class UniversalFluidItemVariantStorage extends UniversalFluidVariantStorage implements UniversalFluidItemStorage {
    protected final ContainerItemContext context;
    public UniversalFluidItemVariantStorage(Storage<FluidVariant> fluidStorage, ContainerItemContext context) {
        super(fluidStorage);
        this.context = context;
    }


    @Override
    public ItemStack getContainer() {
        return context.getItemVariant().toStack();
    }
}
