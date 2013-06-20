package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.PressEvent;
import io.ashton.fastpress.client.fast.PressHandler;

import java.util.List;

import com.google.gwt.user.client.Timer;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

/**
 * Button that can be used to load (and recompute) results from the local
 * storage.
 * 
 * @author Group Fingerpaint
 */
public class LoadResultsButton extends FastButton implements PressHandler {

	/**
	 * Construct a new button that can be used to load a result from the local
	 * storage.
	 */
	public LoadResultsButton() {
		super(FingerpaintConstants.INSTANCE.btnLoadResults());
		addPressHandler(this);
		ensureDebugId("loadResultsButton");
	}

	/**
	 * Creates a panel with the names of all locally stored results.
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
				GuiState.loadVerticalPanel.clear();
		
				List<String> geometryResults = StorageManager.INSTANCE.getResults();
				GuiState.LoadResultsCellList.fillCellList(geometryResults);
		
				GuiState.loadVerticalPanel.addList(GuiState.LoadResultsCellList);
				GuiState.loadVerticalPanel.add(GuiState.closeLoadButton);
				GuiState.loadPanel.show();
			}
		};
		runLater.schedule(100);
	}

}
