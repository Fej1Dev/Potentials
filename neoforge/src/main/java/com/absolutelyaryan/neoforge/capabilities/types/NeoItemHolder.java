package com.absolutelyaryan.neoforge.capabilities.types;

import com.absolutelyaryan.capabilities.types.CapabilityProvider;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.HashMap;

public class NeoItemHolder<X,Y> implements ItemCapabilityHolder<X,Y>, Registerable {
    private final ItemCapability<X,Y> itemCapability;
    private final HashMap<Item, CapabilityProvider<ItemStack, X, Y>> registeredItems = new HashMap<>();

    public NeoItemHolder(ItemCapability<X, Y> itemCapability) {
        this.itemCapability = itemCapability;
    }


    @Override
    public X getCapability(ItemStack item, Y context) {
        return itemCapability.getCapability(item, context);
    }

    @Override
    public void registerForItems(CapabilityProvider<ItemStack, X, Y> provider, Item... items) {
        for(Item item: items){
            registeredItems.put(item, provider);
        }
    }

    public HashMap<Item, CapabilityProvider<ItemStack, X, Y>> getRegisteredItems() {
        return registeredItems;
    }

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
        registeredItems.forEach((item, provider) -> event.registerItem(getItemCapability(), provider::getCapability, item));
    }
}
