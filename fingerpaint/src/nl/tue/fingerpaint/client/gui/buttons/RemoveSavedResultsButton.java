package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

/**
 * Button that can be used to remove previously saved mixing results.
 * 
 * @author Group Fingerpaint
 */
public class RemoveSavedResultsButton extends FastButton implements PressHandler {
	/**
	 * Construct a new button that can be used to remove previously saved mixing
	 * results.
	 */
	public RemoveSavedResultsButton() {
		super(FingerpaintConstants.INSTANCE.btnRemoveResults());
		addPressHandler(this);
		ensureDebugId("removeSavedResultsButton");
	}

	/**
	 * Creates a popup panel showing all saved results.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		GuiState.removeResultsVerticalPanel.clear();

		final ArrayList<String> names = (ArrayList<String>) StorageManager.INSTANCE
				.getResults();
		GuiState.resultsFlexTable.fillFlexTable(names);
		
		GuiState.removeResultsVerticalPanel.addList(GuiState.resultsFlexTable);
		GuiState.removeResultsVerticalPanel.add(GuiState.closeResultsButton);

		GuiState.removeResultsPanel.center();
	}

}
