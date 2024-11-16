package test;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.capabilities.CapabilityData;
import com.absolutelyaryan.capabilities.CapabilityProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import test.gas.GasProvider;
import test.gas.IGasStorage;
import com.mojang.serialization.Codec;
import dev.architectury.fluid.FluidStack;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.UnaryOperator;

public class TestMain {
    public static final String MOD_ID = "space_energy_test";

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final DeferredSupplier<DataComponentType<Integer>> ENERGY = register("energy", builder -> builder.persistent(Codec.INT));
    public static final DeferredSupplier<DataComponentType<FluidStack>> FLUID = register("fluid", builder -> builder.persistent(FluidStack.CODEC));


    private static <T>DeferredSupplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENTS.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static DeferredSupplier<Block> TEST_BLOCK = BLOCKS.register("test_block", () -> new TestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static DeferredSupplier<Item> TEST_ITEM = ITEMS.register("test_item", () -> new TestItem(new Item.Properties().stacksTo(1), 1024, 1024, 1024));
    public static DeferredSupplier<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItem(TEST_BLOCK.get(),new Item.Properties()));

    public static DeferredSupplier<BlockEntityType<?>> TEST_BLOCK_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("test_block_entity_type", () -> BlockEntityType.Builder.of(TestBlockEntity::new).build(null));

    public static ResourceLocation GAS_IDENTIFIER;


    public static void init() {
        BLOCKS.register();
        ITEMS.register();
        DATA_COMPONENTS.register();
        BLOCK_ENTITY_TYPES.register();

        //registering capabilities manually only needed for fabric
        SpaceEnergyCommon.getCapabilityManager().registerItemEnergy(TEST_ITEM.get());
        SpaceEnergyCommon.getCapabilityManager().registerBlockEnergy(TEST_BLOCK.get());
        SpaceEnergyCommon.getCapabilityManager().registerItemFluid(TEST_ITEM.get());
        SpaceEnergyCommon.getCapabilityManager().registerBlockFluid(TEST_BLOCK.get());


        //identifier of capability
        GAS_IDENTIFIER = ResourceLocation.fromNamespaceAndPath(MOD_ID, "testgas");


        //provider for capability
        CapabilityProvider gasProvider = new TestGasProvider();

        //capability data for further verification
        CapabilityData<IGasStorage, Direction> gasData = new CapabilityData<>(IGasStorage.class, Direction.class, gasProvider);
        //registering the capability here
        SpaceEnergyCommon.getCapabilityManager().registerSidedCapability(gasData, GAS_IDENTIFIER);

        //registering blocks using the previous capability we registered, now we can register all blocks we need to
        SpaceEnergyCommon.getCapabilityManager().registerForBlocks(GAS_IDENTIFIER, TEST_BLOCK.get());


        BlockEntity blockEntity = null;
        Direction direction = null;

        //getting the capability from the block entity
        IGasStorage gasStorage = (IGasStorage) SpaceEnergyCommon.getCapabilityManager().getCapability(GAS_IDENTIFIER, blockEntity, direction);


    }
}
