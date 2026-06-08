package venomized.mods.extendedsignals.core.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyMappings {
    public static KeyMapping REQUEST_SHUNT = new KeyMapping(
            "key.extendedsignals.train.shunt", GLFW.GLFW_KEY_R, "key.categories.extendedsignals"
    );
}
