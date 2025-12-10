package org.lithereal.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;

public class ElectricCrucibleScreen extends AbstractContainerScreen<ElectricCrucibleMenu> {
    private static final ResourceLocation OFF_BUTTON =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/sprites/electric_crucible/off_button.png");
    private static final ResourceLocation ON_BUTTON =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/sprites/electric_crucible/on_button.png");
    private static final ResourceLocation OFF_BUTTON_HOVER =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/sprites/electric_crucible/off_button_hover.png");
    private static final ResourceLocation ON_BUTTON_HOVER =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/sprites/electric_crucible/on_button_hover.png");
    private static final WidgetSprites BUTTON_SPRITE = new WidgetSprites(OFF_BUTTON, ON_BUTTON, OFF_BUTTON_HOVER, ON_BUTTON_HOVER);
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Lithereal.MOD_ID, "textures/gui/electric_crucible_gui.png");
    public ElectricCrucibleScreen(ElectricCrucibleMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        addRenderableWidget(new ImageButton(x + 35, y + 36, 18, 18, BUTTON_SPRITE, button -> {

        }));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(x, y, guiGraphics);
        renderHeatSource(x, y, guiGraphics);
        renderBucketProcessing(x, y, guiGraphics);
    }

    private void renderProgressArrow(int x, int y, GuiGraphics guiGraphics) {
        if (menu.isCrafting()) {
            int progressHeight = menu.getScaledProgress();
            int yOffset = y + 51 - progressHeight;
            guiGraphics.blit(TEXTURE, x + 80, yOffset, 176, 14 - progressHeight, 14, progressHeight);
        }
    }

    private void renderHeatSource(int x, int y, GuiGraphics guiGraphics) {
        if (menu.hasEnergySourcesAttached()) guiGraphics.blit(TEXTURE, x + 22, y + 25, 176, 31, 8, 36);
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
