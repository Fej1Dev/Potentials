package com.absolutelyaryan.neoforge.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import com.absolutelyaryan.capabilities.types.EntityCapabilityHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.EntityCapability;

import java.util.HashMap;

public class NeoEntityHolder<X,Y> implements EntityCapabilityHolder<X,Y> {
    private final HashMap<EntityType<?>, CapabilityProvider> registeredEntities = new HashMap<>();
    private final EntityCapability<X,Y> entityCapability;

    public NeoEntityHolder(EntityCapability<X, Y> entityCapability) {
        this.entityCapability = entityCapability;
    }

    @Override
    public X getCapability(Entity entity, Y context) {
        return entityCapability.getCapability(entity, context);
    }

    @Override
    public void registerForEntities(CapabilityProvider provider, EntityType<?>... entities) {
        for(EntityType<?> entity: entities){
            registeredEntities.put(entity, provider);
        }
    }

    public HashMap<EntityType<?>, CapabilityProvider> getRegisteredEntities() {
        return registeredEntities;
    }

    public EntityCapability<X, Y> getEntityCapability() {
        return entityCapability;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return entityCapability.name();
    }
}
