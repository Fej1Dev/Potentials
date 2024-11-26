package com.absolutelyaryan;

import com.absolutelyaryan.capabilities.CapabilityManager;
import org.jetbrains.annotations.ApiStatus;

public final class SpaceEnergyCommon {
    public static final String MOD_ID = "space_energy_api";
    private static CapabilityManager capabilityManager;

    public static void init() {
    }

    @ApiStatus.Internal
    public static void setCapabilityManager(CapabilityManager capabilityManager) {
        SpaceEnergyCommon.capabilityManager = capabilityManager;
    }

    private static CapabilityManager getCapabilityManager() {
        return capabilityManager;
    }
}
