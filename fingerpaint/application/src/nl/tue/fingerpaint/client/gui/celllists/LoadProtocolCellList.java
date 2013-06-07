package nl.tue.fingerpaint.client.gui.celllists;

import java.util.List;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.storage.StorageManager;
import nl.tue.fingerpaint.shared.GeometryNames;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * CellList that can be used to load a previously saved mixing protocol.
 * 
 * @author Group Fingerpaint
 */
public class LoadProtocolCellList extends CellList<String> {
	/**
	 * Reference to the model. Used to set the protocol and retrieve the current
	 * geometry choice.
	 */
	protected ApplicationState as;

	/** The selection model of this cell list. */
	protected final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();

	/**
	 * Construct a new cell list that can be used to load a previously saved
	 * mixing protocol.
	 * 
	 * @param appState
	 *            Reference to the model, used to set the protocol and retrieve
	 *            the current geometry choice.
	 */
	public LoadProtocolCellList(ApplicationState appState) {
		super(new TextCell());
		this.as = appState;

		setSelectionHandler();
		setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		// Add a selection model to handle user selection
		setSelectionModel(selectionModel);

		// Retrieve the stored protocols for the current geometry from the local
		// storage.
		List<String> geometryProtocols = StorageManager.INSTANCE
				.getProtocols(GeometryNames.getShortName(as.getGeometryChoice()));

		/*
		 * Set the total row count. This isn't strictly necessary, but it
		 * affects paging calculations, so its good habit to keep the row count
		 * up to date.
		 */
		setRowCount(geometryProtocols.size(), true);

		// Push the data into the widget.
		setRowData(0, geometryProtocols);

		ensureDebugId("loadProtocolCellList");
	}

	/**
	 * Sets the selection change event handler for this selectionmodel.
	 */
	private void setSelectionHandler() {
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						String selected = selectionModel.getSelectedObject();

						as.setProtocol(StorageManager.INSTANCE.getProtocol(
								GeometryNames.getShortName(as
										.getGeometryChoice()), selected));
						GuiState.labelProtocolRepresentation.setText(as
								.getProtocol().toString());
						GuiState.mixNowButton.setEnabled(true);

						GuiState.loadPanel.hide();
					}
				});
	}

}
