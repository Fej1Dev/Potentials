//package test;
//
//import com.absolutelyaryan.fluid.BaseFluidTank;
//import com.absolutelyaryan.fluid.UniversalFluidTank;
//import com.absolutelyaryan.providers.FluidProvider;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//
//public class TestFluidItemTank extends Item implements FluidProvider.ITEM {
//    UniversalFluidTank tank;
//    public TestFluidItemTank(Properties properties) {
//        super(properties);
//        tank = new BaseFluidTank(1000, 1000);
//    }
//
//    @Override
//    public UniversalFluidTank getFluidTank(ItemStack stack) {
//        return tank;
//    }
//}
