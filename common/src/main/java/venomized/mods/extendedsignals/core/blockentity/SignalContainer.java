package venomized.mods.extendedsignals.core.blockentity;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SignalContainer {
    private final HashMap<String, SignalLight> lights = new HashMap<>();
    private final List<String> powered = new ArrayList<>();
    private final List<String> unpowered = new ArrayList<>();

    public SignalContainer withLight(String lightNameKey, SignalLight light) {
        lights.put(lightNameKey, light);
        return this;
    }

    public SignalContainer withFadeSeconds(float seconds) {
        allLights().forEach(e -> e.getState().setFadeSeconds(seconds));
        return this;
    }

    public SignalContainer withFadeMilliSeconds(float milliSeconds) {
        withFadeSeconds(milliSeconds / 1000f);
        return this;
    }

    public SignalLight keyedLight(String lightNameKey) {
        SignalLight light = lights.get(lightNameKey);
        if (light == null) {
            ExtendedSignals.LOGGER.error("Attempted to get light with key [{}], but is not a part of the Block Entity. Something is wrong here...", lightNameKey);
            return null;
        }
        return light;
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
        powered.forEach(lightNameKey -> keyedLight(lightNameKey).getState().setLit(true));
        unpowered.forEach(lightNameKey -> keyedLight(lightNameKey).getState().setLit(false));
    }

    public Collection<SignalLight> allLights() {
        return lights.values();
    }
}
