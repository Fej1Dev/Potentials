package test;

import com.fej1fun.capabilities.Capabilities;
import com.fej1fun.energy.ItemEnergyStorage;
import com.fej1fun.energy.UniversalEnergyStorage;
import com.fej1fun.fluid.ItemFluidTank;
import com.fej1fun.fluid.UniversalFluidTank;
import com.fej1fun.providers.EnergyProvider;
import com.fej1fun.providers.FluidProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import test.gas.GasProvider;
import test.gas.GasTank;
import test.gas.IGasStorage;

import java.util.List;

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
            List<UniversalFluidTank> fluids = Capabilities.Fluid.BLOCK.getCapability(level, pos, direction);
            if (useOnContext.getPlayer() != null && fluids!=null)
                for (UniversalFluidTank fluid : fluids) {
                useOnContext.getPlayer().sendSystemMessage(Component.literal(fluid.getBaseFluid().defaultFluidState().toString()));
                useOnContext.getPlayer().sendSystemMessage(Component.literal(fluid.getFluidValue() + "/" + fluid.getMaxAmount()));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, tooltipContext, list, tooltipFlag);
        UniversalEnergyStorage energy = getEnergy(stack);
        UniversalFluidTank fluid = getFluidTank(stack);
        if (energy!=null)
            list.add(Component.literal("Energy: " + energy.getEnergy() + "/" + energy.getMaxEnergy()));
        if (fluid!=null)
            list.add(Component.literal("Fluid: " + fluid.getFluidValue() + "/" + fluid.getMaxAmount()));
        UniversalEnergyStorage capabilityTestEnergy = Capabilities.Energy.ITEM.getCapability(stack);
        List<UniversalFluidTank> capabilityTestFluid = Capabilities.Fluid.ITEM.getCapability(stack);
        if (capabilityTestEnergy!=null)
            list.add(Component.literal("Energy capability: " + capabilityTestEnergy.getEnergy() + "/" + capabilityTestEnergy.getMaxEnergy()));
        if (capabilityTestFluid!=null)
            for (UniversalFluidTank tank : capabilityTestFluid)
                list.add(Component.literal("Fluid capability: " + tank.getFluidValue() + "/" + tank.getMaxAmount()));
    }
}
