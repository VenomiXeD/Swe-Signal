package venomized.mods.extendedsignals.core.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyMappings {
    public static KeyMapping REQUEST_SHUNT = new KeyMapping(
            "key.extendedsignals.train.shunt", GLFW.GLFW_KEY_R, "key.categories.extendedsignals"
    );

    public static KeyMapping MODE_SWITCH = new KeyMapping(
            "key.extendedsignals.mode", GLFW.GLFW_KEY_LEFT_SHIFT, "key.categories.extendedsignals"
    );

    public static void init() {
    }
}
