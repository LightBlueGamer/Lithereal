package org.lithereal.block.entity;


//? fabric {
/*import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
*///?}
//? neoforge {
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.lithereal.neoforge.client.event.ClientEvents;
import org.lithereal.neoforge.util.ImplementedItemHandler;
import org.lithereal.neoforge.util.NeoForgeInventory;
//?}
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.lithereal.block.FireCrucibleBlock;
import org.lithereal.block.ModBlocks;
import org.lithereal.block.ModStorageBlocks;
import org.lithereal.client.gui.screens.inventory.FireCrucibleMenu;
import org.lithereal.client.particle.ModParticles;
import org.lithereal.data.recipes.ContainerRecipeInput;
import org.lithereal.data.recipes.FireCrucibleRecipe;
import org.lithereal.data.recipes.ModRecipes;
import org.lithereal.util.CommonUtils;

import java.util.Optional;
//? neoforge {
import java.util.concurrent.atomic.AtomicInteger;
//?}

//? fabric {
/*public class FireCrucibleBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory, ExtendedMenuProvider<BlockPos> {
*///?}
//? neoforge {
public class FireCrucibleBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory, NeoForgeInventory {
//?}
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 200;
    protected HeatState heatState = HeatState.UNLIT;
    protected int fuelLevel = 0;
    protected int maxFuel = 0;

