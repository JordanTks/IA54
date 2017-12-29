package fr.utbm.ia54.Class;

public abstract class StaticVars {
	public static boolean isDistributed = false;
	public static int speed = 50; // lower == faster
	public static int problemSize = 0;
	public static int levelDebug = 0; // TODO: if you want to log [0: no log, 1: errors (important logs), 2: infos (every logs)]
	
	// Timeout for distributed version
	public static int timeout = 20;
}
