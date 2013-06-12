package nl.tue.fingerpaint.client.gui.buttons;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to remove previously saved mixing results. 
 * 
 * @author Group Fingerpaint
 */
public class RemoveSavedResultsButton extends Button implements ClickHandler {	
	/**
	 * Construct a new button that can be used to remove previously saved
	 * mixing results.
	 */
	public RemoveSavedResultsButton(){
		super(FingerpaintConstants.INSTANCE.btnRemoveResults()); 
		addClickHandler(this);
		ensureDebugId("removeSavedResultsButton");
	}
	
	/**
	 * Creates a popup panel showing all saved results.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		GuiState.resultsFlexTable.removeFromParent();
		GuiState.closeResultsButton.removeFromParent();
		
		GuiState.removeResultsVerticalPanel
				.add(GuiState.resultsFlexTable);
		GuiState.removeResultsVerticalPanel
				.add(GuiState.closeResultsButton);

		final ArrayList<String> names = (ArrayList<String>) StorageManager.INSTANCE
				.getResults();
		GuiState.resultsFlexTable.fillFlexTable(names); 

		GuiState.removeResultsPanel.center();
	}

}
