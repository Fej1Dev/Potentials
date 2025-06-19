package com.fej1fun.potentials.fabric.capabilities.holders;

import com.fej1fun.potentials.Potentials;
import com.fej1fun.potentials.capabilities.types.NoProviderEntityCapabilityHolder;
import com.fej1fun.potentials.fabric.fluid.FabricFluidStorage;
import com.fej1fun.potentials.fabric.fluid.UniversalFluidVariantStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FluidEntityHolder implements NoProviderEntityCapabilityHolder<UniversalFluidStorage, Direction> {
    public static final FluidEntityHolder INSTANCE = new FluidEntityHolder();
    private final EntityApiLookup<Storage<FluidVariant>, @Nullable Direction> entityApiLookup =
            EntityApiLookup.get(ResourceLocation.fromNamespaceAndPath(Potentials.MOD_ID, "entity_fluid_storage"), Storage.asClass(), Direction.class);

    @Override
    public UniversalFluidStorage getCapability(Entity entity, Direction direction) {
        if (entity == null) return null;

        if (entity instanceof FluidProvider.ENTITY provider)
            return provider.getFluidTank(direction);

        Storage<FluidVariant> storage = entityApiLookup.find(entity, direction);
        return storage == null ? null : new UniversalFluidVariantStorage(storage);
    }

    @Override
    public void registerForEntity(Supplier<EntityType<? extends Entity>> entity) {
        entityApiLookup.registerForType((entity1, context) -> {
            if (entity1 instanceof FluidProvider.ENTITY fluidItem) {
                UniversalFluidStorage fluid = fluidItem.getFluidTank(context);
                return fluid == null ? null : new FabricFluidStorage(fluid);
            }
            return null;
        }, entity.get());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return FluidStorage.ITEM.getId();
    }
}
