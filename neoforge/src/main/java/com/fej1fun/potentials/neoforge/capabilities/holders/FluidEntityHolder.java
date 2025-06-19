package com.fej1fun.potentials.neoforge.capabilities.holders;

import com.fej1fun.potentials.capabilities.types.NoProviderEntityCapabilityHolder;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import com.fej1fun.potentials.neoforge.fluid.NeoForgeFluidStorage;
import com.fej1fun.potentials.neoforge.fluid.UniversalFluidHandler;
import com.fej1fun.potentials.providers.FluidProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FluidEntityHolder implements NoProviderEntityCapabilityHolder<UniversalFluidStorage, Direction>, Registerable {
    public static final FluidEntityHolder INSTANCE = new FluidEntityHolder();
    private FluidEntityHolder() {registerSelf();}

    private final List<Supplier<EntityType<? extends Entity>>> registeredEntities = new ArrayList<>();

    @Override
    public @Nullable UniversalFluidStorage getCapability(Entity entity, Direction direction) {
        IFluidHandler fluidTank = entity.getCapability(Capabilities.FluidHandler.ENTITY, direction);
        return fluidTank == null ? null : new UniversalFluidHandler(fluidTank);
    }

    @Override
    public void registerForEntity(Supplier<EntityType<? extends Entity>> entity) {
        registeredEntities.add(entity);
    }

    @Override
    public ResourceLocation getIdentifier() {
        return Capabilities.FluidHandler.BLOCK.name();
    }

    @Override
    public void register(RegisterCapabilitiesEvent event) {
        registeredEntities.forEach(entityType -> event.registerEntity(Capabilities.FluidHandler.ENTITY,
                entityType.get(), (entity, ctx) -> {
                    if (entity instanceof FluidProvider.ENTITY provider) {
                        UniversalFluidStorage fluidTank = provider.getFluidTank(ctx);
                        return fluidTank == null ? null : new NeoForgeFluidStorage(fluidTank);
                    }
                    return null;
                }));
    }
}
