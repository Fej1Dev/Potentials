package com.fej1fun.potentials.neoforge.fluid;

import com.fej1fun.potentials.fluid.FluidSnapshots;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.neoforge.utils.DeferredJournal;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NeoForgeFluidStorage extends SnapshotJournal<List<FluidStack>> implements ResourceHandler<FluidResource> {

    private final UniversalFluidStorage storage;
    private final DeferredJournal deferredJournal = new DeferredJournal();

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
        if (storage.deferUntilCommit()) {
            int filled = Math.toIntExact(storage.fill(toArchitecturyStack(resource, amount), true));
            if (filled > 0) {
                deferredJournal.defer(transactionContext, () -> storage.fill(toArchitecturyStack(resource, filled), false));
            }
            return filled;
        }
        updateSnapshots(transactionContext);
        return Math.toIntExact(storage.fill(toArchitecturyStack(resource, amount), false));
    }

    @Override
    public int extract(int tank, FluidResource resource, int amount, TransactionContext transactionContext) {
        if (resource.isEmpty() || amount <= 0) {
            return 0;
        }
        if (storage.deferUntilCommit()) {
            int drained = Math.toIntExact(storage.drain(toArchitecturyStack(resource, amount), true).getAmount());
            if (drained > 0) {
                deferredJournal.defer(transactionContext, () -> storage.drain(toArchitecturyStack(resource, drained), false));
            }
            return drained;
        }
        updateSnapshots(transactionContext);
        return Math.toIntExact(storage.drain(toArchitecturyStack(resource, amount), false).getAmount());
    }

    private FluidStack toArchitecturyStack(FluidResource resource, int amount) {
        return FluidStackHooksForge.fromForge(resource.toStack(amount));
    }

    @Override
    protected List<FluidStack> createSnapshot() {
        return FluidSnapshots.take(storage);
    }

    @Override
    protected void revertToSnapshot(List<FluidStack> snapshot) {
        FluidSnapshots.restore(storage, snapshot);
    }
}
