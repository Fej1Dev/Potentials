package com.absolutelyaryan.fluid;


public interface UniversalFluidTank {

    int getFluidValue();
    Object getFluidObject();
    int getMaxAmount();
    boolean isValid();
    int fillFluid(int amount);
    int drainFluid(int amount);


}
