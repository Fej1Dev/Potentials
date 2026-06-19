package com.fej1fun.potentials.neoforge.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.neoforge.FluidStackHooksForge;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UniversalFluidHandler implements UniversalFluidStorage {
    protected final ResourceHandler<FluidResource> fluidHandler;

    public UniversalFluidHandler(ResourceHandler<FluidResource> fluidHandler) {
        this.fluidHandler = fluidHandler;
    }

    @Override
    public int getTanks() {
        return fluidHandler.size();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return getFluidInTankSafe(tank);
    }

    @Override
    public long getTankCapacity(int tank) {
        return fluidHandler.getCapacityAsLong(tank, FluidResource.EMPTY);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return this.fluidHandler.isValid(tank, FluidResource.of(FluidStackHooksForge.toForge(stack)));
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        if (stack == null || stack.isEmpty()) {
            return 0;
        }

        try (Transaction transaction = Transaction.open(null)) {
            long inserted = fluidHandler.insert(FluidResource.of(FluidStackHooksForge.toForge(stack)), (int) stack.getAmount(), transaction);
            if (simulate || inserted == 0L) {
                transaction.close();
            } else {
                transaction.commit();
            }
            return inserted;
        }
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        if (stack == null || stack.isEmpty()) {
            return FluidStack.empty();
        }

        try (Transaction transaction = Transaction.open(null)) {
            long extracted = fluidHandler.extract(FluidResource.of(FluidStackHooksForge.toForge(stack)), (int) stack.getAmount(), transaction);
            if (simulate || extracted == 0L) {
                transaction.close();
            } else {
                transaction.commit();
            }
            return stack.copyWithAmount(extracted);
        }
    }

    @Override
    public @NotNull Iterator<FluidStack> iterator() {
        List<FluidStack> toReturn = new ArrayList<>();
        for (int i = 0; i < fluidHandler.size(); i++) {
            toReturn.add(getFluidInTankSafe(i));
        }
        return toReturn.iterator();
    }

    private FluidStack getFluidInTankSafe(int tank) {
        FluidResource resource = fluidHandler.getResource(tank);
        if (resource.isEmpty()) {
            return FluidStack.empty();
        }

        int amount = fluidHandler.getAmountAsInt(tank);
        if (amount <= 0) {
            return FluidStack.empty();
        }

        return FluidStackHooksForge.fromForge(resource.toStack(amount));
    }
}