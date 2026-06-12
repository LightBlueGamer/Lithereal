package org.lithereal.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;
import org.lithereal.block.entity.InfusementChamberBlockEntity;
import org.lithereal.client.gui.screens.CommonAssets;

public class InfusementChamberScreen extends AbstractContainerScreen<InfusementChamberMenu> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID,"textures/gui/infusement_chamber_gui.png");
    private static final Identifier COMBINING_ARROW =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID,"textures/gui/sprites/infusement_chamber/combining_arrow.png");
    public InfusementChamberScreen(InfusementChamberMenu menu, Inventory inventory, Component component) {
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
        extractPowerArrow(xPos, yPos, graphics);
    }

    private void extractProgressArrow(int x, int y, GuiGraphicsExtractor guiGraphics) {
        if (menu.isCrafting()) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, COMBINING_ARROW, 14, 35, 0, 0, x + 81, y + 19, 14, menu.getScaledProgress() + 4);
        }
    }

    public void extractPowerArrow(int x, int y, GuiGraphicsExtractor guiGraphics) {
        if(menu.getPowerState() != InfusementChamberBlockEntity.PowerState.UNPOWERED) {
            Identifier sprite = switch (menu.getPowerState()) {
                case UNPOWERED -> CommonAssets.FIRE_FUEL_BAR; // Impossible
                case FROZEN -> CommonAssets.FREEZING_POWER;
                case BURNING -> CommonAssets.BLUE_FIRE_FUEL_BAR;
                case CHARGED -> CommonAssets.CHARGING_BAR;
            };
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, 10, 64, 0, 0, x + 23, y + 11, 10, 64);
        }
    }
}
