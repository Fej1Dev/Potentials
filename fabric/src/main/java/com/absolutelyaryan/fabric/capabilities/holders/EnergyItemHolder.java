package com.absolutelyaryan.fabric.capabilities.holders;

import com.absolutelyaryan.capabilities.types.NoProviderItemCapabilityHolder;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fabric.energy.FabricEnergyStorage;
import com.absolutelyaryan.fabric.energy.UniversalEnergyWrapper;
import com.absolutelyaryan.providers.EnergyProvider;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

import java.util.Objects;
import java.util.function.Supplier;

public class EnergyItemHolder implements NoProviderItemCapabilityHolder<UniversalEnergyStorage, Void> {
    public static final EnergyItemHolder INSTANCE = new EnergyItemHolder();
    ItemApiLookup<EnergyStorage, ContainerItemContext> itemApiLookup = EnergyStorage.ITEM;

    @Override
    public @Nullable UniversalEnergyStorage getCapability(ItemStack stack) {
        EnergyStorage energyStorage = itemApiLookup.find(stack, null);
        return energyStorage == null ? null : new UniversalEnergyWrapper(energyStorage);
    }

    @Override
    public void registerForItem(Supplier<Item> item) {
        itemApiLookup.registerForItems((stack, ctx) -> {
            if (stack.getItem() instanceof EnergyProvider.ITEM provider) {
                return new FabricEnergyStorage(Objects.requireNonNull(provider.getEnergy(stack)));
            }
            return null;
        }, item.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return EnergyStorage.ITEM.getId();
    }
}
