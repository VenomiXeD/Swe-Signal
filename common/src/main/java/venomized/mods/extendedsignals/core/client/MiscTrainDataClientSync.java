package venomized.mods.extendedsignals.core.client;

import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;

import java.util.Map;
import java.util.UUID;

public class MiscTrainDataClientSync {
    private static final Map<UUID, TrainData> TRAIN_SYNC_DATA = new Object2ReferenceArrayMap<>();
    private static final TrainData DEFAULT_TRAIN_SYNC_DATA = new TrainData(0, 0);

    public static void updateTrainData(UUID trainID, TrainData newData) {
        TRAIN_SYNC_DATA.put(trainID, newData);
    }

    public static TrainData getTrainData(UUID trainID) {
        return TRAIN_SYNC_DATA.getOrDefault(trainID, DEFAULT_TRAIN_SYNC_DATA);
    }

    public record TrainData(double speed, double acceleration) {
    }
}
