package com.absolutelyaryan.capabilities.types;

import com.absolutelyaryan.capabilities.types.providers.CapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface ItemCapabilityHolder<X,Y> {

   @Nullable X getCapability(ItemStack stack, Y context);
   void registerForItem(CapabilityProvider<ItemStack, X, Y> provider, Supplier<Item> item);
   ResourceLocation getIdentifier();

}
