package org.lithereal.util.ether;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

public interface IEnergyUserProvider {
    <B extends BlockEntity & IEnergyUserProvider> B asBlockEntity();
    IEnergyUser getEnergyUser();
    TransferMode getTransferModeForDirection(Direction direction);
    default float transferPreferenceProgress(IEnergyUser.EnergyRange energyRange) {
        return getEnergyUser().transferPreferenceProgress(energyRange);
    }
    default int transferPreference(IEnergyUser.EnergyRange energyRange) {
        return getEnergyUser().transferPreference(energyRange);
    }
    default void onSuccess(int remainingEnergy, IEnergyUser.TransferDirection direction) {

    }
    default int requiredEnergy() {
        return getEnergyUser().getAbsorbRate().min() == -1 ? 1 : getEnergyUser().getAbsorbRate().min();
    }
    default int choosePresentedRate(IEnergyUserProvider absorber, IEnergyUser.EnergyRange requiredRemainingRange, IEnergyUser.EnergyRange requiredForThisConnectionRange, IEnergyUser.EnergyRange connectionTransferRateRange) {
        return getEnergyUser().chooseConnectionRate(absorber, requiredRemainingRange, requiredForThisConnectionRange, connectionTransferRateRange);
    }
    default void saveEnergy(@NotNull ValueOutput valueOutput) {
        getEnergyUser().save(valueOutput.child("energy_user"));
    }

    default void loadEnergy(@NotNull ValueInput valueInput) {
        getEnergyUser().load(valueInput.childOrEmpty("energy_user"));
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
