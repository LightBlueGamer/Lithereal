package org.litecraft.lithereal.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.litecraft.lithereal.Lithereal;

public class InfusementChamberControllerScreen extends AbstractContainerScreen<InfusementChamberControllerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Lithereal.MOD_ID,"textures/gui/infusement_chamber_controller_gui.png");
    public InfusementChamberControllerScreen(InfusementChamberControllerMenu menu, Inventory inventory, Component component) {
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

        guiGraphics.blit(new ResourceLocation("lithereal:infusement_chamber_controller_screen"), x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics.pose(), x, y, guiGraphics);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        if (menu.isCrafting()) {
            int arrowHeight = 14;
            int progressHeight = menu.getScaledProgress();
            int yOffset = y + 37 + arrowHeight - progressHeight;
            guiGraphics.blit(new ResourceLocation("lithereal:infusement_chamber_controller_screen"), x + 80, yOffset, 176, arrowHeight - progressHeight, 14, progressHeight);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
