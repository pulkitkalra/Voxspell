package Voice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
/**
 * Voice class provides festival the ability to be switched between the two voices
 * @author pulkit
 *
 */
public class Voice {
	private File speakFile;
	/**
	 * Method creates a .scm file with the correct voice and the SayText festival command.
	 * @param phrase
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public String speak(String phrase) throws FileNotFoundException, UnsupportedEncodingException{
		// voice must be a string: either: kal_diphone or akl_nz_jdt_diphone
		//
		speakFile = new File("speak.scm");

		if(speakFile.exists() && !speakFile.isDirectory()) { 
			speakFile = new File("speak.scm");
			
		}
		
		PrintWriter writer = new PrintWriter(speakFile); 
		//System.out.println(VoiceInstance.getInstance()); // DEBUG STATEMENT
		writer.println("(voice_kal_diphone)");
		writer.println("(voice_"+VoiceInstance.getTypeInstance()+")");
		writer.println("(Parameter.set 'Duration_Stretch "+VoiceInstance.getSpeedInstance()+")");
		if (phrase.startsWith("Correct")||phrase.startsWith("Incorrect")){
			// Do nothing
		} else {
			writer.println("(SayText "+ "\""+"Please Spell"+"\")");
		}
		writer.println("(SayText "+ "\""+phrase+"\")");
		
		
		writer.close();
		
		return "speak.scm";
	}
	/**
	 * Alternative method: can specify speed when calling method and does say "Please spell"
	 * @param phrase
	 * @param speed
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public String speak(String phrase, String speed) throws FileNotFoundException, UnsupportedEncodingException{
		// voice must be a string: either: kal_diphone or akl_nz_jdt_diphone
		speakFile = new File("speak.scm");
		
		if(speakFile.exists() && !speakFile.isDirectory()) { 
			speakFile = new File("speak.scm");
		}
		PrintWriter writer = new PrintWriter(speakFile); 
		//System.out.println(VoiceInstance.getInstance()); // DEBUG STATEMENT
		writer.println("(voice_kal_diphone)");
		writer.println("(voice_"+VoiceInstance.getTypeInstance()+")");
		writer.println("(Parameter.set 'Duration_Stretch "+speed+")");
		writer.println("(SayText "+ "\""+phrase+"\")");
		writer.close();
		return "speak.scm";
	}
}
