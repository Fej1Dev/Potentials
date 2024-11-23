package com.absolutelyaryan.fabric.capabilities.types;

import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.providers.BlockCapabilityProvider;
import com.absolutelyaryan.capabilities.types.providers.CapabilityProvider;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FabricBlockProviderHolder<X,Y> implements BlockCapabilityHolder<X,Y> {

    private final BlockApiLookup<X,Y> blockApiLookup;
    public FabricBlockProviderHolder(BlockApiLookup<X, Y> blockApiLookup) {
        this.blockApiLookup = blockApiLookup;
    }

    @Override
    public @Nullable X getCapability(Level level, BlockPos pos, Y context) {
       return blockApiLookup.find(level, pos, context);
    }

    @Override
    public @Nullable X getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Y context) {
        return blockApiLookup.find(level, pos, state, blockEntity, context);
    }

    @Override
    public void registerForBlocks(BlockCapabilityProvider<X, Y> provider, Supplier<Block> block) {
        blockApiLookup.registerForBlocks(provider::getCapability, block.get());
    }

    @Override
    public void registerForBlockEntity(CapabilityProvider<BlockEntity, X, Y> provider, Supplier<BlockEntityType<?>> blockEntityType) {
        blockApiLookup.registerForBlockEntity(provider::getCapability, blockEntityType.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return blockApiLookup.getId();
    }
}
