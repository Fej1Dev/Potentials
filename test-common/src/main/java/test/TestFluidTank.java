package test;

import com.absolutelyaryan.fluid.BaseFluidTank;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.providers.FluidProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class TestFluidTank extends BlockEntity implements FluidProvider.BLOCK {
    HashMap<Direction, UniversalFluidTank> tanks = new HashMap<>();


    public TestFluidTank(BlockPos blockPos, BlockState blockState) {
        super(TestMain.TEST_BLOCK_ENTITY_TYPE.get(), blockPos, blockState);
        for (Direction direction : Direction.values()) {
            tanks.put(direction, new BaseFluidTank(1000));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        for(Direction direction : Direction.values()) {
            compoundTag.putLong(direction + "_amount", tanks.get(direction).getFluidValue());
            compoundTag.putLong(direction + "_maxAmount", tanks.get(direction).getMaxAmount());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        for(Direction direction : Direction.values()) {
            tanks.get(direction).setFluidValue(compoundTag.getLong(direction + "_amount"));
            tanks.get(direction).setMaxAmount(compoundTag.getLong(direction + "_maxAmount"));
        }
    }

    @Override
    public UniversalFluidTank getFluidTank(@Nullable Direction direction) {
        return tanks.get(direction);
    }
}
