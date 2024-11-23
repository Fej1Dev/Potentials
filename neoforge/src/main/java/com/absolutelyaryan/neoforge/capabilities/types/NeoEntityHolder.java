package com.absolutelyaryan.neoforge.capabilities.types;

import com.absolutelyaryan.capabilities.types.providers.CapabilityProvider;
import com.absolutelyaryan.capabilities.types.EntityCapabilityHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.HashMap;
import java.util.function.Supplier;

public class NeoEntityHolder<X,Y> implements EntityCapabilityHolder<X,Y>, Registerable {
    private final HashMap<Supplier<EntityType<?>>, CapabilityProvider<Entity, X, Y>> registeredEntities = new HashMap<>();
    private final EntityCapability<X,Y> entityCapability;

    public NeoEntityHolder(EntityCapability<X, Y> entityCapability) {
        this.entityCapability = entityCapability;
        registerSelf();
    }

    @Override
    public X getCapability(Entity entity, Y context) {
        return entity.getCapability(getEntityCapability(), context);
    }

    @Override
    public void registerForEntity(CapabilityProvider<Entity, X, Y> provider, Supplier<EntityType<?>> entityType) {
        registeredEntities.put(entityType, provider);
    }

//    public HashMap<EntityType<?>, CapabilityProvider<Entity, X, Y>> getRegisteredEntities() {
//        return registeredEntities;
//    }

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
        registeredEntities.forEach((type, provider) -> event.registerEntity(getEntityCapability(), type.get(), provider::getCapability));
    }
}
