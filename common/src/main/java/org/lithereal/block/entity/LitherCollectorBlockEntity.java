package org.lithereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lithereal.LitherealExpectPlatform;
import org.lithereal.screen.LitherCollectorMenu;
import org.lithereal.util.LitherEnergyContainer;

public class LitherCollectorBlockEntity extends BlockEntity implements MenuProvider {
    protected final ContainerData data;
    protected int progress = 0;
    protected int maxProgress = 200;

    private final LitherEnergyContainer ENERGY_CONTAINER = new LitherEnergyContainer(0, 5000, 20);

    public LitherCollectorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(LitherealExpectPlatform.getLitherCollectorBlockEntity(), blockPos, blockState);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> LitherCollectorBlockEntity.this.progress;
                    case 1 -> LitherCollectorBlockEntity.this.maxProgress;
                    case 2 -> LitherCollectorBlockEntity.this.ENERGY_CONTAINER.energy;
                    case 3 -> LitherCollectorBlockEntity.this.ENERGY_CONTAINER.maxEnergy;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> LitherCollectorBlockEntity.this.progress = value;
                    case 1 -> LitherCollectorBlockEntity.this.maxProgress = value;
                    case 2 -> LitherCollectorBlockEntity.this.ENERGY_CONTAINER.energy = value;
                    case 3 -> LitherCollectorBlockEntity.this.ENERGY_CONTAINER.maxEnergy = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Lither Collector");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new LitherCollectorMenu(i, inventory, this, this.data);
    }

    public LitherEnergyContainer getEnergyContainer() {
      return this.ENERGY_CONTAINER;
    };
}
