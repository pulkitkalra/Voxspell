package voxspell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * New Quiz class designed to control the processing of new tests and updating stats + 
 * review wordlist
 * @author dariusau
 *
 */
public class NewQuiz implements TestFactory{
	private Process _pSpeak = null;
	private ArrayList<String> _test;
	private int _wordNumber = 0;
	private boolean _isSecondAttempt = false;
	private double test = 0;
	private int _level;
	private Result _result;


	/**
	 * Constructor to assign level variable for use in other parts of the class.
	 * @param level
	 */
	public NewQuiz(int level){
		_level = level;
	}
	
	@Override
	/**
	 * Gets possible words from the word list
	 * Gets 10 random words from the level specified
	 */
	public boolean getTestedWords(WordList wordList, int level) {
		// TODO Auto-generated method stub
		HashMap<Integer,ArrayList<String>> hm =  wordList.getInstance(Test.NewQuiz);	
		ArrayList<String> temp = new ArrayList<String>(hm.get(level));
		Collections.shuffle(temp);
		_test = new ArrayList<String>(temp.subList(0, 10));
		return true;
	}

	@Override
	/**
	 * Prepares the test
	 * Starts the test with the first word
	 */
	public void startTest(WordList currentList) {
		// TODO Auto-generated method stub
		getTestedWords(currentList,_level);
		festival(_test.get(_wordNumber));
	}

	@Override
	/**
	 *Method used to process attempted words and updating stats + review wordlist
	 */
	public Result logic(String word, WordList list) {
		
		String correctWord = _test.get(_wordNumber);
		if (word.equalsIgnoreCase(correctWord)){
			//Correct on first attempt
			if (!_isSecondAttempt){
				test=+0.5;
				list.updateWordRecord(_level, correctWord, Stat.Mastered);
			//Correct on second attempt
			} else {
				test++;
				list.updateWordRecord(_level, correctWord, Stat.Faulted);
				_isSecondAttempt = false;
			}
			festival("Correct");
			_result = Result.Correct;
			list.updateReviewHM(_level,correctWord,true);
			
			_wordNumber++;
			if (_wordNumber<10){
				festival(_test.get(_wordNumber));
			}
		
		} else if (!_isSecondAttempt){
			festival("Incorrect, try once more");
			festival(correctWord);
			festival(correctWord);
			_result = Result.Incorrect_1;
			_isSecondAttempt = true;
		} else {
			festival("Incorrect");
			_result = Result.Incorrect_2;
			list.updateReviewHM(_level,_test.get(_wordNumber),false);
			list.updateWordRecord(_level, correctWord, Stat.Failed);
			_wordNumber++;
			if (_wordNumber<10){
				festival(_test.get(_wordNumber));
			}
			_isSecondAttempt = false;
		}
		
		return _result;
	}

	@Override
	/**
	 * Method contents sourced from SpellingTest class in A02
	 * Festival call to bash environment
	 * @author dariusau
	 */
	public void festival(String phrase) {
		// TODO Auto-generated method stub
		if (_pSpeak!=null){
			try {
				_pSpeak.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}							
		try {
			Voice v = new Voice();
			String fileSpeak = v.speak(phrase);
			String cmd = "festival -b " + fileSpeak;
			ProcessBuilder speech = new ProcessBuilder("/bin/bash", "-c", cmd);
			speech.redirectError(new File("error.txt")); // debugging statement essentially.
			_pSpeak = speech.start();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	public int getWordNumber() {
		return _wordNumber;
	}

	
	public ArrayList<String> getTestList() {
		return _test;
	}
	@Override
	public int getWordCount() {
		// TODO Auto-generated method stub
		return 10;
	}
	@Override
	public boolean hasWords() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	/**
	 * This method is used to speak festival with a certain speed.
	 */
	public void festival(String phrase, String speed) {
		if (_pSpeak!=null){
			try {
				_pSpeak.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}							
		try {
			Voice v = new Voice();
			String fileSpeak = v.speak(phrase,speed);
			String cmd = "festival -b " + fileSpeak;
			ProcessBuilder speech = new ProcessBuilder("/bin/bash", "-c", cmd);
			speech.redirectError(new File("error.txt")); // debugging statement essentially.
			_pSpeak = speech.start();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean getTestedWords(int level) {
		// TODO Auto-generated method stub
		return false;
	}


	
}