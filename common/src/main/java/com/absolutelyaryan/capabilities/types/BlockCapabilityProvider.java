package com.absolutelyaryan.capabilities.types;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface BlockCapabilityProvider<X,Y> {
    @Nullable X getCapability(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, Y context);
}
