package nl.tue.fingerpaint.client.gui.panels;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.labels.NoFilesFoundLabel;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Panel used in the loading popup
 * 
 * @author Group Fingerpaint
 */
public class LoadVerticalPanel extends VerticalPanel {

	/**
	 * the label for when there are no files found to load
	 */
	NoFilesFoundLabel noFilesFoundLabel = new NoFilesFoundLabel();
	
	/**
	 * Adds a "no saved files found" to the loadVerticalPanel if the parameter list is empty, 
	 * removes this label if not
	 * 
	 * @param list the list that should be checked to see if the noFilesFoundLabel should be added
	 * @throws NullPointerException if list == null
	 */
	public void addList(CellList<String> list){
		if(list == null){
			throw new NullPointerException();
		}
		if(list.getVisibleItemCount() == 0){
			//remove(GuiState.listScrollPanel);
			add(noFilesFoundLabel);
		}else{
			remove(noFilesFoundLabel);
			GuiState.listScrollPanel.setWidget(list);
			add(GuiState.listScrollPanel);
		//	add(list);
		}
	}
}
