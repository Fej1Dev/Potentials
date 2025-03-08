package com.fej1fun.potentials.fluid;

import net.minecraft.world.item.ItemStack;

public interface UniversalFluidItemStorage extends UniversalFluidStorage {
    /**
    * look at {@link net.neoforged.neoforge.fluids.capability.IFluidHandlerItem} for detailed instructions to why this is necessary
    * */
    ItemStack getContainer();
}
