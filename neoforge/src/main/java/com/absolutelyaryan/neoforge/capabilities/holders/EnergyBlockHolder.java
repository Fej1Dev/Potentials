package com.absolutelyaryan.neoforge.capabilities.holders;

import com.absolutelyaryan.capabilities.types.NoProviderBlockCapabilityHolder;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.neoforge.capabilities.types.Registerable;
import com.absolutelyaryan.neoforge.energy.NeoForgeEnergyStorage;
import com.absolutelyaryan.neoforge.energy.UniversalIEnergyStorage;
import com.absolutelyaryan.providers.EnergyProvider;
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
    public @Nullable UniversalEnergyStorage getCapability(Level level, BlockPos pos, Direction context) {
        IEnergyStorage energyStorage =  level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, context);
        return energyStorage == null ? null : new UniversalIEnergyStorage(energyStorage);
    }

    @Override
    public @Nullable UniversalEnergyStorage getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Direction context) {
        IEnergyStorage energyStorage = level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, state, blockEntity, context);
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
                }, block.get()));
        registeredBlockEntities.forEach(type ->
                event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, type.get(), ((blockEntity, direction) -> {
                    if (blockEntity instanceof EnergyProvider.BLOCK energyBlock) {
                        var energy = energyBlock.getEnergy(direction);
                        return energy == null ? null : new NeoForgeEnergyStorage(energy);
                    }
                    return null;
                })));
    }
}
