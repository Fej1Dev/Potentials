package test;

import com.absolutelyaryan.capabilities.Capabilities;
import com.absolutelyaryan.energy.ItemEnergyStorage;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.ItemFluidTank;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import test.gas.GasProvider;
import test.gas.GasTank;
import test.gas.IGasStorage;

public class TestItem extends Item implements EnergyProvider.ITEM, FluidProvider.ITEM, GasProvider.ITEM {

    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public TestItem(Properties properties, int capacity, int maxReceive, int maxExtract) {
        super(properties);
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    public UniversalEnergyStorage getEnergy(@NotNull ItemStack stack) {
        return new ItemEnergyStorage(stack, TestMain.ENERGY.get(), capacity, maxReceive, maxExtract);
    }

    @Override
    public UniversalFluidTank getFluidTank(@NotNull ItemStack stack) {
        return new ItemFluidTank(stack, TestMain.FLUID.get(), 1000);
    }

    @Override
    public IGasStorage getGas(ItemStack stack) {
        return new GasTank();
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if (!level.isClientSide()) {
            BlockPos pos = useOnContext.getClickedPos();
            Direction direction = useOnContext.getClickedFace();
            UniversalFluidTank fluid = Capabilities.Fluid.BLOCK.getCapability(level, pos, direction);
            if (fluid != null && useOnContext.getPlayer() != null) {
                useOnContext.getPlayer().sendSystemMessage(Component.literal(fluid.getBaseFluid().defaultFluidState().toString()));
                useOnContext.getPlayer().sendSystemMessage(Component.literal(fluid.getFluidValue() + "/" + fluid.getMaxAmount()));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
