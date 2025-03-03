package com.fej1fun.potentials.fabric.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.potentials.fabric.fluid.FabricFluidItemStorage;
import com.fej1fun.potentials.fabric.fluid.UniversalFluidItemVariantStorage;
import com.fej1fun.potentials.fabric.utils.ItemStackStorage;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import com.fej1fun.potentials.providers.FluidProvider;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class FluidItemHolder implements NoProviderFluidItemCapabilityHolder<UniversalFluidItemStorage, Void> {
    public static final FluidItemHolder INSTANCE = new FluidItemHolder();
    private final ItemApiLookup<Storage<FluidVariant>, ContainerItemContext> itemApiLookup = FluidStorage.ITEM;

    @Override
    public UniversalFluidItemStorage getCapability(ItemStack stack) {
        if (stack.getItem() instanceof FluidProvider.ITEM provider)
            return provider.getFluidTank(stack);

        ContainerItemContext context = ContainerItemContext.ofSingleSlot(new ItemStackStorage(stack));
        Storage<FluidVariant> fluidStorage = itemApiLookup.find(stack, context);
        return fluidStorage == null ? null : new UniversalFluidItemVariantStorage(fluidStorage, context);
    }

    @Override
    public void registerForItem(Supplier<Item> item) {
        itemApiLookup.registerForItems((stack, context) -> {
            if (stack.getItem() instanceof FluidProvider.ITEM fluidItem) {
                UniversalFluidItemStorage fluid = fluidItem.getFluidTank(stack);
                return fluid == null ? null : new FabricFluidItemStorage(fluid);
            }
            return null;
        }, item.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return FluidStorage.ITEM.getId();
    }
}
