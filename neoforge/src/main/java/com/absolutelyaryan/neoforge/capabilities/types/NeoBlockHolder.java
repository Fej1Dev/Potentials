package com.absolutelyaryan.neoforge.capabilities.types;

import com.absolutelyaryan.capabilities.types.BlockCapabilityProvider;
import com.absolutelyaryan.capabilities.types.CapabilityProvider;
import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.HashMap;

public class NeoBlockHolder<X,Y> implements BlockCapabilityHolder<X,Y>, Registerable {
    private final BlockCapability<X,Y> blockCapability;
    private final HashMap<Block, BlockCapabilityProvider<X, Y>> registeredBlocks = new HashMap<>();
    private final HashMap<BlockEntityType<?>, CapabilityProvider<BlockEntity, X, Y>> registeredBlockEntities = new HashMap<>();

    public NeoBlockHolder(BlockCapability<X, Y> blockCapability) {
        this.blockCapability = blockCapability;
        registerSelf();
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
    public void registerForBlocks(BlockCapabilityProvider<X, Y> provider, Block... blocks) {
        for(Block block: blocks){
            registeredBlocks.put(block, provider);
        }
    }

    @Override
    public void registerForBlockEntity(CapabilityProvider<BlockEntity, X, Y> provider, BlockEntityType<?>... entities) {
        for(BlockEntityType<?> entity: entities){
            registeredBlockEntities.put(entity, provider);
        }
    }

    public HashMap<Block, BlockCapabilityProvider<X, Y>> getRegisteredBlocks() {
        return registeredBlocks;
    }

    public HashMap<BlockEntityType<?>, CapabilityProvider<BlockEntity, X, Y>> getRegisteredBlockEntities() {
        return registeredBlockEntities;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return blockCapability.name();
    }

    public BlockCapability<X, Y> getBlockCapability() {
        return blockCapability;
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        //register block capabilities
        registeredBlocks.forEach((block, provider) -> event.registerBlock(getBlockCapability(), provider::getCapability, block));

        //register block entity capabilities
        registeredBlockEntities.forEach((type, provider) -> event.registerBlockEntity(getBlockCapability(), type, provider::getCapability));
    }
}
