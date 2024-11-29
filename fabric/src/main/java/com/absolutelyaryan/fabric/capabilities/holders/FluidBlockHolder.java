package com.absolutelyaryan.fabric.capabilities.holders;

import com.absolutelyaryan.capabilities.types.NoProviderBlockCapabilityHolder;
import com.absolutelyaryan.fabric.fluid.SingleVariantTank;
import com.absolutelyaryan.fabric.fluid.UniversalFluidWrapper;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.providers.FluidProvider;
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

public class FluidBlockHolder implements NoProviderBlockCapabilityHolder<UniversalFluidTank, Direction> {
    public static final FluidBlockHolder INSTANCE = new FluidBlockHolder();
    private final BlockApiLookup<Storage<FluidVariant>, @Nullable Direction> blockApiLookup = FluidStorage.SIDED;

    @Override
    public @Nullable UniversalFluidTank getCapability(Level level, BlockPos pos, Direction context) {
        Storage<FluidVariant> fluidStorage = blockApiLookup.find(level, pos, context);
        return fluidStorage instanceof SingleVariantTank tank ? new UniversalFluidWrapper(tank) : null;
    }

    @Override
    public @Nullable UniversalFluidTank getCapability(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Direction context) {
        return getCapability(level, pos, context);
    }

    @Override
    public void registerForBlock(Supplier<Block> block) {
        blockApiLookup.registerForBlocks((level, pos, state, blockEntity, direction) -> {
            if (blockEntity instanceof FluidProvider.BLOCK fluidBlock) {
                var fluid = fluidBlock.getFluidTank(direction);
                return fluid == null ? null : new SingleVariantTank(fluid);
            }
            if (state.getBlock() instanceof FluidProvider.BLOCK fluidBlock) {
                var fluid = fluidBlock.getFluidTank(direction);
                return fluid == null ? null : new SingleVariantTank(fluid);
            }
            return null;
        }, block.get());
    }

    @Override
    public void registerForBlockEntity(Supplier<BlockEntityType<?>> blockEntityType) {
        blockApiLookup.registerForBlockEntity((blockEntity, direction) -> {
            if (blockEntity instanceof FluidProvider.BLOCK fluidBlock) {
                var fluid = fluidBlock.getFluidTank(direction);
                return fluid == null ? null : new SingleVariantTank(fluid);
            }
            return null;
        }, blockEntityType.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return FluidStorage.SIDED.getId();
    }
}
