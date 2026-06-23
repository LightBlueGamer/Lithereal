package org.lithereal.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;
import org.lithereal.client.gui.screens.CommonAssets;

public class FireCrucibleScreen extends AbstractContainerScreen<FireCrucibleMenu> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/fire_crucible_gui.png");
    public static final Identifier INCLUDING_ARROW =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "fire_crucible/including_arrow");
    public static final Identifier INCLUDING_ARROW_BOTH =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "fire_crucible/including_arrow_both");
    public static final Identifier PROGRESS_NORMAL =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "fire_crucible/progress_normal");
    public static final Identifier PROGRESS_BLUE_LIT =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID, "fire_crucible/progress_blue_lit");
    public FireCrucibleScreen(FireCrucibleMenu menu, Inventory inventory, Component component) {
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
        extractHeatSource(xPos, yPos, graphics);
        extractBucketProcessing(xPos, yPos, graphics);
    }

    private void extractProgressArrow(int x, int y, GuiGraphicsExtractor guiGraphics) {
        if (menu.isCrafting()) {
            int progressHeight = menu.getScaledProgress();
            int yOffset = y + 51 - progressHeight;
            if (menu.getHeatLevel() == 1) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FireCrucibleScreen.PROGRESS_NORMAL, 14, 14, 0, 14 - progressHeight, x + 80, yOffset, 14, progressHeight);
            else if (menu.getHeatLevel() == 2) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FireCrucibleScreen.PROGRESS_BLUE_LIT, 14, 14, 0, 14 - progressHeight, x + 80, yOffset, 14, progressHeight);
        }
    }

    private void extractHeatSource(int x, int y, GuiGraphicsExtractor guiGraphics) {
        int fuelProgress = menu.getScaledProgressFuel();
        if (menu.getHeatLevel() == 2) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CommonAssets.BLUE_FIRE_FUEL_BAR, 10, 64, 0, 64 - fuelProgress, x + 23, y + 11 + 64 - fuelProgress, 10, fuelProgress);
        else if (menu.getHeatLevel() == 1) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CommonAssets.FIRE_FUEL_BAR, 10, 64, 0, 64 - fuelProgress, x + 23, y + 11 + 64 - fuelProgress, 10, fuelProgress);
    }

    private void extractBucketProcessing(int x, int y, GuiGraphicsExtractor guiGraphics) {
        if (!menu.hasBucket() && menu.isCrafting()) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FireCrucibleScreen.INCLUDING_ARROW, 8, 11, 0, 0, x + 84, y + 55, 8, 11);
        else if (menu.isCrafting()) guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FireCrucibleScreen.INCLUDING_ARROW_BOTH, 8, 11, 0, 0, x + 84, y + 55, 8, 11);
    }
}
