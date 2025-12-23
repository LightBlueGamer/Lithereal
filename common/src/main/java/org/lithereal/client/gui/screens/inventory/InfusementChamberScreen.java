package org.lithereal.client.gui.screens.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.client.gui.screens.CommonAssets;

public class InfusementChamberScreen extends AbstractContainerScreen<InfusementChamberMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID,"textures/gui/infusement_chamber_gui.png");
    public InfusementChamberScreen(InfusementChamberMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int xPos = this.leftPos;
        int yPos = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, xPos, yPos, 0, 0, this.imageWidth, this.imageHeight);

        renderProgressArrow(guiGraphics.pose(), xPos, yPos, guiGraphics);
        renderPowerArrow(guiGraphics.pose(), xPos, yPos, guiGraphics);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 81, y + 19, 176, 3, 14, menu.getScaledProgress() + 4);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public void renderPowerArrow(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        if(menu.getPowerState() != InfusementChamberBlockEntity.PowerState.UNPOWERED) {
            ResourceLocation sprite = switch (menu.getPowerState()) {
                case UNPOWERED -> CommonAssets.FIRE_FUEL_BAR; // Impossible
                case FROZEN -> CommonAssets.FREEZING_POWER;
                case BURNING -> CommonAssets.BLUE_FIRE_FUEL_BAR;
                case CHARGED -> CommonAssets.CHARGING_BAR;
            };
            guiGraphics.blitSprite(sprite, 10, 64, 0, 0, x + 23, y + 11, 10, 64);
        }
    }
}
