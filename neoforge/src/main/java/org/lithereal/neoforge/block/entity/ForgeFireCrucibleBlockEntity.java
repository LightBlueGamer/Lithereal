package org.lithereal.neoforge.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.entity.FireCrucibleBlockEntity;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.neoforge.block.custom.ForgeFireCrucibleBlock;
import org.lithereal.neoforge.item.ForgeItems;
import org.lithereal.item.ModItems;
import org.lithereal.recipe.FireCrucibleRecipe;
import org.lithereal.recipe.ModRecipes;
import org.lithereal.util.CommonUtils;

import java.util.Optional;

public class ForgeFireCrucibleBlockEntity extends FireCrucibleBlockEntity implements ImplementedInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

    public ForgeFireCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 4);
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
        nbt.putInt("fire_crucible.progress", progress);
        nbt.putInt("fire_crucible.heat_level", heatLevel);
        nbt.putInt("fire_crucible.fuel_level", fuelLevel);
        nbt.putInt("fire_crucible.has_bucket", hasBucket);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        ContainerHelper.loadAllItems(nbt, inventory, provider);
        progress = nbt.getInt("fire_crucible.progress");
        heatLevel = nbt.getInt("fire_crucible.heat_level");
        fuelLevel = nbt.getInt("fire_crucible.fuel_level");
        hasBucket = nbt.getInt("fire_crucible.has_bucket");
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ForgeFireCrucibleBlockEntity pEntity) {
        if (level.isClientSide()) {
            if (hasRecipe(pEntity)) {
                Vec3 center = Vec3.upFromBottomCenterOf(blockPos, 0.2);
                RandomSource randomSource = level.getRandom();
                double xDir = (randomSource.nextDouble() - randomSource.nextDouble()) * 2;
                double zDir = (randomSource.nextDouble() - randomSource.nextDouble()) * 2;
                if (pEntity.heatLevel == 1)
                    level.addParticle(ParticleTypes.FLAME, center.x, center.y, center.z, xDir, 0.0015, zDir);
                else if (pEntity.heatLevel >= 2) // Temp Soul Fire
                    level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, center.x, center.y, center.z, xDir, 0.002, zDir);
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
                pEntity.heatLevel = 2;
                level.setBlockAndUpdate(blockPos, blockState.setValue(ForgeFireCrucibleBlock.BLUE_LIT, true));
            } else if (isFireBelow) {
                pEntity.fuelLevel = 75;
                pEntity.maxFuel = 75;
                pEntity.heatLevel = 1;
                level.setBlockAndUpdate(blockPos, blockState.setValue(ForgeFireCrucibleBlock.LIT, true));
            } else if (hasSolidFuel) {
                int fuel = AbstractFurnaceBlockEntity.getFuel().getOrDefault(((Container) pEntity).getItem(1).getItem(), 0);
                pEntity.maxFuel = fuel;
                pEntity.fuelLevel = fuel;
                pEntity.removeItem(1, 1);
                pEntity.heatLevel = 1;
                level.setBlockAndUpdate(blockPos, blockState.setValue(ForgeFireCrucibleBlock.LIT, true));
            } else {
                pEntity.maxFuel = 0;
                pEntity.fuelLevel = 0;
                pEntity.heatLevel = 0;
                level.setBlockAndUpdate(blockPos, blockState.setValue(ForgeFireCrucibleBlock.BLUE_LIT, false).setValue(ForgeFireCrucibleBlock.LIT, false));
            }
        }

        pEntity.setBucket(pEntity);

        if(hasRecipe(pEntity)) {
            pEntity.progress += pEntity.heatLevel;
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            if(pEntity.progress > 0) pEntity.progress--;
            setChanged(level, blockPos, blockState);
        }
    }

    protected static void craftItem(ForgeFireCrucibleBlockEntity pEntity) {
        Level level = pEntity.getLevel();
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            inventory.setItem(i, pEntity.getItem(i));
        }

        Optional<RecipeHolder<FireCrucibleRecipe>> crucibleRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.BURNING_TYPE.get(), inventory, level);

        Optional<RecipeHolder<SmeltingRecipe>> furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, inventory, level);

        ItemStack resultItem = crucibleRecipe.isPresent() ? crucibleRecipe.get().value().getResultItem(level.registryAccess()) : furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().getResultItem(level.registryAccess())).orElse(ItemStack.EMPTY);
        ItemStack outputItem = resultItem.copy();
        outputItem.setCount(pEntity.getItem(2).getCount() + outputItem.getCount());

        if(pEntity.hasBucket(pEntity)  && pEntity.getItem(0).is(ForgeItems.LITHERITE_CRYSTAL)) {
            outputItem = new ItemStack(ModItems.MOLTEN_LITHERITE_BUCKET.get(), pEntity.getItem(2).getCount() + 1);
            pEntity.removeItem(3, 1);
        }

        if(hasRecipe(pEntity) && !outputItem.isEmpty()) craftItem(pEntity, outputItem);
    }

    protected static void craftItem(ForgeFireCrucibleBlockEntity entity, ItemStack outputItem) {
        entity.removeItem(0, 1);
        entity.setItem(2, outputItem);

        entity.resetProgress();
    }

    protected static boolean hasRecipe(ForgeFireCrucibleBlockEntity entity) {
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
            ItemStack resultItem = crucibleRecipe.isPresent() ? crucibleRecipe.get().value().getResultItem(level.registryAccess()) : furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().getResultItem(level.registryAccess())).orElse(ItemStack.EMPTY);
            if(entity.hasBucket(entity) && entity.getItem(0).is(ForgeItems.LITHERITE_CRYSTAL))
                resultItem = new ItemStack(ModItems.MOLTEN_LITHERITE_BUCKET.get(), entity.getItem(2).getCount() + 1);
            if (canInsertAmountIntoOutput(inventory) && canInsertItemIntoOutput(inventory, resultItem))
                hasRecipe = true;
        }

        return hasRecipe;
    }

    protected static boolean hasSolidFuel(ForgeFireCrucibleBlockEntity entity) {
        ItemStack item = entity.getItem(1);
        int burnTime = AbstractFurnaceBlockEntity.getFuel().getOrDefault(item.getItem(), 0);
        return burnTime > 0;
    }
}
