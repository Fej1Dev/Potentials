package com.absolutelyaryan.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

public interface FabricFluidTank {

    default long insert(FluidVariant fluid, long amount, Transaction transaction){
        return getVarStorage().insert(fluid, amount, transaction);
    }
    default long extract(FluidVariant fluid, long amount, Transaction transaction){
        return getVarStorage().extract(fluid, amount, transaction);
    }
    default long getAmount(){
        return getVarStorage().getAmount();
    }
    default long getCapacity(){
        return getVarStorage().getCapacity();
    }
    default boolean canInsert(){
        return getVarStorage().supportsInsertion();
    }
    default boolean canExtract(){
        return getVarStorage().supportsExtraction();
    }

    SingleVariantStorage<FluidVariant> getVarStorage();


}
