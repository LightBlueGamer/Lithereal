package org.lithereal.util.ether;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Arrays;

public interface IEnergyUser {
    default <B extends BlockEntity & IEnergyUserProvider> int getConnectionsThatCanReceiveEnergy(B pEntity) {
        return (int) Arrays.stream(Direction.values())
                .filter(direction -> pEntity.getTransferModeForDirection(direction).providesInput())
                .map(direction -> Pair.of(direction, pEntity.getLevel().getBlockEntity(pEntity.getBlockPos().relative(direction))))
                .filter(pair -> pair.getSecond() instanceof IEnergyUserProvider iEnergyUserProvider && iEnergyUserProvider.getTransferModeForDirection(pair.getFirst().getOpposite()).providesInput() && pEntity.getEnergyUser().canProvideEnergy(pair.getSecond()))
                .count();
    }

    default <B extends BlockEntity & IEnergyUserProvider> int getConnectionsThatCanProvideEnergy(B pEntity) {
        return (int) Arrays.stream(Direction.values())
                .filter(direction -> pEntity.getTransferModeForDirection(direction).providesOutput())
                .map(direction -> Pair.of(direction, pEntity.getLevel().getBlockEntity(pEntity.getBlockPos().relative(direction))))
                .filter(pair -> pair.getSecond() instanceof IEnergyUserProvider iEnergyUserProvider && iEnergyUserProvider.getTransferModeForDirection(pair.getFirst().getOpposite()).providesOutput() && iEnergyUserProvider.getEnergyUser().canProvideEnergy(pEntity))
                .count();
    }

    boolean canProvideEnergy(BlockEntity pEntity);

    default void transferEnergy(IEnergyUser user, int toTransfer) {
        toTransfer = user.restrictAbsorbed(restrictTransferred(toTransfer));
        user.absorbEnergy(toTransfer);
        removeEnergy(toTransfer);
    }

    default <B extends BlockEntity & IEnergyUserProvider> void transferEnergy(B pEntity) {
        int validConnections = getConnectionsThatCanReceiveEnergy(pEntity);

        if (validConnections > 0) {
            Arrays.stream(Direction.values())
                    .filter(direction -> pEntity.getTransferModeForDirection(direction).providesOutput())
                    .map(direction -> Pair.of(direction, pEntity.getLevel().getBlockEntity(pEntity.getBlockPos().relative(direction))))
                    .filter(pair -> pair.getSecond() instanceof IEnergyUserProvider iEnergyUserProvider && iEnergyUserProvider.getTransferModeForDirection(pair.getFirst().getOpposite()).providesInput() && pEntity.getEnergyUser().canProvideEnergy(pair.getSecond()))
                    .forEach(pair -> {
                        IEnergyUserProvider provider = (IEnergyUserProvider) pair.getSecond();
                        this.transferEnergy(provider.getEnergyUser(), chooseAbsorbOrTransferRate(provider, getTransferRate() / validConnections, provider.getEnergyUser().getAbsorbRate()));
                    });
        }
    }

    default <B extends BlockEntity & IEnergyUserProvider> void tick(B pEntity) {
        transferEnergy(pEntity);
    }

    void absorbEnergy(int transferred);

    void removeEnergy(int transferred);

    int restrictTransferred(int transferred);

    int restrictAbsorbed(int absorbed);

    int getTransferRate();

    int getAbsorbRate();

    default int chooseAbsorbOrTransferRate(IEnergyUserProvider absorber, int transferRate, int absorbRate) {
        if (absorbRate < 0) return transferRate;
        return Math.min(transferRate, absorbRate);
    }

    void save(CompoundTag tag, HolderLookup.Provider provider);

    void load(CompoundTag energyUser, HolderLookup.Provider provider);
}
