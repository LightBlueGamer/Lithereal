package org.lithereal.util.ether;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Arrays;

public interface IEnergyUser {
    default <B extends BlockEntity & IEnergyUserProvider> int getConnectionsThatCouldProvideEnergy(B pEntity) {
        return (int) Arrays.stream(Direction.values())
                .filter(direction -> pEntity.getTransferModeForDirection(direction).providesInput())
                .map(direction -> Pair.of(direction, pEntity.getLevel().getBlockEntity(pEntity.getBlockPos().relative(direction))))
                .filter(pair -> pair.getSecond() instanceof IEnergyUserProvider iEnergyUserProvider &&
                        iEnergyUserProvider.getTransferModeForDirection(pair.getFirst().getOpposite()).providesOutput() &&
                        iEnergyUserProvider.getEnergyUser().couldProvideEnergy(pEntity))
                .count();
    }

    boolean canProvideEnergy(BlockEntity pEntity);

    default boolean couldProvideEnergy(BlockEntity pEntity) {
        return canProvideEnergy(pEntity);
    }

    default void transferEnergy(IEnergyUser user, int toTransfer) {
        toTransfer = user.restrictAbsorbed(restrictTransferred(toTransfer));
        user.absorbEnergy(toTransfer);
        removeEnergy(toTransfer);
    }

    default <B extends BlockEntity & IEnergyUserProvider> void absorbEnergy(B pEntity) {
        int connections = getConnectionsThatCouldProvideEnergy(pEntity);

        if (connections > 0) {
            int absorbRate = getAbsorbRate();
            int expectedRequiredAbsorptionPerConnection = absorbRate / connections;
            MutableInt absorbedEnergy = new MutableInt(0);
            Arrays.stream(Direction.values())
                    .filter(direction -> pEntity.getTransferModeForDirection(direction).providesInput())
                    .map(direction -> Pair.of(direction, pEntity.getLevel().getBlockEntity(pEntity.getBlockPos().relative(direction))))
                    .filter(pair -> pair.getSecond() instanceof IEnergyUserProvider iEnergyUserProvider &&
                            iEnergyUserProvider.getTransferModeForDirection(pair.getFirst().getOpposite()).providesOutput() &&
                            iEnergyUserProvider.getEnergyUser().canProvideEnergy(pEntity))
                    .forEach(pair -> {
                        IEnergyUserProvider provider = (IEnergyUserProvider) pair.getSecond();
                        if (absorbedEnergy.getValue() < 0) return;
                        int absorbed = pEntity.choosePresentedRate(provider, absorbRate < 0 ? -1 : absorbRate - absorbedEnergy.getValue(), expectedRequiredAbsorptionPerConnection, provider.getEnergyUser().getTransferRate());
                        if (absorbed > 0) {
                            provider.getEnergyUser().transferEnergy(this, absorbed);
                            provider.getEnergyUser().onSuccess(pEntity, absorbed, TransferDirection.TRANSFER);
                            absorbedEnergy.add(absorbed);
                        }
                    });
            this.onSuccess(pEntity, absorbedEnergy.getValue(), TransferDirection.ABSORB);
        }
    }

    default <B extends BlockEntity & IEnergyUserProvider> void tick(B pEntity) {
        absorbEnergy(pEntity);
    }

    default <B extends BlockEntity & IEnergyUserProvider> void onSuccess(B pEntity, int remainingEnergy, TransferDirection direction) {

    }

    void absorbEnergy(int transferred);

    void removeEnergy(int transferred);

    int restrictTransferred(int transferred);

    int restrictAbsorbed(int absorbed);

    int getTransferRate();

    int getAbsorbRate();

    default int chooseConnectionRate(IEnergyUserProvider connected, int requiredRemaining, int requiredForThisConnection, int connectionTransferRate) {
        if (requiredRemaining < 0)
            return connectionTransferRate;
        return Math.min(requiredRemaining, Math.max(connectionTransferRate, requiredForThisConnection));
    }

    void save(CompoundTag tag, HolderLookup.Provider provider);

    void load(CompoundTag energyUser, HolderLookup.Provider provider);

    enum TransferDirection {
        ABSORB,
        TRANSFER
    }
}
