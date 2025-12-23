package org.lithereal.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;
import org.lithereal.client.gui.screens.CommonAssets;

public class ElectricCrucibleScreen extends AbstractContainerScreen<ElectricCrucibleMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/electric_crucible_gui.png");
    public ElectricCrucibleScreen(ElectricCrucibleMenu menu, Inventory inventory, Component component) {
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

        renderProgressArrow(xPos, yPos, guiGraphics);
        renderHeatSource(xPos, yPos, guiGraphics);
        renderBucketProcessing(xPos, yPos, guiGraphics);
    }

    private void renderProgressArrow(int x, int y, GuiGraphics guiGraphics) {
        if (menu.isCrafting()) {
            int progressHeight = menu.getScaledProgress();
            int yOffset = y + 51 - progressHeight;
            guiGraphics.blit(TEXTURE, x + 80, yOffset, 176, 14 - progressHeight, 14, progressHeight);
        }
    }

    private void renderHeatSource(int x, int y, GuiGraphics guiGraphics) {
        if (menu.hasEnergySourcesAttached()) guiGraphics.blitSprite(CommonAssets.CHARGING_BAR, 10, 64, 0, 0, x + 23, y + 11, 10, 64);
    }

    private void renderBucketProcessing(int x, int y, GuiGraphics guiGraphics) {
        if(!menu.hasBucket() && menu.isCrafting()) guiGraphics.blit(TEXTURE, x + 84, y + 55, 176, 68, 8, 11);
        else if (menu.isCrafting()) guiGraphics.blit(TEXTURE, x + 84, y + 55, 176, 80, 8, 11);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
