package com.fej1fun.potentials.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FluidAmountMapDataComponent implements Serializable {

    @SuppressWarnings("all")
    public static final Codec<FluidAmountMapDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.FLUID.holderByNameCodec().listOf().fieldOf("fluids")
                    .forGetter(component ->
                        getListFromStream(component.fluids.stream().map(fluid -> (Holder<Fluid>) fluid.builtInRegistryHolder()))
                    ),
            Codec.LONG.listOf().fieldOf("amounts").forGetter(component -> component.amounts)
    ).apply(instance, FluidAmountMapDataComponent::create));
    @SuppressWarnings("all")
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidAmountMapDataComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.FLUID).apply(ByteBufCodecs.list()), component ->
                    getListFromStream(component.fluids.stream().map(fluid-> (Holder<Fluid>) fluid.builtInRegistryHolder())),
            ByteBufCodecs.VAR_LONG.apply(ByteBufCodecs.list()), component -> getListFromStream(component.amounts.stream()),
            FluidAmountMapDataComponent::create
    );

    protected final List<Fluid> fluids;
    protected final List<Long> amounts;

    public FluidAmountMapDataComponent(final Map<Fluid, Long> fluidAmounts) {
        if (fluidAmounts.isEmpty()) throw new IllegalArgumentException("Map<Fluid, Long> Cannot be empty");
        this.fluids = getListFromStream(fluidAmounts.keySet().stream());
        this.amounts = getListFromStream(fluidAmounts.values().stream());
    }
    public FluidAmountMapDataComponent(final List<Fluid> fluids, final List<Long> amounts) {
        if (fluids.isEmpty()) throw new IllegalArgumentException("List<Fluid> Cannot be empty");
        if (amounts.isEmpty()) throw new IllegalArgumentException("List<Long> Cannot be empty");
        if (fluids.size() != amounts.size()) throw new IllegalArgumentException("List<Fluid> must be the same length as List<Long>");

        this.fluids = fluids;
        this.amounts = amounts;
    }

    public static FluidAmountMapDataComponent create(List<Holder<Fluid>> holders, List<Long> amounts) {
        if (holders.isEmpty()) throw new IllegalArgumentException("List<Holder<Fluid>> Cannot be empty");
        if (amounts.isEmpty()) throw new IllegalArgumentException("List<Long> Cannot be empty");
        if (holders.size() != amounts.size()) throw new IllegalArgumentException("List<Holder<Fluid>> must be the same length as List<Long>");

        return new FluidAmountMapDataComponent(getListFromStream(holders.stream().map(Holder::value)), amounts);
    }
    public static FluidAmountMapDataComponent create(final List<FluidStack> fluidStacks) {
        if (fluidStacks.isEmpty()) throw new IllegalArgumentException("List<FluidStack> Cannot be empty");

        return new FluidAmountMapDataComponent(fluidStacks.stream().collect(Collectors.toMap(FluidStack::getFluid, FluidStack::getAmount)));
    }
    public static FluidAmountMapDataComponent emptyWithSize(int size) {
        List<Fluid> fluids = NonNullList.withSize(size, Fluids.EMPTY);
        List<Long> amounts = NonNullList.withSize(size, 0L);
        return new FluidAmountMapDataComponent(fluids, amounts);
    }

    public List<FluidStack> asFluidStackList() {
        ArrayList<FluidStack> stacks = new ArrayList<>(this.fluids.size());
        for (int i = 0; i < this.fluids.size(); i++)
            stacks.add(getAsFluidStack(i));
        stacks.trimToSize();

        return Collections.unmodifiableList(stacks);
    }

    public Fluid getFluid(int i) {
        return this.fluids.get(i);
    }

    public long getAmount(int i) {
        return this.amounts.get(i);
    }

    public FluidStack getAsFluidStack(int i) {
        return FluidStack.create(getFluid(i), getAmount(i));
    }

    public void setFluid(int i, Fluid fluid) {
        this.fluids.set(i, fluid);
    }

    public void setAmount(int i, Long amount) {
        this.amounts.set(i, amount);
    }

    public void setFluidStack(int i, FluidStack stack) {
        setFluid(i, stack.getFluid());
        setAmount(i, stack.getAmount());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FluidAmountMapDataComponent component)
            return component.fluids.equals(this.fluids) && component.amounts.equals(this.amounts);
        return super.equals(obj);
    }

    private static <T> List<T> getListFromStream(Stream<T> s) {
        ArrayList<T> list = new ArrayList<>(0);
        s.forEach(list::add);
        list.trimToSize();
        return list;
    }
}
