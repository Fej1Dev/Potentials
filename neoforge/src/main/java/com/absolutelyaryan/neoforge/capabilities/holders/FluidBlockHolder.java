package com.absolutelyaryan.neoforge.capabilities.holders;

import com.absolutelyaryan.capabilities.types.NoProviderBlockCapabilityHolder;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.neoforge.capabilities.Registerable;
import com.absolutelyaryan.neoforge.fluid.NeoForgeFluidTank;
import com.absolutelyaryan.neoforge.fluid.UniversalFluidStorage;
import com.absolutelyaryan.providers.FluidProvider;
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
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FluidBlockHolder implements NoProviderBlockCapabilityHolder<UniversalFluidTank, Direction>, Registerable {
    public static final FluidBlockHolder INSTANCE = new FluidBlockHolder();
    private FluidBlockHolder() {registerSelf();}

    private final List<Supplier<Block>> registeredBlocks = new ArrayList<>();
    private final List<Supplier<BlockEntityType<?>>> registeredBlockEntities = new ArrayList<>();

    @Override
    public @Nullable UniversalFluidTank getCapability(Level level, BlockPos pos, Direction context) {
        IFluidHandler fluidTank = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, context);
        return fluidTank == null ? null : new UniversalFluidStorage(fluidTank);
    }

    @Override
    public @Nullable UniversalFluidTank getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Direction context) {
        IFluidHandler fluidTank = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, state, blockEntity, context);
        return fluidTank == null ? null : new UniversalFluidStorage(fluidTank);
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
        return Capabilities.FluidHandler.BLOCK.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredBlocks.forEach(block ->
                event.registerBlock(Capabilities.FluidHandler.BLOCK, (level, pos, state, blockEntity, direction) -> {
                    if (blockEntity instanceof FluidProvider.BLOCK fluidBlock) {
                        var fluid = fluidBlock.getFluidTank(direction);
                        return fluid == null ? null : new NeoForgeFluidTank(fluid);
                    }
                    return null;
                }, block.get()));

        registeredBlockEntities.forEach(type ->
                event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, type.get(), (blockEntity, direction) -> {
                    if (blockEntity instanceof FluidProvider.BLOCK fluidBlock) {
                        var fluid = fluidBlock.getFluidTank(direction);
                        return fluid == null ? null : new NeoForgeFluidTank(fluid);
                    }
                    return null;
                }));
    }
}