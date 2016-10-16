package voxspell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

/**
 * This singleton class will store words from the NZCER-spelling-lists.txt file 
 * (located at same level as src file) into a data structure (hashmap).
 * The class will also deal with the review word list in adding and removing words.
 *  
 * @author dariusau
 *
 */
public class Word {

	private static File wordFile; // updated by pkal608 to add relative file path.
	private static Word _wordLists = null;
	private static HashMap<Integer,ArrayList<String>> _newWordHashMap = new HashMap<Integer,ArrayList<String>>();
	private static HashMap<Integer,ArrayList<String>> _reviewWordHashMap = new HashMap<Integer,ArrayList<String>>();


	/**
	 * Method to get either normal or review word lists for all levels
	 * @param type
	 * @return
	 */
	public static HashMap<Integer,ArrayList<String>> getInstance(Test type){	
		switch(type){
		case NewQuiz: 
			if (_wordLists==null){
				_wordLists = new Word();
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
	private Word(){
		updateNew();	
	}

	/**
	 * Method contents sourced from WordList class in Assignment 2
	 * @author dariusau
	 */
	private static void updateNew(){
		// edit to add relative path from main.
		wordFile = new File(FilePathInstance.getFileInstance(MainMenu.currentFile)); 
		//Sourced from http://stackoverflow.com/questions/16027229/reading-from-a-text-file-and-storing-in-a-string		
		//Scan every line in the wordlist file, and store it in an arraylist
		try {
			// change to add relative path.
			BufferedReader br = new BufferedReader(new FileReader(wordFile));			 
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
	public static void updateReviewHM(int level, String word, boolean isCorrect){
		
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


}