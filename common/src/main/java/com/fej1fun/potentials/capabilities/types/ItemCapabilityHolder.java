package com.fej1fun.potentials.capabilities.types;

import com.fej1fun.potentials.capabilities.types.providers.CapabilityProvider;
import com.fej1fun.potentials.platform.HolderHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface ItemCapabilityHolder<X,Y> {

   static <X,Y> ItemCapabilityHolder<X,Y> create(Class<X> apiClass, Class<Y> contextClass, Identifier identifier)
      {return HolderHelper.createItem(apiClass, contextClass, identifier);}

   static <X> ItemCapabilityHolder<X, Void> createVoid(Class<X> apiClass, Identifier identifier)
      {return create(apiClass, void.class, identifier);}


   @Nullable X getCapability(ItemStack stack, Y context);
   void registerForItem(CapabilityProvider<ItemStack, X, Y> provider, Supplier<Item> item);
   Identifier getIdentifier();

}
