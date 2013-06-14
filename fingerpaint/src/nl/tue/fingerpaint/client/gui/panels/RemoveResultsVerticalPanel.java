package nl.tue.fingerpaint.client.gui.panels;

import nl.tue.fingerpaint.client.gui.labels.NoFilesFoundLabel;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Panel used in the loading popup
 * 
 * @author Group Fingerpaint
 */
public class RemoveResultsVerticalPanel extends VerticalPanel {
	
	/**
	 * the label for when there are no files found to load
	 */
	NoFilesFoundLabel noFilesFoundLabel = new NoFilesFoundLabel();
	
	/**
	 * Adds the parameter list to this panel, adds a noFilesFoundLabel instead if the list is empty
	 * 
	 * @param list the list that should be checked to see if the noFilesFoundLabel should be added
	 * It is considered empty if only  the head of the list (consisting of the labels) is present
	 * @throws NullPointerException if list == null
	 */
	public void addList(FlexTable list){
		if(list == null){
			throw new NullPointerException();
		}
		if(list.getRowCount() == 1){ // only the labels are in the table
			add(noFilesFoundLabel);
		}else{
			remove(noFilesFoundLabel);
			add(list);
		}
	}
}
