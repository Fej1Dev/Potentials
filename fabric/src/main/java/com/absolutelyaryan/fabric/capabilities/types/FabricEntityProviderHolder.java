package com.absolutelyaryan.fabric.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import com.absolutelyaryan.capabilities.types.EntityCapabilityHolder;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

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
    @SuppressWarnings("unchecked")
    public void registerForEntities(CapabilityProvider<Entity> provider, EntityType<?>... entities) {
        for(EntityType<?> entityType: entities){
            entityApiLookup.registerForType((entity, context) -> (X) provider.getCapability(entity, context), entityType);
        }
    }

    @Override
    public ResourceLocation getIdentifier() {
        return null;
    }
}
