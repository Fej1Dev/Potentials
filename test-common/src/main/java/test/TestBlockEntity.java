package test;

import com.fej1fun.potentials.energy.BaseEnergyStorage;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fluid.BaseFluidStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import com.fej1fun.potentials.providers.FluidProvider;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import test.gas.GasProvider;
import test.gas.GasTank;
import test.gas.IGasStorage;

public class TestBlockEntity extends BlockEntity implements EnergyProvider.BLOCK, FluidProvider.BLOCK, GasProvider.BLOCK {
    BaseFluidStorage tanks = new BaseFluidStorage(6, 1000);
    private final BaseEnergyStorage energy = new BaseEnergyStorage(1024, 1024, 1024);
    private final GasTank gasTank = new GasTank();

    public TestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TestMain.TEST_BLOCK_ENTITY_TYPE.get(), blockPos, blockState);
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
        for (int i = 0; i < tanks.getTanks(); i++) {

            if (!tanks.getFluidInTank(i).isEmpty()) {
                compoundTag.put("fluid-"+i, FluidStackHooks.write(provider, tanks.getFluidInTank(i), new CompoundTag()));
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        energy.setEnergyStored(compoundTag.getInt("energy"));
        for (int i = 0; i < tanks.getTanks(); i++) {

            if (compoundTag.contains("fluid-"+i)) {
                tanks.setFluidInTank(i, FluidStackHooks.read(provider, compoundTag.get("fluid-"+i)).orElse(FluidStack.empty()));
            }
        }
    }


    @Override
    public UniversalEnergyStorage getEnergy(@Nullable Direction direction) {
        return energy;
    }

    @Override
    public @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction) {
        return tanks;
    }

    @Override
    public @Nullable IGasStorage getGas() {
        return gasTank;
    }
}