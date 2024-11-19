package com.absolutelyaryan.neoforge;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.neoforge.capabilities.NeoForgeCapabilityManager;
import com.absolutelyaryan.neoforge.capabilities.types.NeoBlockHolder;
import com.absolutelyaryan.neoforge.capabilities.types.NeoEntityHolder;
import com.absolutelyaryan.neoforge.capabilities.types.NeoItemHolder;
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
import net.neoforged.neoforge.capabilities.BlockCapability;
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

        for(Block block : BuiltInRegistries.BLOCK) {
            event.registerBlock(
                    Capabilities.EnergyStorage.BLOCK,
                    (level, pos, state, blockEntity, direction) -> {
                        if (blockEntity!=null)
                            if (blockEntity instanceof EnergyProvider.BLOCK energyBlock){
                                var energy = energyBlock.getEnergy(direction);
                                return energy == null ? null : new NeoForgeEnergyStorage(energy);
                            }
                        if (state.getBlock() instanceof EnergyProvider.BLOCK energyBlock) {
                            var energy = energyBlock.getEnergy(direction);
                            return energy == null ? null : new NeoForgeEnergyStorage(energy);
                        }
                        return null;
                    },
                    block
            );
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


//        //Registering capabilities for blocks
//        for(ResourceLocation identifier : capabilityManager.getBlockCapabilityHolders().keySet()){
//            NeoBlockHolder<?, ?> holder = capabilityManager.getBlockCapabilityHolders().get(identifier);
//            holder.getRegisteredBlocks().forEach(((block, blockCapabilityProvider) -> {
//
//                        event.registerBlock(holder.getBlockCapability(), blockCapabilityProvider::getCapability, block);
//                    }));
//
//        }
//
//
//
//        //Registering capabilities for blocks entities
//        for(ResourceLocation identifier : capabilityManager.getBlockCapabilityHolders().keySet()){
//            NeoBlockHolder<?, ?> holder = capabilityManager.getBlockCapabilityHolders().get(identifier);
//            holder.getRegisteredBlockEntities().forEach((blockEntityType, capabilityProvider) ->
//                    event.registerBlockEntity(holder.getBlockCapability(), blockEntityType, capabilityProvider::getCapability));
//        }
//
//        //Registering capabilities for items
//        for (ResourceLocation identifier : capabilityManager.getItemCapabilityHolders().keySet()) {
//            NeoItemHolder<?, ?> holder = capabilityManager.getItemCapabilityHolders().get(identifier);
//            holder.getRegisteredItems().forEach((item, capabilityProvider) ->
//                    event.registerItem(holder.getItemCapability(), capabilityProvider::getCapability, item));
//        }
//
//        //Registering capabilities for entities
//        for (ResourceLocation identifier : capabilityManager.getEntityCapabilityHolders().keySet()) {
//            NeoEntityHolder<?, ?> holder = capabilityManager.getEntityCapabilityHolders().get(identifier);
//            holder.getRegisteredEntities().forEach((entityType, capabilityProvider) ->
//                    event.registerEntity(holder.getEntityCapability(), entityType, capabilityProvider::getCapability));
//        }

    }
}
