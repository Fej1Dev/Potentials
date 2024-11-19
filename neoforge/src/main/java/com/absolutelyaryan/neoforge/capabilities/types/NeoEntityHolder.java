package com.absolutelyaryan.neoforge.capabilities.types;

import com.absolutelyaryan.capabilities.types.CapabilityProvider;
import com.absolutelyaryan.capabilities.types.EntityCapabilityHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.HashMap;

public class NeoEntityHolder<X,Y> implements EntityCapabilityHolder<X,Y>, Registerable {
    private final HashMap<EntityType<?>, CapabilityProvider<Entity, X, Y>> registeredEntities = new HashMap<>();
    private final EntityCapability<X,Y> entityCapability;

    public NeoEntityHolder(EntityCapability<X, Y> entityCapability) {
        this.entityCapability = entityCapability;
        registerSelf();
    }

    @Override
    public X getCapability(Entity entity, Y context) {
        return entityCapability.getCapability(entity, context);
    }

    @Override
    public void registerForEntities(CapabilityProvider<Entity, X, Y> provider, EntityType<?>... entities) {
        for(EntityType<?> entity: entities){
            registeredEntities.put(entity, provider);
        }
    }

    public HashMap<EntityType<?>, CapabilityProvider<Entity, X, Y>> getRegisteredEntities() {
        return registeredEntities;
    }

    public EntityCapability<X, Y> getEntityCapability() {
        return entityCapability;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return entityCapability.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        //register entity capabilities
        registeredEntities.forEach((type, provider) -> event.registerEntity(getEntityCapability(), type, provider::getCapability));
    }
}
