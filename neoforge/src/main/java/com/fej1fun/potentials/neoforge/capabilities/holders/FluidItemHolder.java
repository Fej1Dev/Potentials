package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.fluid.NeoForgeFluidStorage;
import com.fej1fun.potentials.neoforge.fluid.UniversalFluidItemHandler;
import com.fej1fun.potentials.providers.FluidProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class FluidItemHolder implements NoProviderFluidItemCapabilityHolder<UniversalFluidItemStorage, Void>, Registerable {
    public static final FluidItemHolder INSTANCE = new FluidItemHolder();
    private FluidItemHolder() {registerSelf();}

    private final Set<Supplier<Item>> registeredItems = new HashSet<>();

    @Override
    public UniversalFluidItemStorage getCapability(ItemStack stack) {
        ResourceHandler<FluidResource> fluidTank = stack.getCapability(Capabilities.Fluid.ITEM, null);
        return fluidTank == null ? null : new UniversalFluidItemHandler((NeoForgeFluidStorage) fluidTank, stack);
    }

    @Override
    public void registerForItem(Supplier<Item> item) {
        registeredItems.add(item);
    }

    @Override
    public ResourceLocation getIdentifier() {
        return Capabilities.Fluid.ITEM.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredItems.forEach(item -> event.registerItem(Capabilities.Fluid.ITEM, (stack, ctx) -> {
            if (stack.getItem() instanceof FluidProvider.ITEM fluidItem) {
                UniversalFluidItemStorage fluid = fluidItem.getFluidTank(stack);
                return fluid == null ? null : new NeoForgeFluidStorage(fluid);
            }
            return null;
        }, item.get()));
    }
}
