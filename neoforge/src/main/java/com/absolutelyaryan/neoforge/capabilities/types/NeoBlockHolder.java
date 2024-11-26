package com.absolutelyaryan.neoforge.capabilities.types;

import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.providers.BlockCapabilityProvider;
import com.absolutelyaryan.capabilities.types.providers.CapabilityProvider;
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
import java.util.function.Supplier;

public class NeoBlockHolder<X,Y> implements BlockCapabilityHolder<X,Y>, Registerable {
    private final BlockCapability<X,Y> blockCapability;
    private final HashMap<Supplier<Block>, BlockCapabilityProvider<X, Y>> registeredBlocks = new HashMap<>();
    private final HashMap<Supplier<BlockEntityType<?>>, CapabilityProvider<BlockEntity, X, Y>> registeredBlockEntities = new HashMap<>();

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
    public void registerForBlock(BlockCapabilityProvider<X, Y> provider, Supplier<Block> block) {
        registeredBlocks.put(block, provider);

    }

    @Override
    public void registerForBlockEntity(CapabilityProvider<BlockEntity, X, Y> provider, Supplier<BlockEntityType<?>> blockEntityType) {
        registeredBlockEntities.put(blockEntityType, provider);

    }

//    public HashMap<Block, BlockCapabilityProvider<X, Y>> getRegisteredBlocks() {
//        return registeredBlocks;
//    }
//
//    public HashMap<BlockEntityType<?>, CapabilityProvider<BlockEntity, X, Y>> getRegisteredBlockEntities() {
//        return registeredBlockEntities;
//    }

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
        registeredBlocks.forEach((block, provider) -> event.registerBlock(getBlockCapability(), provider::getCapability, block.get()));

        //register block entity capabilities
        registeredBlockEntities.forEach((type, provider) -> event.registerBlockEntity(getBlockCapability(), type.get(), provider::getCapability));
    }
}
