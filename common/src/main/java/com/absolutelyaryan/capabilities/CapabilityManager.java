package com.absolutelyaryan.capabilities;

import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.EntityCapabilityHolder;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface CapabilityManager {
    void registerBlockEnergy(Block block);
    void registerItemEnergy(Item item);
    void registerBlockEntityEnergy(BlockEntityType<?> entity);
    void registerBlockFluid(Block block);
    void registerItemFluid(Item item);
    void registerBlockEntityFluid(BlockEntityType<?> entity);


    <X, Y> BlockCapabilityHolder<X,Y> registerBlockCapability(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier);
    <X, Y> ItemCapabilityHolder<X,Y> registerItemCapability(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier);
    <X, Y> EntityCapabilityHolder<X,Y> registerEntityCapability(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier);



}
