package com.absolutelyaryan.fabric.capabilities.types;

import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.CapabilityProvider;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FabricBlockProviderHolder<X,Y> implements BlockCapabilityHolder<X,Y> {

    private final BlockApiLookup<X,Y> blockApiLookup;
    public FabricBlockProviderHolder(BlockApiLookup<X, Y> blockApiLookup) {
        this.blockApiLookup = blockApiLookup;
    }

    @Override
    public X getCapability(Level level, BlockPos pos, Y context) {
       return blockApiLookup.find(level, pos, context);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void registerForBlocks(CapabilityProvider provider, Block... blocks) {
        for(Block block: blocks){
            blockApiLookup.registerForBlocks((level, pos, state, blockEntity, side) -> {
                if(blockApiLookup.apiClass().isInstance(blockEntity)){
                    return (X) provider.getCapability(blockEntity, side);
                }
                return null;
            }, block);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public void registerForBlockEntity(CapabilityProvider provider, BlockEntityType<?>... entities) {
        for(BlockEntityType<?> entity: entities){
            blockApiLookup.registerForBlockEntity((blockEntity, direction) -> (X) provider.getCapability(blockEntity, direction), entity);
        }
    }

    @Override
    public ResourceLocation getIdentifier() {
        return blockApiLookup.getId();
    }
}
