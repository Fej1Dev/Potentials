package test;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
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
import test.gas.TestGasProvider;

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
    public static BlockCapabilityHolder<IGasStorage, Direction> GAS_PROVIDER;

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



        //registering the capability
        GAS_PROVIDER = SpaceEnergyCommon.getCapabilityManager().registerSidedCapability(IGasStorage.class, Direction.class, GAS_IDENTIFIER);

        //registering the capability for blocks
        GAS_PROVIDER.registerForBlocks(new TestGasProvider(), TEST_BLOCK.get());

        //example of how to get the capability from a block entity
        IGasStorage gasStorage = GAS_PROVIDER.getCapability(null, null, null);


    }
}
