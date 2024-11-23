package com.absolutelyaryan.capabilities.types;

import com.absolutelyaryan.capabilities.types.providers.CapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface EntityCapabilityHolder<X,Y> {

    @Nullable X getCapability(Entity entity, Y context);
    void registerForEntity(CapabilityProvider<Entity, X, Y> provider, Supplier<EntityType<?>> entityType);
    ResourceLocation getIdentifier();




}
