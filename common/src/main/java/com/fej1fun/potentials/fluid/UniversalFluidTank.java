package com.fej1fun.potentials.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.ApiStatus;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")
public interface UniversalFluidTank {

    Fluid getBaseFluid();
    long getFluidValue();
    FluidStack getFluidStack();
    long getMaxAmount();
    boolean isValid(FluidStack stack);
    long fillFluid(FluidStack stack, boolean simulate);
    long drainFluid(FluidStack stack, boolean simulate);

}
