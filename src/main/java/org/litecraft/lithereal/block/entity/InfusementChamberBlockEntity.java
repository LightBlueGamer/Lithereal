package org.litecraft.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.litecraft.lithereal.Lithereal;
import org.litecraft.lithereal.recipe.InfusementChamberRecipe;
import org.litecraft.lithereal.screen.InfusementChamberMenu;

import java.util.Optional;

public class InfusementChamberBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    public InfusementChamberBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INFUSEMENT_CHAMBER.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> InfusementChamberBlockEntity.this.progress;
                    case 1 -> InfusementChamberBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> InfusementChamberBlockEntity.this.progress = value;
                    case 1 -> InfusementChamberBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Infusement Chamber");
    }

    @Nullable
    @Override
    public InfusementChamberMenu createMenu(int id, Inventory inventory, Player player) {
        return new InfusementChamberMenu(id, inventory, this, this.data);
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

    

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, InfusementChamberBlockEntity pEntity) {
        if(level.isClientSide()) return;

        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        if(hasRecipe(pEntity)) {
            pEntity.progress++;
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            if(pEntity.progress > 0) pEntity.progress--;
            setChanged(level, blockPos, blockState);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(InfusementChamberBlockEntity pEntity) {
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
        ItemStack outputItem = new ItemStack(resultItem.getItem(), pEntity.itemHandler.getStackInSlot(2).getCount() + resultItem.getCount());

        if(hasRecipe(pEntity)) {
            craftItem(pEntity, resultItem, outputItem);
        }
    }

    private static void craftItem(InfusementChamberBlockEntity entity, ItemStack resultItem, ItemStack outputItem) {
        CompoundTag nbt = resultItem.getTag();
        if(nbt != null) {
            outputItem.setTag(nbt.copy());
        }

        entity.itemHandler.extractItem(0, 1, false);
        entity.itemHandler.extractItem(1, 1, false);
        entity.itemHandler.setStackInSlot(2, outputItem);

        entity.resetProgress();
    }

    private static boolean hasRecipe(InfusementChamberBlockEntity entity) {
        Level level = entity.level;
        Boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<InfusementChamberRecipe> infusingRecipe = level.getRecipeManager()
                .getRecipeFor(InfusementChamberRecipe.Type.INSTANCE, inventory, level);

        if (infusingRecipe.isPresent()) {
            ItemStack resultItem = infusingRecipe.get().getResultItem(level.registryAccess());
            if (canInsertAmountIntoOutput(inventory) && canInsertItemIntoOutput(inventory, resultItem)) {
                hasRecipe = true;
            }
        }

        return hasRecipe;
    }

    private static boolean canInsertItemIntoOutput(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2) == itemStack || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutput(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }
}
