package com.fej1fun.potentials.capabilities.types;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

@ApiStatus.Internal
public interface NoProviderFluidBlockCapabilityHolder<X, Y> {

    @Nullable List<X> getCapability(Level level, BlockPos pos, Y context);
    @Nullable List<X> getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Y context);
    void registerForBlock(Supplier<Block> block);
    void registerForBlockEntity(Supplier<BlockEntityType<?>> blockEntityType);
    ResourceLocation getIdentifier();

}
