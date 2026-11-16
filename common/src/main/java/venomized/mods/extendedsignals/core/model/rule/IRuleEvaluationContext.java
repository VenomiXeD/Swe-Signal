package venomized.mods.extendedsignals.core.model.rule;

import venomized.mods.extendedsignals.core.api.ITrainData;

public interface IRuleEvaluationContext {
    ITrainData trainData();

    boolean direction();
}
