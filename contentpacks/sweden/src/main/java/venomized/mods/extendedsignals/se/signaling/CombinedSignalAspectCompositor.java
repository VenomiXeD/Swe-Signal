// package venomized.mods.extendedsignals.se.signaling;
//
// import net.minecraft.core.Direction;
// import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
// import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
//
// import java.util.Arrays;
//
// public record CombinedSignalAspectCompositor(
//         MainSignalAspect main,
//         DistantSignalAspect distant
// ) implements ICombinedSignalAspect {
//
//     public static CombinedSignalAspectCompositor interpret(SignalStateNode state, Direction.AxisDirection direction) {
//         return new CombinedSignalAspectCompositor(
//                 MainSignalAspect.interpret(state, direction),
//                 DistantSignalAspect.interpret(state, direction)
//         );
//     }
//
//     /**
//      * @param totalTicksForBlockEntity
//      * @param states
//      */
//     @Override
//     public void applyAspect(long totalTicksForBlockEntity, LightState[] states) {
//         main.applyAspect(
//                 totalTicksForBlockEntity,
//                 Arrays.copyOfRange(states, 0, 3)
//         );
//         if (main == MainSignalAspect.PROCEED_40 || main == MainSignalAspect.STOP && distant != DistantSignalAspect.NONE)
//             return;
//
//         distant.applyAspect(
//                 totalTicksForBlockEntity,
//                 Arrays.copyOfRange(states, 2, 5)
//         );
//     }
// }
