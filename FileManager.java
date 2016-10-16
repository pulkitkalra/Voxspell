package voxspell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FileManager {

	private File _statsFile;
	private File _failedFile;
	//private List

	/**
	 * name is the Name of the file, e.g. myFile.txt
	 * @param name
	 */

	public FileManager(String name){
		_statsFile = new File(name + "-stats");
		try {
			_statsFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		} // if file already exists will do nothing 
		_failedFile = new File(name + "-failed");
		try {
			_failedFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		} // if file already exists will do nothing 
	}
	/**
	 * method is called everytime program opens up, but only executes if the correct stats files do not
	 * already exist.
	 * @param hm
	 * the hashmap with list of words by level
	 * @throws IOException
	 * if the file does not exists.
	 */
	public void initialiseFiles(HashMap<Integer,ArrayList<String>> hm) throws IOException {
		if (_statsFile.length()==0){
			FileWriter fileOut = new FileWriter(_statsFile);
			int size = hm.size();
			for (int i=1; i<size+1; i++){
				ArrayList<String> _levelList = new ArrayList<String>(hm.get(i));
				fileOut.append("%Level "+i+"\n");
				for (String s: _levelList){
					fileOut.append(s+","+"0"+","+"0"+","+"0"+"\n");
				}
			}
			fileOut.close();
		} else {
			//System.out.println("File already exists");
		}
		if (_failedFile.length()==0){
			FileWriter fileOut = new FileWriter(_failedFile);
			int size = hm.size();
			for (int i=1; i<size+1; i++){
				fileOut.append("%Level "+i+"\n");
			}
			fileOut.close();
		} else {
			//System.out.println("File already exists");
		}

	}

	public HashMap<Integer, HashMap<String, Integer[]>> getMap_newQuiz(){
		// need to return: HashMap<Integer, HashMap<String, Integer[]>> 
		HashMap<Integer, HashMap<String, Integer[]>> finalMap =
				new HashMap<Integer, HashMap<String, Integer[]>>();
		try {
			// change to add relative path.
			BufferedReader br = new BufferedReader(new FileReader(_statsFile));			 
			String line = br.readLine();
			line = br.readLine();
			String[] parts = new String[4];
			//parts = line.split(",");
			HashMap<String, Integer[]> map = new HashMap<String, Integer[]>();
			//ArrayList<String> list = new ArrayList<String>();
			int levelNum = 1;

			while ((line != null)){
				parts = line.split(",");
				if (line.startsWith("%")){
					finalMap.put(levelNum, map);
					//reviewMap.put(levelNum, new ArrayList<String>());
					levelNum++;
					map = new HashMap<String, Integer[]>();
				} else {
					map.put(parts[0], getArray(parts[1],parts[2],parts[3]));
				}
				line = br.readLine();
				
			}
			FilePathInstance.setLevels(levelNum);
			finalMap.put(levelNum, map);
			//reviewMap.put(levelNum, new ArrayList<String>());
			br.close();

		} catch (IOException e){
			JOptionPane.showMessageDialog(null, "                   Word List file not found.\n"
					+ "Please ensure file is included in the current directory.", 
					"Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} 
		return finalMap;
	}
	
	public Integer[] getArray(String s1, String s2, String s3){
		Integer[] arr = new Integer[3];
		arr[0] = Integer.parseInt(s1);
		arr[1] = Integer.parseInt(s2);
		arr[2] = Integer.parseInt(s3);
		return arr;
		
	}
	
	public HashMap<Integer,ArrayList<String>> getMap_review(){
		HashMap<Integer,ArrayList<String>> finalMap = new HashMap<Integer,ArrayList<String>>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(_failedFile));			 
			String line;
			line = br.readLine();
			int levelNum = 1;
			line = br.readLine();
			ArrayList<String> list = new ArrayList<String>();
			while ((line != null)){
				if (line.startsWith("%")){
					finalMap.put(levelNum, list);
					levelNum++;
					list = new ArrayList<String>();
				} else {
					list.add(line.trim()); // trim to remove source of error.
				}
				line = br.readLine();
			}
			finalMap.put(levelNum, list);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return finalMap;
	}
	
	/**
	 * Saves the updated level word hash map to file and failed: AT THE END OF A QUIZ ONLY.
	 * @return
	 * 	Does not return anything. But updates the stats and failed words file.
	 * @param getMap_newQuiz
	 * 
	 */
	public void saveToFile(HashMap<Integer, HashMap<String, Integer[]>> map,
			HashMap<Integer,ArrayList<String>> reviewMap){
		// Method may need to deal with failed list and failed file
		FileWriter fileOut;
		try {
			fileOut = new FileWriter(_statsFile);
			fileOut.write("");
			int size = map.size()+1;
			for (int i = 1; i<size;i++){
				fileOut.append("%Level "+i+"\n");
				HashMap<String,Integer[]> wordRecords = map.get(i);
				
				for (Entry<String, Integer[]> entry : wordRecords.entrySet()) { 
					Integer[] arr = entry.getValue();
					fileOut.append(entry.getKey()+","+arr[0]+","+arr[1]+","+arr[2]+"\n"); 
				}
				
				
			}
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// **** writing to the failed list file ****
		FileWriter fileOut_r;
		try {
			fileOut_r = new FileWriter(_failedFile);
			fileOut_r.write("");
			int size = reviewMap.size()+1;
			for (int i = 1; i<size;i++){
				fileOut_r.append("%Level "+i+"\n");
				ArrayList<String> wordRecords = reviewMap.get(i);
				for (String s: wordRecords) { 
					fileOut_r.append(s+"\n"); 
				}
			}
			fileOut_r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clearStats(){

		if (_statsFile.exists()){
			_statsFile.delete();
		}
		if (_failedFile.exists()){
			_failedFile.delete();
		}
		
	}
}
