package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderEntityCapabilityHolder;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.energy.NeoForgeEnergyStorage;
import com.fej1fun.potentials.neoforge.energy.UniversalIEnergyStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EnergyEntityHolder implements NoProviderEntityCapabilityHolder<UniversalEnergyStorage, Direction>, Registerable {
    public static final EnergyEntityHolder INSTANCE = new EnergyEntityHolder();
    private EnergyEntityHolder() {registerSelf();}

    private final List<Supplier<EntityType<? extends Entity>>> registeredEntities = new ArrayList<>();

    @Override
    public @Nullable UniversalEnergyStorage getCapability(Entity entity, Direction direction) {
        if (entity == null) return null;
        IEnergyStorage energyStorage = entity.getCapability(Capabilities.EnergyStorage.ENTITY, direction);
        return energyStorage == null ? null : new UniversalIEnergyStorage(energyStorage);
    }

    @Override
    public void registerForEntity(Supplier<EntityType<? extends Entity>> entity) {
        registeredEntities.add(entity);
    }

    @Override
    public ResourceLocation getIdentifier() {
        return Capabilities.EnergyStorage.ENTITY.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredEntities.forEach(entityType -> event.registerEntity(Capabilities.EnergyStorage.ENTITY,
                entityType.get(), (entity, ctx) -> {
            if (entity instanceof EnergyProvider.ENTITY provider) {
                UniversalEnergyStorage energy = provider.getEnergy(ctx);
                return energy == null ? null : new NeoForgeEnergyStorage(energy);
            }
            return null;
        }));
    }
}
