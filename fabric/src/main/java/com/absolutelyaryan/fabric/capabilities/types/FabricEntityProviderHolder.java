package com.absolutelyaryan.fabric.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import com.absolutelyaryan.capabilities.types.EntityCapabilityHolder;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class FabricEntityProviderHolder<X,Y> implements EntityCapabilityHolder<X,Y> {
    private final EntityApiLookup<X,Y> entityApiLookup;

    public FabricEntityProviderHolder(EntityApiLookup<X,Y> entityApiLookup) {
        this.entityApiLookup = entityApiLookup;
    }


    @Override
    public X getCapability(Entity entity, Y context) {
        return entityApiLookup.find(entity, context);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerForEntities(CapabilityProvider provider, EntityType<?>... entities) {
        for(EntityType<?> entity: entities){
            entityApiLookup.registerForType((entity1, context) -> (X) provider.getCapability(entity1, context), entity);
        }
    }

    @Override
    public ResourceLocation getIdentifier() {
        return null;
    }
}
