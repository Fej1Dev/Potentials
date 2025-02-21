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
import java.util.*;
import java.util.stream.Collectors;

public class FluidAmountListDataComponent implements Serializable {

    @SuppressWarnings("all")
    public static final Codec<FluidAmountListDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.FLUID.holderByNameCodec().listOf().fieldOf("fluids")
                    .forGetter(component ->
                            component.fluids.stream().map(fluid -> (Holder<Fluid>) fluid.builtInRegistryHolder()).toList()),
            Codec.LONG.listOf().fieldOf("amounts").forGetter(component -> component.amounts.stream().toList())
    ).apply(instance, FluidAmountListDataComponent::create));
    @SuppressWarnings("all")
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidAmountListDataComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.FLUID).apply(ByteBufCodecs.list()), component ->
                    component.fluids.stream().map(fluid-> (Holder<Fluid>) fluid.builtInRegistryHolder()).toList(),
            ByteBufCodecs.VAR_LONG.apply(ByteBufCodecs.list()), component -> component.amounts.stream().toList(),
            FluidAmountListDataComponent::create
    );

    protected final List<Fluid> fluids;
    protected final List<Long> amounts;


    public FluidAmountListDataComponent(final Map<Fluid, Long> fluidAmounts) {
        if (fluidAmounts.isEmpty()) throw new IllegalArgumentException("Map<Fluid, Long> Cannot be empty");

        this.fluids = fluidAmounts.keySet().stream().toList();
        this.amounts = fluidAmounts.values().stream().toList();
    }
    public FluidAmountListDataComponent(final List<Fluid> fluids, final List<Long> amounts) {
        if (fluids.isEmpty()) throw new IllegalArgumentException("List<Fluid> Cannot be empty");
        if (amounts.isEmpty()) throw new IllegalArgumentException("List<Long> Cannot be empty");
        if (fluids.size() != amounts.size()) throw new IllegalArgumentException("List<Fluid> must be the same length as List<Long>");

        this.fluids = fluids;
        this.amounts = amounts;
    }

    public static FluidAmountListDataComponent create(List<Holder<Fluid>> holders, List<Long> amounts) {
        if (holders.isEmpty()) throw new IllegalArgumentException("List<Holder<Fluid>> Cannot be empty");
        if (amounts.isEmpty()) throw new IllegalArgumentException("List<Long> Cannot be empty");
        if (holders.size() != amounts.size()) throw new IllegalArgumentException("List<Holder<Fluid>> must be the same length as List<Long>");

        return new FluidAmountListDataComponent(holders.stream().map(Holder::value).toList(), amounts);
    }
    public static FluidAmountListDataComponent create(final List<FluidStack> fluidStacks) {
        if (fluidStacks.isEmpty()) throw new IllegalArgumentException("List<FluidStack> Cannot be empty");

        return new FluidAmountListDataComponent(fluidStacks.stream().collect(Collectors.toMap(FluidStack::getFluid, FluidStack::getAmount)));
    }
    public static FluidAmountListDataComponent emptyWithSize(int size) {
        List<Fluid> fluids = NonNullList.withSize(size, Fluids.EMPTY);
        List<Long> amounts = NonNullList.withSize(size, 0L);
        return new FluidAmountListDataComponent(fluids, amounts);
    }

    public List<FluidStack> asFluidStackList() {
        List<FluidStack> stacks = new ArrayList<>(0);
        for (int i = 0; i < this.fluids.size(); i++)
            stacks.add(getAsFluidStack(i));

        return Collections.unmodifiableList(stacks);
    }

    public Fluid getFluid(int i) {
        return this.fluids.get(i);
    }

    public long getAmount(int i) {
        return this.amounts.get(i);
    }

    public FluidStack getAsFluidStack(int i) {
        return FluidStack.create(this.fluids.get(i), this.amounts.get(i));
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

}
