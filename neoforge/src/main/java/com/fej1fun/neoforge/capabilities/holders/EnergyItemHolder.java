package com.fej1fun.neoforge.capabilities.holders;

import com.fej1fun.capabilities.types.NoProviderItemCapabilityHolder;
import com.fej1fun.energy.UniversalEnergyStorage;
import com.fej1fun.neoforge.capabilities.Registerable;
import com.fej1fun.neoforge.energy.NeoForgeEnergyStorage;
import com.fej1fun.neoforge.energy.UniversalIEnergyStorage;
import com.fej1fun.providers.EnergyProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EnergyItemHolder implements NoProviderItemCapabilityHolder<UniversalEnergyStorage, Void>, Registerable {
    public static final EnergyItemHolder INSTANCE = new EnergyItemHolder();
    private EnergyItemHolder() {registerSelf();}
    private final List<Supplier<Item>> registeredItems = new ArrayList<>();

    @Override
    public @Nullable UniversalEnergyStorage getCapability(ItemStack stack) {
        IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        return energyStorage == null ? null : new UniversalIEnergyStorage(energyStorage);
    }

    @Override
    public void registerForItem(Supplier<Item> item) {
        registeredItems.add(item);
    }

    @Override
    public ResourceLocation getIdentifier() {
        return Capabilities.EnergyStorage.ITEM.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredItems.forEach(item -> event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> {
            if (stack.getItem() instanceof EnergyProvider.ITEM energyItem) {
                var energy = energyItem.getEnergy(stack);
                return energy == null ? null : new NeoForgeEnergyStorage(energy);
            }
            return null;
        }, item.get()));

    }
}
