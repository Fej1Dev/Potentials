package test;

import com.absolutelyaryan.capabilities.Capabilities;
import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.mojang.serialization.Codec;
import dev.architectury.fluid.FluidStack;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.gas.GasProvider;
import test.gas.IGasStorage;

import java.util.function.UnaryOperator;

public class TestMain {
    public static final String MOD_ID = "space_energy_test";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final DeferredSupplier<DataComponentType<Integer>> ENERGY = register("energy", builder -> builder.persistent(Codec.INT));
    public static final DeferredSupplier<DataComponentType<FluidStack>> FLUID = register("fluid", builder -> builder.persistent(FluidStack.CODEC));

    public static final BlockCapabilityHolder<IGasStorage, Void> GAS_BLOCK =
            BlockCapabilityHolder.createVoid(IGasStorage.class, ResourceLocation.fromNamespaceAndPath(MOD_ID, "gas_block"));


    private static <T>DeferredSupplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENTS.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static final RegistrySupplier<Block> TEST_BLOCK = BLOCKS.register("test_block", () -> new TestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final RegistrySupplier<Item> TEST_ITEM = ITEMS.register("test_item", () -> new TestItem(new Item.Properties().stacksTo(1), 1024, 1024, 1024));
    public static final RegistrySupplier<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItem(TEST_BLOCK.get(),new Item.Properties()));

    public static final RegistrySupplier<BlockEntityType<?>> TEST_BLOCK_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("test_block_entity_type", () -> BlockEntityType.Builder.of(TestBlockEntity::new).build(null));

    public static void init() {
        BLOCKS.register();
        DATA_COMPONENTS.register();
        ITEMS.register();
        BLOCK_ENTITY_TYPES.register();

        //registering capabilities manually only needed for fabric
        //CapabilityManager capabilityManager = SpaceEnergyCommon.getCapabilityManager();
        //Capabilities.Energy.BLOCK.registerForBlockEntity(TEST_BLOCK_ENTITY_TYPE.get());

        //capabilityManager.registerItemEnergy(TEST_ITEM.get());  NOT GOING TO BE NEEDED
        //capabilityManager.registerBlockEnergy(TEST_BLOCK.get());
        //capabilityManager.registerItemFluid(TEST_ITEM.get());
        //capabilityManager.registerBlockFluid(TEST_BLOCK.get());

        Capabilities.Energy.BLOCK.registerForBlock(TEST_BLOCK);



        //you dont need to register for block entity separately, registering for block is automatically handles for block entities
        // Capabilities.Energy.BLOCK.registerForBlockEntity(TEST_BLOCK_ENTITY_TYPE);

        // same here, doing this will give "Must register at least one Block instance with a BlockApiProvider." error
//        GAS_BLOCK.registerForBlockEntity(
//                ((blockEntity, context) -> blockEntity instanceof GasProvider.BLOCK block ? block.getGas() : null), TEST_BLOCK_ENTITY_TYPE);

        GAS_BLOCK.registerForBlock((level, pos, state, blockEntity, context) -> {
            if (state.getBlock() instanceof GasProvider.BLOCK block)
                return block.getGas();
            if (level.getBlockState(pos).getBlock() instanceof GasProvider.BLOCK block)
                return block.getGas();
            if (blockEntity != null)
                if (blockEntity instanceof GasProvider.BLOCK block)
                    return block.getGas();
            return null;
        }, TEST_BLOCK);

    }
}
