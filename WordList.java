package voxspell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * Primary Author: Darius Au (From A3), edited by pulkit kalra.
 * @author pulkit
 *
 */
public class WordList {
	
	private String wordFile;
	private Stats stats;
	

	private FileManager manager;
	private int levels;
	

	private boolean isSet = false;
	private HashMap<Integer,ArrayList<String>> _newWordHashMap = new HashMap<Integer,ArrayList<String>>();
	private HashMap<Integer,ArrayList<String>> _reviewWordHashMap = new HashMap<Integer,ArrayList<String>>();
	private static File file;
	
	// Stats fields
	public HashMap<Integer,Integer[]> _levelRecords 
	= new HashMap<Integer,Integer[]>();
	
	public HashMap<Integer,HashMap<String,Integer[]>> _levelWordsRecords 
	= new HashMap<Integer,HashMap<String,Integer[]>>();
	
	
	public WordList(String type){
		MainMenu.currentFile = type;
		FilePathInstance.setType(FilePathInstance.getFileInstance(type));
		this.wordFile = FilePathInstance.getFileInstance(MainMenu.currentFile);
		this.manager = new FileManager(wordFile);
		getInstance(Test.NewQuiz);
		this.levels = FilePathInstance.getLevels() + 1;
		intialiseStats();
		
	}

	/**
	 * Method to get either normal or review word lists for all levels
	 * @param type
	 * @return
	 */
	public HashMap<Integer,ArrayList<String>> getInstance(Test type){	
		switch(type){
		case NewQuiz: 
			if (!isSet){
				updateNew();
				isSet = true;
			}
			return _newWordHashMap;
		case ReviewQuiz:
			return _reviewWordHashMap;
		default:
			return null;
		}

	}

	/**
	 * Constructor to call updateNew() method
	 */
	/*private getList(){
		updateNew();	
	}*/

