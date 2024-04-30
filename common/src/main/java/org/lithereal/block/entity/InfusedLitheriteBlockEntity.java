package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;

public class InfusedLitheriteBlockEntity extends BlockEntity {
    public PotionContents potion = PotionContents.EMPTY;

    public InfusedLitheriteBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getInfusedLitheriteBlockEntity(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(setPotion(nbt, potion), provider);
    }

    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        if (compoundTag.contains("Potion"))
            setPotion(new PotionContents(BuiltInRegistries.POTION.wrapAsHolder(BuiltInRegistries.POTION.get(ResourceLocation.tryParse(compoundTag.getString("Potion"))))));
    }

    public void setPotion(PotionContents potion) {
        this.potion = potion;
        setChanged();
    }
    public PotionContents getStoredPotion() {
        return this.potion;
    }
    public static CompoundTag setPotion(CompoundTag nbt, PotionContents contents) {
        ResourceLocation resourceLocation = BuiltInRegistries.POTION.getKey(contents.potion().orElse(Potions.WATER).value());
        if (contents.potion().isEmpty()) nbt.remove("Potion");
        else nbt.putString("Potion", resourceLocation.toString());

        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    protected void applyImplicitComponents(BlockEntity.DataComponentInput dataComponentInput) {
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
