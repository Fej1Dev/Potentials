package com.absolutelyaryan.fabric;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.providers.EnergyProvider;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import team.reborn.energy.api.EnergyStorage;

public final class SpaceEnergyFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        SpaceEnergyCommon.init();
        registerCapabilities();
    }

    private void registerCapabilities() {

        for (BlockEntityType<?> type : BuiltInRegistries.BLOCK_ENTITY_TYPE) {
            //Energy
            EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> {
                if (blockEntity instanceof EnergyProvider.BLOCK energyBlock)
                    return energyBlock.getEnergy(direction);
                return null;
            }, type);

        }
        for (Item item : BuiltInRegistries.ITEM) {
            //Energy
            EnergyStorage.ITEM.registerForItems((stack, containerItemContext) -> {
                if (stack.is(item) && item instanceof EnergyProvider.ITEM energyItem)
                    return energyItem.getEnergy(stack);
                return null;
            }, item);
        }
    }
}
