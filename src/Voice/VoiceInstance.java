package Voice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import voxspell.MainMenu;

/**
 * Singleton class is used to get the Instance of the Voice. This helps as it allows voice to
 * be visible throughout - and only one instance of it is available at all times.
 * @author pulkit
 *
 */
public class VoiceInstance {
	private static VoiceInstance voiceType = new VoiceInstance();
	private static VoiceInstance voiceSpeed = new VoiceInstance();
	private String str;
	private String speed;
	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private VoiceInstance(){ }

	/**
	 * method used to get the type of voice in use.
	 * @return
	 * Default/ NZ voice
	 */
	public static String getTypeInstance() {
		return voiceType.str;
	}
	
	/**
	 * set the type of voice instance.
	 * @param str
	 */
	public static void setTypeInstance(String str) {
		voiceType.str = str;
		try {
			FileWriter wr = new FileWriter(new File(MainMenu._resourcesPath+"voiceType.txt"));
			if (str.equals("kal_diphone")){
				wr.append("D");
			} else {
				wr.append("NZ");
			}
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * method used to get the speed of voice in use.
	 * @return
	 * Default/ NZ voice
	 */
	public static String getSpeedInstance() {
		return voiceSpeed.speed;
	}
	
	/**
	 * method used to set the speed instance.
	 * @param speed
	 * string: 0.5, 1.0, 1.5 and 2.0
	 */
	public static void setSpeedInstance(String speed) {
		voiceSpeed.speed = speed;
		try {
			FileWriter wr = new FileWriter(new File(MainMenu._resourcesPath+"voiceSpeed.txt"));
			wr.append(speed);
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
