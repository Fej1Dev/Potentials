package com.fej1fun.potentials.fluid;

import com.fej1fun.potentials.Potentials;
import dev.architectury.fluid.FluidStack;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public final class FluidSnapshots {
    private static final Logger LOGGER = LoggerFactory.getLogger(Potentials.MOD_ID);

    public static List<FluidStack> take(UniversalFluidStorage storage) {
        List<FluidStack> snapshot = new ArrayList<>(storage.getTanks());
        for (int i = 0; i < storage.getTanks(); i++)
            snapshot.add(storage.getFluidInTank(i).copy());
        return snapshot;
    }

    public static void restore(UniversalFluidStorage storage, List<FluidStack> snapshot) {
        try {
            for (int i = 0; i < snapshot.size(); i++)
                storage.setFluidInTank(i, snapshot.get(i).copy());
        } catch (UnsupportedOperationException e) {
            LOGGER.warn("{} does not implement setFluidInTank, simulated or aborted transfers won't be reverted", storage.getClass().getName());
        }
    }

    private FluidSnapshots() {

    }
}
