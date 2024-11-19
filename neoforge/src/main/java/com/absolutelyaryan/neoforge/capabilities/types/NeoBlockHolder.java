package com.absolutelyaryan.neoforge.capabilities.types;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.neoforge.ReflectionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;

import java.util.HashMap;

public class NeoBlockHolder<X,Y> implements BlockCapabilityHolder<X,Y> {
    private final BlockCapability<X,Y> blockCapability;
    private final HashMap<Block, BlockCapabilityProvider> registeredBlocks = new HashMap<>();
    private final HashMap<BlockEntityType<?>, CapabilityProvider<BlockEntity>> registeredBlockEntities = new HashMap<>();

    public NeoBlockHolder(BlockCapability<X, Y> blockCapability) {
        this.blockCapability = blockCapability;
    }

    @Override
    public X getCapability(Level level, BlockPos pos, Y context) {
       return level.getCapability(blockCapability, pos, context);
    }

    @Override
    public X getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Y context) {
        return level.getCapability(blockCapability, pos, state, blockEntity, context);
    }

    @Override
    public void registerForBlocks(BlockCapabilityProvider provider, Block... blocks) {
        for(Block block: blocks){
            ReflectionUtil.registerBlock(block, getBlockCapability(), provider::getCapability);
            registeredBlocks.put(block, provider);
        }
    }

    @Override
    public void registerForBlockEntity(CapabilityProvider<BlockEntity> provider, BlockEntityType<?>... entities) {
        for(BlockEntityType<?> entity: entities){
            ReflectionUtil.registerBlockEntity(entity, getBlockCapability(), provider);
            registeredBlockEntities.put(entity, provider);
        }
    }

    public HashMap<Block, BlockCapabilityProvider> getRegisteredBlocks() {
        return registeredBlocks;
    }

    public HashMap<BlockEntityType<?>, CapabilityProvider<BlockEntity>> getRegisteredBlockEntities() {
        return registeredBlockEntities;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return blockCapability.name();
    }

    public BlockCapability<X, Y> getBlockCapability() {
        return blockCapability;
    }
}
