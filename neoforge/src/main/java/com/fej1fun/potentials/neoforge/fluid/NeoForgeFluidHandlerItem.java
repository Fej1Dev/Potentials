package com.fej1fun.potentials.neoforge.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

public class NeoForgeFluidHandlerItem implements IFluidHandlerItem {
    private final UniversalFluidStorage fluidStorage;
    private final ItemStack stack;

    public NeoForgeFluidHandlerItem(@NotNull final UniversalFluidStorage fluidStorage, @NotNull final ItemStack stack) {
        this.fluidStorage = fluidStorage;
        this.stack = stack;
    }

    @Override
    public @NotNull ItemStack getContainer() {
        return stack;
    }

    @Override
    public int getTanks() {
        return fluidStorage.getTanks();
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int i) {
        return FluidStackHooksForge.toForge(fluidStorage.getFluidInTank(i));
    }

    @Override
    public int getTankCapacity(int i) {
        return (int) fluidStorage.getTankCapacity(i);
    }

    @Override
    public boolean isFluidValid(int i, @NotNull FluidStack fluidStack) {
        return fluidStorage.isFluidValid(i, FluidStackHooksForge.fromForge(fluidStack));
    }

    @Override
    public int fill(@NotNull FluidStack fluidStack, FluidAction fluidAction) {
        return (int) fluidStorage.fill(FluidStackHooksForge.fromForge(fluidStack), fluidAction.simulate());
    }

    @Override
    public @NotNull FluidStack drain(@NotNull FluidStack fluidStack, FluidAction fluidAction) {
        return FluidStackHooksForge.toForge(fluidStorage.drain(FluidStackHooksForge.fromForge(fluidStack), fluidAction.simulate()));
    }

    @Override
    public @NotNull FluidStack drain(int i, FluidAction fluidAction) {
        return FluidStackHooksForge.toForge(fluidStorage.drain(i, fluidAction.simulate()));
    }
}
