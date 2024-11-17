package com.absolutelyaryan.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface BlockCapabilityHolder<X,Y> {

    X getCapability(Level level, BlockPos pos, Y context);
    void registerForBlocks(CapabilityProvider provider, Block... blocks);
    void registerForBlockEntity(CapabilityProvider provider, BlockEntityType<?>... entities);
    ResourceLocation getIdentifier();

}
