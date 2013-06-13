package nl.tue.fingerpaint.client.gui.buttons;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;
import nl.tue.fingerpaint.shared.GeometryNames;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;

public class RemoveSavedProtButton extends Button implements ClickHandler {

	/**
	 * Reference to the model. Used to get the saved protocols for the current
	 * geometry.
	 */
	protected ApplicationState as;

	public RemoveSavedProtButton(ApplicationState as) {
		super(FingerpaintConstants.INSTANCE.btnRemoveSavedProtButton());
		addClickHandler(this);
		this.as = as;
		this.getElement().setId("removeSavedProtButton");
	}

	/**
	 * Creates a popup panel showing all saved protocols.
	 * 
	 * @param event
	 *            The event that has fired.
	 */
	@Override
	public void onClick(ClickEvent event) {
	//	GuiState.protocolFlexTable.removeFromParent();
		//GuiState.closeResultsButton.removeFromParent();

		// TODO: Remove either below 1 or above 2 lines
		GuiState.removeResultsVerticalPanel.clear();

		GuiState.removeResultsVerticalPanel.add(GuiState.protocolFlexTable);
		GuiState.removeResultsVerticalPanel.add(GuiState.closeResultsButton);

		final ArrayList<String> names = (ArrayList<String>) StorageManager.INSTANCE
				.getProtocols(GeometryNames.getShortName(as.getGeometryChoice()));
		GuiState.protocolFlexTable.fillFlexTable(names, GeometryNames.getShortName(as.getGeometryChoice()));

		GuiState.removeResultsPanel.center();
	}

}
