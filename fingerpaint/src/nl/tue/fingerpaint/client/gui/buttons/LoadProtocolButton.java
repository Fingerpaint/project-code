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
 * Button that can be used to load a protocol from the local storage.
 * 
 * @author Group Fingerpaint
 */
public class LoadProtocolButton extends Button implements ClickHandler {

	/**
	 * Reference to the model. Used to get the currently selected geometry.
	 */
	protected ApplicationState as;

	/**
	 * Construct a new button that can be used to load a protocol from the local
	 * storage.
	 * 
	 * @param appState
	 *            Reference to the model, used to retrieve the currently
	 *            selected geometry.
	 */
	public LoadProtocolButton(ApplicationState appState) {
		super(FingerpaintConstants.INSTANCE.btnLoadProt());
		this.as = appState;
		addClickHandler(this);
		ensureDebugId("loadProtocolButton");
	}

	/**
	 * Creates a panel with the names of all locally stored distributions.
	 * @param event The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
		List<String> geometryProtocols = StorageManager.INSTANCE
				.getProtocols(GeometryNames.getShortName(as.getGeometryChoice()));
		GuiState.loadProtocolCellList.fillCellList(geometryProtocols);

		GuiState.loadVerticalPanel.updateNoFilesFoundLabel(GuiState.loadProtocolCellList);
		GuiState.loadVerticalPanel.remove(GuiState.loadInitDistCellList);
		GuiState.loadVerticalPanel.add(GuiState.loadProtocolCellList);
		GuiState.loadVerticalPanel.add(GuiState.closeLoadButton);
		GuiState.loadPanel.center();
	}

}
