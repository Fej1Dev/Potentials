package com.fej1fun.potentials.capabilities.types;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@ApiStatus.Internal
public interface NoProviderItemCapabilityHolder<X,Y> {

   @Nullable X getCapability(ItemStack stack);
   void registerForItem(Supplier<Item> item);
   ResourceLocation getIdentifier();

}
