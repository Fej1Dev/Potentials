package test.gas;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class GasProvider {
    public interface BLOCK {
        IGasStorage getGas(Direction direction);
    }

    public interface ITEM {
        IGasStorage getGas(ItemStack itemStack);
    }

}
