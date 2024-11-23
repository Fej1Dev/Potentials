package com.absolutelyaryan.capabilities.types;

import com.absolutelyaryan.capabilities.types.providers.BlockCapabilityProvider;
import com.absolutelyaryan.capabilities.types.providers.CapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface BlockCapabilityHolder<X,Y> {

    @Nullable X getCapability(Level level, BlockPos pos, Y context);
    @Nullable X getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Y context);
    void registerForBlock(BlockCapabilityProvider<X,Y> provider, Supplier<Block> block);
    void registerForBlockEntity(CapabilityProvider<BlockEntity, X, Y> provider, Supplier<BlockEntityType<?>> blockEntityType);
    ResourceLocation getIdentifier();

}
