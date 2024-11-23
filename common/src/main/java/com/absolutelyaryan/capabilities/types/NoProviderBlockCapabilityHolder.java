package com.absolutelyaryan.capabilities.types;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public interface NoProviderBlockCapabilityHolder<X, Y> {

    @Nullable X getCapability(Level level, BlockPos pos, Y context);
    @Nullable X getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Y context);
    void registerForBlocks(Block... blocks);
    void registerForBlockEntity(BlockEntityType<?>... entities);
    ResourceLocation getIdentifier();

}
