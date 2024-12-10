package com.fej1fun.fabric;

import com.fej1fun.Potentials;
import net.fabricmc.api.ModInitializer;

public final class PotentialsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Potentials.init();
    }
}
