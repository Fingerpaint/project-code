package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;

import java.util.List;

import com.google.gwt.user.client.Timer;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

/**
 * Button that can be used to load a previously saved initial concentration
 * distribution.
 * 
 * @author Group Fingerpaint
 */
public class LoadInitDistButton extends FastButton implements PressHandler {

	/**
	 * Reference to the model. Used to to retrieve the currently selected
	 * geometry
	 */
	protected ApplicationState as;

	/**
	 * Construct a new button that can be used to load a previously saved
	 * initial concentration distribution.
	 * 
	 * @param appState
	 *            Reference to the model, used to retrieve the currently
	 *            selected geometry.
	 */
	public LoadInitDistButton(ApplicationState appState) {
		super(FingerpaintConstants.INSTANCE.btnLoadDist());
		this.as = appState;
		addPressHandler(this);
		ensureDebugId("loadInitDistButton");
	}

	/**
	 * Creates a panel with the names of all locally stored distributions.
	 * @param event The event that has fired.
	 */
	@Override
	public void onPress(PressEvent event) {
		GuiState.loadPanel.setIsLoading();
		Timer runLater = new Timer() {
			@Override
			public void run() {
				GuiState.loadVerticalPanel.clear();
				// Get all initial distributions for current geometry
				List<String> geometryDistributions = StorageManager.INSTANCE
						.getDistributions(as
								.getGeometryChoice());
				GuiState.loadInitDistCellList.fillCellList(geometryDistributions);
		
				GuiState.loadVerticalPanel.addList(GuiState.loadInitDistCellList);
				GuiState.loadVerticalPanel.add(GuiState.closeLoadButton);
				GuiState.loadPanel.show();
			}
		};
		runLater.schedule(100);
	}

}
