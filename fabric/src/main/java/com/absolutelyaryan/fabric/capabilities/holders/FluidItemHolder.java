package com.absolutelyaryan.fabric.capabilities.holders;

import com.absolutelyaryan.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.absolutelyaryan.capabilities.types.NoProviderItemCapabilityHolder;
import com.absolutelyaryan.fabric.fluid.FluidStorageWrapper;
import com.absolutelyaryan.fabric.fluid.SingleVariantTank;
import com.absolutelyaryan.fabric.fluid.StorageViewWrapper;
import com.absolutelyaryan.fabric.fluid.UniversalFluidWrapper;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.providers.FluidProvider;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class FluidItemHolder implements NoProviderFluidItemCapabilityHolder<UniversalFluidTank, Void> {
    public static final FluidItemHolder INSTANCE = new FluidItemHolder();
    private final ItemApiLookup<Storage<FluidVariant>, ContainerItemContext> itemApiLookup = FluidStorage.ITEM;

    @Override
    public @Nullable List<UniversalFluidTank> getCapability(ItemStack stack) {
        Storage<FluidVariant> fluidStorage = itemApiLookup.find(stack, null);
        List<UniversalFluidTank> tanks = new ArrayList<>();
        if (fluidStorage == null) return null;
        fluidStorage.iterator().forEachRemaining(storageView -> {
            UniversalFluidTank tank = new StorageViewWrapper(fluidStorage, storageView);
            tanks.add(tank);
        });
        return Collections.unmodifiableList(tanks);
    }

    @Override
    public void registerForItem(Supplier<Item> item) {
        itemApiLookup.registerForItems((stack, context) -> {
            if (stack.getItem() instanceof FluidProvider.ITEM fluidItem) {
                var fluid = fluidItem.getFluidTank(stack);
                return fluid == null ? null : new SingleVariantTank(fluid);
            }
            return null;
        }, item.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return FluidStorage.ITEM.getId();
    }
}
