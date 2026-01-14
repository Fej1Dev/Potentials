package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderEntityCapabilityHolder;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.fluid.NeoForgeFluidStorage;
import com.fej1fun.potentials.neoforge.fluid.UniversalFluidHandler;
import com.fej1fun.potentials.providers.FluidProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class FluidEntityHolder implements NoProviderEntityCapabilityHolder<UniversalFluidStorage, Direction>, Registerable {
    public static final FluidEntityHolder INSTANCE = new FluidEntityHolder();
    private FluidEntityHolder() {registerSelf();}

    private final Set<Supplier<EntityType<? extends Entity>>> registeredEntities = new HashSet<>();

    @Override
    public @Nullable UniversalFluidStorage getCapability(Entity entity, Direction direction) {
        ResourceHandler<FluidResource> fluidTank = entity.getCapability(Capabilities.Fluid.ENTITY, direction);
        return fluidTank == null ? null : new UniversalFluidHandler(fluidTank);
    }

    @Override
    public void registerForEntity(Supplier<EntityType<? extends Entity>> entity) {
        registeredEntities.add(entity);
    }

    @Override
    public Identifier getIdentifier() {
        return Capabilities.Fluid.BLOCK.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredEntities.forEach(entityType -> event.registerEntity(Capabilities.Fluid.ENTITY,
                entityType.get(), (entity, ctx) -> {
                    if (entity instanceof FluidProvider.ENTITY provider) {
                        UniversalFluidStorage fluidTank = provider.getFluidTank(ctx);
                        return fluidTank == null ? null : new NeoForgeFluidStorage(fluidTank);
                    }
                    return null;
                }));
    }
}
