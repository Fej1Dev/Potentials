package com.absolutelyaryan.fabric;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.fabric.energy.FabricEnergyStorage;
import com.absolutelyaryan.fabric.fluid.SingleVariantTank;
import com.absolutelyaryan.fluid.BaseFluidTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import team.reborn.energy.api.EnergyStorage;

public final class SpaceEnergyFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SpaceEnergyCommon.init();
        registerCapabilities();
    }

    private void registerCapabilities() {

        for (BlockEntityType<?> type : BuiltInRegistries.BLOCK_ENTITY_TYPE) {
            EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> {
                if (blockEntity instanceof EnergyProvider.BLOCK energyBlock)
                    return new FabricEnergyStorage(energyBlock.getEnergy(direction));
                return null;
            }, type);

            FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> {
                if (blockEntity instanceof FluidProvider.BLOCK fluidBlock)
                    return new SingleVariantTank(fluidBlock.getFluidTank(direction));
                return null;
            }, type);

        }

        for(Block block : BuiltInRegistries.BLOCK){
            EnergyStorage.SIDED.registerForBlocks(
                    (level, pos, state, blockEntity, side) -> {
                        if (blockEntity instanceof EnergyProvider.BLOCK energyBlock)
                            return new FabricEnergyStorage(energyBlock.getEnergy(side));
                        return null;
                    },
                    block
            );

            FluidStorage.SIDED.registerForBlocks(
                    (level, pos, state, blockEntity, side) -> {
                        if (blockEntity instanceof FluidProvider.BLOCK fluidBlock)
                            return new SingleVariantTank(fluidBlock.getFluidTank(side));
                        return null;
                    },
                    block
            );

        }

        for (Item item : BuiltInRegistries.ITEM) {
            EnergyStorage.ITEM.registerForItems((stack, containerItemContext) -> {
                if (stack.is(item) && item instanceof EnergyProvider.ITEM energyItem)
                    return new FabricEnergyStorage(energyItem.getEnergy(stack));
                return null;
            }, item);

            FluidStorage.ITEM.registerForItems((stack, containerItemContext) -> {
                if (stack.is(item) && item instanceof FluidProvider.ITEM fluidItem)
                    return new SingleVariantTank(fluidItem.getFluidTank(stack));
                return null;
            }, item);
        }
    }
}
