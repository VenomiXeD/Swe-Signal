package venomized.mods.extendedsignals.core.signalling;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;

@RequiredArgsConstructor
public class SignalStateRemapper {
    @Getter
    private static Object2ObjectArrayMap<ResourceLocation, SignalStateRemapper> mappers = new Object2ObjectArrayMap<>();

    public static SignalStateRemapper NONE = register(new SignalStateRemapper(ExtendedSignalsCore.res("none")));

    @Getter
    private final ResourceLocation id;

    public static <T extends SignalStateRemapper> T register(T mapper) {
        if (mappers.containsKey(mapper.getId()))
            throw new IllegalArgumentException("A mapper already exists with this key");

        mappers.put(mapper.getId(), mapper);

        return mapper;
    }

    public static SignalStateNode transform(ResourceLocation id, SignalStateNode state) {
        if (mappers.containsKey(id))
            return mappers.get(id).remap(state);

        return state;
    }


    public SignalStateNode remap(SignalStateNode old) {
        return old;
    }
}
