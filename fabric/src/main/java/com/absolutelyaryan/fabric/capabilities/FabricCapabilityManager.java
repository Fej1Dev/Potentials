package com.absolutelyaryan.fabric.capabilities;

import com.absolutelyaryan.capabilities.CapabilityManager;
import com.absolutelyaryan.fabric.energy.FabricEnergyStorage;
import com.absolutelyaryan.fabric.fluid.SingleVariantTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import team.reborn.energy.api.EnergyStorage;

public class FabricCapabilityManager implements CapabilityManager {
    @Override
    public void registerBlockEnergy(Block block) {
        EnergyStorage.SIDED.registerForBlocks(
                (level, pos, state, blockEntity, side) -> {
                    if (blockEntity instanceof EnergyProvider.BLOCK energyBlock){
                        return new FabricEnergyStorage(energyBlock.getEnergy(side));
                    }
                    return null;
                },
                block
        );
    }

    @Override
    public void registerItemEnergy(Item item) {
        EnergyStorage.ITEM.registerForItems((stack, containerItemContext) -> {
            if (stack.is(item) && item instanceof EnergyProvider.ITEM energyItem)
                return new FabricEnergyStorage(energyItem.getEnergy(stack));
            return null;
        }, item);
    }

    @Override
    public void registerBlockEntityEnergy(BlockEntityType<?> entity) {
        EnergyStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> {
            if (blockEntity instanceof EnergyProvider.BLOCK energyBlock){
                return new FabricEnergyStorage(energyBlock.getEnergy(direction));
            }
            return null;
        }), entity);
    }

    @Override
    public void registerBlockFluid(Block block) {
        FluidStorage.SIDED.registerForBlocks(
                (level, pos, state, blockEntity, side) -> {
                    if (blockEntity instanceof FluidProvider.BLOCK fluidBlock){
                        return new SingleVariantTank(fluidBlock.getFluidTank(side));
                    }
                    return null;
                },
                block
        );
    }

    @Override
    public void registerItemFluid(Item item) {
        FluidStorage.ITEM.registerForItems((stack, containerItemContext) -> {
            if (stack.is(item) && item instanceof FluidProvider.ITEM fluidItem)
                return new SingleVariantTank(fluidItem.getFluidTank(stack));
            return null;
        }, item);
    }

    @Override
    public void registerBlockEntityFluid(BlockEntityType<?> entity) {
        FluidStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> {
            if (blockEntity instanceof FluidProvider.BLOCK fluidBlock){
                return new SingleVariantTank(fluidBlock.getFluidTank(direction));
            }
            return null;
        }), entity);
    }


}
