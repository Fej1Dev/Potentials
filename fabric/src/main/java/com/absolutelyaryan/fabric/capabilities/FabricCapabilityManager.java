package com.absolutelyaryan.fabric.capabilities;

import com.absolutelyaryan.capabilities.CapabilityData;
import com.absolutelyaryan.capabilities.CapabilityManager;
import com.absolutelyaryan.fabric.energy.FabricEnergyStorage;
import com.absolutelyaryan.fabric.fluid.SingleVariantTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import team.reborn.energy.api.EnergyStorage;

import java.util.HashMap;

public class FabricCapabilityManager implements CapabilityManager{
    private final HashMap<BlockApiLookup<?, ?>, Class<?>> blockCapabilityMap = new HashMap<>();
    private final HashMap<ItemApiLookup<?, ?>, Class<?>> itemCapabilityMap = new HashMap<>();
    private final HashMap<EntityApiLookup<?, ?>, Class<?>> entityCapabilityMap = new HashMap<>();
    private final HashMap<ResourceLocation, CapabilityData<?,?>> capabilityDataMap = new HashMap<>();






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



    @Override
    public <X, Y> void registerSidedCapability(CapabilityData<X, Y> capabilityData, ResourceLocation identifier) {
        blockCapabilityMap.put(BlockApiLookup.get(identifier, capabilityData.getCapabilityClass(), capabilityData.getContextClass()), capabilityData.getCapabilityClass());
        capabilityDataMap.put(identifier, capabilityData);
    }

    @Override
    public <X, Y> void registerItemCapability(CapabilityData<X, Y> capabilityData, ResourceLocation identifier) {
        itemCapabilityMap.put(ItemApiLookup.get(identifier, capabilityData.getCapabilityClass(), capabilityData.getContextClass()), capabilityData.getCapabilityClass());
        capabilityDataMap.put(identifier, capabilityData);
    }

    @Override
    public <X, Y> void registerEntityCapability(CapabilityData<X, Y> capabilityData, ResourceLocation identifier) {
        entityCapabilityMap.put(EntityApiLookup.get(identifier, capabilityData.getCapabilityClass(), capabilityData.getContextClass()), capabilityData.getCapabilityClass());
        capabilityDataMap.put(identifier, capabilityData);
    }

    @Override
    public <X, Y> CapabilityData<X, Y> getCapabilityData(ResourceLocation identifier) {
        @SuppressWarnings("unchecked")
        CapabilityData<X, Y> capabilityData = (CapabilityData<X, Y>) capabilityDataMap.get(identifier);
        return capabilityData;
    }



    @Override
    public <X, Y> void registerForBlocks(ResourceLocation identifier, Block... blocks) {
        CapabilityData<X, Y> capabilityData = getCapabilityData(identifier);
        BlockApiLookup<X, Y> blockApiLookup = BlockApiLookup.get(identifier, capabilityData.getCapabilityClass(), capabilityData.getContextClass());

        if (blockCapabilityMap.containsKey(blockApiLookup)) {
            for (Block block : blocks) {
                blockApiLookup.registerForBlocks((world, pos, state, blockEntity, side) -> {
                    if (capabilityData.getCapabilityClass().isInstance(blockEntity)) {
                        Object capability = capabilityData.getProvider().getCapability(blockEntity, side);
                        if (capabilityData.getCapabilityClass().isInstance(capability)) {
                            return capabilityData.getCapabilityClass().cast(capability);
                        }
                    }
                    return null;
                }, block);
            }
        }
    }





    @Override
    public <X,Y> void registerForBlockEntity(ResourceLocation identifier, BlockEntityType<?>... entities) {
        CapabilityData<X, Y> capabilityData = getCapabilityData(identifier);
        BlockApiLookup<X, Y> blockApiLookup = BlockApiLookup.get(identifier, capabilityData.getCapabilityClass(), capabilityData.getContextClass());

        if (blockCapabilityMap.containsKey(blockApiLookup)) {
            for (BlockEntityType<?> entity : entities) {
                blockApiLookup.registerForBlockEntity((blockEntity, direction) -> {
                    if (capabilityData.getCapabilityClass().isInstance(blockEntity)) {
                        Object capability = capabilityData.getProvider().getCapability(blockEntity, direction);
                        if (capabilityData.getCapabilityClass().isInstance(capability)) {
                            return capabilityData.getCapabilityClass().cast(capability);
                        }
                    }
                    return null;
                }, entity);
            }
        }
    }

    @Override
    public <X, Y> void registerForItems(ResourceLocation identifier, Item... items) {
        CapabilityData<X, Y> capabilityData = getCapabilityData(identifier);
        ItemApiLookup<X, Y> itemApiLookup = ItemApiLookup.get(identifier, capabilityData.getCapabilityClass(), capabilityData.getContextClass());

        if (itemCapabilityMap.containsKey(itemApiLookup)) {
            for (Item item : items) {
                itemApiLookup.registerForItems((stack, containerItemContext) -> {
                    if (capabilityData.getCapabilityClass().isInstance(stack)) {
                        Object capability = capabilityData.getProvider().getCapability(stack.getItem(), stack);
                        if (capabilityData.getCapabilityClass().isInstance(capability)) {
                            return capabilityData.getCapabilityClass().cast(capability);
                        }
                    }
                    return null;
                }, item);
            }
        }
    }

    @Override
    public <X, Y> void registerForEntities(ResourceLocation identifier, EntityType<?>... entities) {
        CapabilityData<X, Y> capabilityData = getCapabilityData(identifier);
        EntityApiLookup<X, Y> entityApiLookup = EntityApiLookup.get(identifier, capabilityData.getCapabilityClass(), capabilityData.getContextClass());

        if (entityCapabilityMap.containsKey(entityApiLookup)) {
            for (EntityType<?> entity : entities) {
                entityApiLookup.registerForType((entity1, context) -> {
                    if (capabilityData.getCapabilityClass().isInstance(entity1)) {
                        Object capability = capabilityData.getProvider().getCapability(entity1, context);
                        if (capabilityData.getCapabilityClass().isInstance(capability)) {
                            return capabilityData.getCapabilityClass().cast(capability);
                        }
                    }
                    return null;
                }, entity);
            }
        }
    }

    @Override
    public <X, Y> Object getCapability(ResourceLocation identifier, Object provider, Object context) {
        CapabilityData<?, ?> capabilityData = capabilityDataMap.get(identifier);
        Object capability = capabilityData.getProvider().getCapability(provider, context);

        //TODO: incompleted
        return null;

    }
}
