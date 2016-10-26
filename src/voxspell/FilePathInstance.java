package voxspell;
/**
 * Class is used to get singleton instances of the files of the two lists: the default and
 * custom lists.
 * @author pulkit
 *
 */
public class FilePathInstance {
	
	private static FilePathInstance singletonDef = new FilePathInstance();
	private static FilePathInstance singletonCust = new FilePathInstance();
	private String str;
	private int levels;

	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private FilePathInstance(){ }

	/* Static 'instance' method */
	public static String getFileInstance(String type) {
		if (type.equals("d")){ // default file
			return singletonDef.str;
		} else if (type.equals("c")) {
			return singletonCust.str;
		} else {
			return null;
		}
	}
	
	/**
	 * Gets number of levels depening on the current quiz (either custom or defualt)
	 * @return number of levels in list as an integer.
	 */
	public static int getLevels() {
		if (MainMenu.currentFile.equals("d")){
			return singletonDef.levels;
		} else {
			return singletonCust.levels;
		}
	}
	
	/* Other methods protected by singleton-ness */
	public static void setType(String str) {
		if (MainMenu.currentFile.equals("d")){ // default file
			singletonDef.str = str;
		} else {
			singletonCust.str = str;
		}
	}
	public static void setLevels(int number) {
		if (MainMenu.currentFile.equals("d")){
			singletonDef.levels = number;
		} else {
			singletonCust.levels = number;
		}
	}
}
