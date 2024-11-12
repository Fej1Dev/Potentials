package com.absolutelyaryan;

import com.absolutelyaryan.capabilities.CapabilityManager;

public final class SpaceEnergyCommon {
    public static final String MOD_ID = "space_energy_api";
    private static CapabilityManager capabilityManager;

    public static void init() {

    }

    public static void setCapabilityManager(CapabilityManager capabilityManager) {
        SpaceEnergyCommon.capabilityManager = capabilityManager;
    }

    public static CapabilityManager getCapabilityManager() {
        return capabilityManager;
    }
}
