package com.absolutelyaryan.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ItemCapabilityHolder<X,Y> {

    X getCapability(ItemStack stack, Y context);
    void registerForItems(CapabilityProvider provider, Item... items);
   ResourceLocation getIdentifier();




}
