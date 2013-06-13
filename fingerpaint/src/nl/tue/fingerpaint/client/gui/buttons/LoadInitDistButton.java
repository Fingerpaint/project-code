package nl.tue.fingerpaint.client.gui.buttons;

import java.util.List;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;
import nl.tue.fingerpaint.shared.GeometryNames;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to load a previously saved initial concentration
 * distribution.
 * 
 * @author Group Fingerpaint
 */
public class LoadInitDistButton extends Button implements ClickHandler {

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
		addClickHandler(this);
		ensureDebugId("loadInitDistButton");
	}

	/**
	 * Creates a panel with the names of all locally stored distributions.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		// Get all initial distributions for current geometry
		List<String> geometryDistributions = StorageManager.INSTANCE
				.getDistributions(GeometryNames.getShortName(as
						.getGeometryChoice()));
		GuiState.loadInitDistCellList.fillCellList(geometryDistributions);

		GuiState.loadVerticalPanel.updateNoFilesFoundLabel(GuiState.loadInitDistCellList);
		GuiState.loadVerticalPanel.remove(GuiState.loadProtocolCellList);
		GuiState.loadVerticalPanel.add(GuiState.loadInitDistCellList);
		GuiState.loadVerticalPanel.add(GuiState.closeLoadButton);
		GuiState.loadPanel.center();
	}

}
