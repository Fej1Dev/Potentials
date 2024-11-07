package test;

import com.absolutelyaryan.energy.BaseEnergyStorage;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.BaseFluidTank;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class TestBlockEntity extends BlockEntity implements EnergyProvider.BLOCK, FluidProvider.BLOCK {
    HashMap<Direction, BaseFluidTank> tanks = new HashMap<>();
    private final BaseEnergyStorage energy = new BaseEnergyStorage(1024, 1024, 1024);

    public TestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TestMain.TEST_BLOCK_ENTITY_TYPE.get(), blockPos, blockState);
        for (Direction direction : Direction.values()) {
            tanks.put(direction, new BaseFluidTank(1000));
        }
    }


    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.putInt("energy", energy.getEnergy());
        Tag tag;
        for(Direction direction : Direction.values()) {
            tag = new CompoundTag();
            tag = FluidStackHooks.write(provider, this.tanks.get(direction).getFluidStack(), tag);
            compoundTag.put("fluid", tag);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        energy.setEnergyStored(compoundTag.getInt("energy"));
        for(Direction direction : Direction.values()) {
            FluidStack.read(provider, compoundTag).ifPresent(this.tanks.get(direction)::setFluidStack);
        }
    }

    @Override
    public UniversalEnergyStorage getEnergy(@Nullable Direction direction) {
        return energy;
    }

    @Override
    public UniversalFluidTank getFluidTank(@Nullable Direction direction) {
        return tanks.get(direction);
    }
}