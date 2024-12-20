package com.fej1fun.potentials.fabric.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderFluidBlockCapabilityHolder;
import com.fej1fun.potentials.fabric.fluid.FabricFluidStorage;
import com.fej1fun.potentials.fabric.fluid.UniversalFluidVariantStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FluidBlockHolder implements NoProviderFluidBlockCapabilityHolder<UniversalFluidStorage, Direction> {
    public static final FluidBlockHolder INSTANCE = new FluidBlockHolder();
    private final BlockApiLookup<Storage<FluidVariant>, @Nullable Direction> blockApiLookup = FluidStorage.SIDED;

    @Override
    public UniversalFluidStorage getCapability(Level level, BlockPos pos, Direction direction) {
        return getCapability(level, pos, null, null, direction);
    }

    @Override
    public @Nullable UniversalFluidStorage getCapability(Level level, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity, Direction direction) {
        Storage<FluidVariant> fluidStorage = blockApiLookup.find(level, pos, state, blockEntity, direction);
        return fluidStorage == null ? null : new UniversalFluidVariantStorage(fluidStorage);
    }

    @Override
    public void registerForBlock(Supplier<Block> block) {
        blockApiLookup.registerForBlocks((level, pos, state, blockEntity, direction) -> {
            if (blockEntity instanceof FluidProvider.BLOCK fluidBlock) {
                var fluid = fluidBlock.getFluidTank(direction);
                return fluid == null ? null : new FabricFluidStorage(fluid);
            }
            if (state.getBlock() instanceof FluidProvider.BLOCK fluidBlock) {
                var fluid = fluidBlock.getFluidTank(direction);
                return fluid == null ? null : new FabricFluidStorage(fluid);
            }
            return null;
        }, block.get());
    }

    @Override
    public void registerForBlockEntity(Supplier<BlockEntityType<?>> blockEntityType) {
        blockApiLookup.registerForBlockEntity((blockEntity, direction) -> {
            if (blockEntity instanceof FluidProvider.BLOCK fluidBlock) {
                var fluid = fluidBlock.getFluidTank(direction);
                return fluid == null ? null : new FabricFluidStorage(fluid);
            }
            return null;
        }, blockEntityType.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return FluidStorage.SIDED.getId();
    }
}
