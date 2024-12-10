package com.fej1fun.neoforge;

import com.fej1fun.Potentials;
import com.fej1fun.neoforge.capabilities.Registerable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Mod(Potentials.MOD_ID)
public class PotentialsNeo {

    public PotentialsNeo(IEventBus bus){
        Potentials.init();
        bus.addListener(PotentialsNeo::registerCapabilities);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        Registerable.registerAll(event);
    }
}
