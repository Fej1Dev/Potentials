package com.fej1fun.fabric.capabilities.types;

import com.fej1fun.capabilities.types.EntityCapabilityHolder;
import com.fej1fun.capabilities.types.providers.CapabilityProvider;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FabricEntityProviderHolder<X,Y> implements EntityCapabilityHolder<X,Y> {
    private final EntityApiLookup<X,Y> entityApiLookup;

    public FabricEntityProviderHolder(EntityApiLookup<X,Y> entityApiLookup) {
        this.entityApiLookup = entityApiLookup;
    }


    @Override
    public @Nullable X getCapability(Entity entity, Y context) {
        return entityApiLookup.find(entity, context);
    }

    @Override
    public void registerForEntity(CapabilityProvider<Entity, X, Y> provider, Supplier<EntityType<?>> entityType) {
        entityApiLookup.registerForType(provider::getCapability, entityType.get());

    }

    @Override
    public ResourceLocation getIdentifier() {
        return entityApiLookup.getId();
    }
}
