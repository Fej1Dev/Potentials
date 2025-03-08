package test;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TestBlock extends BaseEntityBlock {

    protected TestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(TestBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TestMain.TEST_BLOCK_ENTITY_TYPE.get().create(blockPos, blockState);
    }

//    @Override
//    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
//        return level.isClientSide() ? null : createTickerHelper(blockEntityType, TestMain.TEST_BLOCK_ENTITY_TYPE.get(),
//                (level1, blockPos, blockState1, blockEntity) -> ((TestBlockEntity) blockEntity).tick());
//    }
}
