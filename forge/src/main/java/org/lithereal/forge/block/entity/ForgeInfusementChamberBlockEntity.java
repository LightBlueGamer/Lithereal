package org.lithereal.forge.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.forge.screen.ForgeInfusementChamberMenu;
import org.lithereal.recipe.InfusementChamberRecipe;

import java.util.Optional;
import java.util.Random;

public class ForgeInfusementChamberBlockEntity extends InfusementChamberBlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    @Override
    public Potion getStoredPotion() {
        return PotionUtils.getPotion(itemHandler.getStackInSlot(1));
    }

    @Override
    public ItemStack getStoredItem() {
        return itemHandler.getStackInSlot(0);
    }

    public ForgeInfusementChamberBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ForgeInfusementChamberMenu(id, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private static void craftItem(ForgeInfusementChamberBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            ItemStack item = pEntity.itemHandler.getStackInSlot(i);
            inventory.setItem(i, item);
        }

        Potion potion = PotionUtils.getPotion(pEntity.itemHandler.getStackInSlot(1));

        Optional<InfusementChamberRecipe> infusingRecipe = level.getRecipeManager()
                .getRecipeFor(InfusementChamberRecipe.Type.INSTANCE, inventory, level);

        ItemStack resultItem = infusingRecipe.get().getResultItem(level.registryAccess());
        PotionUtils.setPotion(resultItem, potion);
        ItemStack outputItem = new ItemStack(resultItem.getItem(), resultItem.getCount());

        if(hasRecipe(pEntity)) {
            craftItem(pEntity, resultItem, outputItem);
            setChanged(level, pEntity.getBlockPos(), pEntity.getBlockState());
        }
    }

    private static void craftItem(ForgeInfusementChamberBlockEntity entity, ItemStack resultItem, ItemStack outputItem) {
        CompoundTag nbt = resultItem.getTag();
        Random random = new Random();
        if(nbt != null) {
            outputItem.setTag(nbt.copy());
        }

        entity.itemHandler.extractItem(0, 1, false);
        if(entity.itemHandler.getStackInSlot(1).getCount() - 1 > 0) {
            if (entity.usedPotions <= 64) {
                entity.usedPotions++;
            }
            entity.itemHandler.extractItem(1, 1, false);
        } else {
            if(entity.itemHandler.getStackInSlot(1).is(Items.POTION)) entity.itemHandler.setStackInSlot(1, new ItemStack(Items.GLASS_BOTTLE, entity.usedPotions+1));
            else entity.itemHandler.extractItem(1, entity.usedPotions, false);
            entity.usedPotions = 0;
        }
        if (random.nextFloat() < entity.successRate) {
            entity.itemHandler.setStackInSlot(0, outputItem);
        } else {
            boolean mobGriefingEnabled = entity.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
            BlockPos blockPos = entity.getBlockPos();
            entity.level.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 7.0f, mobGriefingEnabled, Level.ExplosionInteraction.TNT);
        }

        entity.resetProgress();
    }

    private static boolean hasRecipe(ForgeInfusementChamberBlockEntity entity) {
        Level level = entity.level;
        Boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<InfusementChamberRecipe> infusingRecipe = level.getRecipeManager()
                .getRecipeFor(InfusementChamberRecipe.Type.INSTANCE, inventory, level);

        if (infusingRecipe.isPresent()) hasRecipe = true;

        return hasRecipe;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ForgeInfusementChamberBlockEntity pEntity) {
        if(level.isClientSide()) return;

        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
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
