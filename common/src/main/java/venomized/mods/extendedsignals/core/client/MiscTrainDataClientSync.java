package venomized.mods.extendedsignals.core.client;

import venomized.mods.extendedsignals.core.create.SignalPolicy;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MiscTrainDataClientSync {
    private static final Map<UUID, TrainData> TRAIN_SYNC_DATA = new ConcurrentHashMap<>();
    private static final TrainData DEFAULT_TRAIN_SYNC_DATA = new TrainData(0, 0, null);

    public static void updateTrainData(UUID trainID, TrainData newData) {
        TRAIN_SYNC_DATA.put(trainID, newData);
    }

    public static TrainData getTrainData(UUID trainID) {
        return TRAIN_SYNC_DATA.getOrDefault(trainID, DEFAULT_TRAIN_SYNC_DATA);
    }

    public static void flushTrainData() {
        TRAIN_SYNC_DATA.clear();
    }

    public record TrainData(double speed, double acceleration, SignalPolicy signalPolicyMode) {
    }
}
