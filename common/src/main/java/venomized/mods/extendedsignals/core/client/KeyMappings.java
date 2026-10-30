package venomized.mods.extendedsignals.core.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyMappings {
    public static KeyMapping REQUEST_SHUNT = new KeyMapping(
            "key.extendedsignals.train.shunt", GLFW.GLFW_KEY_R, "key.categories.immersive_signals"
    );

    public static KeyMapping MODE_SWITCH = new KeyMapping(
            "key.extendedsignals.mode", GLFW.GLFW_KEY_LEFT_SHIFT, "key.categories.immersive_signals"
    );

    public static KeyMapping SIGNAL_MODE = new KeyMapping(
            "key.extendedsignals.signal_policy_mode", GLFW.GLFW_KEY_RIGHT_CONTROL, "key.categories.immersive_signals"
    );

    public static KeyMapping SWITCH_LEFT = new KeyMapping(
            "key.immersive_signals.switch_left", GLFW.GLFW_KEY_J, "key.categories.immersive_signals"
    );

    public static KeyMapping SWITCH_RIGHT = new KeyMapping(
            "key.immersive_signals.switch_right", GLFW.GLFW_KEY_L, "key.categories.immersive_signals"
    );

    public static KeyMapping SWITCH_ADVANCE = new KeyMapping(
            "key.immersive_signals.switch_advance", GLFW.GLFW_KEY_I, "key.categories.immersive_signals"
    );

    public static KeyMapping SWITCH_RETRACT = new KeyMapping(
            "key.immersive_signals.switch_retract", GLFW.GLFW_KEY_K, "key.categories.immersive_signals"
    );

    public static void init() {
    }
}
