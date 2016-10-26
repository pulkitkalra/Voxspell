package logic;

import java.util.ArrayList;

import voxspell.WordList;

/**
 * TestFactory abstract factory class designed so that I could code the review test 
 * to an interface rather than to an implementation
 * @author dariusau and pulkit
 *
 */
public interface TestFactory {
	//Get words of a certain level
	boolean getTestedWords(int level);
	
	//Start test
	//void startTest();
	
	//Get attempt
	Result logic(String word, WordList currentList);
	
	//Festival bash phrase
	void festival(String phrase);
	
	void festival(String phrase, String speed);
	
	// gets the word number being tested.
	int getWordNumber();

	// gets number of words that will be tested in a level
	public int getWordCount();
	
	//Gets test word list
	public ArrayList<String> getTestList();
	
	// checks if the specific level has any words to be tested.
	public boolean hasWords();

	boolean getTestedWords(WordList wordList, int level);

	boolean startTest(WordList currentList);
}