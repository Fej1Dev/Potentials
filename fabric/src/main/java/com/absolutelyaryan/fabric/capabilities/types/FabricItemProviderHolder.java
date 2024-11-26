package com.absolutelyaryan.fabric.capabilities.types;

import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import com.absolutelyaryan.capabilities.types.providers.CapabilityProvider;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FabricItemProviderHolder<X,Y> implements ItemCapabilityHolder<X,Y> {

    private final ItemApiLookup<X,Y> itemApiLookup;

    public FabricItemProviderHolder(ItemApiLookup<X, Y> itemApiLookup) {
        this.itemApiLookup = itemApiLookup;
    }

    @Override
    public @Nullable X getCapability(ItemStack stack, Y context) {
        return itemApiLookup.find(stack, context);
    }

    @Override
    public void registerForItem(CapabilityProvider<ItemStack, X, Y> provider, Supplier<Item> item) {
        itemApiLookup.registerForItems(provider::getCapability, item.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return itemApiLookup.getId();
    }
}
