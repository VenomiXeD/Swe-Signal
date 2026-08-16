package venomized.mods.extendedsignals.core.signalling;

import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;

@FunctionalInterface
public interface ISignalAspect {
    void applyAspect(float seconds, SignalLighting states);

    record RGB(int color) {
        public static final RGB RED = new RGB(0xFF0000);
        public static final RGB YELLOW = new RGB(0xEDB508);
        public static final RGB GREEN = new RGB(0x00FF00);
        public static final RGB BLUE = new RGB(0x0000FF);

        public static final RGB WHITE = new RGB(0xFFFFFF);
        public static final RGB BLACK = new RGB(0x000000);

        public void apply(SignalLight.LightState state) {
            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = color & 0xFF;

            state.setColor(
                    r, g, b
            );
        }

        public int argb() {
            return 0xFF000000 | color;
        }
    }
}
