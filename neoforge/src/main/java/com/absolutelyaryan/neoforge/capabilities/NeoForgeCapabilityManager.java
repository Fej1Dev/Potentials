package com.absolutelyaryan.neoforge.capabilities;

import com.absolutelyaryan.capabilities.CapabilityData;
import com.absolutelyaryan.capabilities.CapabilityManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class NeoForgeCapabilityManager implements CapabilityManager {


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
    public <X, Y> void registerSidedCapability(CapabilityData<X, Y> capabilityData, ResourceLocation identifier) {

    }

    @Override
    public <X, Y> void registerItemCapability(CapabilityData<X, Y> capabilityData, ResourceLocation identifier) {

    }

    @Override
    public <X, Y> void registerEntityCapability(CapabilityData<X, Y> capabilityData, ResourceLocation identifier) {

    }

    @Override
    public <X, Y> CapabilityData<X, Y> getCapabilityData(ResourceLocation identifier) {
        return null;
    }

    @Override
    public <X, Y> void registerForBlocks(ResourceLocation identifier, Block... blocks) {

    }

    @Override
    public <X, Y> void registerForBlockEntity(ResourceLocation identifier, BlockEntityType<?>... entities) {

    }

    @Override
    public <X, Y> void registerForItems(ResourceLocation identifier, Item... items) {

    }

    @Override
    public <X, Y> void registerForEntities(ResourceLocation identifier, EntityType<?>... entities) {

    }

    @Override
    public <X, Y> Object getCapability(ResourceLocation identifier, Object provider, Object context) {
        return null;
    }
}
