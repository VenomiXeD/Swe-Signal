package venomized.mc.mods.swsignals;

public enum SwedishSignalAspect {
	// Standard signals
	STOP("Stop", "Do not proceed", "USUUU"),
	PROCEED_40_SHORT("Proceed 40, Short Route", "Proceed at max 40 kph, next signal is closer than 450m, may display stop", "SUSUS"),
	PROCEED_40_CAUTION("Proceed 80, Caution", "Proceed at max 40 kph, next signal is further than 450m showing stop","SUSUU"),
	PROCEED_80("Proceed 80","Proceed at max 80 kph","SUUUU"),

	// Distant signals
	PROCEED_80_STOP("Proceed 80, expect Stop","Proceed at max 80 kph, next signal showing stop","SUFUU"),
	PROCEED_80_EXPECT_PROCEED_40("Proceed 80, expect Proceed 40","Proceed at max 80 kph, next signal is showing Proceed 40","SUFUF"),
	PROCEED_80_EXPECT_PROCEED_80("Proceed 80, expect Proceed 80","Proceed at max 80 kph, next signal is showing Proceed 80","SUUFU");

	private final String name;
	private final String description;
	private final String lightPattern;

	SwedishSignalAspect(String name, String description, String lightPattern) {
		this.name = name;
		this.description = description;
		this.lightPattern = lightPattern;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	public String getLightPattern() {
		return this.lightPattern;
	}
}
