package com.fej1fun.potentials.capabilities.types;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface NoProviderEntityCapabilityHolder<X, Y> {

    @Nullable X getCapability(Entity entity, Y context);
    <T extends Entity> void registerForEntity(Supplier<EntityType<T>> entity);
    Identifier getIdentifier();

}
