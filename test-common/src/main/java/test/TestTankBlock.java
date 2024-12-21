package test;

import com.fej1fun.potentials.fluid.BaseFluidStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class TestTankBlock extends Block implements FluidProvider.BLOCK {
    UniversalFluidStorage tank = new BaseFluidStorage(5, 1000);

    public TestTankBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction) {
        return tank;
    }
}
