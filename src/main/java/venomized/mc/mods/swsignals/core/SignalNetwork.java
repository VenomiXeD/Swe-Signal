package venomized.mc.mods.swsignals.core;

import java.util.HashMap;
import java.util.UUID;

/**
 * A class that represents the signal network for both the client and server.
 */
public class SignalNetwork {
	public static HashMap<UUID, Signal> signals = new HashMap<UUID, Signal>();
}
