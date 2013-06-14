package nl.tue.fingerpaint.client.gui.buttons;

import java.util.List;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to load (and recompute) results from the local
 * storage.
 * 
 * @author Group Fingerpaint
 */
public class LoadResultsButton extends Button implements ClickHandler {

	/**
	 * Construct a new button that can be used to load a result from the local
	 * storage.
	 */
	public LoadResultsButton() {
		super(FingerpaintConstants.INSTANCE.btnLoadResults());
		addClickHandler(this);
		ensureDebugId("loadResultsButton");
	}

	/**
	 * Creates a panel with the names of all locally stored results.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {

		GuiState.loadVerticalPanel.clear();

		List<String> geometryResults = StorageManager.INSTANCE.getResults();
		GuiState.LoadResultsCellList.fillCellList(geometryResults);

		GuiState.loadVerticalPanel.addList(GuiState.LoadResultsCellList);
		GuiState.loadVerticalPanel.add(GuiState.closeLoadButton);
		GuiState.loadPanel.center();
	}

}
