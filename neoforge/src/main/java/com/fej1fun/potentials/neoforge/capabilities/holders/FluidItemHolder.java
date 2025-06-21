package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.fluid.NeoForgeFluidHandlerItem;
import com.fej1fun.potentials.neoforge.fluid.UniversalFluidItemHandler;
import com.fej1fun.potentials.providers.FluidProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class FluidItemHolder implements NoProviderFluidItemCapabilityHolder<UniversalFluidItemStorage, Void>, Registerable {
    public static final FluidItemHolder INSTANCE = new FluidItemHolder();
    private FluidItemHolder() {registerSelf();}

    private final Set<Supplier<Item>> registeredItems = new HashSet<>();

    @Override
    public UniversalFluidItemStorage getCapability(ItemStack stack) {
        IFluidHandlerItem fluidTank = stack.getCapability(Capabilities.FluidHandler.ITEM);
        return fluidTank == null ? null : new UniversalFluidItemHandler(fluidTank);
    }

    @Override
    public void registerForItem(Supplier<Item> item) {
        registeredItems.add(item);
    }

    @Override
    public ResourceLocation getIdentifier() {
        return Capabilities.FluidHandler.ITEM.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredItems.forEach(item -> event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> {
            if (stack.getItem() instanceof FluidProvider.ITEM fluidItem) {
                UniversalFluidItemStorage fluid = fluidItem.getFluidTank(stack);
                return fluid == null ? null : new NeoForgeFluidHandlerItem(fluid);
            }
            return null;
        }, item.get()));
    }
}
