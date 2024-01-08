package org.lithereal.fabric.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.Lithereal;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.custom.FireCrucibleBlock;
import org.lithereal.block.entity.FireCrucibleBlockEntity;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.fabric.block.custom.FabricFireCrucibleBlock;
import org.lithereal.fabric.item.FabricItems;
import org.lithereal.fabric.screen.FabricFireCrucibleMenu;
import org.lithereal.fabric.screen.FabricFreezingStationMenu;
import org.lithereal.item.ModItems;
import org.lithereal.recipe.FireCrucibleRecipe;

import java.util.Optional;

public class FabricFireCrucibleBlockEntity extends FireCrucibleBlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

    public FabricFireCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 4);
        super.setChanged();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeBlockPos(worldPosition);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FabricFireCrucibleMenu(id, inventory, this, this.data);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, inventory);
        nbt.putInt("fire_crucible.progress", progress);
        nbt.putInt("fire_crucible.heat_level", heatLevel);
        nbt.putInt("fire_crucible.fuel_level", fuelLevel);
        nbt.putInt("fire_crucible.has_bucket", hasBucket);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ContainerHelper.loadAllItems(nbt, inventory);
        progress = nbt.getInt("fire_crucible.progress");
        heatLevel = nbt.getInt("fire_crucible.heat_level");
        fuelLevel = nbt.getInt("fire_crucible.fuel_level");
        hasBucket = nbt.getInt("fire_crucible.has_bucket");
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, FabricFireCrucibleBlockEntity pEntity) {
        if(level.isClientSide()) return;

        boolean hasSolidFuel = hasSolidFuel(pEntity);
        boolean isBlueFireBelow = level.getBlockState(blockPos.below()).getBlock() == ModBlocks.BLUE_FIRE.get();

        if (getFuelLevel(pEntity) > 0 && !isBlueFireBelow) {
            pEntity.fuelLevel--;
        } else {
            if (isBlueFireBelow) {
                pEntity.fuelLevel = 100;
                pEntity.maxFuel = 100;
                pEntity.heatLevel = 2;
                level.setBlockAndUpdate(blockPos, blockState.setValue(FabricFireCrucibleBlock.BLUE_LIT, true));
            } else if (hasSolidFuel) {
                int fuel = AbstractFurnaceBlockEntity.getFuel().getOrDefault(((Container) pEntity).getItem(1).getItem(), 0);;
                pEntity.maxFuel = fuel;
                pEntity.fuelLevel = fuel;
                pEntity.removeItem(1, 1);
                pEntity.heatLevel = 1;
                level.setBlockAndUpdate(blockPos, blockState.setValue(FabricFireCrucibleBlock.LIT, true));
            } else {
                pEntity.maxFuel = 0;
                pEntity.fuelLevel = 0;
                pEntity.heatLevel = 0;
                level.setBlockAndUpdate(blockPos, blockState.setValue(FabricFireCrucibleBlock.BLUE_LIT, false).setValue(FireCrucibleBlock.LIT, false));
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

    protected static void craftItem(FabricFireCrucibleBlockEntity pEntity) {
        Level level = pEntity.getLevel();
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            inventory.setItem(i, pEntity.getItem(i));
        }

        Optional<FireCrucibleRecipe> crucibleRecipe = level.getRecipeManager()
                .getRecipeFor(FireCrucibleRecipe.Type.INSTANCE, inventory, level);

        Optional<SmeltingRecipe> furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, inventory, level);

        ItemStack resultItem = crucibleRecipe.isPresent() ? crucibleRecipe.get().getResultItem(level.registryAccess()) : furnaceRecipe.get().getResultItem(level.registryAccess());
        ItemStack outputItem = new ItemStack(resultItem.getItem(), pEntity.getItem(2).getCount() + resultItem.getCount());

        if(pEntity.hasBucket(pEntity)  && pEntity.getItem(0).is(FabricItems.LITHERITE_CRYSTAL)) {
            outputItem = new ItemStack(ModItems.MOLTEN_LITHERITE_BUCKET.get(), pEntity.getItem(2).getCount() + 1);
            pEntity.removeItem(3, 1);
        }

        if(hasRecipe(pEntity)) craftItem(pEntity, resultItem, outputItem);
    }

    protected static void craftItem(FabricFireCrucibleBlockEntity entity, ItemStack resultItem, ItemStack outputItem) {
        CompoundTag nbt = resultItem.getTag();
        if(nbt != null) {
            outputItem.setTag(nbt.copy());
        }

        entity.removeItem(0, 1);
        entity.setItem(2, outputItem);

        entity.resetProgress();
    }

    protected static boolean hasRecipe(FabricFireCrucibleBlockEntity entity) {
        Level level = entity.getLevel();
        Boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
        for (int i = 0; i < entity.getContainerSize(); i++) {
            inventory.setItem(i, entity.getItem(i));
        }

        Optional<FireCrucibleRecipe> crucibleRecipe = level.getRecipeManager()
                .getRecipeFor(FireCrucibleRecipe.Type.INSTANCE, inventory, level);

        Optional<SmeltingRecipe> furnaceRecipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, inventory, level);

        if (crucibleRecipe.isPresent() || furnaceRecipe.isPresent()) {
            ItemStack resultItem = crucibleRecipe.isPresent() ? crucibleRecipe.get().getResultItem(level.registryAccess()) : furnaceRecipe.get().getResultItem(level.registryAccess());
            if(entity.hasBucket(entity)  && entity.getItem(0).is(FabricItems.LITHERITE_CRYSTAL)) {
                resultItem = new ItemStack(ModItems.MOLTEN_LITHERITE_BUCKET.get(), entity.getItem(2).getCount() + 1);
            }
            if (canInsertAmountIntoOutput(inventory) && canInsertItemIntoOutput(inventory, resultItem)) {
                hasRecipe = true;
            }
        }

        return hasRecipe;
    }

    protected static boolean hasSolidFuel(FabricFireCrucibleBlockEntity entity) {
        ItemStack item = entity.getItem(1);
        int burnTime = AbstractFurnaceBlockEntity.getFuel().getOrDefault(item.getItem(), 0);
        return burnTime > 0;
    }

}
