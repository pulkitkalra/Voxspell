package voxspell;

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
		writer.println("(voice_"+VoiceInstance.getInstance()+")");
		writer.println("(SayText "+ "\""+phrase+"\")");
		writer.close();
		
		return "speak.scm";
	}
	
	public String speak(String phrase, String speed) throws FileNotFoundException, UnsupportedEncodingException{
		// voice must be a string: either: kal_diphone or akl_nz_jdt_diphone
		//
		speakFile = new File("speak.scm");
		
		if(speakFile.exists() && !speakFile.isDirectory()) { 
			speakFile = new File("speak.scm");
			
		}
		
		PrintWriter writer = new PrintWriter(speakFile); 
		//System.out.println(VoiceInstance.getInstance()); // DEBUG STATEMENT
		writer.println("(voice_kal_diphone)");
		writer.println("(voice_"+VoiceInstance.getInstance()+")");
		writer.println("(Parameter.set 'Duration_Stretch "+speed+")");
		writer.println("(SayText "+ "\""+phrase+"\")");
		writer.close();
		
		return "speak.scm";
	}
}
