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
import org.lithereal.world.block.entity.InfusementChamberBlockEntity;

public class InfusementChamberScreen extends AbstractContainerScreen<InfusementChamberMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Lithereal.MOD_ID,"textures/gui/infusement_chamber_gui.png");
    public InfusementChamberScreen(InfusementChamberMenu menu, Inventory inventory, Component component) {
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

        guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/infusement_chamber_gui.png"), x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics.pose(), x, y, guiGraphics);
        renderPowerArrow(guiGraphics.pose(), x, y, guiGraphics);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y, GuiGraphics guiGraphics) {
        if(menu.isCrafting()) {
            guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/infusement_chamber_gui.png"), x + 84, y + 32, 176, 3, 8, menu.getScaledProgress());
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
            guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/infusement_chamber_gui.png"), x + 98, y + 35, 176, 25, 28, 16);
            int u = switch (menu.getPowerState()) {
                case UNPOWERED -> 0; // Impossible
                case FROZEN -> 50;
                case BURNING -> 41;
                case CHARGED -> 59;
            };
            guiGraphics.blit(new ResourceLocation(Lithereal.MOD_ID,"textures/gui/infusement_chamber_gui.png"), x + 130, y + 38, 176, u, 33, 9);
        }
    }
}
