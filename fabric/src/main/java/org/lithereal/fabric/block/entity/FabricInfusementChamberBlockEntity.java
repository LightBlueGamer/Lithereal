package org.lithereal.fabric.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
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
import org.jetbrains.annotations.Nullable;
import org.lithereal.block.entity.ImplementedInventory;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.fabric.screen.FabricInfusementChamberMenu;
import org.lithereal.recipe.InfusementChamberRecipe;

import java.util.Optional;
import java.util.Random;

public class FabricInfusementChamberBlockEntity extends InfusementChamberBlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 2;
    private static final int INPUT_SLOT_2 = 1;

    public FabricInfusementChamberBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public ItemStack getRenderStack() {
        if(this.getItem(OUTPUT_SLOT).isEmpty()) {
            return this.getItem(INPUT_SLOT);
        } else {
            return this.getItem(OUTPUT_SLOT);
        }
    }

    @Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        super.setChanged();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeBlockPos(worldPosition);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FabricInfusementChamberMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, inventory);
        nbt.putInt("infusement_chamber.progress", progress);
        nbt.putFloat("infusement_chamber.power", power);
        nbt.putFloat("infusement_chamber.success_rate", successRate);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ContainerHelper.loadAllItems(nbt, inventory);
        progress = nbt.getInt("infusement_chamber.progress");
        power = nbt.getFloat("infusement_chamber.power");
        successRate = nbt.getFloat("infusement_chamber.success_rate");
    }

    private static void craftItem(FabricInfusementChamberBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize());
        for (int i = 0; i < pEntity.getContainerSize(); i++) {
            ItemStack item = pEntity.getItem(i);
            inventory.setItem(i, item);
        }

        Potion potion = PotionUtils.getPotion(pEntity.getItem(1));

        Optional<InfusementChamberRecipe> infusingRecipe = level.getRecipeManager()
                .getRecipeFor(InfusementChamberRecipe.Type.INSTANCE, inventory, level);

        ItemStack resultItem = infusingRecipe.get().getResultItem(level.registryAccess());
        PotionUtils.setPotion(resultItem, potion);
        ItemStack outputItem = new ItemStack(resultItem.getItem(), pEntity.getItem(2).getCount() + resultItem.getCount());

        if(hasRecipe(pEntity)) {
            craftItem(pEntity, resultItem, outputItem);
        }
    }

    private static void craftItem(FabricInfusementChamberBlockEntity entity, ItemStack resultItem, ItemStack outputItem) {
        CompoundTag nbt = resultItem.getTag();
        Random random = new Random();
        if(nbt != null) {
            outputItem.setTag(nbt.copy());
        }

        entity.removeItem(0, 1);
        if(entity.getItem(1).is(Items.POTION)) entity.setItem(1, new ItemStack(Items.GLASS_BOTTLE));
        else entity.removeItem(1, 1);
        if (random.nextFloat() < entity.successRate) {
            entity.setItem(2, outputItem);
        } else {
            boolean mobGriefingEnabled = entity.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
            BlockPos blockPos = entity.getBlockPos();
            entity.level.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 7.0f, mobGriefingEnabled, Level.ExplosionInteraction.TNT);
        }

        entity.resetProgress();
    }

    private static boolean hasRecipe(FabricInfusementChamberBlockEntity entity) {
        Level level = entity.level;
        Boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());
        for (int i = 0; i < entity.getContainerSize(); i++) {
            inventory.setItem(i, entity.getItem(i));
        }

        Optional<InfusementChamberRecipe> infusingRecipe = level.getRecipeManager()
                .getRecipeFor(InfusementChamberRecipe.Type.INSTANCE, inventory, level);

        if (infusingRecipe.isPresent()) hasRecipe = true;

        return hasRecipe;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, FabricInfusementChamberBlockEntity pEntity) {
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
