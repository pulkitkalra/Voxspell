package voxspell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * Review Quiz class designed to control the processing of review tests and 
 * updating stats + review wordlist
 * @author dariusau
 *
 */
public class ReviewQuiz implements TestFactory {
	private Process _pSpeak = null;
	private ArrayList<String> _test;
	private int _wordCount;
	private int _wordNumber = 0;
	private boolean _isSecondAttempt = false;
	private double test = 0;
	private int _level;
	private Result _result;
	private boolean _isAssist = false;
	private boolean _hasWords =  true;
	private String _voice;
	
	/**
	 * Constructor to set the level
	 * @param level
	 */
	public ReviewQuiz(int level){
		_level = level;
	}
	
	@Override
	/**
	 * Checks if there are any words in the review list for a given level
	 */
	public boolean getTestedWords(WordList wordList, int level) {
		// TODO Auto-generated method stub

		HashMap<Integer,ArrayList<String>> hm = wordList.getInstance(Test.ReviewQuiz);	
		if (hm.size() == 0){
			JOptionPane.showMessageDialog(new JFrame(), "  There are no word to review!\nYou must"
					+ " attempt a New Quiz first","Error!",JOptionPane.ERROR_MESSAGE);
			_hasWords = false;
			return false;
		}
		ArrayList<String> temp = new ArrayList<String>(hm.get(level));
		
		if (temp.isEmpty()){
			JOptionPane.showMessageDialog(new JFrame(), "No words to review in this level!");
			_hasWords = false;
			return false;
		}
		
		Collections.shuffle(temp);		
		if (temp.size()<10){
			_test = new ArrayList<String>(temp);
			_wordCount = temp.size();
		} else {
			_test = new ArrayList<String>(temp.subList(0, 10));
			_wordCount = 10;
		}
		return true;
	}

	@Override
	/**
	 * Starts the test and speaks the first word
	 */
	public void startTest(WordList currentList) {
		if (!getTestedWords(currentList,_level)){
			return;
		}
		festival(_test.get(_wordNumber));
	}

	@Override
	/**
	 * Logic to deal with processing attempted words, as well as updating stats + review wordlist
	 */
	public Result logic(String word,WordList list) {
		if (word.equalsIgnoreCase(_test.get(_wordNumber))){
			//Correct on first attempt
			if (!_isSecondAttempt){
				if (!_isAssist){
					test=+0.5;
					list.updateWordRecord(_level, _test.get(_wordNumber), Stat.Mastered);
				}
				//Correct on second attempt
			} else {
				if (!_isAssist){
					test++;
					list.updateWordRecord(_level, _test.get(_wordNumber), Stat.Faulted);
				}

				_isSecondAttempt = false;
			}
			festival("Correct");
			_result = Result.Correct;
			if (!_isAssist){
				list.updateReviewHM(_level, _test.get(_wordNumber), true);
			}
			_wordNumber++;
			if (_wordNumber<_wordCount){
				festival(_test.get(_wordNumber));
			}
			_isAssist = false;
		
		} else if (!_isSecondAttempt){
			festival("Incorrect, try once more");
			festival(_test.get(_wordNumber));
			festival(_test.get(_wordNumber));
			_result = Result.Incorrect_1;
			_isSecondAttempt = true;
		} else {
			festival("Incorrect");
			_result = Result.Incorrect_2;

			if (!_isAssist){
				list.updateWordRecord(_level, _test.get(_wordNumber), Stat.Failed);
			}
			
			if (!_isAssist){
				secondChance(_test.get(_wordNumber),list);

			} else {
				list.updateReviewHM(_level, _test.get(_wordNumber), false);
				
				_wordNumber++;
				if (_wordNumber<_wordCount){
					festival(_test.get(_wordNumber));
				}
				_isAssist = false;
				_isSecondAttempt = false;
			}
		}
		
		return _result;
	}


	@Override
	/**
	 * Getter for current word number in test
	 */
	public int getWordNumber() {
		// TODO Auto-generated method stub
		return _wordNumber;
	}


	@Override
	/**
	 * Getter for the list of review words
	 */
	public ArrayList<String> getTestList() {
		// TODO Auto-generated method stub
		return _test;
	}
	
	@Override
	/**
	 * Getter for the count of test words
	 */
	public int getWordCount() {
		// TODO Auto-generated method stub
		return _wordCount;
	}
	/**
	 * Sourced from A02
	 * Method to process logic to give the user a third chance to spell the word with 
	 * voice assistance.
	 * @param word
	 * @param list 
	 */
	public void secondChance(final String word, WordList list){
		_isSecondAttempt = true;
		String[] options = {"Yes","No"};
		//Ask the user if they wish to have a 3rd attempt with the word read out letter by letter
		int select = JOptionPane.showOptionDialog(new JFrame(),
				"Getting a review word wrong allows you to try once more, with assistance."
						+ "\nThis attempt will be taken into account in the test score,"
						+ "but will not be recorded in the stats."
						+ "\nWould you like to attempt this word one more time?",
						"Spell out option",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[1]);


		if (select==0){
			_isAssist = true;
			//http://stackoverflow.com/questions/8894258/fastest-way-to-iterate-over-all-the-chars-in-a-string
			//Reading letter by letter
			SwingWorker<Void, Void> assist = new SwingWorker<Void,Void>(){

				@Override
				protected Void doInBackground() throws Exception {
					// TODO Auto-generated method stub
					for (int i = 0, n = word.length(); i < n; i++) {
						festival(String.valueOf(word.charAt(i) + "... "));
					}
					return null;
				}
				
			};
			
			assist.execute();

		} else {
			list.updateReviewHM(_level, _test.get(_wordNumber), false);
			_wordNumber++;
			if (_wordNumber<_wordCount){
				festival(_test.get(_wordNumber));
			}
			_isAssist = false;
			_isSecondAttempt = false;
		}
	}
	@Override
	/**
	 * Getter to check if the review test has words
	 */
	public boolean hasWords() {
		// TODO Auto-generated method stub
		return _hasWords;
	}

	@Override
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

	/*@Override
	public boolean getTestedWords(int level) {
		// TODO Auto-generated method stub
		return false;
	}*/


}
