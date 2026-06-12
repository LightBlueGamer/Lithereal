package org.lithereal.block;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
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
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.function.Function;

public class BlueFireBlock extends BaseFireBlock {
    public static final MapCodec<BlueFireBlock> CODEC = simpleCodec(BlueFireBlock::new);
    public static final int MAX_AGE = 15;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION
            .entrySet()
            .stream()
            .filter(e -> e.getKey() != Direction.DOWN)
            .collect(Util.toMap());
    private final Function<BlockState, VoxelShape> shapes;
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
        super(properties, 2.0F);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false));
        this.shapes = this.makeShapes();
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        Map<Direction, VoxelShape> shapes = Shapes.rotateAll(Block.boxZ(16.0F, 0.0F, 1.0F));
        return this.getShapeForEachState((state) -> {
            VoxelShape shape = Shapes.empty();

            for(Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
                if ((Boolean)state.getValue((Property)entry.getValue())) {
                    shape = Shapes.or(shape, shapes.get(entry.getKey()));
                }
            }

            return shape.isEmpty() ? SHAPE : shape;
        }, AGE);
    }

    protected BlockState updateShape(final BlockState state, final LevelReader level, final ScheduledTickAccess ticks, final BlockPos pos, final Direction directionToNeighbour, final BlockPos neighbourPos, final BlockState neighbourState, final RandomSource random) {
        return this.canSurvive(state, level, pos) ? this.getStateWithAge(level, pos, (Integer)state.getValue(AGE)) : Blocks.AIR.defaultBlockState();
    }

    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return this.shapes.apply(state);
    }

    public @NotNull BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.getStateForPlacement(null, blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos());
    }

    public BlockState getStateForPlacement(BaseFireBlock block, BlockGetter blockGetter, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.below();
        BlockState blockState = blockGetter.getBlockState(blockPos2);
        Block newBlock = block == null ? this : block;
        if (!this.canBurn(blockState) && !blockState.isFaceSturdy(blockGetter, blockPos2, Direction.UP)) {
            BlockState blockState2 = newBlock.defaultBlockState();

            for (Direction direction : Direction.values()) {
                BooleanProperty booleanProperty = PROPERTY_BY_DIRECTION.get(direction);
                if (booleanProperty != null) {
                    blockState2 = blockState2.setValue(booleanProperty, this.canBurn(blockGetter.getBlockState(blockPos.relative(direction))));
                }
            }

            return blockState2;
        } else {
            return newBlock.defaultBlockState();
        }
    }

    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.below();
        return levelReader.getBlockState(blockPos2).isFaceSturdy(levelReader, blockPos2, Direction.UP) || this.isValidFireLocation(levelReader, blockPos);
    }

    @Override
    protected void tick(BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        level.scheduleTick(pos, this, getFireTickDelay(level.getRandom()));
        if (level.canSpreadFireAround(pos)) {
            if (!state.canSurvive(level, pos)) {
                level.removeBlock(pos, false);
            }

            BlockState belowState = level.getBlockState(pos.below());
            boolean infiniBurn = belowState.is(level.dimensionType().infiniburn());
            int age = state.getValue(AGE);
            if (!infiniBurn && level.isRaining() && this.isNearRain(level, pos) && random.nextFloat() < 0.2F + age * 0.03F) {
                level.removeBlock(pos, false);
            } else {
                int newAge = Math.min(MAX_AGE, age + random.nextInt(3) / 2);
                if (age != newAge) {
                    state = state.setValue(AGE, newAge);
                    level.setBlock(pos, state, 260);
                }

                if (!infiniBurn) {
                    if (!this.isValidFireLocation(level, pos)) {
                        BlockPos below = pos.below();
                        if (!level.getBlockState(below).isFaceSturdy(level, below, Direction.UP) || age > 3) {
                            BlockState newState = getStateForPlacement((BaseFireBlock) Blocks.FIRE, level, pos);
                            newState.setValue(FireBlock.AGE, 0);
                            level.setBlock(pos, newState, 67);
                        }

                        return;
                    }

                    if (age == MAX_AGE && random.nextInt(4) == 0 && !this.canBurn(level.getBlockState(pos.below()))) {
                        level.removeBlock(pos, false);
                        return;
                    }
                }

                boolean increasedBurnout = level.environmentAttributes().getValue(EnvironmentAttributes.INCREASED_FIRE_BURNOUT, pos);
                int extra = increasedBurnout ? -50 : 0;
                this.checkBurnOut(level, pos.east(), 300 + extra, random, age);
                this.checkBurnOut(level, pos.west(), 300 + extra, random, age);
                this.checkBurnOut(level, pos.below(), 250 + extra, random, age);
                this.checkBurnOut(level, pos.above(), 250 + extra, random, age);
                this.checkBurnOut(level, pos.north(), 300 + extra, random, age);
                this.checkBurnOut(level, pos.south(), 300 + extra, random, age);
                BlockPos.MutableBlockPos testPos = new BlockPos.MutableBlockPos();

                for (int xx = -1; xx <= 1; xx++) {
                    for (int zz = -1; zz <= 1; zz++) {
                        for (int yy = -1; yy <= 4; yy++) {
                            if (xx != 0 || yy != 0 || zz != 0) {
                                int rate = 100;
                                if (yy > 1) {
                                    rate += (yy - 1) * 100;
                                }

                                testPos.setWithOffset(pos, xx, yy, zz);
                                int igniteOdds = this.getIgniteOdds(level, testPos);
                                if (igniteOdds > 0) {
                                    int odds = (igniteOdds + 40 + level.getDifficulty().getId() * 7) / (age + 30);
                                    if (increasedBurnout) {
                                        odds /= 2;
                                    }

                                    if (odds > 0 && random.nextInt(rate) <= odds && (!level.isRaining() || !this.isNearRain(level, testPos))) {
                                        int spreadAge = Math.min(MAX_AGE, age + random.nextInt(5) / 4);
                                        level.setBlock(testPos, this.getStateWithAge(level, testPos, spreadAge), 3);
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
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.burnOdds.getInt(blockState.getBlock()) / 2;
    }

    private int getIgniteOdds(BlockState blockState) {
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.igniteOdds.getInt(blockState.getBlock()) / 2;
    }

    private void checkBurnOut(final Level level, final BlockPos pos, final int chance, final RandomSource random, final int age) {
        int odds = this.getBurnOdds(level.getBlockState(pos));
        if (random.nextInt(chance) < odds) {
            BlockState oldState = level.getBlockState(pos);
            if (random.nextInt(age + 10) < 5 && !level.isRainingAt(pos)) {
                int newAge = Math.min(age + random.nextInt(5) / 4, MAX_AGE);
                level.setBlock(pos, this.getStateWithAge(level, pos, newAge), 3);
            } else {
                level.removeBlock(pos, false);
            }

            Block block = oldState.getBlock();
            if (block instanceof TntBlock) {
                TntBlock.prime(level, pos);
            }
        }
    }

    private BlockState getStateWithAge(LevelReader levelReader, BlockPos blockPos, int i) {
        return getStateForPlacement(null, levelReader, blockPos).setValue(AGE, i);
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
            int odds = 0;

            for (Direction direction : Direction.values()) {
                BlockState blockState = levelReader.getBlockState(blockPos.relative(direction));
                odds = Math.max(this.getIgniteOdds(blockState), odds);
            }

            return odds;
        }
    }

    protected boolean canBurn(@NonNull BlockState blockState) {
        return getIgniteOdds(blockState) > 0;
    }

    protected void onPlace(@NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        level.scheduleTick(blockPos, this, getFireTickDelay(level.getRandom()));
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
        this.setFlammable(Blocks.PALE_OAK_PLANKS, IGNITE_HARD, BURN_MEDIUM);
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
        this.setFlammable(Blocks.PALE_OAK_SLAB, IGNITE_HARD, BURN_MEDIUM);
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
        this.setFlammable(Blocks.PALE_OAK_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.MANGROVE_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_FENCE_GATE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.OAK_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.SPRUCE_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BIRCH_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.JUNGLE_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.ACACIA_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.CHERRY_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.DARK_OAK_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.PALE_OAK_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.MANGROVE_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_FENCE, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.OAK_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BIRCH_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.SPRUCE_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.JUNGLE_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.ACACIA_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.CHERRY_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.DARK_OAK_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.PALE_OAK_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.MANGROVE_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_MOSAIC_STAIRS, IGNITE_HARD, BURN_MEDIUM);
        this.setFlammable(Blocks.OAK_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.SPRUCE_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.BIRCH_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.JUNGLE_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.ACACIA_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.CHERRY_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.PALE_OAK_LOG, IGNITE_HARD, BURN_HARD);
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
        this.setFlammable(Blocks.STRIPPED_PALE_OAK_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_MANGROVE_LOG, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_BAMBOO_BLOCK, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_OAK_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_SPRUCE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_BIRCH_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_JUNGLE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_ACACIA_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_CHERRY_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_DARK_OAK_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_PALE_OAK_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.STRIPPED_MANGROVE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.OAK_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.SPRUCE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.BIRCH_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.JUNGLE_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.ACACIA_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.CHERRY_WOOD, IGNITE_HARD, BURN_HARD);
        this.setFlammable(Blocks.PALE_OAK_WOOD, IGNITE_HARD, BURN_HARD);
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
        this.setFlammable(Blocks.PALE_OAK_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.MANGROVE_LEAVES, IGNITE_EASY, BURN_EASY);
        this.setFlammable(Blocks.BOOKSHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.TNT, IGNITE_MEDIUM, BURN_INSTANT);
        this.setFlammable(Blocks.SHORT_GRASS, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.FERN, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.DEAD_BUSH, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.SHORT_DRY_GRASS, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.TALL_DRY_GRASS, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.SUNFLOWER, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.LILAC, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.ROSE_BUSH, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.PEONY, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.TALL_GRASS, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.LARGE_FERN, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.DANDELION, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.GOLDEN_DANDELION, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.POPPY, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.OPEN_EYEBLOSSOM, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.CLOSED_EYEBLOSSOM, IGNITE_INSTANT, BURN_INSTANT);
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
        this.setFlammable(Blocks.WILDFLOWERS, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.LEAF_LITTER, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.CACTUS_FLOWER, IGNITE_INSTANT, BURN_INSTANT);
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
        this.setFlammable(Blocks.PALE_MOSS_BLOCK, IGNITE_HARD, BURN_INSTANT);
        this.setFlammable(Blocks.PALE_MOSS_CARPET, IGNITE_HARD, BURN_INSTANT);
        this.setFlammable(Blocks.PALE_HANGING_MOSS, IGNITE_HARD, BURN_INSTANT);
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
        this.setFlammable(Blocks.FIREFLY_BUSH, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.BUSH, IGNITE_INSTANT, BURN_INSTANT);
        this.setFlammable(Blocks.ACACIA_SHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.BAMBOO_SHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.BIRCH_SHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.CHERRY_SHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.DARK_OAK_SHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.JUNGLE_SHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.MANGROVE_SHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.OAK_SHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.PALE_OAK_SHELF, IGNITE_EASY, BURN_MEDIUM);
        this.setFlammable(Blocks.SPRUCE_SHELF, IGNITE_EASY, BURN_MEDIUM);
    }
}
