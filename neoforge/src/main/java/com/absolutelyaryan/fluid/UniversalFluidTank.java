package com.absolutelyaryan.fluid;

import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public interface UniversalFluidTank extends IFluidTank  {


    Fluid getBaseFluid();
    long getFluidValue();
    dev.architectury.fluid.FluidStack getFluidStack();
    long getMaxAmount();
    boolean isValid(dev.architectury.fluid.FluidStack stack);
    long fillFluid(dev.architectury.fluid.FluidStack stack, boolean simulate);
    long drainFluid(dev.architectury.fluid.FluidStack stack, boolean simulate);


    default FluidStack getStorage(){
        return getFluid();
    }



    @Override
    @NotNull
    FluidStack getFluid();

    @Override
    default int getFluidAmount(){
       return (int) getFluidValue();
    }


    @Override
    default int getCapacity(){
        return (int) getMaxAmount();
    }
    @Override
    default boolean isFluidValid(@NotNull FluidStack fluidStack){
        return isValid(FluidStackHooksForge.fromForge(fluidStack));
    }

    @Override
    default int fill(@NotNull FluidStack fluidStack, IFluidHandler.@NotNull FluidAction fluidAction){
        return (int) fillFluid(FluidStackHooksForge.fromForge(fluidStack), fluidAction == IFluidHandler.FluidAction.EXECUTE);
    }
    @Override
    @NotNull
    default FluidStack drain(int i, IFluidHandler.@NotNull FluidAction fluidAction){
        getFluid().setAmount(((int) drainFluid(FluidStackHooksForge.fromForge(new FluidStack(getBaseFluid(), i)), fluidAction == IFluidHandler.FluidAction.EXECUTE)));
        return getFluid();
    }
    @Override
    @NotNull
    default FluidStack drain(@NotNull FluidStack fluidStack, IFluidHandler.@NotNull FluidAction fluidAction){
        getFluid().setAmount(((int) drainFluid(FluidStackHooksForge.fromForge(fluidStack), fluidAction == IFluidHandler.FluidAction.EXECUTE)));
        return getFluid();
    }
}
