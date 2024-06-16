package org.lithereal.client;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class KeyMapping {
    public static final String KEY_CATEGORY_LITHEREAL = "key.category.lithereal.lithereal";
    public static final String KEY_SCORCH_GROUND = "key.lithereal.scorch_ground";
    public static final String KEY_FREEZE_WATER = "key.lithereal.freeze_water";

    public static final net.minecraft.client.KeyMapping SCORCH_KEY = new net.minecraft.client.KeyMapping(KEY_SCORCH_GROUND, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY_LITHEREAL);
    public static final net.minecraft.client.KeyMapping FREEZE_KEY = new net.minecraft.client.KeyMapping(KEY_FREEZE_WATER, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F, KEY_CATEGORY_LITHEREAL);

}