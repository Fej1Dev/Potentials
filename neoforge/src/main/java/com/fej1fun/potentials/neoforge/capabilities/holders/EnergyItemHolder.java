package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderItemCapabilityHolder;
import com.fej1fun.potentials.energy.BaseEnergyStorage;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.energy.NeoForgeEnergyStorage;
import com.fej1fun.potentials.neoforge.energy.UniversalIEnergyStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class EnergyItemHolder implements NoProviderItemCapabilityHolder<UniversalEnergyStorage, Void>, Registerable {
    public static final EnergyItemHolder INSTANCE = new EnergyItemHolder();
    private EnergyItemHolder() {registerSelf();}
    private final Set<Supplier<Item>> registeredItems = new HashSet<>();

    @Override
    public @Nullable UniversalEnergyStorage getCapability(ItemStack stack) {
        EnergyHandler energyStorage = stack.getCapability(Capabilities.Energy.ITEM, null);
        return energyStorage == null ? null : new UniversalIEnergyStorage(energyStorage);
    }

    @Override
    public void registerForItem(Supplier<Item> item) {
        registeredItems.add(item);
    }

    @Override
    public Identifier getIdentifier() {
        return Capabilities.Energy.ITEM.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredItems.forEach(item -> event.registerItem(Capabilities.Energy.ITEM, (stack, ctx) -> {
            if (stack.getItem() instanceof EnergyProvider.ITEM energyItem) {
                UniversalEnergyStorage energy = energyItem.getEnergy(stack);
                return energy == null ? null : new NeoForgeEnergyStorage((BaseEnergyStorage) energy);
            }
            return null;
        }, item.get()));

    }
}
