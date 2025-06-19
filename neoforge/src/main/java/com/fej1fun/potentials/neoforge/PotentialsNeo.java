package com.fej1fun.potentials.neoforge;

import com.fej1fun.potentials.Potentials;
import com.fej1fun.potentials.neoforge.capabilities.Registerable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Potentials.MOD_ID)
public class PotentialsNeo {

    public PotentialsNeo(IEventBus bus){
        Potentials.init();
        bus.addListener(Registerable::registerAll);
    }

}
