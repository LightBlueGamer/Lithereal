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
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

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
    private final Object2IntMap<Block> igniteOdds = new Object2IntOpenHashMap();
    private final Object2IntMap<Block> burnOdds = new Object2IntOpenHashMap();

    public MapCodec<BlueFireBlock> codec() {
        return CODEC;
    }

    public BlueFireBlock(Properties properties) {
        super(properties, 1.0F);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(AGE, 0)).setValue(NORTH, false)).setValue(EAST, false)).setValue(SOUTH, false)).setValue(WEST, false)).setValue(UP, false));
        this.shapesCache = ImmutableMap.copyOf((Map)this.stateDefinition.getPossibleStates().stream().filter((blockState) -> {
            return (Integer)blockState.getValue(AGE) == 0;
        }).collect(Collectors.toMap(Function.identity(), BlueFireBlock::calculateShape)));
    }

    private static VoxelShape calculateShape(BlockState blockState) {
        VoxelShape voxelShape = Shapes.empty();
        if ((Boolean)blockState.getValue(UP)) {
            voxelShape = UP_AABB;
        }

        if ((Boolean)blockState.getValue(NORTH)) {
            voxelShape = Shapes.or(voxelShape, NORTH_AABB);
        }

        if ((Boolean)blockState.getValue(SOUTH)) {
            voxelShape = Shapes.or(voxelShape, SOUTH_AABB);
        }

        if ((Boolean)blockState.getValue(EAST)) {
            voxelShape = Shapes.or(voxelShape, EAST_AABB);
        }

        if ((Boolean)blockState.getValue(WEST)) {
            voxelShape = Shapes.or(voxelShape, WEST_AABB);
        }

        return voxelShape.isEmpty() ? DOWN_AABB : voxelShape;
    }

    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        return this.canSurvive(blockState, levelAccessor, blockPos) ? this.getStateWithAge(levelAccessor, blockPos, (Integer)blockState.getValue(AGE)) : Blocks.AIR.defaultBlockState();
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return (VoxelShape)this.shapesCache.get(blockState.setValue(AGE, 0));
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.getStateForPlacement(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos());
    }

    protected BlockState getStateForPlacement(BlockGetter blockGetter, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.below();
        BlockState blockState = blockGetter.getBlockState(blockPos2);
        if (!this.canBurn(blockState) && !blockState.isFaceSturdy(blockGetter, blockPos2, Direction.UP)) {
            BlockState blockState2 = this.defaultBlockState();
            Direction[] var6 = Direction.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Direction direction = var6[var8];
                BooleanProperty booleanProperty = (BooleanProperty)PROPERTY_BY_DIRECTION.get(direction);
                if (booleanProperty != null) {
                    blockState2 = (BlockState)blockState2.setValue(booleanProperty, this.canBurn(blockGetter.getBlockState(blockPos.relative(direction))));
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
            int i = (Integer)blockState.getValue(AGE);
            if (!bl && serverLevel.isRaining() && this.isNearRain(serverLevel, blockPos) && randomSource.nextFloat() < 0.2F + (float)i * 0.03F) {
                serverLevel.removeBlock(blockPos, false);
            } else {
                int j = Math.min(15, i + randomSource.nextInt(3) / 2);
                if (i != j) {
                    blockState = (BlockState)blockState.setValue(AGE, j);
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

                    if (i == 15 && randomSource.nextInt(4) == 0 && !this.canBurn(serverLevel.getBlockState(blockPos.below()))) {
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
                                        int r = Math.min(15, i + randomSource.nextInt(5) / 4);
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
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && (Boolean)blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.burnOdds.getInt(blockState.getBlock());
    }

    private int getIgniteOdds(BlockState blockState) {
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && (Boolean)blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.igniteOdds.getInt(blockState.getBlock());
    }

    private void checkBurnOut(Level level, BlockPos blockPos, int i, RandomSource randomSource, int j) {
        int k = this.getBurnOdds(level.getBlockState(blockPos));
        if (randomSource.nextInt(i) < k) {
            BlockState blockState = level.getBlockState(blockPos);
            if (randomSource.nextInt(j + 10) < 5 && !level.isRainingAt(blockPos)) {
                int l = Math.min(j + randomSource.nextInt(5) / 4, 15);
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
        return blockState.is(ModBlocks.BLUE_FIRE) ? (BlockState)blockState.setValue(AGE, i) : blockState;
    }

    private boolean isValidFireLocation(BlockGetter blockGetter, BlockPos blockPos) {
        Direction[] var3 = Direction.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
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
            Direction[] var4 = Direction.values();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Direction direction = var4[var6];
                BlockState blockState = levelReader.getBlockState(blockPos.relative(direction));
                i = Math.max(this.getIgniteOdds(blockState), i);
            }

            return i;
        }
    }

    protected boolean canBurn(BlockState blockState) {
        return this.getIgniteOdds(blockState) > 0;
    }

    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        level.scheduleTick(blockPos, this, getFireTickDelay(level.random));
    }

    private static int getFireTickDelay(RandomSource randomSource) {
        return 30 + randomSource.nextInt(10);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE, NORTH, EAST, SOUTH, WEST, UP});
    }

    public void setFlammable(Block block, int i, int j) {
        this.igniteOdds.put(block, i);
        this.burnOdds.put(block, j);
    }

    public static void bootStrap() {
        BlueFireBlock blueFireBlock = (BlueFireBlock)Blocks.FIRE;
        blueFireBlock.setFlammable(Blocks.OAK_PLANKS, 5, 20);
        blueFireBlock.setFlammable(Blocks.SPRUCE_PLANKS, 5, 20);
        blueFireBlock.setFlammable(Blocks.BIRCH_PLANKS, 5, 20);
        blueFireBlock.setFlammable(Blocks.JUNGLE_PLANKS, 5, 20);
        blueFireBlock.setFlammable(Blocks.ACACIA_PLANKS, 5, 20);
        blueFireBlock.setFlammable(Blocks.CHERRY_PLANKS, 5, 20);
        blueFireBlock.setFlammable(Blocks.DARK_OAK_PLANKS, 5, 20);
        blueFireBlock.setFlammable(Blocks.MANGROVE_PLANKS, 5, 20);
        blueFireBlock.setFlammable(Blocks.BAMBOO_PLANKS, 5, 20);
        blueFireBlock.setFlammable(Blocks.BAMBOO_MOSAIC, 5, 20);
        blueFireBlock.setFlammable(Blocks.OAK_SLAB, 5, 20);
        blueFireBlock.setFlammable(Blocks.SPRUCE_SLAB, 5, 20);
        blueFireBlock.setFlammable(Blocks.BIRCH_SLAB, 5, 20);
        blueFireBlock.setFlammable(Blocks.JUNGLE_SLAB, 5, 20);
        blueFireBlock.setFlammable(Blocks.ACACIA_SLAB, 5, 20);
        blueFireBlock.setFlammable(Blocks.CHERRY_SLAB, 5, 20);
        blueFireBlock.setFlammable(Blocks.DARK_OAK_SLAB, 5, 20);
        blueFireBlock.setFlammable(Blocks.MANGROVE_SLAB, 5, 20);
        blueFireBlock.setFlammable(Blocks.BAMBOO_SLAB, 5, 20);
        blueFireBlock.setFlammable(Blocks.BAMBOO_MOSAIC_SLAB, 5, 20);
        blueFireBlock.setFlammable(Blocks.OAK_FENCE_GATE, 5, 20);
        blueFireBlock.setFlammable(Blocks.SPRUCE_FENCE_GATE, 5, 20);
        blueFireBlock.setFlammable(Blocks.BIRCH_FENCE_GATE, 5, 20);
        blueFireBlock.setFlammable(Blocks.JUNGLE_FENCE_GATE, 5, 20);
        blueFireBlock.setFlammable(Blocks.ACACIA_FENCE_GATE, 5, 20);
        blueFireBlock.setFlammable(Blocks.CHERRY_FENCE_GATE, 5, 20);
        blueFireBlock.setFlammable(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
        blueFireBlock.setFlammable(Blocks.MANGROVE_FENCE_GATE, 5, 20);
        blueFireBlock.setFlammable(Blocks.BAMBOO_FENCE_GATE, 5, 20);
        blueFireBlock.setFlammable(Blocks.OAK_FENCE, 5, 20);
        blueFireBlock.setFlammable(Blocks.SPRUCE_FENCE, 5, 20);
        blueFireBlock.setFlammable(Blocks.BIRCH_FENCE, 5, 20);
        blueFireBlock.setFlammable(Blocks.JUNGLE_FENCE, 5, 20);
        blueFireBlock.setFlammable(Blocks.ACACIA_FENCE, 5, 20);
        blueFireBlock.setFlammable(Blocks.CHERRY_FENCE, 5, 20);
        blueFireBlock.setFlammable(Blocks.DARK_OAK_FENCE, 5, 20);
        blueFireBlock.setFlammable(Blocks.MANGROVE_FENCE, 5, 20);
        blueFireBlock.setFlammable(Blocks.BAMBOO_FENCE, 5, 20);
        blueFireBlock.setFlammable(Blocks.OAK_STAIRS, 5, 20);
        blueFireBlock.setFlammable(Blocks.BIRCH_STAIRS, 5, 20);
        blueFireBlock.setFlammable(Blocks.SPRUCE_STAIRS, 5, 20);
        blueFireBlock.setFlammable(Blocks.JUNGLE_STAIRS, 5, 20);
        blueFireBlock.setFlammable(Blocks.ACACIA_STAIRS, 5, 20);
        blueFireBlock.setFlammable(Blocks.CHERRY_STAIRS, 5, 20);
        blueFireBlock.setFlammable(Blocks.DARK_OAK_STAIRS, 5, 20);
        blueFireBlock.setFlammable(Blocks.MANGROVE_STAIRS, 5, 20);
        blueFireBlock.setFlammable(Blocks.BAMBOO_STAIRS, 5, 20);
        blueFireBlock.setFlammable(Blocks.BAMBOO_MOSAIC_STAIRS, 5, 20);
        blueFireBlock.setFlammable(Blocks.OAK_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.SPRUCE_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.BIRCH_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.JUNGLE_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.ACACIA_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.CHERRY_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.DARK_OAK_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.MANGROVE_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.BAMBOO_BLOCK, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_OAK_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_SPRUCE_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_BIRCH_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_JUNGLE_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_ACACIA_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_CHERRY_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_DARK_OAK_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_MANGROVE_LOG, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_BAMBOO_BLOCK, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_OAK_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_SPRUCE_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_BIRCH_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_JUNGLE_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_ACACIA_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_CHERRY_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_DARK_OAK_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.STRIPPED_MANGROVE_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.OAK_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.SPRUCE_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.BIRCH_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.JUNGLE_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.ACACIA_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.CHERRY_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.DARK_OAK_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.MANGROVE_WOOD, 5, 5);
        blueFireBlock.setFlammable(Blocks.MANGROVE_ROOTS, 5, 20);
        blueFireBlock.setFlammable(Blocks.OAK_LEAVES, 30, 60);
        blueFireBlock.setFlammable(Blocks.SPRUCE_LEAVES, 30, 60);
        blueFireBlock.setFlammable(Blocks.BIRCH_LEAVES, 30, 60);
        blueFireBlock.setFlammable(Blocks.JUNGLE_LEAVES, 30, 60);
        blueFireBlock.setFlammable(Blocks.ACACIA_LEAVES, 30, 60);
        blueFireBlock.setFlammable(Blocks.CHERRY_LEAVES, 30, 60);
        blueFireBlock.setFlammable(Blocks.DARK_OAK_LEAVES, 30, 60);
        blueFireBlock.setFlammable(Blocks.MANGROVE_LEAVES, 30, 60);
        blueFireBlock.setFlammable(Blocks.BOOKSHELF, 30, 20);
        blueFireBlock.setFlammable(Blocks.TNT, 15, 100);
        blueFireBlock.setFlammable(Blocks.SHORT_GRASS, 60, 100);
        blueFireBlock.setFlammable(Blocks.FERN, 60, 100);
        blueFireBlock.setFlammable(Blocks.DEAD_BUSH, 60, 100);
        blueFireBlock.setFlammable(Blocks.SUNFLOWER, 60, 100);
        blueFireBlock.setFlammable(Blocks.LILAC, 60, 100);
        blueFireBlock.setFlammable(Blocks.ROSE_BUSH, 60, 100);
        blueFireBlock.setFlammable(Blocks.PEONY, 60, 100);
        blueFireBlock.setFlammable(Blocks.TALL_GRASS, 60, 100);
        blueFireBlock.setFlammable(Blocks.LARGE_FERN, 60, 100);
        blueFireBlock.setFlammable(Blocks.DANDELION, 60, 100);
        blueFireBlock.setFlammable(Blocks.POPPY, 60, 100);
        blueFireBlock.setFlammable(Blocks.BLUE_ORCHID, 60, 100);
        blueFireBlock.setFlammable(Blocks.ALLIUM, 60, 100);
        blueFireBlock.setFlammable(Blocks.AZURE_BLUET, 60, 100);
        blueFireBlock.setFlammable(Blocks.RED_TULIP, 60, 100);
        blueFireBlock.setFlammable(Blocks.ORANGE_TULIP, 60, 100);
        blueFireBlock.setFlammable(Blocks.WHITE_TULIP, 60, 100);
        blueFireBlock.setFlammable(Blocks.PINK_TULIP, 60, 100);
        blueFireBlock.setFlammable(Blocks.OXEYE_DAISY, 60, 100);
        blueFireBlock.setFlammable(Blocks.CORNFLOWER, 60, 100);
        blueFireBlock.setFlammable(Blocks.LILY_OF_THE_VALLEY, 60, 100);
        blueFireBlock.setFlammable(Blocks.TORCHFLOWER, 60, 100);
        blueFireBlock.setFlammable(Blocks.PITCHER_PLANT, 60, 100);
        blueFireBlock.setFlammable(Blocks.WITHER_ROSE, 60, 100);
        blueFireBlock.setFlammable(Blocks.PINK_PETALS, 60, 100);
        blueFireBlock.setFlammable(Blocks.WHITE_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.ORANGE_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.MAGENTA_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.LIGHT_BLUE_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.YELLOW_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.LIME_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.PINK_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.GRAY_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.LIGHT_GRAY_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.CYAN_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.PURPLE_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.BLUE_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.BROWN_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.GREEN_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.RED_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.BLACK_WOOL, 30, 60);
        blueFireBlock.setFlammable(Blocks.VINE, 15, 100);
        blueFireBlock.setFlammable(Blocks.COAL_BLOCK, 5, 5);
        blueFireBlock.setFlammable(Blocks.HAY_BLOCK, 60, 20);
        blueFireBlock.setFlammable(Blocks.TARGET, 15, 20);
        blueFireBlock.setFlammable(Blocks.WHITE_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.ORANGE_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.MAGENTA_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.LIGHT_BLUE_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.YELLOW_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.LIME_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.PINK_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.GRAY_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.LIGHT_GRAY_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.CYAN_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.PURPLE_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.BLUE_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.BROWN_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.GREEN_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.RED_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.BLACK_CARPET, 60, 20);
        blueFireBlock.setFlammable(Blocks.DRIED_KELP_BLOCK, 30, 60);
        blueFireBlock.setFlammable(Blocks.BAMBOO, 60, 60);
        blueFireBlock.setFlammable(Blocks.SCAFFOLDING, 60, 60);
        blueFireBlock.setFlammable(Blocks.LECTERN, 30, 20);
        blueFireBlock.setFlammable(Blocks.COMPOSTER, 5, 20);
        blueFireBlock.setFlammable(Blocks.SWEET_BERRY_BUSH, 60, 100);
        blueFireBlock.setFlammable(Blocks.BEEHIVE, 5, 20);
        blueFireBlock.setFlammable(Blocks.BEE_NEST, 30, 20);
        blueFireBlock.setFlammable(Blocks.AZALEA_LEAVES, 30, 60);
        blueFireBlock.setFlammable(Blocks.FLOWERING_AZALEA_LEAVES, 30, 60);
        blueFireBlock.setFlammable(Blocks.CAVE_VINES, 15, 60);
        blueFireBlock.setFlammable(Blocks.CAVE_VINES_PLANT, 15, 60);
        blueFireBlock.setFlammable(Blocks.SPORE_BLOSSOM, 60, 100);
        blueFireBlock.setFlammable(Blocks.AZALEA, 30, 60);
        blueFireBlock.setFlammable(Blocks.FLOWERING_AZALEA, 30, 60);
        blueFireBlock.setFlammable(Blocks.BIG_DRIPLEAF, 60, 100);
        blueFireBlock.setFlammable(Blocks.BIG_DRIPLEAF_STEM, 60, 100);
        blueFireBlock.setFlammable(Blocks.SMALL_DRIPLEAF, 60, 100);
        blueFireBlock.setFlammable(Blocks.HANGING_ROOTS, 30, 60);
        blueFireBlock.setFlammable(Blocks.GLOW_LICHEN, 15, 100);
    }

    static {
        AGE = BlockStateProperties.AGE_15;
        NORTH = PipeBlock.NORTH;
        EAST = PipeBlock.EAST;
        SOUTH = PipeBlock.SOUTH;
        WEST = PipeBlock.WEST;
        UP = PipeBlock.UP;
        PROPERTY_BY_DIRECTION = (Map)PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((entry) -> {
            return entry.getKey() != Direction.DOWN;
        }).collect(Util.toMap());
        UP_AABB = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
        WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
        EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
        NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
        SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    }
}
