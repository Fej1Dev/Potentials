package com.fej1fun.potentials.neoforge.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

public class NeoForgeFluidStorage implements ResourceHandler<FluidResource> {

    final UniversalFluidStorage fluidStorage;

    public NeoForgeFluidStorage(@NotNull final UniversalFluidStorage storage) {
        this.fluidStorage = storage;
    }

    @Override
    public int size() {
        return fluidStorage.getTanks();
    }

    @Override
    public FluidResource getResource(int tank) {
        FluidStack stack = fluidStorage.getFluidInTank(tank);
        return stack.isEmpty() ? FluidResource.EMPTY : FluidResource.of(FluidStackHooksForge.toForge(stack));
    }

    @Override
    public long getAmountAsLong(int tank) {
        return fluidStorage.getFluidInTank(tank).getAmount();
    }

    @Override
    public long getCapacityAsLong(int tank, FluidResource resource) {
        return fluidStorage.getTankCapacity(tank);
    }

    @Override
    public boolean isValid(int tank, FluidResource resource) {
        return fluidStorage.isFluidValid(tank, FluidStackHooksForge.fromForge(resource.toStack(getAmountAsInt(tank))));
    }

    @Override
    public int insert(int tank, FluidResource resource, int amount, TransactionContext transactionContext) {
        if (resource.isEmpty()) {
            return 0;
        }
        try (Transaction tx = Transaction.open(null)) {
            int inserted = Math.toIntExact(fluidStorage.fill(FluidStackHooksForge.fromForge(resource.toStack(amount)), false));
            tx.commit();
            return inserted;
        }
    }

    @Override
    public int extract(int tank, FluidResource resource, int amount, TransactionContext transactionContext) {
        if (resource.isEmpty()) {
            return 0;
        }
        try (Transaction tx = Transaction.open(null)) {
            FluidStack extracted = fluidStorage.drain(FluidStackHooksForge.fromForge(resource.toStack(amount)), false);
            tx.commit();
            return Math.toIntExact(extracted.getAmount());
        }
    }
}
