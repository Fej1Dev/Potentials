package test;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import test.gas.GasProvider;
import test.gas.IGasStorage;

public class TestGasProvider implements CapabilityProvider {

    @Override
    public IGasStorage getCapability(Object capability, Object context) {

        if (capability instanceof GasProvider.BLOCK provider) {
            return provider.getGas((Direction) context);
        }

        if (capability instanceof GasProvider.ITEM provider) {
            return provider.getGas((ItemStack) context);
        }

        return null;
    }
}
