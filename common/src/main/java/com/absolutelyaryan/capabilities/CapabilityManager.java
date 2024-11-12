package com.absolutelyaryan.capabilities;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface CapabilityManager {
    void registerBlockEnergy(Block block);
    void registerItemEnergy(Item item);
    void registerBlockEntityEnergy(BlockEntityType<?> entity);
    void registerBlockFluid(Block block);
    void registerItemFluid(Item item);
    void registerBlockEntityFluid(BlockEntityType<?> entity);
}
