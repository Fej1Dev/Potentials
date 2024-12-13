package com.fej1fun.potentials.fabric;

import com.fej1fun.potentials.Potentials;
import net.fabricmc.api.ModInitializer;

public final class PotentialsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Potentials.init();
    }
}
