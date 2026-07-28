package com.fej1fun.potentials.fabric.capabilities.types;

import com.fej1fun.potentials.capabilities.types.EntityCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.providers.CapabilityProvider;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
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
    public <T extends Entity> void registerForEntity(CapabilityProvider<Entity, X, Y> provider, Supplier<EntityType<T>> entityType) {
        entityApiLookup.registerForType(provider::getCapability, entityType.get());
    }

    @Override
    public Identifier getIdentifier() {
        return entityApiLookup.getId();
    }
}
