package com.fej1fun.potentials.neoforge.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.neoforge.FluidStackHooksForge;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntSupplier;

public class NeoForgeFluidStorage implements ResourceHandler<FluidResource> {

    private final UniversalFluidStorage storage;

    public NeoForgeFluidStorage(@NotNull final UniversalFluidStorage storage) {
        this.storage = storage;
    }

    @Override
    public int size() {
        return storage.getTanks();
    }

    @Override
    public FluidResource getResource(int tank) {
        FluidStack fluidStack = storage.getFluidInTank(tank);
        return fluidStack.isEmpty() ? FluidResource.EMPTY : FluidResource.of(FluidStackHooksForge.toForge(fluidStack));
    }

    @Override
    public long getAmountAsLong(int tank) {
        return storage.getFluidInTank(tank).getAmount();
    }

    @Override
    public long getCapacityAsLong(int tank, FluidResource resource) {
        return storage.getTankCapacity(tank);
    }

    @Override
    public boolean isValid(int tank, FluidResource resource) {
        return storage.isFluidValid(tank, toArchitecturyStack(resource, getAmountAsInt(tank)));
    }

    @Override
    public int insert(int tank, FluidResource resource, int amount, TransactionContext transactionContext) {
        if (resource.isEmpty() || amount <= 0) {
            return 0;
        }
        return runInTransaction(transactionContext,
                () -> Math.toIntExact(storage.fill(toArchitecturyStack(resource, amount), false)));
    }

    @Override
    public int extract(int tank, FluidResource resource, int amount, TransactionContext transactionContext) {
        if (resource.isEmpty() || amount <= 0) {
            return 0;
        }
        return runInTransaction(transactionContext,
                () -> Math.toIntExact(storage.drain(toArchitecturyStack(resource, amount), false).getAmount()));
    }

    private FluidStack toArchitecturyStack(FluidResource resource, int amount) {
        return FluidStackHooksForge.fromForge(resource.toStack(amount));
    }

    private int runInTransaction(TransactionContext transactionContext, IntSupplier operation) {
        try (Transaction tx = Transaction.open(transactionContext)) {
            int transferred = operation.getAsInt();
            tx.commit();
            return transferred;
        }
    }
}
