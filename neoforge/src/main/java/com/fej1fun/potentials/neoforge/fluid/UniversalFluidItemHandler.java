package com.fej1fun.potentials.neoforge.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UniversalFluidItemHandler implements UniversalFluidItemStorage {
    protected final IFluidHandlerItem fluidHandler;

    public UniversalFluidItemHandler(IFluidHandlerItem fluidHandler) {
        this.fluidHandler = fluidHandler;
    }

    @Override
    public int getTanks() {
        return fluidHandler.getTanks();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return FluidStackHooksForge.fromForge(fluidHandler.getFluidInTank(tank));
    }

    @Override
    public long getTankCapacity(int tank) {
        return fluidHandler.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return fluidHandler.isFluidValid(tank, FluidStackHooksForge.toForge(stack));
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        return fluidHandler.fill(FluidStackHooksForge.toForge(stack), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        return FluidStackHooksForge.fromForge(fluidHandler.drain(FluidStackHooksForge.toForge(stack), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE));
    }

    @Override
    public @NotNull Iterator<FluidStack> iterator() {
        List<FluidStack> toReturn = new ArrayList<>();
        for (int i = 0; i < fluidHandler.getTanks(); i++) {
            toReturn.add(FluidStackHooksForge.fromForge(fluidHandler.getFluidInTank(i)));
        }
        return toReturn.iterator();
    }

    @Override
    public ItemStack getContainer() {
        return fluidHandler.getContainer();
    }
}
