package org.lithereal.client.gui.screens.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;
import org.lithereal.client.gui.screens.CommonAssets;

public class FireCrucibleScreen extends AbstractContainerScreen<FireCrucibleMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/fire_crucible_gui.png");
    public FireCrucibleScreen(FireCrucibleMenu menu, Inventory inventory, Component component) {
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
        renderHeatSource(guiGraphics.pose(), xPos, yPos, guiGraphics);
        renderBucketProcessing(guiGraphics.pose(), xPos, yPos, guiGraphics);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        if (menu.isCrafting()) {
            int progressHeight = menu.getScaledProgress();
            int yOffset = y + 51 - progressHeight;
            if (menu.getHeatLevel() == 1) guiGraphics.blit(TEXTURE, x + 80, yOffset, 176, 14 - progressHeight, 14, progressHeight);
            else if (menu.getHeatLevel() == 2) guiGraphics.blit(TEXTURE, x + 80, yOffset, 190, 14 - progressHeight, 14, progressHeight);
        }
    }

    private void renderHeatSource(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        int fuelProgress = menu.getScaledProgressFuel();
        if (menu.getHeatLevel() == 2) guiGraphics.blitSprite(CommonAssets.BLUE_FIRE_FUEL_BAR, 10, 64, 0, 64 - fuelProgress, x + 23, y + 11 + 64 - fuelProgress, 10, fuelProgress);
        else if (menu.getHeatLevel() == 1) guiGraphics.blitSprite(CommonAssets.FIRE_FUEL_BAR, 10, 64, 0, 64 - fuelProgress, x + 23, y + 11 + 64 - fuelProgress, 10, fuelProgress);
    }

    private void renderBucketProcessing(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        if (!menu.hasBucket() && menu.isCrafting()) guiGraphics.blit(TEXTURE, x + 84, y + 55, 176, 50, 8, 11);
        else if (menu.isCrafting()) guiGraphics.blit(TEXTURE, x + 84, y + 55, 176, 62, 8, 11);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
