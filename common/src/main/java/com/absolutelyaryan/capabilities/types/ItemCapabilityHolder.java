package com.absolutelyaryan.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ItemCapabilityHolder<X,Y> {

   @Nullable X getCapability(ItemStack stack, Y context);
   void registerForItems(CapabilityProvider<ItemStack, X, Y> provider, Item... items);
   ResourceLocation getIdentifier();

}
