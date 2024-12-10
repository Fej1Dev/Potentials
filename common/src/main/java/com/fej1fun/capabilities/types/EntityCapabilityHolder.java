package com.fej1fun.capabilities.types;

import com.fej1fun.capabilities.types.providers.CapabilityProvider;
import com.fej1fun.platform.HolderHelper;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface EntityCapabilityHolder<X,Y> {

    static <X,Y> EntityCapabilityHolder<X,Y> create(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
        {return HolderHelper.createEntity(apiClass, contextClass, identifier);}

    static <X> EntityCapabilityHolder<X, @Nullable Direction> createSided(Class<X> apiClass, ResourceLocation identifier)
        {return create(apiClass, Direction.class, identifier);}

    static <X> EntityCapabilityHolder<X, Void> createVoid(Class<X> apiClass, ResourceLocation identifier)
        {return create(apiClass, void.class, identifier);}


    @Nullable X getCapability(Entity entity, Y context);
    void registerForEntity(CapabilityProvider<Entity, X, Y> provider, Supplier<EntityType<?>> entityType);
    ResourceLocation getIdentifier();

}
