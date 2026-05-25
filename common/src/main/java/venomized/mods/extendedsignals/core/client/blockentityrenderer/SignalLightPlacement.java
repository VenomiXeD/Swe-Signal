package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SignalLightPlacement {
    @Getter
    final private double x;
    @Getter
    final private double y;
    @Getter
    final private double z;
    @Getter
    final private float xScale;
    @Getter
    final private float yScale;
    @Getter
    final private float zScale;
}
