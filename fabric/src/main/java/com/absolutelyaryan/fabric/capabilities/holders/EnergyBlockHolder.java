package com.absolutelyaryan.fabric.capabilities.holders;

import com.absolutelyaryan.capabilities.types.NoProviderBlockCapabilityHolder;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fabric.energy.FabricEnergyStorage;
import com.absolutelyaryan.fabric.energy.UniversalEnergyWrapper;
import com.absolutelyaryan.providers.EnergyProvider;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

import java.util.Objects;
import java.util.function.Supplier;

public class EnergyBlockHolder implements NoProviderBlockCapabilityHolder<UniversalEnergyStorage, Direction> {
    public static final EnergyBlockHolder INSTANCE = new EnergyBlockHolder();
    BlockApiLookup<EnergyStorage, @Nullable Direction> blockApiLookup = EnergyStorage.SIDED;

    @Override
    public @Nullable UniversalEnergyStorage getCapability(Level level, BlockPos pos, Direction context) {
        EnergyStorage energyStorage =  blockApiLookup.find(level, pos, context);


        return energyStorage == null ? null : new UniversalEnergyWrapper(energyStorage);


    }

    @Override
    public @Nullable UniversalEnergyStorage getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Direction context) {
        EnergyStorage energyStorage = blockApiLookup.find(level, pos, context);
        return energyStorage == null ? null : new UniversalEnergyWrapper(energyStorage);
    }

    @Override
    public void registerForBlock(Supplier<Block> block) {
        blockApiLookup.registerForBlocks((level, blockPos, state, blockEntity,direction )->{
            if(blockEntity instanceof EnergyProvider.BLOCK provider){
                return new FabricEnergyStorage(Objects.requireNonNull(provider.getEnergy(direction)));
            }
            return null;
        }, block.get());
    }

    @Override
    public void registerForBlockEntity(Supplier<BlockEntityType<?>> blockEntityType) {
        blockApiLookup.registerForBlockEntity((blockEntity, direction) -> {
            if(blockEntity instanceof EnergyProvider.BLOCK provider){
                return new FabricEnergyStorage(Objects.requireNonNull(provider.getEnergy(direction)));
            }
            return null;
        }, blockEntityType.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return EnergyStorage.SIDED.getId();
    }

}
