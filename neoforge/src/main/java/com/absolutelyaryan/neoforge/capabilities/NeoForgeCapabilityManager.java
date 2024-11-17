package com.absolutelyaryan.neoforge.capabilities;

import com.absolutelyaryan.capabilities.CapabilityManager;
import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.EntityCapabilityHolder;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import com.absolutelyaryan.neoforge.capabilities.types.NeoBlockHolder;
import com.absolutelyaryan.neoforge.capabilities.types.NeoEntityHolder;
import com.absolutelyaryan.neoforge.capabilities.types.NeoItemHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

import java.util.HashMap;

public class NeoForgeCapabilityManager implements CapabilityManager {

    HashMap<ResourceLocation, NeoBlockHolder<?, ?>> blockCapabilityHolders = new HashMap<>();
    HashMap<ResourceLocation, NeoItemHolder<?, ?>> itemCapabilityHolders = new HashMap<>();
    HashMap<ResourceLocation, NeoEntityHolder<?, ?>> entityCapabilityHolders = new HashMap<>();


    @Override
    public void registerBlockEnergy(Block block) {

    }

    @Override
    public void registerItemEnergy(Item item) {

    }

    @Override
    public void registerBlockEntityEnergy(BlockEntityType<?> entity) {

    }

    @Override
    public void registerBlockFluid(Block block) {

    }

    @Override
    public void registerItemFluid(Item item) {

    }

    @Override
    public void registerBlockEntityFluid(BlockEntityType<?> entity) {

    }

    @Override
    public <X, Y> BlockCapabilityHolder<X, Y> registerSidedCapability(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier) {
        NeoBlockHolder<X, Y> blockCapabilityHolder = new NeoBlockHolder<>(BlockCapability.create(identifier, apiClass, contextClass));
        blockCapabilityHolders.put(identifier, blockCapabilityHolder);
        return blockCapabilityHolder;
    }

    @Override
    public <X, Y> ItemCapabilityHolder<X, Y> registerItemCapability(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier) {
        NeoItemHolder<X, Y> itemCapabilityHolder = new NeoItemHolder<>(ItemCapability.create(identifier, apiClass, contextClass));
        itemCapabilityHolders.put(identifier, itemCapabilityHolder);
        return itemCapabilityHolder;
    }

    @Override
    public <X, Y> EntityCapabilityHolder<X, Y> registerEntityCapability(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier) {
        NeoEntityHolder<X, Y> entityCapabilityHolder = new NeoEntityHolder<>(EntityCapability.create(identifier, apiClass, contextClass));
        entityCapabilityHolders.put(identifier, entityCapabilityHolder);
        return entityCapabilityHolder;
    }

    public HashMap<ResourceLocation, NeoBlockHolder<?, ?>> getBlockCapabilityHolders() {
        return blockCapabilityHolders;
    }

    public HashMap<ResourceLocation, NeoEntityHolder<?, ?>> getEntityCapabilityHolders() {
        return entityCapabilityHolders;
    }

    public HashMap<ResourceLocation, NeoItemHolder<?, ?>> getItemCapabilityHolders() {
        return itemCapabilityHolders;
    }
}
