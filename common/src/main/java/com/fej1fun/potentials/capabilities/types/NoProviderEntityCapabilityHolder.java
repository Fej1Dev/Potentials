package com.fej1fun.potentials.capabilities.types;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface NoProviderEntityCapabilityHolder<X, Y> {

    @Nullable X getCapability(Entity entity, Y context);
    void registerForEntity(Supplier<EntityType<? extends Entity>> entity);
    ResourceLocation getIdentifier();

}
