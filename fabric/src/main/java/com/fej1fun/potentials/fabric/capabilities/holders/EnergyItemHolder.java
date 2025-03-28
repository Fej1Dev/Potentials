package com.fej1fun.potentials.fabric.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderItemCapabilityHolder;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fabric.energy.FabricEnergyStorage;
import com.fej1fun.potentials.fabric.energy.UniversalEnergyWrapper;
import com.fej1fun.potentials.fabric.utils.ItemStackStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

import java.util.function.Supplier;

public class EnergyItemHolder implements NoProviderItemCapabilityHolder<UniversalEnergyStorage, Void> {
    public static final EnergyItemHolder INSTANCE = new EnergyItemHolder();
    ItemApiLookup<EnergyStorage, ContainerItemContext> itemApiLookup = EnergyStorage.ITEM;

    @Override
    public @Nullable UniversalEnergyStorage getCapability(ItemStack stack) {
        if (stack.getItem() instanceof EnergyProvider.ITEM provider)
            return provider.getEnergy(stack);

        EnergyStorage energyStorage = itemApiLookup.find(stack, ContainerItemContext.ofSingleSlot(new ItemStackStorage(stack)));
        return energyStorage == null ? null : new UniversalEnergyWrapper(energyStorage);
    }

    @Override
    public void registerForItem(Supplier<Item> item) {
        itemApiLookup.registerForItems((stack, ctx) -> {
            if (stack.getItem() instanceof EnergyProvider.ITEM provider) {
                UniversalEnergyStorage energy = provider.getEnergy(stack);
                return energy == null ? null : new FabricEnergyStorage(energy);
            }
            return null;
        }, item.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return EnergyStorage.ITEM.getId();
    }
}
