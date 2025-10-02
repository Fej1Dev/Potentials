package com.fej1fun.potentials.neoforge.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UniversalFluidItemHandler implements UniversalFluidItemStorage {
    protected final NeoForgeFluidStorage fluidHandler;
    protected final ItemStack stack;

    public UniversalFluidItemHandler(NeoForgeFluidStorage fluidHandler, ItemStack stack) {
        this.fluidHandler = fluidHandler;
        this.stack = stack;
    }

    @Override
    public int getTanks() {
        return fluidHandler.size();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return FluidStackHooksForge.fromForge(fluidHandler.getResource(tank).toStack(fluidHandler.getAmountAsInt(tank)));
    }

    @Override
    public long getTankCapacity(int tank) {
        return fluidHandler.getCapacityAsLong(tank, fluidHandler.getResource(tank));
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        //return fluidHandler.isFluidValid(tank, FluidStackHooksForge.toForge(stack)); TODO implement this
        return true;
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        return fluidHandler.insert(FluidResource.of(FluidStackHooksForge.toForge(stack)), (int) stack.getAmount(), null);
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        return stack.copyWithAmount(fluidHandler.extract(FluidResource.of(FluidStackHooksForge.toForge(stack)), (int) stack.getAmount(), null));
    }

    @Override
    public @NotNull Iterator<FluidStack> iterator() {
        List<FluidStack> toReturn = new ArrayList<>();
        for (int i = 0; i < fluidHandler.size(); i++) {
            toReturn.add(FluidStackHooksForge.fromForge(fluidHandler.getResource(i).toStack(fluidHandler.getAmountAsInt(i))));
        }
        return toReturn.iterator();
    }

    @Override
    public ItemStack getContainer() {
        return stack;
    }
}
