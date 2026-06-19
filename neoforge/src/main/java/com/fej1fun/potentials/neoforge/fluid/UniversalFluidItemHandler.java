package com.fej1fun.potentials.neoforge.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.neoforge.FluidStackHooksForge;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UniversalFluidItemHandler implements UniversalFluidItemStorage {
    protected final ResourceHandler<FluidResource> fluidHandler;
    protected final ItemStack container;
    protected final @Nullable ItemAccess itemAccess;

    public UniversalFluidItemHandler(ResourceHandler<FluidResource> fluidHandler, ItemStack stack) {
        this(fluidHandler, stack, null);
    }

    public UniversalFluidItemHandler(ResourceHandler<FluidResource> fluidHandler, ItemStack stack, @Nullable ItemAccess itemAccess) {
        this.fluidHandler = fluidHandler;
        this.container = stack;
        this.itemAccess = itemAccess;
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
        try {
            return fluidHandler.getCapacityAsLong(tank, FluidResource.EMPTY);
        } catch (NullPointerException ignored) {
            return 0L;
        }
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return fluidHandler.isValid(tank, FluidResource.of(FluidStackHooksForge.toForge(stack)));
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        return transfer(stack, simulate, true);
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        return stack.copyWithAmount(transfer(stack, simulate, false));
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
        try {
            FluidResource resource = fluidHandler.getResource(tank);
            if (resource.isEmpty()) {
                return FluidStack.empty();
            }

            int amount = fluidHandler.getAmountAsInt(tank);
            if (amount <= 0) {
                return FluidStack.empty();
            }

            return FluidStackHooksForge.fromForge(resource.toStack(amount));
        } catch (NullPointerException ignored) {
            return FluidStack.empty();
        }
    }

    private boolean shouldTryBucketAmount(int requestedAmount, FluidStack requestedStack) {
        long bucketAmount = FluidStack.bucketAmount();
        if (requestedAmount <= 0 || requestedAmount >= bucketAmount || getTanks() != 1) {
            return false;
        }

        if (getTankCapacity(0) != bucketAmount) {
            return false;
        }

        FluidStack contained = getFluidInTankSafe(0);
        return !contained.isEmpty()
                && contained.getAmount() == bucketAmount
                && contained.isFluidEqual(requestedStack);
    }

    @Override
    public ItemStack getContainer() {
        if (itemAccess != null) {
            return itemAccess.getResource().toStack(itemAccess.getAmount());
        }
        return container;
    }

    private long transfer(FluidStack requestedStack, boolean simulate, boolean inserting) {
        try (Transaction transaction = Transaction.open(null)) {
            int requestedAmount = Math.toIntExact(Math.min(Integer.MAX_VALUE, requestedStack.getAmount()));
            FluidResource requestedResource = FluidResource.of(FluidStackHooksForge.toForge(requestedStack));

            long transferred = inserting
                    ? fluidHandler.insert(requestedResource, requestedAmount, transaction)
                    : fluidHandler.extract(requestedResource, requestedAmount, transaction);

            if (transferred == 0L && shouldTryBucketAmount(requestedAmount, requestedStack)) {
                transferred = inserting
                        ? fluidHandler.insert(requestedResource, Math.toIntExact(FluidStack.bucketAmount()), transaction)
                        : fluidHandler.extract(requestedResource, Math.toIntExact(FluidStack.bucketAmount()), transaction);
            }

            if (simulate || transferred == 0L) {
                transaction.close();
            } else {
                transaction.commit();
            }
            return transferred;
        }
    }
}
