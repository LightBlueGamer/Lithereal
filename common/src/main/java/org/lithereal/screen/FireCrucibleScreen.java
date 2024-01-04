package org.lithereal.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;

public class FireCrucibleScreen extends AbstractContainerScreen<FireCrucibleMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Lithereal.MOD_ID,"textures/gui/fire_crucible_gui.png");
    public FireCrucibleScreen(FireCrucibleMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/fire_crucible_gui.png"), x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics.pose(), x, y, guiGraphics);
        renderHeatSource(guiGraphics.pose(), x, y, guiGraphics);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        if (menu.isCrafting()) {
            int arrowHeight = 14;
            int progressHeight = menu.getScaledProgress();
            int yOffset = y + 37 + arrowHeight - progressHeight;
            if(menu.getHeatLevel() == 1) guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/fire_crucible_gui.png"), x + 80, yOffset, 176, arrowHeight - progressHeight, 14, progressHeight);
            else if(menu.getHeatLevel() == 2) guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/fire_crucible_gui.png"), x + 80, yOffset, 190, arrowHeight - progressHeight, 14, progressHeight);
        }
    }

    private void renderHeatSource(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        if(menu.getHeatLevel() == 2) guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/fire_crucible_gui.png"), x + 131, y + 41, 176, 39, menu.getScaledProgressFuel(), 9);
        else if(menu.getHeatLevel() == 1) guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/fire_crucible_gui.png"), x + 131, y + 41, 176, 30, menu.getScaledProgressFuel(), 9);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}