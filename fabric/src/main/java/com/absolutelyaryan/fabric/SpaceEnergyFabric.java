package com.absolutelyaryan.fabric;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.fluid.SingleVariantTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
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
                    return energyBlock.getEnergy(direction);
                return null;
            }, type);

            FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> {
                if(blockEntity instanceof FluidProvider.BLOCK fluidBlock)
                    return new SingleVariantTank(fluidBlock.getFluidTank(direction));
                return null;
            }, type);

        }
        for (Item item : BuiltInRegistries.ITEM) {
            EnergyStorage.ITEM.registerForItems((stack, containerItemContext) -> {
                if (stack.is(item) && item instanceof EnergyProvider.ITEM energyItem)
                    return energyItem.getEnergy(stack);
                return null;
            }, item);

            FluidStorage.ITEM.registerForItems((stack, containerItemContext) -> {
                if(stack.is(item) && item instanceof FluidProvider.ITEM fluidItem)
                    return new SingleVariantTank(fluidItem.getFluidTank(stack));
                return null;
            }, item);
        }
    }
}
