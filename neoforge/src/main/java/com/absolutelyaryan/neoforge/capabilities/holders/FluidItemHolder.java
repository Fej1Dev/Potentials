package com.absolutelyaryan.neoforge.capabilities.holders;

import com.absolutelyaryan.capabilities.types.NoProviderItemCapabilityHolder;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.neoforge.capabilities.Registerable;
import com.absolutelyaryan.neoforge.fluid.NeoForgeFluidItem;
import com.absolutelyaryan.neoforge.fluid.UniversalFluidStorage;
import com.absolutelyaryan.providers.FluidProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FluidItemHolder implements NoProviderItemCapabilityHolder<UniversalFluidTank, Void>, Registerable {
    public static final FluidItemHolder INSTANCE = new FluidItemHolder();
    private FluidItemHolder() {registerSelf();}

    private final List<Supplier<Item>> registeredItems = new ArrayList<>();

    @Override
    public @Nullable UniversalFluidTank getCapability(ItemStack stack) {
        IFluidHandler fluidTank = stack.getCapability(Capabilities.FluidHandler.ITEM);
        return fluidTank == null ? null : new UniversalFluidStorage(fluidTank);
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
        registeredItems.forEach(item ->
                event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> {
                    if (stack.getItem() instanceof FluidProvider.ITEM fluidItem) {
                        var fluid = fluidItem.getFluidTank(stack);
                        return fluid == null ? null : new NeoForgeFluidItem(stack, fluid);
                    }
                    return null;
                }, item.get()));
    }
}
