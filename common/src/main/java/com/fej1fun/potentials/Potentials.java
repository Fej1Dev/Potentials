package com.fej1fun.potentials;

import com.fej1fun.potentials.registry.DataComponetRegistry;

public final class Potentials {
    public static final String MOD_ID = "potentials";

    public static void init() {
        DataComponetRegistry.DATA_COMPONENT_TYPE.register();
    }
}
