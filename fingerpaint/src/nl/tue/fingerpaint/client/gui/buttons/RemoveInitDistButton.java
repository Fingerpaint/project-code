package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;

import java.util.ArrayList;

import com.google.gwt.user.client.Timer;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

/**
 * Button to initiate the removal of an initial distribution.
 * 
 * @author Group Fingerpaint
 */
public class RemoveInitDistButton extends FastButton implements PressHandler {

	/**
	 * Reference to the model. Used to get the saved protocols for the current
	 * geometry.
	 */
	protected ApplicationState as;
	
	/**
	 * Construct a new button that can be used to initiate the removal of
	 * an initial distribution.
	 * 
	 * @param appState
	 *            Reference to the model, used to get the currently selected
	 *            geometry, which in turn is used in the local storage.
	 */
	public RemoveInitDistButton(ApplicationState appState) {
		super(FingerpaintConstants.INSTANCE.btnRemoveInitDistButton());
		addPressHandler(this);
		this.as = appState;		
		this.getElement().setId("removeInitDistButton");
	}

	/**
	 * Creates a popup panel showing all saved initial distributions.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		GuiState.loadPanel.setIsLoading();
		Timer runLater = new Timer() {
			@Override
			public void run() {
				GuiState.removeResultsVerticalPanel.clear();
				
				final ArrayList<String> names = (ArrayList<String>) StorageManager.INSTANCE
						.getDistributions(as.getGeometryChoice());
				GuiState.initDistFlexTable.fillFlexTable(names, as.getGeometryChoice());
				
				GuiState.removeResultsVerticalPanel.addList(GuiState.initDistFlexTable);
				GuiState.removeResultsVerticalPanel.add(GuiState.closeResultsButton);
		
				GuiState.loadPanel.hide();
				GuiState.removeResultsPanel.center();
			}
		};
		runLater.schedule(100);
	}
}
