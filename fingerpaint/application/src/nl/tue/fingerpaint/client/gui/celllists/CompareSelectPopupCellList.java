package nl.tue.fingerpaint.client.gui.celllists;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

/**
 * CellList that can be used to select multiple mixing runs from all available
 * saved mixing runs.
 * 
 * @author Group Fingerpaint
 */
public class CompareSelectPopupCellList extends CellList<String> implements
		Handler<String> {
	/** The selection model of this cell list. */
	protected final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>();

	/** The selection event manager of this cell list. */
	protected final Handler<String> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();

	/**
	 * Construct a new cell list that can be used to select multiple mixing runs
	 * from all available saved mixing runs.
	 */
	public CompareSelectPopupCellList() {
		super(new TextCell());
		addCellPreviewHandler(this);

		// Initialise the cellList to contain all the mixing runs
		setSelectionModel(selectionModel, selectionEventManager);
		ensureDebugId("compareSelectPopupCellList");
	}

	@Override
	public MultiSelectionModel<String> getSelectionModel() {
		return selectionModel;
	}

	@Override
	public void onCellPreview(CellPreviewEvent<String> event) {
		if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

			final String value = event.getValue();
			final Boolean state = !event.getDisplay().getSelectionModel()
					.isSelected(value);
			event.getDisplay().getSelectionModel().setSelected(value, state);
			event.setCanceled(true);
		}
	}

}
