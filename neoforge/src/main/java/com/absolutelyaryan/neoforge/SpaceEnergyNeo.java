package com.absolutelyaryan.neoforge;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.neoforge.capabilities.NeoForgeCapabilityManager;
import com.absolutelyaryan.neoforge.energy.NeoForgeEnergyStorage;
import com.absolutelyaryan.neoforge.fluid.NeoForgeFluidItem;
import com.absolutelyaryan.neoforge.fluid.NeoForgeFluidTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

@Mod(SpaceEnergyCommon.MOD_ID)
public class SpaceEnergyNeo {
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final NeoForgeCapabilityManager capabilityManager = new NeoForgeCapabilityManager();


    public SpaceEnergyNeo(IEventBus bus){
        bus.addListener(SpaceEnergyNeo::registerCapabilities);


        //neoforge dosent need this because Capabilities are registered when all items are loaded
        SpaceEnergyCommon.setCapabilityManager(capabilityManager);

        
    }


    //Using Neoforge's Event to register capabilities
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {



        for (BlockEntityType<?> blockEntityType : BuiltInRegistries.BLOCK_ENTITY_TYPE) {
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, blockEntityType, (entity, direction) -> {
                if (entity instanceof EnergyProvider.BLOCK energyBlock) {
                    return energyBlock.getEnergy(direction) == null ? null : new NeoForgeEnergyStorage(energyBlock.getEnergy(direction));
                }
                return null;
            });


            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, blockEntityType, (entity, direction) -> {
                if(entity instanceof FluidProvider.BLOCK fluidBlock)
                    return fluidBlock.getFluidTank(direction) == null ? null : new NeoForgeFluidTank(fluidBlock.getFluidTank(direction));
                return null;
            });

        }


        for(Block block : BuiltInRegistries.BLOCK){
            event.registerBlock(
                    Capabilities.EnergyStorage.BLOCK,
                    (level, pos, state, blockEntity, direction) -> {
                        if (blockEntity instanceof EnergyProvider.BLOCK energyBlock) {
                            return energyBlock.getEnergy(direction) == null ? null : new NeoForgeEnergyStorage(energyBlock.getEnergy(direction));
                        }
                        return null;
                    },
                    block
            );
            event.registerBlock(
                    Capabilities.FluidHandler.BLOCK,
                    (level, pos, state, blockEntity, direction) -> {
                        if (blockEntity instanceof FluidProvider.BLOCK fluidBlock) {
                            return fluidBlock.getFluidTank(direction) == null ? null : new NeoForgeFluidTank(fluidBlock.getFluidTank(direction));
                        }
                        return null;
                    },
                    block
            );

        }

        for (Item item : BuiltInRegistries.ITEM) {
            event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, object) -> {
                if (stack.getItem() instanceof EnergyProvider.ITEM energyItem)
                    return energyItem.getEnergy(stack) == null ? null : new NeoForgeEnergyStorage(energyItem.getEnergy(stack));
                return null;
            }, item);

            event.registerItem(Capabilities.FluidHandler.ITEM, (stack, object) -> {
                if (stack.getItem() instanceof FluidProvider.ITEM fluidItem)
                    return fluidItem.getFluidTank(stack) == null ? null : new NeoForgeFluidItem(stack,fluidItem.getFluidTank(stack));
                return null;
            }, item);

        }
    }
}
