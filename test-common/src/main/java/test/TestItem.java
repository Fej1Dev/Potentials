package test;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.fej1fun.potentials.energy.ItemEnergyStorage;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fluid.ItemFluidStorage;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import com.fej1fun.potentials.providers.FluidProvider;
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
        return new ItemEnergyStorage(stack, capacity, maxReceive, maxExtract);
    }

    @Override
    public UniversalFluidItemStorage getFluidTank(@NotNull ItemStack stack) {
        return new ItemFluidStorage(stack, 1, 10000);
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
            UniversalFluidStorage fluids = Capabilities.Fluid.BLOCK.getCapability(level, pos, direction);
            if (useOnContext.getPlayer() != null && fluids!=null) {
                useOnContext.getPlayer().sendSystemMessage(Component.literal("Tanks: " + fluids.getTanks()));
                for (int i = 0; i < fluids.getTanks(); i++) {
                    useOnContext.getPlayer().sendSystemMessage(Component.literal(fluids.getFluidInTank(i).getFluid().defaultFluidState().toString()));
                    useOnContext.getPlayer().sendSystemMessage(Component.literal(fluids.getFluidInTank(i).getAmount() + "/" + fluids.getTankCapacity(i)));
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, tooltipContext, list, tooltipFlag);
        UniversalEnergyStorage energy = getEnergy(stack);
        UniversalFluidStorage fluid = getFluidTank(stack);
        if (energy!=null)
            list.add(Component.literal("Energy: " + energy.getEnergy() + "/" + energy.getMaxEnergy()));
        if (fluid!=null)
            for (int i = 0; i < fluid.getTanks(); i++)
                list.add(Component.literal("Fluid: " + fluid.getFluidInTank(i).getAmount() + "/" + fluid.getTankCapacity(i)));
        UniversalEnergyStorage capabilityTestEnergy = Capabilities.Energy.ITEM.getCapability(stack);
        UniversalFluidStorage capabilityTestFluid  = Capabilities.Fluid.ITEM.getCapability(stack);
        if (capabilityTestEnergy!=null)
            list.add(Component.literal("Energy capability: " + capabilityTestEnergy.getEnergy() + "/" + capabilityTestEnergy.getMaxEnergy()));
        if (capabilityTestFluid!=null)
            for (int i = 0; i < capabilityTestFluid.getTanks(); i++)
                list.add(Component.literal("Fluid capability: " + capabilityTestFluid.getFluidInTank(i).getAmount() + "/" + capabilityTestFluid.getTankCapacity(i)));
    }
}
