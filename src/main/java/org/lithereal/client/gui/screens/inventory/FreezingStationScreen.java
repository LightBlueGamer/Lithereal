package org.lithereal.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;
import org.lithereal.client.gui.screens.CommonAssets;

public class FreezingStationScreen extends AbstractContainerScreen<FreezingStationMenu> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID,"textures/gui/freezing_station_gui.png");
    public FreezingStationScreen(FreezingStationMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int xPos = this.leftPos;
        int yPos = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, xPos, yPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        extractProgressArrow(xPos, yPos, graphics);
        extractChillSource(xPos, yPos, graphics);
    }

    private void extractProgressArrow(int x, int y, GuiGraphicsExtractor guiGraphics) {
        if(menu.isCrafting()) {
            if(menu.getCooling() == 1) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CommonAssets.ICE_PROGRESS_BAR, 22, 15, 0, 0, x + 96, y + 35, menu.getScaledProgress(), 15);
            else if(menu.getCooling() == 2) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CommonAssets.FROSTED_PROGRESS_BAR, 22, 15, 0, 0, x + 96, y + 35, menu.getScaledProgress(), 15);
        }
    }

    private void extractChillSource(int x, int y, GuiGraphicsExtractor guiGraphics) {
        int fuelProgress = menu.getScaledProgressChill();
        if (menu.getCooling() == 2) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CommonAssets.FREEZING_POWER, 10, 64, 0, 64 - fuelProgress, x + 23, y + 11 + 64 - fuelProgress, 10, fuelProgress);
        else if (menu.getCooling() == 1) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CommonAssets.CHILLING_POWER, 10, 64, 0, 64 - fuelProgress, x + 23, y + 11 + 64 - fuelProgress, 10, fuelProgress);
    }
}
