package org.lithereal.forge.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.recipe.InfusementChamberRecipe;
import org.lithereal.recipe.ModRecipes;

import java.util.Optional;
import java.util.Random;

public class ForgeInfusementChamberBlockEntity extends InfusementChamberBlockEntity implements ImplementedInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);

    @Override
    public PotionContents getStoredPotion() {
        return inventory.get(1).getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    @Override
    public ItemStack getStoredItem() {
        return inventory.get(0);
    }

    public ForgeInfusementChamberBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        super.setChanged();
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        ContainerHelper.saveAllItems(nbt, inventory, provider);
        nbt.putInt("infusement_chamber.progress", progress);
        nbt.putFloat("infusement_chamber.power", power);
        nbt.putFloat("infusement_chamber.success_rate", successRate);
        nbt.putInt("infusement_chamber.used_potions", usedPotions);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        ContainerHelper.loadAllItems(nbt, inventory, provider);
        progress = nbt.getInt("infusement_chamber.progress");
        power = nbt.getFloat("infusement_chamber.power");
        successRate = nbt.getFloat("infusement_chamber.success_rate");
        usedPotions = nbt.getInt("infusement_chamber.used_potions");
    }

    private static void craftItem(ForgeInfusementChamberBlockEntity pEntity) {
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

    private static void craftItem(ForgeInfusementChamberBlockEntity entity, ItemStack outputItem) {
        Random random = new Random();

        entity.removeItem(0, 1);
        if(entity.getItem(1).getCount() - 1 > 0) {
            if (entity.usedPotions <= 99)
                entity.usedPotions++;
            entity.removeItem(1, 1);
        } else {
            if(entity.getItem(1).is(Items.POTION)) entity.setItem(1, new ItemStack(Items.GLASS_BOTTLE, entity.usedPotions+1));
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

    private static boolean hasRecipe(ForgeInfusementChamberBlockEntity entity) {
        Level level = entity.level;
        boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
        for (int i = 0; i < entity.getContainerSize(); i++) {
            inventory.setItem(i, entity.getItem(i));
        }

        Optional<RecipeHolder<InfusementChamberRecipe>> infusingRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.INFUSING_TYPE.get(), inventory, level);

        if (infusingRecipe.isPresent()) hasRecipe = true;

        return hasRecipe;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ForgeInfusementChamberBlockEntity pEntity) {
        if(level.isClientSide()) return;

        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            inventory.setItem(i, pEntity.getItem(i));
        }

        if(hasRecipe(pEntity)) {
            pEntity.progress += (int) (10 * pEntity.power);
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            if(pEntity.progress > 0) pEntity.progress -= (int) (10 * pEntity.power);
            setChanged(level, blockPos, blockState);
        }
    }
}
