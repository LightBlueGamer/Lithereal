package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;

import static net.minecraft.world.item.alchemy.PotionUtils.getPotion;

public class InfusedLitheriteBlockEntity extends BlockEntity {
    public Potion potion = Potions.EMPTY;

    public InfusedLitheriteBlockEntity(BlockPos pos, BlockState state) {
        super(LitherealExpectPlatform.getInfusedLitheriteBlockEntity(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(setPotion(nbt, potion));
    }

    @Override
    public void load(CompoundTag nbt) {
        setPotion(getPotion(nbt));
        super.load(nbt);
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
        setChanged();
    }
    public Potion getStoredPotion() {
        return this.potion;
    }
    public static CompoundTag setPotion(CompoundTag nbt, Potion p_43551_) {
        ResourceLocation resourcelocation = BuiltInRegistries.POTION.getKey(p_43551_);
        if (p_43551_ == Potions.EMPTY) {
            nbt.remove("Potion");
        } else {
            nbt.putString("Potion", resourcelocation.toString());
        }

        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
