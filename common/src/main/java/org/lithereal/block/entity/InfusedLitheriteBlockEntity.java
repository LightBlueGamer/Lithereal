package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class InfusedLitheriteBlockEntity extends BlockEntity {
    public PotionContents potion = PotionContents.EMPTY;

    public InfusedLitheriteBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INFUSED_LITHERITE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(setPotion(nbt, provider, potion), provider);
    }

    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        if (compoundTag.contains("Potion"))
            setPotion(PotionContents.CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), compoundTag.get("Potion")).mapOrElse(Function.identity(), potionContentsError -> PotionContents.EMPTY));
    }

    public void setPotion(PotionContents potion) {
        this.potion = potion;
        setChanged();
    }
    public PotionContents getStoredPotion() {
        return this.potion;
    }
    public static CompoundTag setPotion(CompoundTag nbt, HolderLookup.Provider provider, PotionContents contents) {
        if (contents.potion().isEmpty()) nbt.remove("Potion");
        else {
            Tag potion = PotionContents.CODEC.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), contents).getOrThrow();
            nbt.put("Potion", potion);
        }

        return nbt;
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

    protected void applyImplicitComponents(DataComponentInput dataComponentInput) {
        super.applyImplicitComponents(dataComponentInput);
        this.potion = dataComponentInput.get(DataComponents.POTION_CONTENTS);
    }

    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.POTION_CONTENTS, this.potion);
    }

    @Override
    public void removeComponentsFromTag(CompoundTag compoundTag) {
        compoundTag.remove("Potion");
        super.removeComponentsFromTag(compoundTag);
    }
}
