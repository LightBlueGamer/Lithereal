package org.lithereal.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.lithereal.Lithereal;

public class EtherCollectorScreen extends AbstractContainerScreen<EtherCollectorMenu> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Lithereal.MOD_ID,"textures/gui/ether_collector_gui.png");
    public EtherCollectorScreen(EtherCollectorMenu menu, Inventory inventory, Component component) {
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
    }
}