package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.potentials.fluid.UniversalFluidTank;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.fluid.NeoForgeFluidItem;
import com.fej1fun.potentials.neoforge.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FluidItemHolder implements NoProviderFluidItemCapabilityHolder<UniversalFluidTank, Void>, Registerable {
    public static final FluidItemHolder INSTANCE = new FluidItemHolder();
    private FluidItemHolder() {registerSelf();}

    private final List<Supplier<Item>> registeredItems = new ArrayList<>();

    @Override
    public UniversalFluidTank getCapability(ItemStack stack) {
        IFluidHandler fluidTank = stack.getCapability(Capabilities.FluidHandler.ITEM);
        return fluidTank == null ? null : List.of(new UniversalFluidStorage(fluidTank));
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
                var fluid = fluidItem.getFluidTank(stack);
                return fluid == null ? null : new NeoForgeFluidItem(stack, fluid);
            }
            return null;
        }, item.get()));
    }
}
