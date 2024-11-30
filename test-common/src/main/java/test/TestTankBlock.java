package test;

import com.absolutelyaryan.fluid.BaseFluidTank;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.providers.FluidProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class TestTankBlock extends Block implements FluidProvider.BLOCK {
    UniversalFluidTank tank = new BaseFluidTank(1000);

    public TestTankBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable UniversalFluidTank getFluidTank(@Nullable Direction direction) {
        return tank;
    }
}