	/**
	 * Method contents sourced from WordList class in Assignment 2
	 * @author dariusau
	 */
	private void updateNew(){
		// edit to add relative path from main.
		file = new File(wordFile); 
		//Sourced from http://stackoverflow.com/questions/16027229/reading-from-a-text-file-and-storing-in-a-string		
		//Scan every line in the wordlist file, and store it in an arraylist
		try {
			// change to add relative path.
			BufferedReader br = new BufferedReader(new FileReader(file));			 
			String line = br.readLine();
			line = br.readLine();

			ArrayList<String> list = new ArrayList<String>();
			int levelNum = 1;

			while ((line != null)){
				if (line.startsWith("%")){
					_newWordHashMap.put(levelNum, list);
					_reviewWordHashMap.put(levelNum, new ArrayList<String>());
					levelNum++;
					list = new ArrayList<String>();
				} else {
					list.add(line); 
				}
				line = br.readLine();
			}
			FilePathInstance.setLevels(levelNum);
			_newWordHashMap.put(levelNum, list);
			_reviewWordHashMap.put(levelNum, new ArrayList<String>());
			br.close();

		} catch (IOException e){
			JOptionPane.showMessageDialog(null, "                    Word List file not found.\n"
					+ "Please ensure file is included in the current directory.", 
					"Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} 
	}

	/**
	 * Method to update review word list for a given level. 
	 * A word is either added or removed from the list.
	 * @param level
	 * @param word
	 * @param isCorrect
	 */
	public void updateReviewHM(int level, String word, boolean isCorrect){
		
		//Word is wrong
		if (!isCorrect){
			ArrayList<String> _failedList = _reviewWordHashMap.get(level);
			if (!_failedList.contains(word)){
				_failedList.add(word);
				System.out.println("Word added: "+word);
			}

			//Word is right
		} else {
			ArrayList<String> _failedList = _reviewWordHashMap.get(level);
			if (_failedList.contains(word)){
				_failedList.remove(_failedList.indexOf(word));
				System.out.println("Removed added: "+word);
			}
		}
	}
	
	public int getLevels() {
		return levels;
	}
	
	public Stats getStats1() {
		return stats;
	}
	
	//******************************************************************** Imported From stats class.
	
	private static String[][] listOfStats = new String[11][2];
	private JTable _testTable;
	/*
	public Stats( ){
		int lev = FilePathInstance.getLevels();
		refresh(lev);
		//_testTable = testTable;
	}*/
	
	/*
	public Stats(JTable testTable,int level){
		_testTable = testTable;
		_level = level;
	}
	*/
	public void intialiseStats(){
		//refresh(this.levels);
		try {
			manager.initialiseFiles(_newWordHashMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_levelWordsRecords = manager.getMap_newQuiz();
		_reviewWordHashMap = manager.getMap_review();
	}
	
	public String[][] getStats(){		
		 return listOfStats;
	}
	
	public HashMap<Integer,HashMap<String,Integer[]>> getWordStats(){
		return _levelWordsRecords;
	}

	public void updateLevelRecord(int level, Result result){
		switch (result){
		case Correct:
			_levelRecords.get(level)[1]++;
			_levelRecords.get(level)[0]++;
			break;
		case Incorrect_1:
			_levelRecords.get(level)[1]++;
			break;
		case Incorrect_2:
			_levelRecords.get(level)[1]++;
			break;
		}
		
		String value = String.valueOf(getLevelRecord(level));
		_testTable.setValueAt(value + "%", level-1, 1);
	} 

	public double getLevelRecord(int level){
		Integer[] values = _levelRecords.get(level);
		if (values[1]==0){
			return 0;
		}
		return 100*values[0]/values[1];
	}
	
	
	public void updateWordRecord(int level, String word, Stat result){
		//HashMap<String,Integer[]> test = _levelWordsRecords.get(level);
		//Integer[] test2 = test.get(word);
		
		switch(result){
		case Mastered: 
			_levelWordsRecords.get(level).get(word)[0]++;
			break;
		case Faulted:
			_levelWordsRecords.get(level).get(word)[1]++;
			break;
		case Failed:
			_levelWordsRecords.get(level).get(word)[2]++;
			break;
		}
	}
	
	public void clearLevelStats(){
		Object[] options = {"Yes","No"};
		//Ask the user if they wish to have a 3rd attempt with the word read out letter by letter
		int select = JOptionPane.showOptionDialog(new JFrame(),
				"<html>Are you sure you want to Clear All Stats for this list?<br>"
						+ "<html>",
						"Clear Stats",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[1]);


		if (select==0){
			this.manager.clearStats();
			//this.updateNew();
		} else {
			return;
		}
	}
	
	public void saveWordProgress(){
		manager.saveToFile(_levelWordsRecords,_reviewWordHashMap);
	}
	
	public List<Integer> getRedundantLevels(){
		saveWordProgress();
		//_reviewWordHashMap = manager.getMap_review();
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < this.levels;i++){
			if (_reviewWordHashMap.get(i).isEmpty()){
				list.add(i);
			}
		}
		return list;
	}
	/*private void refresh(int noOfLevels){
		// this method is no longer going to be called like this.
		Integer[] zeros = {0,0};
		Integer[] recordZeros = {0,0,0};
		//HashMap<Integer,ArrayList<String>> _wordHashMap = getInstance(Test.NewQuiz);
		
		for (int i=0; i<noOfLevels-1; i++){
			_levelRecords.put(i+1,zeros.clone());
			
			ArrayList<String> _levelList = new ArrayList<String>(_newWordHashMap.get(i+1));
			HashMap<String,Integer[]> _wordRecords = new HashMap<String,Integer[]>();
			for (int j=0; j<_levelList.size(); j++){
				_wordRecords.put(_levelList.get(j), recordZeros.clone());
			}
			_levelWordsRecords.put(i+1, _wordRecords);
		}
	}*/
	
}
