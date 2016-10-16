package voxspell;

/**
 * Singleton class is used to get the Instance of the Voice. This helps as it allows voice to
 * be visible throughout - and only one instance of it is available at all times.
 * @author pulkit
 *
 */
public class VoiceInstance {
	private static VoiceInstance singleton = new VoiceInstance();
	private String str;

	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private VoiceInstance(){ }

	/* Static 'instance' method */
	public static String getInstance() {
		return singleton.str;
	}
	/* Other methods protected by singleton-ness */
	public static void setType(String str) {
		singleton.str = str;
	}
}
