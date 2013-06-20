package nl.tue.fingerpaint.client.gui.celllists;

import java.util.List;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.resources.FingerpaintCellListResources;
import nl.tue.fingerpaint.client.storage.StorageManager;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Timer;
import com.google.gwt.view.client.Range;
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
		super(new TextCell(), (CellList.Resources) GWT.create(FingerpaintCellListResources.class));	
		this.as = appState;
		setSelectionHandler();
		setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		setSelectionModel(selectionModel);
		ensureDebugId("loadProtocolCellList");
	}

	/**
	 * Fills this CellList with the items in {@code geometryProtocols}.
	 * 
	 * @param geometryProtocols
	 *            List of all mixing protocols currently stored in the local
	 *            storage.
	 */
	public void fillCellList(List<String> geometryProtocols) {
		setVisibleRangeAndClearData(new Range(0, geometryProtocols.size()), true);
		// Push the data into the widget.
		setRowData(0, geometryProtocols);

		// Alternate between white and light-gray background colors
		for (int i = 0; i < geometryProtocols.size(); i += 2) {
			this.getRowElement(i).addClassName("cellListStyleGray");
		}
		for (int i = 1; i < geometryProtocols.size(); i += 2) {
			this.getRowElement(i).addClassName("cellListStyleWhite");
		}
	}

	/**
	 * Sets the selection change event handler for this selectionmodel.
	 */
	private void setSelectionHandler() {
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						final String selected = selectionModel.getSelectedObject();
						
						if (selected != null) {
							// close popup to show we are busy loading
							GuiState.loadPanel.setIsLoading();
							
							// Now wait a bit until this has happened, before
							// loading a distribution from the storage, as that
							// is very CPU intensive
							Timer runLater = new Timer() {
								@Override
								public void run() {
									as.setProtocol(StorageManager.INSTANCE
											.getProtocol(as.getGeometryChoice(),
													selected));
									GuiState.labelProtocolRepresentation
											.getElement().setInnerHTML(
													as.getProtocol().toString());
									GuiState.mixNowButton.setEnabled(true);
									
									GuiState.saveResultsButton.setEnabled(false);
									GuiState.viewSingleGraphButton.setEnabled(false);
		
									selectionModel.setSelected(selected, false);
									GuiState.loadPanel.hide();
		
									GuiState.labelProtocolRepresentation
											.setVisible(true);
									GuiState.saveProtocolButton.setEnabled(true);
								}
							};
							runLater.schedule(100);
						}
					}
				});
	}

}