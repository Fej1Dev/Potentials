package com.absolutelyaryan.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

public interface EntityCapabilityHolder<X,Y> {

    @Nullable X getCapability(Entity entity, Y context);
    void registerForEntities(CapabilityProvider<Entity, X, Y> provider, EntityType<?>... entities);
    ResourceLocation getIdentifier();




}
