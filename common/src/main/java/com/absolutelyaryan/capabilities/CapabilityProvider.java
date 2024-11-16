package com.absolutelyaryan.capabilities;

import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public interface CapabilityProvider {
   Object getCapability(Object itemInstance, Object context);
}
