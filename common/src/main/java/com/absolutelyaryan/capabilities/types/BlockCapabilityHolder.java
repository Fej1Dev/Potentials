package com.absolutelyaryan.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface BlockCapabilityHolder<X,Y> {

    @Nullable X getCapability(Level level, BlockPos pos, Y context);
    @Nullable X getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Y context);
    void registerForBlocks(BlockCapabilityProvider<X,Y> provider, Block... blocks);
    void registerForBlockEntity(CapabilityProvider<BlockEntity, X, Y> provider, BlockEntityType<?>... entities);
    ResourceLocation getIdentifier();

    @FunctionalInterface
    interface BlockCapabilityProvider<X,Y> {
        @Nullable X getCapability(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, Y context);
    }
}
