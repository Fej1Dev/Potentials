package com.absolutelyaryan.neoforge.capabilities;

import com.absolutelyaryan.capabilities.CapabilityManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;


//JUST FOR EXISTING, DONT HAVE ANY PURPOSE BESIDES AVOIDING NULL WHEN REGISTERING FOR FABRIC BUT DO NOT DELETE
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
}
