package org.lithereal.util.ether;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.apache.commons.lang3.mutable.MutableInt;
import org.lithereal.Lithereal;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
            EnergyRange absorbRate = getAbsorbRate();
            EnergyRange expectedRequiredAbsorptionPerConnection = absorbRate.splitBetween(connections);
            MutableInt absorbedEnergy = new MutableInt(0);
            Arrays.stream(Direction.values())
                    .filter(direction -> pEntity.getTransferModeForDirection(direction).providesInput())
                    .map(direction -> Pair.of(direction, pEntity.getLevel().getBlockEntity(pEntity.getBlockPos().relative(direction))))
                    .filter(pair -> pair.getSecond() instanceof IEnergyUserProvider iEnergyUserProvider &&
                            iEnergyUserProvider.getTransferModeForDirection(pair.getFirst().getOpposite()).providesOutput() &&
                            iEnergyUserProvider.getEnergyUser().canProvideEnergy(pEntity))
                    .forEach(pair -> {
                        IEnergyUserProvider provider = (IEnergyUserProvider) pair.getSecond();
                        if (absorbedEnergy.intValue() < 0) return;
                        int absorbed = pEntity.choosePresentedRate(provider, absorbRate.isEmpty() ? EnergyRange.of(-1) : absorbRate.lose(absorbedEnergy.intValue()), expectedRequiredAbsorptionPerConnection, provider.getEnergyUser().getTransferRate());
                        if (absorbed > 0) {
                            provider.getEnergyUser().transferEnergy(this, absorbed);
                            provider.getEnergyUser().onSuccess(pEntity, absorbed, TransferDirection.TRANSFER);
                            absorbedEnergy.add(absorbed);
                        }
                    });
            this.onSuccess(pEntity, absorbedEnergy.intValue(), TransferDirection.ABSORB);
        }
    }

    default <B extends BlockEntity & IEnergyUserProvider> void tick(B pEntity) {
        absorbEnergy(pEntity);
    }

    default <B extends BlockEntity & IEnergyUserProvider> void onSuccess(B pEntity, int remainingEnergy, TransferDirection direction) {
        pEntity.onSuccess(remainingEnergy, direction);
    }

    float transferPreferenceProgress(EnergyRange requiredForThisConnection);

    default int transferPreference(EnergyRange requiredForThisConnection) {
        return requiredForThisConnection.lerp(transferPreferenceProgress(requiredForThisConnection));
    }

    void absorbEnergy(int transferred);

    void removeEnergy(int transferred);

    int restrictTransferred(int transferred);

    int restrictAbsorbed(int absorbed);

    EnergyRange getTransferRate();

    EnergyRange getAbsorbRate();

    default int chooseConnectionRate(IEnergyUserProvider connected, EnergyRange requiredRemainingRange, EnergyRange requiredForThisConnectionRange, EnergyRange connectionTransferRateRange) {
        float partial = connected.transferPreferenceProgress(requiredRemainingRange);
        int requiredRemaining = requiredRemainingRange.lerp(partial);
        int requiredForThisConnection = requiredForThisConnectionRange.lerp(partial);
        int connectionTransferRate = connected.transferPreference(connectionTransferRateRange);
        if (requiredRemaining < 0)
            return connectionTransferRate;
        return Math.min(requiredRemaining, Math.max(connectionTransferRate, requiredForThisConnection));
    }

    void save(ValueOutput valueOutput);

    void load(ValueInput valueInput);

    enum TransferDirection {
        ABSORB,
        TRANSFER
    }

    record EnergyRange(int min, int max) {
        private static final Map<Long, EnergyRange> ALL_RANGES = new ConcurrentHashMap<>();
        public static EnergyRange of(int value) {
            return ALL_RANGES.computeIfAbsent(keyMatching(value, value), _ -> new EnergyRange(value, value));
        }

        public static EnergyRange of(int min, int max) {
            return ALL_RANGES.computeIfAbsent(keyMatching(min, max), l -> new EnergyRange(unpackMin(l), unpackMax(l)));
        }

        private static long keyMatching(int min, int max) {
            return ((long) max << 32) | (min & 0xFFFFFFFFL);
        }

        private static int unpackMin(long packed) {
            return (int) (packed & 0x0000FFFFL);
        }

        private static int unpackMax(long packed) {
            return (int) (packed >>> 32);
        }

        public EnergyRange splitBetween(int connections) {
            if (connections <= 0) {
                Lithereal.LOGGER.warning("Invalid connection count for energy range: " + connections);
                return this;
            }
            return EnergyRange.of(this.min / connections, this.max / connections);
        }

        public boolean isEmpty() {
            return this.max <= 0;
        }

        public int lerp(float progress) {
            return Math.round(this.min + (size() * progress));
        }

        public float progressOf(int input) {
            return (float) (input - this.min) / size();
        }

        public int size() {
            return this.max - this.min;
        }

        public EnergyRange lose(int value) {
            return EnergyRange.of(Math.max(this.min - value, 0), Math.max(this.max - value, 0));
        }
    }
}
