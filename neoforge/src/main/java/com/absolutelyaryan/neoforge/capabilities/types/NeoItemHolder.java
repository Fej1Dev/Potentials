package com.absolutelyaryan.neoforge.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.ItemCapability;

import java.util.HashMap;

public class NeoItemHolder<X,Y> implements ItemCapabilityHolder<X,Y> {
    private final ItemCapability<X,Y> itemCapability;
    private final HashMap<Item, CapabilityProvider> registeredItems = new HashMap<>();

    public NeoItemHolder(ItemCapability<X, Y> itemCapability) {
        this.itemCapability = itemCapability;
    }


    @Override
    public X getCapability(ItemStack item, Y context) {
        return itemCapability.getCapability(item, context);
    }

    @Override
    public void registerForItems(CapabilityProvider provider, Item... items) {
        for(Item item: items){
            registeredItems.put(item, provider);
        }
    }

    public HashMap<Item, CapabilityProvider> getRegisteredItems() {
        return registeredItems;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return itemCapability.name();
    }

    public ItemCapability<X, Y> getItemCapability() {
        return itemCapability;
    }
}
