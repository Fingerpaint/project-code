package nl.tue.fingerpaint.client.gui.flextables;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.client.gui.labels.NoFilesFoundLabel;
import nl.tue.fingerpaint.client.gui.panels.NotificationPopupPanel;
import nl.tue.fingerpaint.client.resources.FingerpaintConstants;
import nl.tue.fingerpaint.client.storage.StorageManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * FlexTable that displays all protocols currently stored in the local storage .
 * 
 * @author Group Fingerpaint
 */
public class ProtocolFlexTable extends FlexTable {
	
	/**
	 * Reference to this class, so that it can be accessed from within the
	 * ClickHandler.
	 */
	FlexTable thisTable;

	/**
	 * Construct a new FlexTable that can be used to display all results
	 * currently stored in the local storage. Also allows for removing these
	 * results individually.
	 */
	public ProtocolFlexTable() {
		super();
		
		thisTable = this;

		setText(0, 0, FingerpaintConstants.INSTANCE.flexFileName());
		setText(0, 1, FingerpaintConstants.INSTANCE.flexRemove());

		getRowFormatter().addStyleName(0, "removeListHeader");
		addStyleName("removeList");

		ensureDebugId("protocolFlexTable");
	}

	/**
	 * Fills this FlexTable with the items in {@code results}. Also adds a click
	 * handler for each item in the FlexTable, to remove the item.
	 * 
	 * @param protocols
	 *            List of all results currently stored in the local storage.
	 * @param geom
	 *            Currently selected geometry: used to locate the protocol
	 *            in the local storage.
	 */
	public void fillFlexTable(final ArrayList<String> protocols, final String geom) {
		for (int i = 0; i < protocols.size(); i++) {
			final int row = i + 1;
			final String name = protocols.get(i);
			setText(row, 0, name);
			Button removeButton = new Button("x");
			removeButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					int removedIndex = protocols.indexOf(name);
					protocols.remove(removedIndex);
					StorageManager.INSTANCE.removeProtocol(name, geom);
					removeRow(removedIndex + 1);
					new NotificationPopupPanel(FingerpaintConstants.INSTANCE
							.deleteSuccess())
							.show(GuiState.DEFAULT_TIMEOUT);
					
					if (protocols.isEmpty()) {
						GuiState.removeResultsVerticalPanel.remove(thisTable);
						GuiState.removeResultsVerticalPanel.insert(new NoFilesFoundLabel(), 0);
					}
				}
			});
			setWidget(row, 1, removeButton);
		}
	}
}