package qcy.qsky;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class QskyKeybinding {
    private static final String CATEGORY = "Qsky";
    private static KeyBinding qskyKeybinding;

    public static void register() {
        qskyKeybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.Qsky",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G, // Default key (G)
                CATEGORY
        ));
    }
    public static KeyBinding getQskyKeybinding() {
        return qskyKeybinding;
    }
}