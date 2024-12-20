package test;

import com.fej1fun.potentials.energy.BaseEnergyStorage;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fluid.BaseFluidTank;
import com.fej1fun.potentials.fluid.UniversalFluidTank;
import com.fej1fun.potentials.providers.EnergyProvider;
import com.fej1fun.potentials.providers.FluidProvider;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import test.gas.GasProvider;
import test.gas.GasTank;
import test.gas.IGasStorage;

import java.util.HashMap;

public class TestBlockEntity extends BlockEntity implements EnergyProvider.BLOCK, FluidProvider.BLOCK, GasProvider.BLOCK {
    HashMap<Direction, BaseFluidTank> tanks = new HashMap<>();
    private final BaseEnergyStorage energy = new BaseEnergyStorage(1024, 1024, 1024);
    private final GasTank gasTank = new GasTank();

    public TestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TestMain.TEST_BLOCK_ENTITY_TYPE.get(), blockPos, blockState);
        for (Direction direction : Direction.values()) {
            tanks.put(direction, new BaseFluidTank(1000));
        }
    }

//    public void tick() {
//        for (Direction direction : Direction.values()) {
//            BlockPos dPos = getBlockPos().relative(direction);
//            UniversalEnergyStorage relativeEnergy = Capabilities.Energy.BLOCK.getCapability(getLevel(), dPos, direction.getOpposite());
//            if (relativeEnergy!=null) {
//                int a = relativeEnergy.getEnergy();
//                TestMain.LOGGER.error(Integer.toString(a));
//                int amountOut = relativeEnergy.extract(10, true);
//                int amountIn = energy.insert(10, true);
//                int amount = Math.min(amountIn, amountOut);
//                if (amount>0) {
//                    relativeEnergy.extract(amount, false);
//                    energy.insert(amount, false);
//                    setChanged();
//                }
//            }
//        }
//    }


    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.putInt("energy", energy.getEnergy());
        Tag tag;
        for(Direction direction : Direction.values()) {
            if (!this.tanks.get(direction).getFluidStack().isEmpty()) {
                tag = new CompoundTag();
                tag = FluidStackHooks.write(provider, this.tanks.get(direction).getFluidStack(), tag);
                compoundTag.put("fluid", tag);
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        energy.setEnergyStored(compoundTag.getInt("energy"));
        for(Direction direction : Direction.values()) {
            FluidStack.read(provider, compoundTag).ifPresent(this.tanks.get(direction)::setFluidStack);
        }
    }


    @Override
    public UniversalEnergyStorage getEnergy(@Nullable Direction direction) {
        return energy;
    }

    @Override
    public UniversalFluidTank getFluidTank(@Nullable Direction direction) {
        return tanks.get(direction);
    }

    @Override
    public @Nullable IGasStorage getGas() {
        return gasTank;
    }
}