package com.absolutelyaryan.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
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


    <X, Y> void registerSidedCapability(CapabilityData<X, Y> capabilityData, ResourceLocation identifier);
    <X, Y> void registerItemCapability(CapabilityData<X, Y> capabilityData, ResourceLocation identifier);
    <X, Y> void registerEntityCapability(CapabilityData<X, Y> capabilityData, ResourceLocation identifier);


    <X,Y> CapabilityData<X,Y> getCapabilityData(ResourceLocation identifier);
    <X,Y> void registerForBlocks(ResourceLocation identifier, Block... blocks);
    <X,Y> void registerForBlockEntity(ResourceLocation identifier, BlockEntityType<?>... entities);
    <X,Y> void registerForItems(ResourceLocation identifier, Item... items);
    <X,Y> void registerForEntities(ResourceLocation identifier, EntityType<?>... entities);

    <X, Y> Object getCapability(ResourceLocation identifier, Object provider, Object context);
}
