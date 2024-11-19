package com.absolutelyaryan.neoforge.capabilities.holders;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.CapabilityProvider;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.BaseFluidTank;
import com.absolutelyaryan.neoforge.energy.NeoForgeEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class EnergyBlockHolder<X extends UniversalEnergyStorage, Y extends Direction> implements BlockCapabilityHolder<X,Y> {


    @Override
    public @Nullable X getCapability(Level level, BlockPos pos, Y context) {
        IEnergyStorage energyStorage =  level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, context);
        if(energyStorage instanceof NeoForgeEnergyStorage storage){
            return (X) storage.getUniversalEnergyStorage();
        }
        return null;
    }

    @Override
    public @Nullable X getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Y context) {
        IEnergyStorage energyStorage = level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, state, blockEntity, context);
        if(energyStorage instanceof NeoForgeEnergyStorage storage){
            return (X) storage.getUniversalEnergyStorage();
        }
        return null;
    }

    @Override
    public void registerForBlocks(BlockCapabilityProvider<X, Y> provider, Block... blocks) {
        for(Block block : blocks){
            SpaceEnergyCommon.getCapabilityManager().registerBlockEnergy(block);
        }
    }

    @Override
    public void registerForBlockEntity(CapabilityProvider<BlockEntity, X, Y> provider, BlockEntityType<?>... entities) {
        for(BlockEntityType<?> entity : entities){
            SpaceEnergyCommon.getCapabilityManager().registerBlockEntityEnergy(entity);
        }
    }

    @Override
    public ResourceLocation getIdentifier() {
        return Capabilities.EnergyStorage.BLOCK.name();
    }
}
