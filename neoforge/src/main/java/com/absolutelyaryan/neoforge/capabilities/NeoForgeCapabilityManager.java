package com.absolutelyaryan.neoforge.capabilities;

import com.absolutelyaryan.capabilities.CapabilityManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.BlockCapability;

import java.util.HashMap;

public class NeoForgeCapabilityManager implements CapabilityManager {


    private final HashMap<BlockCapability<?,?>, Class<?>> blockApiLookupHashMap = new HashMap<>();


    @Override
    public void registerBlockEnergy(Block block) {

    }

    @Override
    public void registerItemEnergy(Item item) {

    }

    @Override
    public void registerBlockEntityEnergy(BlockEntityType<?> entity) {

    }

    @Override
    public void registerBlockFluid(Block block) {

    }

    @Override
    public void registerItemFluid(Item item) {

    }

    @Override
    public void registerBlockEntityFluid(BlockEntityType<?> entity) {

    }

    @Override
    public <T, C> void registerCapability(Class<T> capabilityClass, Class<C> contextClass, ResourceLocation resourceLocation) {
        BlockCapability<T, C> blockCapability = BlockCapability.create(resourceLocation, capabilityClass, contextClass);
        blockApiLookupHashMap.put(blockCapability, capabilityClass);
    }

}
