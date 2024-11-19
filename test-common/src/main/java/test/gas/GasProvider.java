package test.gas;

import net.minecraft.world.item.ItemStack;

public class GasProvider {
    public interface BLOCK {
        IGasStorage getGas();
    }

    public interface ITEM {
        IGasStorage getGas(ItemStack itemStack);
    }

}
