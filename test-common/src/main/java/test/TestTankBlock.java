package test;

import com.fej1fun.potentials.fluid.BaseFluidTank;
import com.fej1fun.potentials.fluid.UniversalFluidTank;
import com.fej1fun.potentials.providers.FluidProvider;
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
