package testmod.gas;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GasProvider {
    public interface BLOCK {
        @Nullable IGasStorage getGas();
    }

    public interface ITEM {
        @Nullable IGasStorage getGas(ItemStack stack);
    }

}
