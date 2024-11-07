package test;

import com.absolutelyaryan.fluid.BaseFluidTank;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.providers.FluidProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class TestFluidTank extends BlockEntity implements FluidProvider.BLOCK {
    HashMap<Direction, UniversalFluidTank> tanks = new HashMap<>();


    public TestFluidTank(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        for (Direction direction : Direction.values()) {
            tanks.put(direction, new BaseFluidTank(1000, 1000));
        }
    }

    @Override
    public UniversalFluidTank getFluidTank(@Nullable Direction direction) {
        return tanks.get(direction);
    }


    //when using in fabric it will return SingleVariantStorage<FluidVariant> but in neoforge it will return UniversalFluidTank

}
