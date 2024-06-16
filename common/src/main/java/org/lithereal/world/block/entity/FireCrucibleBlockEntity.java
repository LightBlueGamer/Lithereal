package org.lithereal.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.client.gui.screens.inventory.FireCrucibleMenu;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.data.recipes.FireCrucibleRecipe;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.util.CommonUtils;
import org.lithereal.world.block.FireCrucibleBlock;
import org.lithereal.world.block.ModBlocks;

import java.util.Optional;

public class FireCrucibleBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 200;
    protected HeatState heatState = HeatState.UNLIT;
    protected int fuelLevel = 0;
    protected int maxFuel = 0;

    public FireCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getFireCrucibleBlockEntity(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FireCrucibleBlockEntity.this.progress;
                    case 1 -> FireCrucibleBlockEntity.this.maxProgress;
                    case 2 -> FireCrucibleBlockEntity.this.heatState.heat;
                    case 3 -> FireCrucibleBlockEntity.this.fuelLevel;
                    case 4 -> FireCrucibleBlockEntity.this.maxFuel;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FireCrucibleBlockEntity.this.progress = value;
                    case 1 -> FireCrucibleBlockEntity.this.maxProgress = value;
                    case 2 -> FireCrucibleBlockEntity.this.heatState = HeatState.fromHeat(value);
                    case 3 -> FireCrucibleBlockEntity.this.fuelLevel = value;
                    case 4 -> FireCrucibleBlockEntity.this.maxFuel = value;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    public ContainerData getData() {
        return data;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Fire Crucible");
    }

    protected void resetProgress() {
        this.progress = 0;
    }

    protected static boolean canInsertItemIntoOutput(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    protected static boolean canInsertAmountIntoOutput(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    protected static int getFuelLevel(FireCrucibleBlockEntity entity) {
        return entity.fuelLevel;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FireCrucibleMenu(i, inventory, this);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        inventory.replaceAll(item -> items.get(inventory.indexOf(item)));
    }

    @Override
    public GetterAndSetter getOrSet() {
        return new GetterAndSetter(this::getItems, this::setItems);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(nbt, provider);
        saveItems(nbt, provider);
        nbt.putInt("fire_crucible.progress", progress);
        nbt.putInt("fire_crucible.max_progress", maxProgress);
        nbt.putInt("fire_crucible.heat_level", heatState.heat);
        nbt.putInt("fire_crucible.fuel_level", fuelLevel);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(nbt, provider);
        loadItems(nbt, provider);
        progress = nbt.getInt("fire_crucible.progress");
        maxProgress = nbt.getInt("fire_crucible.max_progress");
        heatState = HeatState.fromHeat(nbt.getInt("fire_crucible.heat_level"));
        fuelLevel = nbt.getInt("fire_crucible.fuel_level");
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, FireCrucibleBlockEntity pEntity) {
        if (level.isClientSide()) {
            RandomSource randomSource = level.getRandom();
            if (hasRecipe(pEntity) && randomSource.nextBoolean()) {
                Vec3 center = Vec3.upFromBottomCenterOf(blockPos, 0.2);
                double xDir = (randomSource.nextDouble() - randomSource.nextDouble()) * 0.075;
                double zDir = (randomSource.nextDouble() - randomSource.nextDouble()) * 0.075;
                if (pEntity.heatState == HeatState.LIT)
                    level.addParticle(ParticleTypes.FLAME, center.x, center.y, center.z, xDir * 0.75, 0.1, zDir * 0.75);
                else if (pEntity.heatState == HeatState.BLUE_LIT)
                    level.addParticle(ModParticles.BLUE_FIRE_FLAME.get(), center.x, center.y, center.z, xDir, 0.15, zDir);
            }
            return;
        }

        boolean hasSolidFuel = hasSolidFuel(pEntity);
        Block block = level.getBlockState(blockPos.below()).getBlock();
        boolean isBlueFireBelow = block == ModBlocks.BLUE_FIRE.get();
        boolean isFireBelow = CommonUtils.isAnyOf(block, ModBlocks.BURNING_LITHERITE_BLOCK.get(), Blocks.FIRE, Blocks.SOUL_FIRE);
        boolean isFueledFromBelow = isFireBelow || isBlueFireBelow;

        if (getFuelLevel(pEntity) > 0 && !isFueledFromBelow) {
            pEntity.fuelLevel--;
        } else {
            if (isBlueFireBelow) {
                pEntity.fuelLevel = 100;
                pEntity.maxFuel = 100;
                pEntity.heatState = HeatState.BLUE_LIT;
            } else if (isFireBelow) {
                pEntity.fuelLevel = 75;
                pEntity.maxFuel = 75;
                pEntity.heatState = HeatState.LIT;
            } else if (hasSolidFuel) {
                int fuel = AbstractFurnaceBlockEntity.getFuel().getOrDefault(((Container) pEntity).getItem(1).getItem(), 0);
                pEntity.maxFuel = fuel;
                pEntity.fuelLevel = fuel;
                pEntity.removeItem(1, 1);
                pEntity.heatState = HeatState.LIT;
            } else {
                pEntity.maxFuel = 0;
                pEntity.fuelLevel = 0;
                pEntity.heatState = HeatState.UNLIT;
            }
            level.setBlockAndUpdate(blockPos, blockState.setValue(FireCrucibleBlock.HEAT_STATE, pEntity.heatState));
        }

        if(hasRecipe(pEntity)) {
            pEntity.progress += pEntity.heatState.heat;
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress)
                craftItem(pEntity);
        } else {
            if(pEntity.progress > 0) pEntity.progress--;
            setChanged(level, blockPos, blockState);
        }
    }

    protected static void craftItem(FireCrucibleBlockEntity pEntity) {
        Level level = pEntity.getLevel();
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            inventory.setItem(i, pEntity.getItem(i));
        }

        Optional<RecipeHolder<FireCrucibleRecipe>> crucibleRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.BURNING_TYPE.get(), inventory, level);

        Optional<RecipeHolder<SmeltingRecipe>> furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, inventory, level);

        ItemStack resultItem = crucibleRecipe.map(fireCrucibleRecipeRecipeHolder -> fireCrucibleRecipeRecipeHolder.value().getResultItem(level.registryAccess())).orElseGet(() -> furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().getResultItem(level.registryAccess())).orElse(ItemStack.EMPTY));
        ItemStack outputItem = resultItem.copy();
        outputItem.setCount(pEntity.getItem(2).getCount() + outputItem.getCount());

        if (crucibleRecipe.isPresent() && crucibleRecipe.get().value().bucket().isPresent())
            pEntity.removeItem(3, 1);

        if(hasRecipe(pEntity) && !outputItem.isEmpty()) craftItem(pEntity, outputItem);
    }

    protected static void craftItem(FireCrucibleBlockEntity entity, ItemStack outputItem) {
        entity.removeItem(0, 1);
        entity.setItem(2, outputItem);

        entity.resetProgress();
    }

    protected static boolean hasRecipe(FireCrucibleBlockEntity entity) {
        Level level = entity.getLevel();
        boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
        for (int i = 0; i < entity.getContainerSize(); i++) {
            inventory.setItem(i, entity.getItem(i));
        }

        Optional<RecipeHolder<FireCrucibleRecipe>> crucibleRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.BURNING_TYPE.get(), inventory, level);

        Optional<RecipeHolder<SmeltingRecipe>> furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, inventory, level);

        if (crucibleRecipe.isPresent() || furnaceRecipe.isPresent()) {
            ItemStack resultItem = crucibleRecipe.map(fireCrucibleRecipeRecipeHolder -> fireCrucibleRecipeRecipeHolder.value().getResultItem(level.registryAccess())).orElseGet(() -> furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().getResultItem(level.registryAccess())).orElse(ItemStack.EMPTY));
            if (entity.progress == 0)
                entity.maxProgress = crucibleRecipe.map(fireCrucibleRecipeRecipeHolder -> fireCrucibleRecipeRecipeHolder.value().maxProgress()).orElseGet(() -> furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().getCookingTime()).orElse(200));
            if (canInsertAmountIntoOutput(inventory) && canInsertItemIntoOutput(inventory, resultItem))
                hasRecipe = true;
        }

        return hasRecipe;
    }

    protected static boolean hasSolidFuel(FireCrucibleBlockEntity entity) {
        ItemStack item = entity.getItem(1);
        int burnTime = AbstractFurnaceBlockEntity.getFuel().getOrDefault(item.getItem(), 0);
        return burnTime > 0;
    }

    public enum HeatState implements StringRepresentable {
        UNLIT(0, "unlit"),
        LIT(1, "lit"),
        BLUE_LIT(2, "blue_lit");
        public static final HeatState[] VALUES = new HeatState[]{UNLIT, LIT, BLUE_LIT};
        public final int heat;
        public final String name;

        HeatState(int heat, String name) {
            this.heat = heat;
            this.name = name;
        }

        public static HeatState fromHeat(int heat) {
            return VALUES[heat];
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
