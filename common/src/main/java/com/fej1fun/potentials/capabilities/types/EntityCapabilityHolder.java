package com.fej1fun.potentials.capabilities.types;

import com.fej1fun.potentials.capabilities.types.providers.CapabilityProvider;
import com.fej1fun.potentials.platform.HolderHelper;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface EntityCapabilityHolder<X,Y> {

    static <X,Y> EntityCapabilityHolder<X,Y> create(Class<X> apiClass, Class<Y> contextClass, Identifier identifier)
        {return HolderHelper.createEntity(apiClass, contextClass, identifier);}

    static <X> EntityCapabilityHolder<X, @Nullable Direction> createSided(Class<X> apiClass, Identifier identifier)
        {return create(apiClass, Direction.class, identifier);}

    static <X> EntityCapabilityHolder<X, Void> createVoid(Class<X> apiClass, Identifier identifier)
        {return create(apiClass, void.class, identifier);}


    @Nullable X getCapability(Entity entity, Y context);
    <T extends Entity> void registerForEntity(CapabilityProvider<Entity, X, Y> provider, Supplier<EntityType<T>> entityType);
    Identifier getIdentifier();

}
