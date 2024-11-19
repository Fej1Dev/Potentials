package com.absolutelyaryan.neoforge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReflectionUtil {

    public static  <T, C> void registerBlock(Block block, BlockCapability<T, C> capability, IBlockCapabilityProvider<T, C> provider) {
        try {
            Field providersField = BlockCapability.class.getDeclaredField("providers");
            providersField.setAccessible(true);

            @SuppressWarnings("unchecked")
            var providers = (Map<Block, List<IBlockCapabilityProvider<T, C>>>) providersField.get(capability);
            providers.computeIfAbsent(block, b -> new ArrayList<>()).add(provider);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception if the field is not accessible
        }

    }

    public static  <T, C> void registerItem(Item item, ItemCapability<T, C> capability, ICapabilityProvider<ItemStack, T, C> provider) {
        try {
            Field providersField = ItemCapability.class.getDeclaredField("providers");
            providersField.setAccessible(true);

            @SuppressWarnings("unchecked")
            var providers = (Map<Item, List<ICapabilityProvider<ItemStack, ?, ?>>>) providersField.get(capability);
            providers.computeIfAbsent(item, b -> new ArrayList<>()).add(provider);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static  <T, C, E extends Entity> void registerEntity(EntityType<E> type, EntityCapability<T, C> capability, ICapabilityProvider<? super E, T, C> provider) {
        try {
            Field providersField = EntityCapability.class.getDeclaredField("providers");
            providersField.setAccessible(true);

            @SuppressWarnings("unchecked")
            var providers = (Map<EntityType<?>, List<ICapabilityProvider<? super E, ?, ?>>>) providersField.get(capability);
            providers.computeIfAbsent(type, b -> new ArrayList<>()).add(provider);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    public static  <T, C, BE extends BlockEntity> void registerBlockEntity(BlockCapability<T, C> capability, BlockEntityType<BE> blockEntityType, ICapabilityProvider<? super BE, C, T> provider) {
        Objects.requireNonNull(provider);

        @SuppressWarnings("unchecked")
        IBlockCapabilityProvider<T, C> adaptedProvider = (level, pos, state, blockEntity, context) -> {
            if (blockEntity == null || blockEntity.getType() != blockEntityType)
                return null;

            return provider.getCapability((BE) blockEntity, context);
        };

        for (Block block : blockEntityType.getValidBlocks()) {
            Objects.requireNonNull(block);


            try {
                Field providersField = EntityCapability.class.getDeclaredField("providers");
                providersField.setAccessible(true);

                @SuppressWarnings("unchecked")
                var providers = (Map<Block, List<IBlockCapabilityProvider<T, C>>>) providersField.get(capability);
                providers.computeIfAbsent(block, b -> new ArrayList<>()).add(adaptedProvider);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }


}
