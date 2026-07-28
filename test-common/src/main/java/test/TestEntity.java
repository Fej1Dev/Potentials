package test;

import com.fej1fun.potentials.energy.BaseEnergyStorage;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fluid.BaseFluidStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.providers.EnergyProvider;
import com.fej1fun.potentials.providers.FluidProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import test.gas.GasProvider;
import test.gas.GasTank;
import test.gas.IGasStorage;

public class TestEntity extends LivingEntity implements EnergyProvider.ENTITY, FluidProvider.ENTITY, GasProvider.ENTITY {

    BaseFluidStorage fluid = new BaseFluidStorage(10, 1000);
    private final BaseEnergyStorage energy = new BaseEnergyStorage(256);
    private final GasTank           gas    = new GasTank();

    protected TestEntity(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public @Nullable IGasStorage getGas() {
        return gas;
    }

    @Override
    public @Nullable UniversalEnergyStorage getEnergy(@Nullable Direction direction) {
        return energy;
    }

    @Override
    public @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction) {
        return fluid;
    }
}
