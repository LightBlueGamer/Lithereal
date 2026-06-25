package org.lithereal.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;
import org.lithereal.client.gui.screens.CommonAssets;

public class ElectricCrucibleScreen extends AbstractContainerScreen<ElectricCrucibleMenu> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/electric_crucible_gui.png");
    public ElectricCrucibleScreen(ElectricCrucibleMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int pMouseX, int pMouseY, float partialTicks) {
        int xPos = this.leftPos;
        int yPos = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, xPos, yPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        extractProgressArrow(xPos, yPos, graphics);
        extractHeatSource(xPos, yPos, graphics);
        extractBucketProcessing(xPos, yPos, graphics);
    }

    private void extractProgressArrow(int x, int y, GuiGraphicsExtractor guiGraphics) {
        if (menu.isCrafting()) {
            int progressHeight = menu.getScaledProgress();
            int yOffset = y + 51 - progressHeight;
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FireCrucibleScreen.PROGRESS_BLUE_LIT, 14, 14, 0, 14 - progressHeight, x + 80, yOffset, 14, progressHeight);
        }
    }

    private void extractHeatSource(int x, int y, GuiGraphicsExtractor guiGraphics) {
        int chargeProgress = menu.getScaledProgressCharge();
        if (menu.hasEnergySourcesAttached()) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CommonAssets.CHARGING_BAR, 10, 64, 0, 64 - chargeProgress, x + 23, y + 11 + 64 - chargeProgress, 10, chargeProgress);
    }

    private void extractBucketProcessing(int x, int y, GuiGraphicsExtractor guiGraphics) {
        if (!menu.hasBucket() && menu.isCrafting()) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FireCrucibleScreen.INCLUDING_ARROW, 8, 11, 0, 0, x + 84, y + 55, 8, 11);
        else if (menu.isCrafting()) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FireCrucibleScreen.INCLUDING_ARROW_BOTH, 8, 11, 0, 0, x + 84, y + 55, 8, 11);
    }
}
