package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderBlockCapabilityHolder;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.energy.NeoForgeEnergyStorage;
import com.fej1fun.potentials.neoforge.energy.UniversalIEnergyStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EnergyBlockHolder implements NoProviderBlockCapabilityHolder<UniversalEnergyStorage, Direction>, Registerable {
    public static final EnergyBlockHolder INSTANCE = new EnergyBlockHolder();
    private EnergyBlockHolder() {registerSelf();}

    private final List<Supplier<Block>> registeredBlocks = new ArrayList<>();
    private final List<Supplier<BlockEntityType<?>>> registeredBlockEntities = new ArrayList<>();

    @Override
    public @Nullable UniversalEnergyStorage getCapability(Level level, BlockPos pos, Direction direction) {
        return getCapability(level, pos, null, null, direction);
    }

    @Override
    public @Nullable UniversalEnergyStorage getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Direction direction) {
        IEnergyStorage energyStorage = level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, state, blockEntity, direction);
        return energyStorage == null ? null : new UniversalIEnergyStorage(energyStorage);
    }

    @Override
    public void registerForBlock(Supplier<Block> block) {
        registeredBlocks.add(block);
    }

    @Override
    public void registerForBlockEntity(Supplier<BlockEntityType<?>> blockEntityType) {
        registeredBlockEntities.add(blockEntityType);
    }

    @Override
    public ResourceLocation getIdentifier() {
        return Capabilities.EnergyStorage.BLOCK.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredBlocks.forEach(block ->
                event.registerBlock(Capabilities.EnergyStorage.BLOCK, (level, pos, state, blockEntity, direction) -> {
                    if (blockEntity instanceof EnergyProvider.BLOCK energyBlock) {
                            UniversalEnergyStorage energy = energyBlock.getEnergy(direction);
                            return energy == null ? null : new NeoForgeEnergyStorage(energy);
                    }
                    if (state.getBlock() instanceof EnergyProvider.BLOCK energyBlock) {
                        UniversalEnergyStorage energy = energyBlock.getEnergy(direction);
                        return energy == null ? null : new NeoForgeEnergyStorage(energy);
                    }
                    return null;
                }, block.get()));
        registeredBlockEntities.forEach(type ->
                event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, type.get(), ((blockEntity, direction) -> {
                    if (blockEntity instanceof EnergyProvider.BLOCK energyBlock) {
                        UniversalEnergyStorage energy = energyBlock.getEnergy(direction);
                        return energy == null ? null : new NeoForgeEnergyStorage(energy);
                    }
                    return null;
                })));
    }
}
