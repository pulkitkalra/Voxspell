package voxspell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class Stats {

	public static HashMap<Integer,Integer[]> _levelRecords 
	= new HashMap<Integer,Integer[]>();
	
	public static HashMap<Integer,HashMap<String,Integer[]>> _levelWordsRecords 
	= new HashMap<Integer,HashMap<String,Integer[]>>();
	
	private JTable _testTable;
	
	public Stats( /*, JTable testTable*/){
		int lev = FilePathInstance.getLevels();
		refresh(lev, null);
		//_testTable = testTable;
	}
	
	/*
	public Stats(JTable testTable,int level){
		_testTable = testTable;
		_level = level;
	}
	*/
	
	
	public static HashMap<Integer,HashMap<String,Integer[]>> getWordStats(){
		return _levelWordsRecords;
	}

	
	
	/*public void updateLevelRecord(int level, Result result){
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
	} */

	/*public double getLevelRecord(int level){
		Integer[] values = _levelRecords.get(level);
		if (values[1]==0){
			return 0;
		}
		return 100*values[0]/values[1];
	}*/
	
	
	public static void updateWordRecord(int level, String word, Stat result){
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
		
		/*System.out.println(word);
		for (Integer i : test2){
			System.out.println(i);
		}*/
	}
	
	public void clearLevelStats(){
		Object[] options = {"Yes","No"};
		//Ask the user if they wish to have a 3rd attempt with the word read out letter by letter
		int select = JOptionPane.showOptionDialog(new JFrame(),
				"<html>Would you like to clear all the level statistics?<br>"
						+ "(excluding percentage statistics)<html>",
						"Clearing statistics...",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[1]);


		if (select==0){
			//refresh(FilePathInstance.getLevels());
		}
	}
	
	private void refresh(int noOfLevels, WordList list){
		Integer[] zeros = {0,0};
		Integer[] recordZeros = {0,0,0};
		HashMap<Integer,ArrayList<String>> _wordHashMap = list.getInstance(Test.NewQuiz);
		
		for (int i=0; i<noOfLevels; i++){
			_levelRecords.put(i+1,zeros.clone());
			
			ArrayList<String> _levelList = new ArrayList<String>(_wordHashMap.get(i+1));
			HashMap<String,Integer[]> _wordRecords = new HashMap<String,Integer[]>();
			for (int j=0; j<_levelList.size(); j++){
				_wordRecords.put(_levelList.get(j), recordZeros.clone());
			}
			_levelWordsRecords.put(i+1, _wordRecords);
		}
	}
}
