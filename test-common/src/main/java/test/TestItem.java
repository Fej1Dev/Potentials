package test;

import com.absolutelyaryan.energy.ItemEnergyStorage;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TestItem extends Item implements EnergyProvider.ITEM, FluidProvider.ITEM {

    UniversalFluidTank tank;
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
    public UniversalEnergyStorage getEnergy(ItemStack stack) {
        return new ItemEnergyStorage(stack, capacity, maxReceive, maxExtract);
    }

    @Override
    public UniversalFluidTank getFluidTank(ItemStack stack) {
        return tank;
    }
}
