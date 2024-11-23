package com.absolutelyaryan.neoforge;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.neoforge.capabilities.NeoForgeCapabilityManager;
import com.absolutelyaryan.neoforge.capabilities.types.NeoBlockHolder;
import com.absolutelyaryan.neoforge.capabilities.types.NeoEntityHolder;
import com.absolutelyaryan.neoforge.capabilities.types.NeoItemHolder;
import com.absolutelyaryan.neoforge.capabilities.types.Registerable;
import com.absolutelyaryan.neoforge.energy.NeoForgeEnergyStorage;
import com.absolutelyaryan.neoforge.fluid.NeoForgeFluidItem;
import com.absolutelyaryan.neoforge.fluid.NeoForgeFluidTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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


        SpaceEnergyCommon.setCapabilityManager(capabilityManager);

        
    }


    //Using Neoforge's Event to register capabilities
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        Registerable.registerAll(event);

        for(Block block : BuiltInRegistries.BLOCK) {
            //NOT NEEDED
//            event.registerBlock(
//                    Capabilities.EnergyStorage.BLOCK,
//                    (level, pos, state, blockEntity, direction) -> {
//                        if (blockEntity!=null)
//                            if (blockEntity instanceof EnergyProvider.BLOCK energyBlock){
//                                var energy = energyBlock.getEnergy(direction);
//                                return energy == null ? null : new NeoForgeEnergyStorage(energy);
//                            }
//                        if (state.getBlock() instanceof EnergyProvider.BLOCK energyBlock) {
//                            var energy = energyBlock.getEnergy(direction);
//                            return energy == null ? null : new NeoForgeEnergyStorage(energy);
//                        }
//                        return null;
//                    },
//                    block
//            );
            event.registerBlock(
                    Capabilities.FluidHandler.BLOCK,
                    (level, pos, state, blockEntity, direction) -> {
                        if (blockEntity!=null)
                            if (blockEntity instanceof FluidProvider.BLOCK energyBlock){
                                var fluid = energyBlock.getFluidTank(direction);
                                return fluid == null ? null : new NeoForgeFluidTank(fluid);
                            }
                        if (state.getBlock() instanceof FluidProvider.BLOCK energyBlock) {
                            var fluid = energyBlock.getFluidTank(direction);
                            return fluid == null ? null : new NeoForgeFluidTank(fluid);
                        }
                        return null;
                    },
                    block
            );

        }

        for (Item item : BuiltInRegistries.ITEM) {
            event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, object) -> {
                if (stack.getItem() instanceof EnergyProvider.ITEM energyItem) {
                    var energy = energyItem.getEnergy(stack);
                    return energy == null ? null : new NeoForgeEnergyStorage(energy);
                }
                return null;
            }, item);

            event.registerItem(Capabilities.FluidHandler.ITEM, (stack, object) -> {
                if (stack.getItem() instanceof FluidProvider.ITEM fluidItem) {
                    var fluid = fluidItem.getFluidTank(stack);
                    return fluid == null ? null : new NeoForgeFluidItem(stack, fluid);
                }
                return null;
            }, item);

        }

    }
}
