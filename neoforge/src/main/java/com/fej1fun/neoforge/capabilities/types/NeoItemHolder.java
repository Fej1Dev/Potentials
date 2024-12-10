package com.fej1fun.neoforge.capabilities.types;

import com.fej1fun.capabilities.types.ItemCapabilityHolder;
import com.fej1fun.capabilities.types.providers.CapabilityProvider;
import com.fej1fun.neoforge.capabilities.Registerable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.HashMap;
import java.util.function.Supplier;

public class NeoItemHolder<X,Y> implements ItemCapabilityHolder<X,Y>, Registerable {
    private final ItemCapability<X,Y> itemCapability;
    private final HashMap<Supplier<Item>, CapabilityProvider<ItemStack, X, Y>> registeredItems = new HashMap<>();

    public NeoItemHolder(ItemCapability<X, Y> itemCapability) {
        this.itemCapability = itemCapability;
    }


    @Override
    public X getCapability(ItemStack stack, Y context) {
        return stack.getCapability(getItemCapability(), context);
    }

    @Override
    public void registerForItem(CapabilityProvider<ItemStack, X, Y> provider, Supplier<Item> item) {
        registeredItems.put(item, provider);
    }

//    public HashMap<Item, CapabilityProvider<ItemStack, X, Y>> getRegisteredItems() {
//        return registeredItems;
//    }

    @Override
    public ResourceLocation getIdentifier() {
        return itemCapability.name();
    }

    public ItemCapability<X, Y> getItemCapability() {
        return itemCapability;
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        //register item capabilities
        registeredItems.forEach((item, provider) -> event.registerItem(getItemCapability(), provider::getCapability, item.get()));
    }
}
