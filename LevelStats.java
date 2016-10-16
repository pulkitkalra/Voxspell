package voxspell;

import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * JTable class designed to hold statistics for each word in a given level.
 * @author dariusau
 *
 */
@SuppressWarnings("serial")
public class LevelStats extends JTable{
	private static final String[] COL_NAMES = {"Word","MASTERED","FAULTED","FAILED"};
	private static HashMap<Integer,HashMap<String,Integer[]>> _levelWordRecords;
	private int _level;
	

	/**
	 * Constructor to create a new table of updated stats, and display it to the user on request.
	 * @param level
	 */
	public LevelStats(int level, WordList list){
		super(new DefaultTableModel(COL_NAMES,0){
			public boolean isCellEditable(int row, int column){
				return false;
			}

		});
		_level = level;
		//Calendar cal1 = Calendar.getInstance();

		_levelWordRecords = list.getWordStats(); // can't be null now!
		
		tableSetup(_level);
		/*setVisible(true);
		table.setSize(500, 500);
		table.setVisible(true);
		//table.setResizable(true);
		//table.setLocationRelativeTo(null);*/
	}

	/**
	 * Method used to setup the table for a given level
	 * @param level
	 */
	private void tableSetup(int level){

		HashMap<String,Integer[]> wordRecords = _levelWordRecords.get(level);
		int rowValue = 0;
		java.util.Iterator<Entry<String, Integer[]>> iterateWords = wordRecords.entrySet().iterator();
		while (iterateWords.hasNext()){
			Map.Entry<String, Integer[]> wordRecord = iterateWords.next();
			if (wordRecord.getValue()[0].equals(0) 
					&& wordRecord.getValue()[1].equals(0) 
					&& wordRecord.getValue()[2].equals(0)){
				continue;
			}

			((DefaultTableModel) this.getModel()).addRow(new Object[]{null,null,null,null});
			this.setValueAt(wordRecord.getKey(),rowValue,0);
			this.setValueAt(wordRecord.getValue()[0],rowValue,1);
			this.setValueAt(wordRecord.getValue()[1],rowValue,2);
			this.setValueAt(wordRecord.getValue()[2],rowValue,3);
			rowValue++;
		}

	}

}
