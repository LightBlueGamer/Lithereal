package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.lithereal.util.PotionStorage;

public class InfusedLitheriteBlockEntity extends BlockEntity implements PotionStorage {
    public PotionContents potion = PotionContents.EMPTY;

    public InfusedLitheriteBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INFUSED_LITHERITE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(PotionStorage.setPotion(valueOutput, potion));
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        if (valueInput.contains("Potion"))
            setPotion(valueInput.read("Potion", PotionContents.CODEC).orElse(PotionContents.EMPTY));
    }

    @Override
    public void setPotion(PotionContents potion) {
        this.potion = potion;
        setChanged();
    }
    @Override
    public PotionContents getStoredPotion() {
        return this.potion;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.@NonNull Provider provider) {
        return this.saveWithoutMetadata(provider);
    }

    @Override
    protected void applyImplicitComponents(@NonNull DataComponentGetter components) {
        super.applyImplicitComponents(components);
        this.potion = components.get(DataComponents.POTION_CONTENTS);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NonNull Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.POTION_CONTENTS, this.potion);
    }

    @Override
    public void removeComponentsFromTag(ValueOutput output) {
        output.discard("Potion");
        super.removeComponentsFromTag(output);
    }
}
