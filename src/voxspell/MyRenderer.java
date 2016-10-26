package voxspell;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
/**
 * Class is as a Table Rendered for quiz end table
 * @author pulkit
 *
 */
public class MyRenderer implements TableCellRenderer {
	
	private List<Integer> correctList;

	/**
	 * Constructor initializes the list used to configure logic to use renderer.
	 * @param list
	 */
	public MyRenderer(List<Integer> list){
		correctList = list;
	}
	
	/**
	 * Method is used to generate a table depending on text in the row. If row has text "CORRECT",
	 * the rows is colored green, else red. Also contrasting backgrounds are used.
	 * @return
	 * Component: a JTextField
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		JTextField editor = new JTextField();
		editor.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
		if (value != null){
			editor.setText(value.toString());
		}
		
		if (value == null){
			editor.setBackground(Color.WHITE);
		} else {
			if (correctList.contains(row)){
				editor.setBackground(Color.green);
				editor.setForeground(Color.black);
			} else {
				editor.setBackground(Color.red);
				editor.setForeground(Color.white);
			}
			//editor.setBackground(correctList.contains(row) ? Color.green : lightRed);
		}
		return editor;
	}
}