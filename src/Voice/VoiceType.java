package Voice;
/**
 * Enum for VoiceType: allows representation of NZ_VOICE and DEF_VOICE
 * @author pulkit
 *
 */
public enum VoiceType {
	
	NZ_VOICE("akl_nz_jdt_diphone"), DEF_VOICE("kal_diphone");
	
	private String _str;
	private VoiceType(String str) {
		_str = str;
	}
	
	public String getVoice(){
		return _str;
	}
}
