package com.absolutelyaryan.items;

import com.absolutelyaryan.SpaceEnergyCommon;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public class DataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(SpaceEnergyCommon.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final DeferredSupplier<DataComponentType<Integer>> ENERGY_DATA_COMPONENT = DATA_COMPONENTS.register("energy", () ->
            DataComponentType.<Integer>builder().build());

    public static final DeferredSupplier<DataComponentType<Long>> FLUID_VALUE_DATA_COMPONENT = DATA_COMPONENTS.register("fluid_value", () ->
            DataComponentType.<Long>builder().build());

}
