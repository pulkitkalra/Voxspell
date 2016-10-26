package voxspell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
/**
 * The FileChoose class is responsible for handling the logic required to open a File Chooser for a custom list,
 * get that file, get its name and then copy the file in the current working directory. There is also functionality 
 * check that the file format is somewhat correct : not entirely correct.
 * @author pkal608
 *
 */
public class FileChoose {

	private File selection;
	private File customFile;
	private File customList = new File(MainMenu._resourcesPath+"customStatus.txt");
	private String fileName;
	public boolean exists;
	
	/**
	 * constructor sets the exists field for the corresponding method call in the object will know whether it is 
	 * Necessary to upload a new custom list.
	 */
	public FileChoose(){
		if (customList.length()==0){
			exists = false;
		} else { 
			exists = true;
		}
	}
	/**
	 * getter for selection file
	 * @return
	 * Selected file from File Chooser
	 */
	public File getFile(){
		return selection;
	}
	/**
	 * copies user's selected files in to resourcese folder.
	 */
	public void copyToResources(){
		try {
			FileUtils.copyFile(selection, customFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * gets the name of the custom list if already uploaded else provides ability to upload a custom list and then
	 * returns the name.
	 * @return
	 * String: the name of the custom file
	 */
	public String getCustomName(){
		if (!exists){
			int reply = JOptionPane.showConfirmDialog(null, "There is no Custom list uploaded."
					+ "\nWould you like to upload a custom list now?"
					+ "\nNOTE: Please see 'Help' in settings on guidelines to uploading custom lists.",
					"Abort Quiz", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				final JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					selection = fc.getSelectedFile();
					fileName = selection.getName();
					if (!fileName.endsWith(".txt")){ // ensuring only text files are uploaded.
						JOptionPane.showMessageDialog(null, "Only text files (.txt) can be uploaded", "Incorrect File", 
								JOptionPane.ERROR_MESSAGE);
						return null;
					}
					//This is where a real application would open the file.
					customFile = new File(MainMenu._resourcesPath+selection.getName());
					copyToResources();
					try {
						FileWriter w = new FileWriter(customList);
						w.append(selection.getName());
						w.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			try {
				fileName = getFileName();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}
	/**
	 * getter for name of custom file.
	 * @return
	 * @throws FileNotFoundException
	 */
	public String getFileName() throws FileNotFoundException{
		Scanner w = new Scanner(customList);
		fileName = w.nextLine();
		w.close();
		return fileName;
	}
}
