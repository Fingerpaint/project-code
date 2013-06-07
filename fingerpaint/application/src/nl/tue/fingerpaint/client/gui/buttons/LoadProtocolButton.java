package nl.tue.fingerpaint.client.gui.buttons;

import java.util.List;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;
import nl.tue.fingerpaint.shared.GeometryNames;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * Button that can be used to load a protocol from the local storage.
 * 
 * @author Group Fingerpaint
 */
public class LoadProtocolButton extends Button implements ClickHandler {

	/**
	 * Reference to the model. Used to get the currently selected geometries.
	 * Also used to actually change the protocol after one is selected.
	 */
	protected ApplicationState as;

	/**
	 * Construct a new button that can be used to ...
	 * 
	 * @param appState
	 *            Reference to the model, used to ...
	 */
	public LoadProtocolButton(ApplicationState appState) {
		super(FingerpaintConstants.INSTANCE.btnLoadProt());
		this.as = appState;

		addClickHandler(this);
		ensureDebugId("loadProtocolButton");
	}

	@Override
	public void onClick(ClickEvent event) {
		List<String> geometryProtocols = StorageManager.INSTANCE
				.getProtocols(GeometryNames.getShortName(as
						.getGeometryChoice()));

		// Create a cell to render each value.
		TextCell textCell = new TextCell();

		// Create a CellList that uses the cell.
		CellList<String> cellList = new CellList<String>(textCell);
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Add a selection model to handle user selection.
		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		cellList.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(
							SelectionChangeEvent event) {
						String selected = selectionModel
								.getSelectedObject();

						as.setProtocol(StorageManager.INSTANCE
								.getProtocol(GeometryNames
										.getShortName(as
												.getGeometryChoice()),
										selected));
						GuiState.labelProtocolRepresentation.setText(as
								.getProtocol().toString());
						GuiState.mixNowButton.setEnabled(true);

						GuiState.loadPanel.hide();
					}
				});

		// Set the total row count. This isn't strictly necessary, but
		// it affects
		// paging calculations, so its good habit to keep the row count
		// up to date.
		cellList.setRowCount(geometryProtocols.size(), true);

		// Push the data into the widget.
		cellList.setRowData(0, geometryProtocols);

		GuiState.loadVerticalPanel.add(cellList);
		GuiState.loadVerticalPanel.add(GuiState.closeLoadButton);
		GuiState.loadPanel.center();
	}

}
