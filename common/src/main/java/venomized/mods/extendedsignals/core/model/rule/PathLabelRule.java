package venomized.mods.extendedsignals.core.model.rule;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import venomized.mods.extendedsignals.core.api.EdgePointSnapshot;
import venomized.mods.extendedsignals.core.create.tracks.points.PathIdentifierEdgePoint;

import java.util.regex.Pattern;

public class PathLabelRule extends Rule<PathLabelRule> {
    @Getter
    @Setter
    private Pattern pathLabelPatternMatcher;

    public PathLabelRule(RuleType<PathLabelRule> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @param context
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        if (pathLabelPatternMatcher == null) {
            return false;
        }

        for (EdgePointSnapshot<PathIdentifierEdgePoint> edgePointSnapshot : context.trainData().pathEdgePoints().ofType(PathIdentifierEdgePoint.class)) {
            if (pathLabelPatternMatcher.asMatchPredicate().test(edgePointSnapshot.point().getPathIdentifierKey()))
                return true;
        }

        return false;
    }

    /**
     * @param tag
     */
    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putString("path_label_pattern_matcher", pathLabelPatternMatcher.pattern());
    }

    /**
     * @param tag
     */
    @Override
    public void readFromNbt(CompoundTag tag) {
        setPathLabelPatternMatcher(Pattern.compile(tag.getString("path_label_pattern_matcher")));
    }
}
