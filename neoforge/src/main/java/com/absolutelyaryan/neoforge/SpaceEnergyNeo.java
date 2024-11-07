package com.absolutelyaryan.neoforge;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.neoforge.energy.NeoForgeEnergyStorage;
import com.absolutelyaryan.neoforge.fluid.NeoForgeFluidItem;
import com.absolutelyaryan.neoforge.fluid.NeoForgeFluidTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Mod(SpaceEnergyCommon.MOD_ID)
public class SpaceEnergyNeo {

    public SpaceEnergyNeo(IEventBus bus){
        bus.addListener(SpaceEnergyNeo::registerCapabilities);
    }

    // Register capabilities here
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Register block entity capability
        for (BlockEntityType<?> blockEntityType : BuiltInRegistries.BLOCK_ENTITY_TYPE) {
            //Energy
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, blockEntityType, (entity, direction) -> {
                if (entity instanceof EnergyProvider.BLOCK energyBlock)
                    return new NeoForgeEnergyStorage(energyBlock.getEnergy(direction));
                return null;
            });


            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, blockEntityType, (entity, direction) -> {
                if(entity instanceof FluidProvider.BLOCK fluidBlock)
                    return new NeoForgeFluidTank(fluidBlock.getFluidTank(direction));
                return null;
            });

        }

        // Register item capability
        for (Item item : BuiltInRegistries.ITEM) {
            //Energy
            event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, object) -> {
                if (stack.getItem() instanceof EnergyProvider.ITEM energyItem)
                    return new NeoForgeEnergyStorage(energyItem.getEnergy(stack));
                return null;
            }, item);

            event.registerItem(Capabilities.FluidHandler.ITEM, (stack, object) -> {
                if (stack.getItem() instanceof FluidProvider.ITEM energyItem)
                    return new NeoForgeFluidItem(stack, energyItem.getFluidTank(stack));
                return null;
            }, item);

        }
    }
}
