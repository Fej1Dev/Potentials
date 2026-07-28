package com.fej1fun.potentials.capabilities.types;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@Deprecated
public interface NoProviderFluidItemCapabilityHolder<X,Y> {

   @Nullable X getCapability(ItemStack stack);
   <T extends Item> void registerForItem(Supplier<T> item);
   Identifier getIdentifier();

}
