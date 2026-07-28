package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderEntityCapabilityHolder;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.energy.NeoForgeEnergyStorage;
import com.fej1fun.potentials.neoforge.energy.UniversalIEnergyStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class EnergyEntityHolder implements NoProviderEntityCapabilityHolder<UniversalEnergyStorage, Direction>, Registerable {
    public static final EnergyEntityHolder INSTANCE = new EnergyEntityHolder();
    private EnergyEntityHolder() {registerSelf();}

    private final Set<Supplier<? extends EntityType<? extends Entity>>> registeredEntities = new HashSet<>();

    @Override
    public @Nullable UniversalEnergyStorage getCapability(Entity entity, Direction direction) {
        if (entity == null) return null;
        EnergyHandler energyStorage = entity.getCapability(Capabilities.Energy.ENTITY, direction);
        return energyStorage == null ? null : new UniversalIEnergyStorage(energyStorage);
    }

    @Override
    public <T extends Entity> void registerForEntity(Supplier<EntityType<T>> entity) {
        registeredEntities.add(entity);
    }

    @Override
    public Identifier getIdentifier() {
        return Capabilities.Energy.ENTITY.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredEntities.forEach(entityType -> event.registerEntity(Capabilities.Energy.ENTITY,
                entityType.get(), (entity, ctx) -> {
            if (entity instanceof EnergyProvider.ENTITY provider) {
                UniversalEnergyStorage energy = provider.getEnergy(ctx);
                return energy == null ? null : new NeoForgeEnergyStorage(energy);
            }
            return null;
        }));
    }
}
