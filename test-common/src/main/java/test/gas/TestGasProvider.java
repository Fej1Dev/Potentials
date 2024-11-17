package test.gas;

import com.absolutelyaryan.capabilities.CapabilityProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class TestGasProvider implements CapabilityProvider {

    @Override
    @SuppressWarnings("unchecked")
    public IGasStorage getCapability(Object capability, Object context) {

        if (capability instanceof GasProvider.BLOCK provider) {
            if(provider.getGas((Direction) context) instanceof IGasStorage storage){
                return storage;
            }
            return null;
        }

        if (capability instanceof GasProvider.ITEM provider) {
            if(provider.getGas((ItemStack) context) instanceof IGasStorage storage){
                return storage;
            }
            return null;
        }
        return null;
    }
}
