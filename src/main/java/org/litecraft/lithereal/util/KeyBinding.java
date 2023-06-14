package org.litecraft.lithereal.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_LITHEREAL = "key.category.lithereal.lithereal";
    public static final String KEY_SCORCH_GROUND = "key.lithereal.scorch_ground";
    public static final String KEY_FREEZE_WATER = "key.lithereal.freeze_water";

    public static final KeyMapping SCORCH_KEY = new KeyMapping(KEY_SCORCH_GROUND, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, KEY_CATEGORY_LITHEREAL);

    public static final KeyMapping FREEZE_KEY = new KeyMapping(KEY_FREEZE_WATER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, KEY_CATEGORY_LITHEREAL);
}