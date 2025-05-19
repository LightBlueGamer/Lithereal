package org.lithereal.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlueFireBlock extends BaseFireBlock {
    public static final MapCodec<BlueFireBlock> CODEC = simpleCodec(BlueFireBlock::new);
    public static final int MAX_AGE = 15;
    public static final IntegerProperty AGE;
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;
    public static final BooleanProperty UP;
    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION;
    private static final VoxelShape UP_AABB;
    private static final VoxelShape WEST_AABB;
    private static final VoxelShape EAST_AABB;
    private static final VoxelShape NORTH_AABB;
    private static final VoxelShape SOUTH_AABB;
    private final Map<BlockState, VoxelShape> shapesCache;
    private static final int IGNITE_INSTANT = 60;
    private static final int IGNITE_EASY = 30;
    private static final int IGNITE_MEDIUM = 15;
    private static final int IGNITE_HARD = 5;
    private static final int BURN_INSTANT = 100;
    private static final int BURN_EASY = 60;
    private static final int BURN_MEDIUM = 20;
    private static final int BURN_HARD = 5;
    private final Object2IntMap<Block> igniteOdds = new Object2IntOpenHashMap<>();
    private final Object2IntMap<Block> burnOdds = new Object2IntOpenHashMap<>();

    public @NotNull MapCodec<BlueFireBlock> codec() {
        return CODEC;
    }

    public BlueFireBlock(Properties properties) {
        super(properties, 1.0F);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false));
        this.shapesCache = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().filter((blockState) -> blockState.getValue(AGE) == 0).collect(Collectors.toMap(Function.identity(), BlueFireBlock::calculateShape)));
        this.init();
    }

    private static VoxelShape calculateShape(BlockState blockState) {
        VoxelShape voxelShape = Shapes.empty();
        if (blockState.getValue(UP)) {
            voxelShape = UP_AABB;
        }

        if (blockState.getValue(NORTH)) {
            voxelShape = Shapes.or(voxelShape, NORTH_AABB);
        }

        if (blockState.getValue(SOUTH)) {
            voxelShape = Shapes.or(voxelShape, SOUTH_AABB);
        }

        if (blockState.getValue(EAST)) {
            voxelShape = Shapes.or(voxelShape, EAST_AABB);
        }

        if (blockState.getValue(WEST)) {
            voxelShape = Shapes.or(voxelShape, WEST_AABB);
        }

        return voxelShape.isEmpty() ? DOWN_AABB : voxelShape;
    }

    protected @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        return this.canSurvive(blockState, levelAccessor, blockPos) ? this.getStateWithAge(levelAccessor, blockPos, blockState.getValue(AGE)) : Blocks.AIR.defaultBlockState();
    }

    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return this.shapesCache.get(blockState.setValue(AGE, 0));
    }

    public @NotNull BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.getStateForPlacement(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos());
    }

    protected BlockState getStateForPlacement(BlockGetter blockGetter, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.below();
        BlockState blockState = blockGetter.getBlockState(blockPos2);
        if (!this.canBurn(blockState) && !blockState.isFaceSturdy(blockGetter, blockPos2, Direction.UP)) {
            BlockState blockState2 = this.defaultBlockState();

            for (Direction direction : Direction.values()) {
                BooleanProperty booleanProperty = PROPERTY_BY_DIRECTION.get(direction);
                if (booleanProperty != null) {
                    blockState2 = blockState2.setValue(booleanProperty, this.canBurn(blockGetter.getBlockState(blockPos.relative(direction))));
                }
            }

            return blockState2;
        } else {
            return this.defaultBlockState();
        }
    }

    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.below();
        return levelReader.getBlockState(blockPos2).isFaceSturdy(levelReader, blockPos2, Direction.UP) || this.isValidFireLocation(levelReader, blockPos);
    }

    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        serverLevel.scheduleTick(blockPos, this, getFireTickDelay(serverLevel.random));
        if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            if (!blockState.canSurvive(serverLevel, blockPos)) {
                serverLevel.removeBlock(blockPos, false);
            }

            BlockState blockState2 = serverLevel.getBlockState(blockPos.below());
            boolean bl = blockState2.is(serverLevel.dimensionType().infiniburn());
            int i = blockState.getValue(AGE);
            if (!bl && serverLevel.isRaining() && this.isNearRain(serverLevel, blockPos) && randomSource.nextFloat() < 0.2F + (float)i * 0.03F) {
                serverLevel.removeBlock(blockPos, false);
            } else {
                int j = Math.min(MAX_AGE, i + randomSource.nextInt(3) / 2);
                if (i != j) {
                    blockState = blockState.setValue(AGE, j);
                    serverLevel.setBlock(blockPos, blockState, 4);
                }

                if (!bl) {
                    if (!this.isValidFireLocation(serverLevel, blockPos)) {
                        BlockPos blockPos2 = blockPos.below();
                        if (!serverLevel.getBlockState(blockPos2).isFaceSturdy(serverLevel, blockPos2, Direction.UP) || i > 3) {
                            serverLevel.removeBlock(blockPos, false);
                        }

                        return;
                    }

                    if (i == MAX_AGE && randomSource.nextInt(4) == 0 && !this.canBurn(serverLevel.getBlockState(blockPos.below()))) {
                        serverLevel.removeBlock(blockPos, false);
                        return;
                    }
                }

                boolean bl2 = serverLevel.getBiome(blockPos).is(BiomeTags.INCREASED_FIRE_BURNOUT);
                int k = bl2 ? -50 : 0;
                this.checkBurnOut(serverLevel, blockPos.east(), 300 + k, randomSource, i);
                this.checkBurnOut(serverLevel, blockPos.west(), 300 + k, randomSource, i);
                this.checkBurnOut(serverLevel, blockPos.below(), 250 + k, randomSource, i);
                this.checkBurnOut(serverLevel, blockPos.above(), 250 + k, randomSource, i);
                this.checkBurnOut(serverLevel, blockPos.north(), 300 + k, randomSource, i);
                this.checkBurnOut(serverLevel, blockPos.south(), 300 + k, randomSource, i);
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

                for(int l = -1; l <= 1; ++l) {
                    for(int m = -1; m <= 1; ++m) {
                        for(int n = -1; n <= 4; ++n) {
                            if (l != 0 || n != 0 || m != 0) {
                                int o = 100;
                                if (n > 1) {
                                    o += (n - 1) * 100;
                                }

                                mutableBlockPos.setWithOffset(blockPos, l, n, m);
                                int p = this.getIgniteOdds(serverLevel, mutableBlockPos);
                                if (p > 0) {
                                    int q = (p + 40 + serverLevel.getDifficulty().getId() * 7) / (i + 30);
                                    if (bl2) {
                                        q /= 2;
                                    }

                                    if (q > 0 && randomSource.nextInt(o) <= q && (!serverLevel.isRaining() || !this.isNearRain(serverLevel, mutableBlockPos))) {
                                        int r = Math.min(MAX_AGE, i + randomSource.nextInt(5) / 4);
                                        serverLevel.setBlock(mutableBlockPos, this.getStateWithAge(serverLevel, mutableBlockPos, r), 3);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    protected boolean isNearRain(Level level, BlockPos blockPos) {
        return level.isRainingAt(blockPos) || level.isRainingAt(blockPos.west()) || level.isRainingAt(blockPos.east()) || level.isRainingAt(blockPos.north()) || level.isRainingAt(blockPos.south());
    }

    private int getBurnOdds(BlockState blockState) {
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.burnOdds.getInt(blockState.getBlock());
    }

    private int getIgniteOdds(BlockState blockState) {
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.igniteOdds.getInt(blockState.getBlock());
    }

    private void checkBurnOut(Level level, BlockPos blockPos, int i, RandomSource randomSource, int j) {
        int k = this.getBurnOdds(level.getBlockState(blockPos));
        if (randomSource.nextInt(i) < k) {
            BlockState blockState = level.getBlockState(blockPos);
            if (randomSource.nextInt(j + 10) < 5 && !level.isRainingAt(blockPos)) {
                int l = Math.min(j + randomSource.nextInt(5) / 4, MAX_AGE);
                level.setBlock(blockPos, this.getStateWithAge(level, blockPos, l), 3);
            } else {
                level.removeBlock(blockPos, false);
            }

            Block block = blockState.getBlock();
            if (block instanceof TntBlock) {
                TntBlock.explode(level, blockPos);
            }
        }

    }

    private BlockState getStateWithAge(LevelAccessor levelAccessor, BlockPos blockPos, int i) {
        BlockState blockState = getState(levelAccessor, blockPos);
        return blockState.is(ModBlocks.BLUE_FIRE) ? blockState.setValue(AGE, i) : blockState;
    }

    private boolean isValidFireLocation(BlockGetter blockGetter, BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            if (this.canBurn(blockGetter.getBlockState(blockPos.relative(direction)))) {
                return true;
            }
        }

        return false;
    }

    private int getIgniteOdds(LevelReader levelReader, BlockPos blockPos) {
        if (!levelReader.isEmptyBlock(blockPos)) {
            return 0;
        } else {
            int i = 0;

            for (Direction direction : Direction.values()) {
                BlockState blockState = levelReader.getBlockState(blockPos.relative(direction));
                i = Math.max(this.getIgniteOdds(blockState), i);
            }

            return i;
        }
    }

    protected boolean canBurn(BlockState blockState) {
        return true;
    }

    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        level.scheduleTick(blockPos, this, getFireTickDelay(level.random));
    }

    private static int getFireTickDelay(RandomSource randomSource) {
        return 30 + randomSource.nextInt(10);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
    }

    public void setFlammable(Block block, int i, int j) {
        this.igniteOdds.put(block, i);
        this.burnOdds.put(block, j);
    }

    public void init() {
        this.setFlammable(Blocks.OAK_PLANKS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.SPRUCE_PLANKS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BIRCH_PLANKS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.JUNGLE_PLANKS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.ACACIA_PLANKS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.CHERRY_PLANKS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.DARK_OAK_PLANKS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.MANGROVE_PLANKS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_PLANKS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_MOSAIC, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.OAK_SLAB, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.SPRUCE_SLAB, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BIRCH_SLAB, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.JUNGLE_SLAB, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.ACACIA_SLAB, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.CHERRY_SLAB, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.DARK_OAK_SLAB, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.MANGROVE_SLAB, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_SLAB, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_MOSAIC_SLAB, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.OAK_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.SPRUCE_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BIRCH_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.JUNGLE_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.ACACIA_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.CHERRY_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.DARK_OAK_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.MANGROVE_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.OAK_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.SPRUCE_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BIRCH_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.JUNGLE_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.ACACIA_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.CHERRY_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.DARK_OAK_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.MANGROVE_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.OAK_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BIRCH_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.SPRUCE_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.JUNGLE_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.ACACIA_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.CHERRY_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.DARK_OAK_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.MANGROVE_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_MOSAIC_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.OAK_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.SPRUCE_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.BIRCH_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.JUNGLE_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.ACACIA_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.CHERRY_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.DARK_OAK_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.MANGROVE_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.BAMBOO_BLOCK, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_OAK_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_SPRUCE_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_BIRCH_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_JUNGLE_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_ACACIA_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_CHERRY_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_DARK_OAK_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_MANGROVE_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_BAMBOO_BLOCK, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_OAK_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_SPRUCE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_BIRCH_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_JUNGLE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_ACACIA_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_CHERRY_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_DARK_OAK_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_MANGROVE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.OAK_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.SPRUCE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.BIRCH_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.JUNGLE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.ACACIA_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.CHERRY_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.DARK_OAK_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.MANGROVE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.MANGROVE_ROOTS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.OAK_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.SPRUCE_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.BIRCH_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.JUNGLE_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.ACACIA_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.CHERRY_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.DARK_OAK_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.MANGROVE_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.BOOKSHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.TNT, IGNITE_MEDIUM, BURN_INSTANT);
        this.setFlammable(Blocks.SHORT_GRASS, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.FERN, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.DEAD_BUSH, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.SUNFLOWER, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.LILAC, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.ROSE_BUSH, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.PEONY, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.TALL_GRASS, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.LARGE_FERN, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.DANDELION, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.POPPY, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.BLUE_ORCHID, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.ALLIUM, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.AZURE_BLUET, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.RED_TULIP, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.ORANGE_TULIP, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.WHITE_TULIP, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.PINK_TULIP, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.OXEYE_DAISY, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.CORNFLOWER, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.LILY_OF_THE_VALLEY, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.TORCHFLOWER, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.PITCHER_PLANT, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.WITHER_ROSE, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.PINK_PETALS, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.WHITE_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.ORANGE_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.MAGENTA_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.LIGHT_BLUE_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.YELLOW_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.LIME_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.PINK_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.GRAY_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.LIGHT_GRAY_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.CYAN_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.PURPLE_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.BLUE_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.BROWN_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.GREEN_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.RED_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.BLACK_WOOL, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.VINE, IGNITE_MEDIUM, BURN_INSTANT);
        this.setFlammable(Blocks.COAL_BLOCK, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.HAY_BLOCK, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.TARGET, IGNITE_MEDIUM, BURN_MEDIUM);
        this.setFlammable(Blocks.WHITE_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.ORANGE_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.MAGENTA_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.LIGHT_BLUE_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.YELLOW_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.LIME_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.PINK_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.GRAY_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.LIGHT_GRAY_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.CYAN_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.PURPLE_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.BLUE_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.BROWN_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.GREEN_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.RED_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.BLACK_CARPET, IGNITE_INSTANT, BURN_MEDIUM);
        this.setFlammable(Blocks.DRIED_KELP_BLOCK, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.BAMBOO, IGNITE_INSTANT, BURN_EASY);
        this.setFlammable(Blocks.SCAFFOLDING, IGNITE_INSTANT, BURN_EASY);
        this.setFlammable(Blocks.LECTERN, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.COMPOSTER, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.SWEET_BERRY_BUSH, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.BEEHIVE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BEE_NEST, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.AZALEA_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.FLOWERING_AZALEA_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.CAVE_VINES, IGNITE_MEDIUM, BURN_EASY);
        this.setFlammable(Blocks.CAVE_VINES_PLANT, IGNITE_MEDIUM, BURN_EASY);
        this.setFlammable(Blocks.SPORE_BLOSSOM, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.AZALEA, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.FLOWERING_AZALEA, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.BIG_DRIPLEAF, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.BIG_DRIPLEAF_STEM, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.SMALL_DRIPLEAF, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.HANGING_ROOTS, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.GLOW_LICHEN, IGNITE_MEDIUM, BURN_INSTANT);
    }

    static {
        AGE = BlockStateProperties.AGE_15;
        NORTH = PipeBlock.NORTH;
        EAST = PipeBlock.EAST;
        SOUTH = PipeBlock.SOUTH;
        WEST = PipeBlock.WEST;
        UP = PipeBlock.UP;
        PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((entry) -> entry.getKey() != Direction.DOWN).collect(Util.toMap());
        UP_AABB = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
        WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
        EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
        NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
        SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    }
}
