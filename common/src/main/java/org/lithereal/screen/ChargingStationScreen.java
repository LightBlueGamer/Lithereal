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

public class ChargingStationScreen extends AbstractContainerScreen<ChargingStationMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Lithereal.MOD_ID,"textures/gui/charging_station_gui.png");
    public ChargingStationScreen(ChargingStationMenu menu, Inventory inventory, Component component) {
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

        guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/charging_station_gui.png"), x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics.pose(), x, y, guiGraphics);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        if(menu.isCrafting()) {
            if(menu.getEnergy() == 1) guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/charging_station_gui.png"), x + 96, y + 34, 178, 1, menu.getScaledProgress(), 15);
            else if(menu.getEnergy() == 2) guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/charging_station_gui.png"), x + 96, y + 34, 204, 1, menu.getScaledProgress(), 15);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
