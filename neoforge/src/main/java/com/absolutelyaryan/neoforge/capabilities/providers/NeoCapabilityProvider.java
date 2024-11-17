package com.absolutelyaryan.neoforge.capabilities.providers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class NeoCapabilityProvider<X extends ItemStack, Y> implements IBlockCapabilityProvider<X, Y> {


    @Override
    public @Nullable X getCapability(Level arg, BlockPos arg2, BlockState arg3, @Nullable BlockEntity arg4, Y object) {
        return null;
    }
}
