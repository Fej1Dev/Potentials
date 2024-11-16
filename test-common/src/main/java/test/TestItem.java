package test;

import com.absolutelyaryan.energy.ItemEnergyStorage;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.ItemFluidTank;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import test.gas.GasTank;
import test.gas.IGasStorage;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import test.gas.GasProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TestItem extends Item implements EnergyProvider.ITEM, FluidProvider.ITEM, GasProvider.ITEM {

    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public TestItem(Properties properties, int capacity, int maxReceive, int maxExtract) {
        super(properties);
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    public UniversalEnergyStorage getEnergy(@NotNull ItemStack stack) {
        return new ItemEnergyStorage(stack, TestMain.ENERGY.get(), capacity, maxReceive, maxExtract);
    }

    @Override
    public UniversalFluidTank getFluidTank(@NotNull ItemStack stack) {
        return new ItemFluidTank(stack, TestMain.FLUID.get(), 1000);
    }

    @Override
    public IGasStorage getGas(ItemStack itemStack) {
        return new GasTank();
    }
}
