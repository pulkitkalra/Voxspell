package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import voxspell.QuizFrame;
import voxspell.WordList;

/**
 * Swingworker helper class designed to interact between the GUI and the testing logic.
 * @author dariusau
 *
 */
public class BackgroundProcess extends SwingWorker<Result,Integer>{
	
	private List<Observer> _observers = new ArrayList<Observer>();
	
	private TestFactory _test;
	private String _attempt;
	private WordList currentList;

	/**
	 * Constructor to setup variables used for processing testing logic
	 * @param attempt
	 * @param test
	 * @param gui
	 */
	public BackgroundProcess(String attempt, TestFactory test, QuizFrame gui, WordList list){
		_attempt = attempt;
		_test = test;
		attach(gui);
		currentList = list;
	}
	
	/**
	 * Observer pattern: adding observers
	 * @param obs
	 */
	public void attach(Observer obs){
		_observers.add(obs);
	}
	
	/**
	 * Observer pattern: removing observers (if needed)
	 * @param obs
	 */
	public void detach(Observer obs){
		_observers.remove(obs);
	}
	
	/**
	 * Observer pattern: notifying observers after a word has been attempted, and the 
	 * result has been determined.
	 * @param result
	 */
	public void notify(Result result){
		for (Observer obs : _observers){
			obs.update(result);
		}
	}
	
	@Override
	/**
	 * Swingworker: testing logic done in background thread
	 */
	protected Result doInBackground() throws Exception {
		return _test.logic(_attempt,currentList);
		
	}
	
	@Override
	/**
	 * Swingworker: notifying observers of the word's result
	 */
	protected void done(){
		try {
			notify(get());
		} catch (InterruptedException e) {
			e.printStackTrace();
 		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Getter for the user's attempt
	 * @param attempt
	 */
	public void giveAttempt(String attempt){
		_attempt = attempt;
	}
}