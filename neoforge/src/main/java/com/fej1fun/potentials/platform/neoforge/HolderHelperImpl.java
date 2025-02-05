package com.fej1fun.potentials.platform.neoforge;

import com.fej1fun.potentials.capabilities.types.BlockCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.EntityCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.ItemCapabilityHolder;
import com.fej1fun.potentials.neoforge.capabilities.types.NeoBlockHolder;
import com.fej1fun.potentials.neoforge.capabilities.types.NeoEntityHolder;
import com.fej1fun.potentials.neoforge.capabilities.types.NeoItemHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class HolderHelperImpl {

    public static <X,Y> BlockCapabilityHolder<X,Y> createBlock(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
    {
        return new NeoBlockHolder<>(BlockCapability.create(identifier, apiClass, contextClass));
    }

    public static <X,Y> EntityCapabilityHolder<X,Y> createEntity(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
    {
        return new NeoEntityHolder<>(EntityCapability.create(identifier, apiClass, contextClass));
    }

    public static <X,Y> ItemCapabilityHolder<X,Y> createItem(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
    {
        return new NeoItemHolder<>(ItemCapability.create(identifier, apiClass, contextClass));
    }

}
