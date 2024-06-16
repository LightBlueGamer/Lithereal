package org.lithereal.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.recipe.InfusementChamberRecipe;
import org.lithereal.recipe.ModRecipes;
import org.lithereal.screen.InfusementChamberMenu;
import org.lithereal.world.block.ModBlocks;

import java.util.Optional;
import java.util.Random;

public abstract class InfusementChamberBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory {
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 6000;
    protected PowerState powerState = PowerState.UNPOWERED;
    protected float power = 1.0f;
    protected float successRate = 0.4f;
    protected int usedPotions = 0;
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);

    public PotionContents getStoredPotion() {
        return getItem(1).getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    public ItemStack getStoredItem() {
        return getItem(0);
    }

    public InfusementChamberBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getInfusementChamberBlockEntity(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> InfusementChamberBlockEntity.this.progress;
                    case 1 -> InfusementChamberBlockEntity.this.maxProgress;
                    case 2 -> InfusementChamberBlockEntity.this.powerState.id;
                    case 3 -> InfusementChamberBlockEntity.this.usedPotions;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> InfusementChamberBlockEntity.this.progress = value;
                    case 1 -> InfusementChamberBlockEntity.this.maxProgress = value;
                    case 2 -> InfusementChamberBlockEntity.this.powerState = PowerState.fromId(value);
                    case 3 -> InfusementChamberBlockEntity.this.usedPotions = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ContainerData getData() {
        return data;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Infusement Chamber");
    }

    protected void resetProgress() {
        this.progress = 0;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new InfusementChamberMenu(i, inventory, this);
    }

    public void setEmpowerments() {
        this.power = 1.0f;
        this.successRate = 0.4f;
        int frozenBlocks = 0;
        int burningBlocks = 0;
        int chargedBlocks = 0;
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = this.getBlockPos().relative(direction);
            BlockState blockState = this.level.getBlockState(adjacentPos);
            if(blockState.is(ModBlocks.FROZEN_LITHERITE_BLOCK.get())) {
                this.power -= 0.1f;
                this.successRate += 0.15f;
                frozenBlocks++;
            } else if(blockState.is(ModBlocks.BURNING_LITHERITE_BLOCK.get())) {
                this.power += 0.15f;
                this.successRate -= 0.1f;
                burningBlocks++;
            } else if(blockState.is(ModBlocks.CHARGED_LITHERITE_BLOCK.get())) {
                this.power += 0.2f;
                this.successRate += 0.2f;
                chargedBlocks++;
            }
            powerState = PowerState.fromSurrounding(new SurroundingBlocks(frozenBlocks, burningBlocks, chargedBlocks));
        }

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
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        saveItems(nbt, provider);
        nbt.putInt("infusement_chamber.progress", progress);
        nbt.putInt("infusement_chamber.max_progress", maxProgress);
        nbt.putInt("infusement_chamber.power_state", powerState.id);
        nbt.putFloat("infusement_chamber.power", power);
        nbt.putFloat("infusement_chamber.success_rate", successRate);
        nbt.putInt("infusement_chamber.used_potions", usedPotions);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        loadItems(nbt, provider);
        progress = nbt.getInt("infusement_chamber.progress");
        maxProgress = nbt.getInt("infusement_chamber.max_progress");
        powerState = PowerState.fromId(nbt.getInt("infusement_chamber.power_state"));
        power = nbt.getFloat("infusement_chamber.power");
        successRate = nbt.getFloat("infusement_chamber.success_rate");
        usedPotions = nbt.getInt("infusement_chamber.used_potions");
    }

    private static void craftItem(InfusementChamberBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            ItemStack item = pEntity.getItem(i);
            inventory.setItem(i, item);
        }

        PotionContents potion = pEntity.getItem(1).getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

        Optional<RecipeHolder<InfusementChamberRecipe>> infusingRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.INFUSING_TYPE.get(), inventory, level);

        ItemStack outputItem = ItemStack.EMPTY;
        if (infusingRecipe.isPresent()) {
            ItemStack resultItem = infusingRecipe.get().value().getResultItem(level.registryAccess());
            resultItem.set(DataComponents.POTION_CONTENTS, potion);
            outputItem = resultItem.copy();
        }

        if(hasRecipe(pEntity) && !outputItem.isEmpty()) {
            craftItem(pEntity, outputItem);
            setChanged(level, pEntity.getBlockPos(), pEntity.getBlockState());
        }
    }

    private static void craftItem(InfusementChamberBlockEntity entity, ItemStack outputItem) {
        Random random = new Random();

        entity.removeItem(0, 1);
        if(entity.getItem(1).getCount() - 1 > 0) {
            if (entity.usedPotions <= 99)
                entity.usedPotions++;
            entity.removeItem(1, 1);
        } else {
            if (entity.getItem(1).is(Items.POTION)) entity.setItem(1, new ItemStack(Items.GLASS_BOTTLE, entity.usedPotions+1));
            else entity.removeItem(1, 1);
            entity.usedPotions = 0;
        }
        if (random.nextFloat() < entity.successRate) {
            entity.setItem(0, outputItem);
        } else {
            boolean mobGriefingEnabled = entity.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
            BlockPos blockPos = entity.getBlockPos();
            entity.level.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 7.0f, mobGriefingEnabled, Level.ExplosionInteraction.TNT);
        }

        entity.resetProgress();
    }

    private static boolean hasRecipe(InfusementChamberBlockEntity entity) {
        Level level = entity.level;
        boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
        for (int i = 0; i < entity.getContainerSize(); i++) {
            inventory.setItem(i, entity.getItem(i));
        }

        Optional<RecipeHolder<InfusementChamberRecipe>> infusingRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.INFUSING_TYPE.get(), inventory, level);
        if (entity.progress == 0)
            entity.maxProgress = infusingRecipe.map(infusementChamberRecipeRecipeHolder -> infusementChamberRecipeRecipeHolder.value().maxProgress()).orElse(200);

        if (infusingRecipe.isPresent()) hasRecipe = true;

        return hasRecipe;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, InfusementChamberBlockEntity pEntity) {
        if(level.isClientSide()) return;

        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            inventory.setItem(i, pEntity.getItem(i));
        }

        if(hasRecipe(pEntity)) {
            pEntity.progress += (int) (10 * pEntity.power);
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress)
                craftItem(pEntity);
        } else {
            if(pEntity.progress > 0) pEntity.progress -= (int) (10 * pEntity.power);
            setChanged(level, blockPos, blockState);
        }
    }

    public enum PowerState {
        UNPOWERED(0),
        FROZEN(1),
        BURNING(2),
        CHARGED(3);
        public static final PowerState[] VALUES = new PowerState[]{UNPOWERED, FROZEN, BURNING, CHARGED};
        public final int id;

        PowerState(int id) {
            this.id = id;
        }

        public static PowerState fromId(int id) {
            return VALUES[id];
        }

        public static PowerState fromSurrounding(SurroundingBlocks surrounding) {
            return switch (surrounding) {
                case SurroundingBlocks ignored when surrounding.isGreatestCharged() -> CHARGED;
                case SurroundingBlocks ignored when surrounding.isGreatestFrozen() -> FROZEN;
                case SurroundingBlocks ignored when surrounding.isGreatestBurning() -> BURNING;
                default -> UNPOWERED;
            };
        }
    }

    public record SurroundingBlocks(int frozen, int burning, int charged) {
        public SurroundingBlocks(int[] values) {
            this(values[0], values[1], values[2]);
        }
        public boolean isGreatestCharged() {
            return charged >= frozen && charged >= burning;
        }
        public boolean isGreatestFrozen() {
            return frozen >= burning && frozen >= charged;
        }
        public boolean isGreatestBurning() {
            return burning >= frozen && burning >= charged;
        }

    }

}
