package venomized.mods.extendedsignals.core.blockentity;

import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LightContainer {
    private final Map<String, SignalLight> lights = new Object2ReferenceOpenHashMap<>();
    private final List<String> powered = new ArrayList<>();
    private final List<String> unpowered = new ArrayList<>();

    public LightContainer withLight(String lightNameKey, SignalLight light) {
        lights.put(lightNameKey, light);
        return this;
    }

    public LightContainer withFadeSeconds(float seconds) {
        allLights().forEach(e -> e.getState().setFadeSeconds(seconds));
        return this;
    }

    public LightContainer withFadeMilliSeconds(float milliSeconds) {
        withFadeSeconds(milliSeconds / 1000f);
        return this;
    }

    public SignalLight keyedLight(String lightNameKey) {
        return lights.get(lightNameKey);
    }

    public void powered(String lightNameKey) {
        powered.add(lightNameKey);
        unpowered.remove(lightNameKey);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderFrameBegin() {
        powered.clear();
        unpowered.clear();
        unpowered.addAll(lights.keySet());
    }

    @OnlyIn(Dist.CLIENT)
    public void renderFrameEnd() {
        powered.forEach(lightNameKey -> {
            SignalLight l = keyedLight(lightNameKey);
            if (l == null)
                return;
            l.getState().setLit(true);
        });
        unpowered.forEach(lightNameKey -> {
            SignalLight l = keyedLight(lightNameKey);
            if (l == null)
                return;
            l.getState().setLit(false);
        });
    }

    public Collection<SignalLight> allLights() {
        return lights.values();
    }
}
