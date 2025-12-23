package org.lithereal.client.gui.screens.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;

public class FreezingStationScreen extends AbstractContainerScreen<FreezingStationMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID,"textures/gui/freezing_station_gui.png");
    public FreezingStationScreen(FreezingStationMenu menu, Inventory inventory, Component component) {
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
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        if(menu.isCrafting()) {
            if(menu.getCooling() == 1) guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID,"textures/gui/freezing_station_gui.png"), x + 96, y + 34, 178, 1, menu.getScaledProgress(), 15);
            else if(menu.getCooling() == 2) guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID,"textures/gui/freezing_station_gui.png"), x + 96, y + 34, 204, 1, menu.getScaledProgress(), 15);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
