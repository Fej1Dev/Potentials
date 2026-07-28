package com.fej1fun.potentials.capabilities.types;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@Deprecated
public interface NoProviderFluidBlockCapabilityHolder<X, Y> {

    @Nullable X getCapability(Level level, BlockPos pos, Y context);
    @Nullable X getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Y context);
    <T extends Block> void registerForBlock(Supplier<T> block);
    <T extends BlockEntity> void registerForBlockEntity(Supplier<BlockEntityType<T>> blockEntityType);
    Identifier getIdentifier();

}
