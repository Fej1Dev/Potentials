package com.absolutelyaryan.platform.fabric;

import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.EntityCapabilityHolder;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import com.absolutelyaryan.fabric.capabilities.types.FabricBlockProviderHolder;
import com.absolutelyaryan.fabric.capabilities.types.FabricEntityProviderHolder;
import com.absolutelyaryan.fabric.capabilities.types.FabricItemProviderHolder;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.minecraft.resources.ResourceLocation;

public class HolderHelperImpl {

    public static <X,Y> BlockCapabilityHolder<X,Y> createBlock(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
    {
        return new FabricBlockProviderHolder<>(BlockApiLookup.get(identifier, apiClass, contextClass));
    }

    public static <X,Y> EntityCapabilityHolder<X,Y> createEntity(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
    {
        return new FabricEntityProviderHolder<>(EntityApiLookup.get(identifier, apiClass, contextClass));
    }

    public static <X,Y> ItemCapabilityHolder<X,Y> createItem(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
    {
        return new FabricItemProviderHolder<>(ItemApiLookup.get(identifier, apiClass, contextClass));
    }

}
