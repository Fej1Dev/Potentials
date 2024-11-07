package test;

import com.absolutelyaryan.fluid.BaseFluidTank;
import com.absolutelyaryan.fluid.ItemFluidTank;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.providers.FluidProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TestFluidItemTank extends Item implements FluidProvider.ITEM {
    public TestFluidItemTank(Properties properties) {
        super(properties);
    }

    @Override
    public UniversalFluidTank getFluidTank(ItemStack stack) {
        return new ItemFluidTank(stack, 1000);
    }
}
