package com.absolutelyaryan.fabric.capabilities.types;

import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.BlockCapabilityProvider;
import com.absolutelyaryan.capabilities.types.CapabilityProvider;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

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
    public void registerForBlocks(BlockCapabilityProvider<X, Y> provider, Block... blocks) {
        for(Block block: blocks){
            blockApiLookup.registerForBlocks(provider::getCapability, block);
        }
    }


    @Override
    public void registerForBlockEntity(CapabilityProvider<BlockEntity, X, Y> provider, BlockEntityType<?>... entities) {
        for(BlockEntityType<?> entity: entities){
            blockApiLookup.registerForBlockEntity(provider::getCapability, entity);
        }
    }

    @Override
    public ResourceLocation getIdentifier() {
        return blockApiLookup.getId();
    }
}
