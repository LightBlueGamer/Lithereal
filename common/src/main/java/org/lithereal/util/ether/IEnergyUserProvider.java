package org.lithereal.util.ether;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public interface IEnergyUserProvider {
    <B extends BlockEntity & IEnergyUserProvider> B asBlockEntity();
    IEnergyUser getEnergyUser();
    TransferMode getTransferModeForDirection(Direction direction);
    default void onSuccess(int remainingEnergy, IEnergyUser.TransferDirection direction) {

    }
    default int requiredEnergy() {
        return getEnergyUser().getAbsorbRate() == -1 ? 1 : getEnergyUser().getAbsorbRate();
    }
    default int choosePresentedRate(IEnergyUserProvider absorber, int requiredRemaining, int requiredForThisConnection, int transferRate) {
        return getEnergyUser().chooseConnectionRate(absorber, requiredRemaining, requiredForThisConnection, transferRate);
    }
    default void saveEnergy(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        getEnergyUser().save(tag, provider);
        nbt.put("energy_user", tag);
    }

    default void loadEnergy(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        getEnergyUser().load(nbt.getCompound("energy_user"), provider);
    }
    enum TransferMode {
        NONE(false, false),
        INPUT_ONLY(true, false),
        OUTPUT_ONLY(false, true),
        BOTH(true, false);

        private final boolean input;
        private final boolean output;

        TransferMode(boolean input, boolean output) {
            this.input = input;
            this.output = output;
        }
        public boolean providesInput() {
            return input;
        }
        public boolean providesOutput() {
            return output;
        }
    }
}
