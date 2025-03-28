package com.fej1fun.potentials.fabric.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderBlockCapabilityHolder;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fabric.energy.FabricEnergyStorage;
import com.fej1fun.potentials.fabric.energy.UniversalEnergyWrapper;
import com.fej1fun.potentials.providers.EnergyProvider;
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
        return getCapability(level, pos, null, null, context);
    }

    @Override
    public @Nullable UniversalEnergyStorage getCapability(Level level, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity, Direction context) {
        blockEntity = blockEntity != null ? blockEntity : level.getBlockEntity(pos);

        if (blockEntity != null)
            if (blockEntity instanceof EnergyProvider.BLOCK provider)
                return provider.getEnergy(context);

        if (Objects.requireNonNullElseGet(state, () -> level.getBlockState(pos)).getBlock() instanceof EnergyProvider.BLOCK provider)
            return provider.getEnergy(context);

        EnergyStorage energyStorage = blockApiLookup.find(level, pos, state, blockEntity, context);
        return energyStorage == null ? null : new UniversalEnergyWrapper(energyStorage);
    }

    @Override
    public void registerForBlock(Supplier<Block> block) {
        blockApiLookup.registerForBlocks((level, blockPos, state, blockEntity,direction) -> {
            if (blockEntity instanceof EnergyProvider.BLOCK energyBlock) {
                UniversalEnergyStorage energy = energyBlock.getEnergy(direction);
                return energy == null ? null : new FabricEnergyStorage(energy);
            }
            if (state.getBlock() instanceof EnergyProvider.BLOCK energyBlock) {
                UniversalEnergyStorage energy = energyBlock.getEnergy(direction);
                return energy == null ? null : new FabricEnergyStorage(energy);
            }
            return null;
        }, block.get());
    }

    @Override
    public void registerForBlockEntity(Supplier<BlockEntityType<?>> blockEntityType) {
        blockApiLookup.registerForBlockEntity((blockEntity, direction) -> {
            if (blockEntity instanceof EnergyProvider.BLOCK energyBlock) {
                UniversalEnergyStorage energy = energyBlock.getEnergy(direction);
                return energy == null ? null : new FabricEnergyStorage(energy);
            }
            return null;
        }, blockEntityType.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return EnergyStorage.SIDED.getId();
    }

}
