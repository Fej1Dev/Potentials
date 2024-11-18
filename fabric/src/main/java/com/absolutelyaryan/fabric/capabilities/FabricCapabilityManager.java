package com.absolutelyaryan.fabric.capabilities;

import com.absolutelyaryan.capabilities.CapabilityManager;
import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.EntityCapabilityHolder;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import com.absolutelyaryan.fabric.capabilities.types.FabricBlockProviderHolder;
import com.absolutelyaryan.fabric.capabilities.types.FabricEntityProviderHolder;
import com.absolutelyaryan.fabric.capabilities.types.FabricItemProviderHolder;
import com.absolutelyaryan.fabric.energy.FabricEnergyStorage;
import com.absolutelyaryan.fabric.fluid.SingleVariantTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.reborn.energy.api.EnergyStorage;

public class FabricCapabilityManager implements CapabilityManager{

    @Override
    public void registerBlockEnergy(Block block) {
        EnergyStorage.SIDED.registerForBlocks(
                (level, pos, state, blockEntity, side) -> {
                    if (blockEntity!=null)
                        if (blockEntity instanceof EnergyProvider.BLOCK energyBlock){
                        var energy = energyBlock.getEnergy(side);
                        return energy == null ? null : new FabricEnergyStorage(energy);
                        }
                    BlockState newState = state;
                    if (newState==null)
                        newState = level.getBlockState(pos);
                    if (newState.getBlock() instanceof EnergyProvider.BLOCK energyBlock) {
                        var energy = energyBlock.getEnergy(side);
                        return energy == null ? null : new FabricEnergyStorage(energy);
                    }
                    return null;
                }, block);
    }

    @Override
    public void registerItemEnergy(Item item) {
        EnergyStorage.ITEM.registerForItems((stack, containerItemContext) -> {
            if (stack.is(item) && item instanceof EnergyProvider.ITEM energyItem) {
                var energy = energyItem.getEnergy(stack);
                return energy == null ? null : new FabricEnergyStorage(energy);
            }
            return null;
        }, item);
    }

    @Override
    public void registerBlockEntityEnergy(BlockEntityType<?> entity) {
        EnergyStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> {
            if (blockEntity instanceof EnergyProvider.BLOCK energyBlock){
                var energy = energyBlock.getEnergy(direction);
                return energy == null ? null : new FabricEnergyStorage(energy);
            }
            return null;
        }), entity);
    }

    @Override
    public void registerBlockFluid(Block block) {
        FluidStorage.SIDED.registerForBlocks(
                (level, pos, state, blockEntity, side) -> {
                    if (blockEntity!=null)
                        if (blockEntity instanceof FluidProvider.BLOCK fluidBlock){
                            var fluid = fluidBlock.getFluidTank(side);
                            return fluid == null ? null : new SingleVariantTank(fluid);
                        }
                    BlockState newState = state;
                    if (newState==null)
                        newState = level.getBlockState(pos);
                    if (newState.getBlock() instanceof FluidProvider.BLOCK fluidBlock) {
                        var fluid = fluidBlock.getFluidTank(side);
                        return fluid == null ? null : new SingleVariantTank(fluid);
                    }
                    return null;
                }, block);
    }

    @Override
    public void registerItemFluid(Item item) {
        FluidStorage.ITEM.registerForItems((stack, containerItemContext) -> {
            if (stack.is(item) && item instanceof FluidProvider.ITEM fluidItem) {
                var fluid = fluidItem.getFluidTank(stack);
                return fluid == null ? null : new SingleVariantTank(fluid);
            }
            return null;
        }, item);
    }

    @Override
    public void registerBlockEntityFluid(BlockEntityType<?> entity) {
        FluidStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> {
            if (blockEntity instanceof FluidProvider.BLOCK fluidBlock){
                var fluid = fluidBlock.getFluidTank(direction);
                return fluid == null ? null : new SingleVariantTank(fluid);
            }
            return null;
        }), entity);
    }



    @Override
    public <X, Y> BlockCapabilityHolder<X,Y> registerSidedCapability(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier) {
        return new FabricBlockProviderHolder<>(BlockApiLookup.get(identifier, apiClass, contextClass));
    }

    @Override
    public <X, Y> ItemCapabilityHolder<X,Y> registerItemCapability(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier) {
        return new FabricItemProviderHolder<>(ItemApiLookup.get(identifier, apiClass, contextClass));
    }

    @Override
    public <X, Y> EntityCapabilityHolder<X,Y> registerEntityCapability(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier) {
        return new FabricEntityProviderHolder<>(EntityApiLookup.get(identifier, apiClass, contextClass));
    }



}
