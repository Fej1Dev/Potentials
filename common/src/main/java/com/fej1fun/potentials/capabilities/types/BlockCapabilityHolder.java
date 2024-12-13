package com.fej1fun.potentials.capabilities.types;

import com.fej1fun.potentials.capabilities.types.providers.BlockCapabilityProvider;
import com.fej1fun.potentials.capabilities.types.providers.CapabilityProvider;
import com.fej1fun.potentials.platform.HolderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface BlockCapabilityHolder<X,Y> {

    static <X,Y> BlockCapabilityHolder<X,Y> create(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
        {return HolderHelper.createBlock(apiClass, contextClass, identifier);}

    static <X> BlockCapabilityHolder<X, @Nullable Direction> createSided(Class<X> apiClass, ResourceLocation identifier)
        {return create(apiClass, Direction.class, identifier);}

    static <X> BlockCapabilityHolder<X, Void> createVoid(Class<X> apiClass, ResourceLocation identifier)
        {return create(apiClass, void.class, identifier);}


    @Nullable X getCapability(Level level, BlockPos pos, Y context);
    @Nullable X getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Y context);
    void registerForBlock(BlockCapabilityProvider<X,Y> provider, Supplier<Block> block);
    void registerForBlockEntity(CapabilityProvider<BlockEntity, X, Y> provider, Supplier<BlockEntityType<?>> blockEntityType);
    ResourceLocation getIdentifier();

}
