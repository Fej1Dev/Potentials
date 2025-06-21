package com.fej1fun.potentials.fabric.capabilities.holders;

import com.fej1fun.potentials.Potentials;
import com.fej1fun.potentials.capabilities.types.NoProviderEntityCapabilityHolder;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fabric.energy.FabricEnergyStorage;
import com.fej1fun.potentials.fabric.energy.UniversalEnergyWrapper;
import com.fej1fun.potentials.providers.EnergyProvider;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

import java.util.function.Supplier;

public class EnergyEntityHolder implements NoProviderEntityCapabilityHolder<UniversalEnergyStorage, Direction> {
    public static final EnergyEntityHolder INSTANCE = new EnergyEntityHolder();
    EntityApiLookup<EnergyStorage, @Nullable Direction> entityApiLookup = EntityApiLookup.get(ResourceLocation.fromNamespaceAndPath(Potentials.MOD_ID, "sided_energy"), EnergyStorage.class, Direction.class);

    @Override
    public @Nullable UniversalEnergyStorage getCapability(Entity entity, Direction direction) {
        if (entity == null) return null;

        if (entity instanceof EnergyProvider.ENTITY provider)
            return provider.getEnergy(direction);

        EnergyStorage energyStorage = entityApiLookup.find(entity, direction);
        return energyStorage == null ? null : new UniversalEnergyWrapper(energyStorage);
    }

    @Override
    public void registerForEntity(Supplier<EntityType<? extends Entity>> entity) {
        entityApiLookup.registerForType((entity1, direction) -> {
            if (entity1 instanceof EnergyProvider.ENTITY provider) {
                UniversalEnergyStorage energy = provider.getEnergy(direction);
                return energy == null ? null : new FabricEnergyStorage(energy);
            }
            return null;
        }, entity.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return entityApiLookup.getId();
    }
}
