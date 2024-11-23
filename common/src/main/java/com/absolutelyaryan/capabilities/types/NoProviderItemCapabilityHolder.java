package com.absolutelyaryan.capabilities.types;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public interface NoProviderItemCapabilityHolder<X,Y> {

   @Nullable X getCapability(ItemStack stack, Y context);
   void registerForItems(Item... items);
   ResourceLocation getIdentifier();

}
