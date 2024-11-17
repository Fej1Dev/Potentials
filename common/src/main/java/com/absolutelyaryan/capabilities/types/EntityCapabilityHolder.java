package com.absolutelyaryan.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public interface EntityCapabilityHolder<X,Y> {

    X getCapability(Entity entity, Y context);
    void registerForEntities(CapabilityProvider provider, EntityType<?>... entities);
    ResourceLocation getIdentifier();




}