    public FireCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIRE_CRUCIBLE_BLOCK_ENTITY.get(), pos, state);
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

    public static boolean canInsertItemInto(int slot, SimpleContainer inventory, ItemStack itemStack) {
        ItemStack baseStack = inventory.getItem(slot);
        return baseStack.isEmpty() || (ItemStack.isSameItemSameComponents(baseStack, itemStack) && baseStack.getMaxStackSize() > itemStack.getCount() + baseStack.getCount());
    }

    protected static int getFuelLevel(FireCrucibleBlockEntity entity) {
        return entity.fuelLevel;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FireCrucibleMenu(i, inventory, this);
    }

    //? fabric {
    /*@Override
    public void setChanged() {
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 4);
        super.setChanged();
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        inventory.replaceAll(item -> items.get(inventory.indexOf(item)));
    }
    *///?}

    @Override
    public GetterAndSetter getOrSet() {
        return new GetterAndSetter(this::getItems, this::setItems);
    }

    @Override
    protected void saveAdditional(@NotNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        saveItems(valueOutput);
        valueOutput.putInt("fire_crucible.progress", progress);
        valueOutput.putInt("fire_crucible.max_progress", maxProgress);
        valueOutput.putInt("fire_crucible.heat_level", heatState.heat);
        valueOutput.putInt("fire_crucible.fuel_level", fuelLevel);
        valueOutput.putInt("fire_crucible.max_fuel_level", maxFuel);
    }

    @Override
    public void loadAdditional(@NotNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        loadItems(valueInput);
        progress = valueInput.getIntOr("fire_crucible.progress", 0);
        maxProgress = valueInput.getIntOr("fire_crucible.max_progress", 200);
        heatState = HeatState.fromHeat(valueInput.getIntOr("fire_crucible.heat_level", 0));
        fuelLevel = valueInput.getIntOr("fire_crucible.fuel_level", 0);
        maxFuel = valueInput.getIntOr("fire_crucible.max_fuel_level", 0);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveWithoutMetadata(provider);
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

        boolean hasSolidFuel = hasSolidFuel(pEntity, level);
        Block block = level.getBlockState(blockPos.below()).getBlock();
        boolean isBlueFireBelow = block == ModBlocks.BLUE_FIRE.get();
        boolean isFireBelow = CommonUtils.isAnyOf(block, ModStorageBlocks.BURNING_LITHERITE_BLOCK.get(), Blocks.FIRE, Blocks.SOUL_FIRE);
        boolean isFueledFromBelow = isFireBelow || isBlueFireBelow;

        if (getFuelLevel(pEntity) > 0 && !isFueledFromBelow) {
            if (!hasSolidFuel || hasRecipe(pEntity)) pEntity.fuelLevel--;
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
                int fuel = pEntity.getBurnDuration(level.fuelValues(), pEntity.getItem(0));
                pEntity.maxFuel = fuel;
                pEntity.fuelLevel = fuel;
                pEntity.removeItemWithRemainder(0, 1);
                pEntity.heatState = HeatState.LIT;
            } else {
                pEntity.maxFuel = 0;
                pEntity.fuelLevel = 0;
                pEntity.heatState = HeatState.UNLIT;
            }
            level.setBlockAndUpdate(blockPos, blockState.setValue(FireCrucibleBlock.HEAT_STATE, pEntity.heatState));
        }

        if(pEntity.heatState.isLit() && hasRecipe(pEntity)) {
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
        SimpleContainer inventory = new SimpleContainer(pEntity.getContainerSize() - 1);
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            inventory.setItem(i, pEntity.getItem(i + 1));
        }

        ContainerRecipeInput crucibleInput = new ContainerRecipeInput(inventory);
        Optional<RecipeHolder<FireCrucibleRecipe>> crucibleRecipe = ((ServerLevel)level).recipeAccess()
                .getRecipeFor(ModRecipes.BURNING_TYPE.get(), crucibleInput, level);

        SingleRecipeInput smeltingInput = new SingleRecipeInput(inventory.getItem(0));
        Optional<RecipeHolder<SmeltingRecipe>> furnaceRecipe = ((ServerLevel)level).recipeAccess()
                .getRecipeFor(RecipeType.SMELTING, smeltingInput, level);

        ItemStack resultItem = crucibleRecipe.map(fireCrucibleRecipeRecipeHolder -> fireCrucibleRecipeRecipeHolder.value().assemble(crucibleInput)).orElseGet(() -> furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().assemble(smeltingInput)).orElse(ItemStack.EMPTY));
        ItemStack outputItem = resultItem.copy();
        outputItem.setCount(pEntity.getItem(3).getCount() + outputItem.getCount());

        boolean shouldRemoveSecondary = crucibleRecipe.isPresent() && crucibleRecipe.get().value().secondary().isPresent();

        if(hasRecipe(pEntity) && !outputItem.isEmpty()) craftItem(pEntity, outputItem, shouldRemoveSecondary);
    }

    protected static void craftItem(FireCrucibleBlockEntity entity, ItemStack outputItem, boolean shouldRemoveSecondary) {
        entity.removeItemWithRemainder(1, 1);
        if (shouldRemoveSecondary) entity.removeItemWithRemainder(2, 1);
        entity.setItem(3, outputItem);

        entity.resetProgress();
    }

    protected static boolean hasRecipe(FireCrucibleBlockEntity entity) {
        Level level = entity.getLevel();
        boolean hasRecipe = false;
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize() - 1);
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            inventory.setItem(i, entity.getItem(i + 1));
        }

        ContainerRecipeInput crucibleInput = new ContainerRecipeInput(inventory);
        SingleRecipeInput smeltingInput = new SingleRecipeInput(inventory.getItem(0));
        Optional<RecipeHolder<FireCrucibleRecipe>> crucibleRecipe;
        Optional<RecipeHolder<SmeltingRecipe>> furnaceRecipe;
        if (!(level instanceof ServerLevel serverLevel)) {
            //? fabric {
            /*crucibleRecipe = level.recipeAccess().getSynchronizedRecipes()
                    .getFirstMatch(ModRecipes.BURNING_TYPE.get(), crucibleInput, level);
            furnaceRecipe = level.recipeAccess().getSynchronizedRecipes()
                    .getFirstMatch(RecipeType.SMELTING, smeltingInput, level);
            *///?}
            //? neoforge {
            crucibleRecipe = ClientEvents.ClientForgeBusEvents.getRecipes(ModRecipes.BURNING_TYPE.get())
                    .stream().filter(recipeHolder -> recipeHolder.value().matches(crucibleInput, level)).findFirst();
            furnaceRecipe = ClientEvents.ClientForgeBusEvents.getRecipes(RecipeType.SMELTING)
                    .stream().filter(recipeHolder -> recipeHolder.value().matches(smeltingInput, level)).findFirst();
            //?}
        } else {
            crucibleRecipe = serverLevel.recipeAccess()
                    .getRecipeFor(ModRecipes.BURNING_TYPE.get(), crucibleInput, level);

            furnaceRecipe = serverLevel.recipeAccess()
                    .getRecipeFor(RecipeType.SMELTING, smeltingInput, level);
        }

        if (crucibleRecipe.isPresent() || furnaceRecipe.isPresent()) {
            ItemStack resultItem = crucibleRecipe.map(fireCrucibleRecipeRecipeHolder -> fireCrucibleRecipeRecipeHolder.value().assemble(crucibleInput)).orElseGet(() -> furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().assemble(smeltingInput)).orElse(ItemStack.EMPTY));
            if (entity.progress == 0)
                entity.maxProgress = crucibleRecipe.map(fireCrucibleRecipeRecipeHolder -> fireCrucibleRecipeRecipeHolder.value().maxProgress()).orElseGet(() -> furnaceRecipe.map(smeltingRecipeRecipeHolder -> smeltingRecipeRecipeHolder.value().cookingTime()).orElse(200));
            if (canInsertItemInto(3, inventory, resultItem))
                hasRecipe = true;
        }

        return hasRecipe;
    }

    //? fabric {
    /*@Override
    public BlockPos getScreenOpeningData(@NonNull ServerPlayer player) {
        return worldPosition;
    }
    *///?}

    protected static boolean hasSolidFuel(FireCrucibleBlockEntity entity, Level level) {
        ItemStack item = entity.getItem(0);
        int burnTime = entity.getBurnDuration(level.fuelValues(), item);
        return burnTime > 0;
    }

    protected int getBurnDuration(final FuelValues fuelValues, final ItemStack itemStack) {
        return fuelValues.burnDuration(itemStack);
    }

    //? neoforge {
    private final ImplementedItemHandler itemHandler = new ImplementedItemHandler(4, this);

    @Override
    public NonNullList<ItemStack> getItems() {
        return itemHandler.getStacks();
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        AtomicInteger index = new AtomicInteger();
        items.forEach(itemStack -> {
            if (index.get() > getHandler().size())
                return;
            itemHandler.set(index.getAndIncrement(), ItemResource.of(itemStack), itemStack.getCount());
        });
    }

    @Override
    public ImplementedItemHandler getHandler() {
        return itemHandler;
    }

    @Override
    public void saveItems(@NotNull ValueOutput valueOutput) {
        getHandler().serialize(valueOutput.child("Items"));
    }

    @Override
    public void loadItems(@NotNull ValueInput valueInput) {
        getHandler().deserialize(valueInput.childOrEmpty("Items"));
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return itemHandler.setStackInSlotNoUpdate(slot, ItemStack.EMPTY);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int count) {
        ItemStack originalStack = itemHandler.getStacks().get(slot);
        itemHandler.set(slot, itemHandler.getResourceFrom(originalStack), originalStack.getCount() - count);
        return originalStack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        itemHandler.set(slot, ItemResource.of(stack), stack.getCount());
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return itemHandler.getResource(slot).toStack(itemHandler.getAmountAsInt(slot));
    }

    @Override
    public void clearContent() {
        for (int index = 0; index < itemHandler.size(); index++) {
            removeItemNoUpdate(index);
        }
    }

    @Override
    public int getContainerSize() {
        return itemHandler.size();
    }
    //?}

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
        public boolean isLit() {
            return heat > 0;
        }

        public int light() {
            return switch (this) {
                case UNLIT -> 0;
                case LIT -> 8;
                case BLUE_LIT -> 15;
            };
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
