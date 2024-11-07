package test;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TestMain {
    public static final String MOD_ID = "space_energy_test";

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static DeferredSupplier<Block> TEST_BLOCK = BLOCKS.register("test_block", () -> new TestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static DeferredSupplier<Item> TEST_ITEM = ITEMS.register("test_item", () -> new TestItem(new Item.Properties().stacksTo(1), 1024, 1024, 1024));
    public static DeferredSupplier<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItem(TEST_BLOCK.get(),new Item.Properties()));

    public static DeferredSupplier<BlockEntityType<?>> TEST_BLOCK_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("test_block_entity_type", () -> BlockEntityType.Builder.of(TestBlockEntity::new).build(null));


    public static void init() {
        BLOCKS.register();
        ITEMS.register();
        BLOCK_ENTITY_TYPES.register();
    }
}
