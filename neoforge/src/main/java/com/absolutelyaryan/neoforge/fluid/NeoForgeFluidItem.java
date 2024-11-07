package com.absolutelyaryan.neoforge.fluid;

import com.absolutelyaryan.fluid.UniversalFluidTank;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

public class NeoForgeFluidItem implements IFluidHandlerItem {
    private final UniversalFluidTank universalFluidTank;
    private final ItemStack itemStack;

    public NeoForgeFluidItem(ItemStack itemStack, UniversalFluidTank universalFluidTank) {
        this.universalFluidTank = universalFluidTank;
        this.itemStack = itemStack;
    }

    @Override
    public ItemStack getContainer() {
        return itemStack;
    }
    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int i) {
        return FluidStackHooksForge.toForge(universalFluidTank.getFluidStack());
    }

    @Override
    public int getTankCapacity(int i) {
        return (int) universalFluidTank.getMaxAmount();
    }

    @Override
    public boolean isFluidValid(int i, @NotNull FluidStack fluidStack) {
        return universalFluidTank.isValid(FluidStackHooksForge.fromForge(fluidStack));
    }

    @Override
    public int fill(@NotNull FluidStack fluidStack, IFluidHandler.@NotNull FluidAction fluidAction){
        return (int) universalFluidTank.fillFluid(FluidStackHooksForge.fromForge(fluidStack), fluidAction == IFluidHandler.FluidAction.EXECUTE);
    }
    @Override
    @NotNull
    public FluidStack drain(int i, IFluidHandler.@NotNull FluidAction fluidAction){
        universalFluidTank.getFluidStack().setAmount(((int) universalFluidTank.drainFluid(FluidStackHooksForge.fromForge(new FluidStack(universalFluidTank.getBaseFluid(), i)), fluidAction == IFluidHandler.FluidAction.EXECUTE)));
        return FluidStackHooksForge.toForge(universalFluidTank.getFluidStack());
    }
    @Override
    @NotNull
    public FluidStack drain(@NotNull FluidStack fluidStack, IFluidHandler.@NotNull FluidAction fluidAction){
        universalFluidTank.getFluidStack().setAmount(((int) universalFluidTank.drainFluid(FluidStackHooksForge.fromForge(fluidStack), fluidAction == IFluidHandler.FluidAction.EXECUTE)));
        return FluidStackHooksForge.toForge(universalFluidTank.getFluidStack());
    }
}
