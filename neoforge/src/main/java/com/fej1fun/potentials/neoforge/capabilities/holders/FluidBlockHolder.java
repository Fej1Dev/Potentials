package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderFluidBlockCapabilityHolder;
import com.fej1fun.potentials.fluid.UniversalFluidTank;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.fluid.NeoForgeFluidTank;
import com.fej1fun.potentials.neoforge.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
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

public class FluidBlockHolder implements NoProviderFluidBlockCapabilityHolder<UniversalFluidTank, Direction>, Registerable {
    public static final FluidBlockHolder INSTANCE = new FluidBlockHolder();
    private FluidBlockHolder() {registerSelf();}

    private final List<Supplier<Block>> registeredBlocks = new ArrayList<>();
    private final List<Supplier<BlockEntityType<?>>> registeredBlockEntities = new ArrayList<>();

    @Override
    public UniversalFluidTank getCapability(Level level, BlockPos pos, Direction direction) {
        return getCapability(level, pos, null, null, direction);
    }

    @Override
    public @Nullable List<UniversalFluidTank> getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Direction direction) {
        IFluidHandler fluidTank = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, state, blockEntity, direction);
        return fluidTank == null ? null : List.of(new UniversalFluidStorage(fluidTank));
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
                    if (state.getBlock() instanceof FluidProvider.BLOCK fluidBlock) {
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
