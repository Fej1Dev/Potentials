package com.absolutelyaryan.neoforge;


import net.minecraft.resources.ResourceLocation;

public class ModResources {
    // Define ResourceLocation instances for capabilities
    public static final ResourceLocation BLOCK_ENERGY_CAPABILITY =
            ResourceLocation.fromNamespaceAndPath("yourmodid", "block_energy");
    public static final ResourceLocation ITEM_ENERGY_CAPABILITY =
            ResourceLocation.fromNamespaceAndPath("yourmodid", "item_energy");

    // Prevent instantiation
    private ModResources() {}
}
