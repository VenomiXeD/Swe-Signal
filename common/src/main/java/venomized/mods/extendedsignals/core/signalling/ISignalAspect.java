package venomized.mods.extendedsignals.core.signalling;

import venomized.mods.extendedsignals.core.SignalLightState;

public interface ISignalAspect {
    void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states);

    record RGB(int color) {
        public static final RGB RED = new RGB(0xFF0000);
        public static final RGB YELLOW = new RGB(0xEDB508);
        public static final RGB GREEN = new RGB(0x00FF00);
        public static final RGB BLUE = new RGB(0x0000FF);

        public static final RGB WHITE = new RGB(0xFFFFFF);
        public static final RGB BLACK = new RGB(0x000000);

        public void apply(SignalLightState state) {
            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = color & 0xFF;

            float rf = r / 255.0f;
            float gf = g / 255.0f;
            float bf = b / 255.0f;

            state.setColorDirect(
                    rf, gf, bf
            );
        }
    }
}
