package testmod;

import com.fej1fun.potentials.capabilities.Capabilities;
import com.fej1fun.potentials.capabilities.types.BlockCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.ItemCapabilityHolder;
import com.fej1fun.potentials.components.FluidAmountMapDataComponent;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testmod.gas.GasProvider;
import testmod.gas.IGasStorage;

import java.util.function.UnaryOperator;

//TESTS ARE FOR VERSIONS 1.21-1.21.1
public class TestMain {
    public static final String MOD_ID = "potentials_test";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<Integer>> ENERGY = register("energy", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
    public static final RegistrySupplier<DataComponentType<FluidAmountMapDataComponent>> FLUID_AMOUNT = register("fluid_amount", builder -> builder
            .persistent(FluidAmountMapDataComponent.CODEC)
            .networkSynchronized(FluidAmountMapDataComponent.STREAM_CODEC));
    private static <T> RegistrySupplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENTS.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static final BlockCapabilityHolder<IGasStorage, Void> GAS_BLOCK =
            BlockCapabilityHolder.createVoid(IGasStorage.class, ResourceLocation.fromNamespaceAndPath(MOD_ID, "gas_block"));
    public static final ItemCapabilityHolder<IGasStorage, Void> GAS_ITEM =
            ItemCapabilityHolder.createVoid(IGasStorage.class, ResourceLocation.fromNamespaceAndPath(MOD_ID, "gas_item"));

    public static final RegistrySupplier<Block> TEST_BLOCK = BLOCKS.register("test_block", () -> new TestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<Block> TEST_TANK_BLOCK = BLOCKS.register("test_tank_block", () -> new TestTankBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final RegistrySupplier<Item> TEST_ITEM = ITEMS.register("test_item", () -> new TestItem(new Item.Properties().stacksTo(1), 1024, 1024, 1024));
    public static final RegistrySupplier<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItem(TEST_BLOCK.get(),new Item.Properties()));
    public static final RegistrySupplier<Item> TEST_TANK_ITEM = ITEMS.register("test_tank", () -> new BlockItem(TEST_TANK_BLOCK.get(),new Item.Properties()));

    public static final RegistrySupplier<BlockEntityType<?>> TEST_BLOCK_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("test_block_entity_type", () -> BlockEntityType.Builder.of(TestBlockEntity::new).build(null));

    public static void init() {
        BLOCKS.register();
        DATA_COMPONENTS.register();
        ITEMS.register();
        BLOCK_ENTITY_TYPES.register();

        Capabilities.Energy.BLOCK.registerForBlock(TEST_BLOCK);
        Capabilities.Fluid.BLOCK.registerForBlock(TEST_BLOCK);
        Capabilities.Fluid.BLOCK.registerForBlock(TEST_TANK_BLOCK);

        Capabilities.Energy.ITEM.registerForItem(TEST_ITEM);
        Capabilities.Fluid.ITEM.registerForItem(TEST_ITEM);

        //you dont need to register for block entity separately, registering for block is automatically handles for block entities
        // Capabilities.Energy.BLOCK.registerForBlockEntity(TEST_BLOCK_ENTITY_TYPE);

        // same here, doing this will give "Must register at least one Block instance with a BlockApiProvider." error in fabric
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

        GAS_ITEM.registerForItem((stack, context) -> stack.getItem() instanceof GasProvider.ITEM gasItem ? gasItem.getGas(stack) : null, TEST_ITEM);

    }
}
