package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderBlockCapabilityHolder;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.fluid.NeoForgeFluidStorage;
import com.fej1fun.potentials.neoforge.fluid.UniversalFluidHandler;
import com.fej1fun.potentials.providers.FluidProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class FluidBlockHolder implements NoProviderBlockCapabilityHolder<UniversalFluidStorage, Direction>, Registerable {
    public static final FluidBlockHolder INSTANCE = new FluidBlockHolder();
    private FluidBlockHolder() {registerSelf();}

    private final Set<Supplier<Block>> registeredBlocks = new HashSet<>();
    private final Set<Supplier<BlockEntityType<?>>> registeredBlockEntities = new HashSet<>();

    @Override
    public UniversalFluidStorage getCapability(Level level, BlockPos pos, Direction direction) {
        return getCapability(level, pos, null, null, direction);
    }

    @Override
    public @Nullable UniversalFluidStorage getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Direction direction) {
        ResourceHandler<FluidResource> fluidTank = level.getCapability(Capabilities.Fluid.BLOCK, pos, state, blockEntity, direction);
        return fluidTank == null ? null : new UniversalFluidHandler((NeoForgeFluidStorage) fluidTank);
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
    public Identifier getIdentifier() {
        return Capabilities.Fluid.BLOCK.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredBlocks.forEach(block ->
                event.registerBlock(Capabilities.Fluid.BLOCK, (level, pos, state, blockEntity, direction) -> {
                    if (blockEntity instanceof FluidProvider.BLOCK fluidBlock) {
                        UniversalFluidStorage fluid = fluidBlock.getFluidTank(direction);
                        return fluid == null ? null : new NeoForgeFluidStorage(fluid);
                    }
                    if (state.getBlock() instanceof FluidProvider.BLOCK fluidBlock) {
                        UniversalFluidStorage fluid = fluidBlock.getFluidTank(direction);
                        return fluid == null ? null : new NeoForgeFluidStorage(fluid);
                    }
                    return null;
                }, block.get()));

        registeredBlockEntities.forEach(type ->
                event.registerBlockEntity(Capabilities.Fluid.BLOCK, type.get(), (blockEntity, direction) -> {
                    if (blockEntity instanceof FluidProvider.BLOCK fluidBlock) {
                        UniversalFluidStorage fluid = fluidBlock.getFluidTank(direction);
                        return fluid == null ? null : new NeoForgeFluidStorage(fluid);
                    }
                    return null;
                }));
    }
}
