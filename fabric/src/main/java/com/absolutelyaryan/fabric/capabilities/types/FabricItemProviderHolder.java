package com.absolutelyaryan.fabric.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

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
    @SuppressWarnings("unchecked")
    public void registerForItems(CapabilityProvider<ItemStack> provider, Item... items) {
        for(Item item: items){
            itemApiLookup.registerForItems((stack, context) -> (X) provider.getCapability(stack, context), item);
        }
    }

    @Override
    public ResourceLocation getIdentifier() {
        return itemApiLookup.getId();
    }
}
