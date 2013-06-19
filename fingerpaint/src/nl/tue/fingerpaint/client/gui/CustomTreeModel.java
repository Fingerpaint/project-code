package nl.tue.fingerpaint.client.gui;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.tue.fingerpaint.client.Fingerpaint;
import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.client.model.RectangleGeometry;
import nl.tue.fingerpaint.client.serverdata.ServerDataCache;
import nl.tue.fingerpaint.shared.GeometryNames;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

/**
 * The model that defines the nodes in the {@link CellBrowser} that is used as a
 * main menu.
 */
public class CustomTreeModel implements TreeViewModel {

	/**
	 * Number of levels in the tree. Is used to determine when the browser
	 * should be closed.
	 */
	private final static int NUM_LEVELS = 2;

	/** Reference to the "parent" class. Used for executing mixing runs. */
	private Fingerpaint fp;
	/**
	 * Reference to the state of the application, to update stuff there when a
	 * menu item is selected.
	 */
	private ApplicationState as;

	/** A selection model that is shared along all levels. */
	private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();

	/** Updater on the highest level. */
	private final ValueUpdater<String> valueGeometryUpdater = new ValueUpdater<String>() {
		@Override
		public void update(String value) {
			as.setGeometryChoice(value);
			lastClickedLevel = 0;
		}
	};

	/** Updater on level 1. */
	private final ValueUpdater<String> valueMixerUpdater = new ValueUpdater<String>() {
		@Override
		public void update(String value) {
			as.setMixerChoice(value);
			lastClickedLevel = 1;
		}
	};

	/** Indicate which level was clicked the last. */
	private int lastClickedLevel = -1;

	/**
	 * Creates the chosen geometry.
	 */
	private void createGeometry() {
		if (as.getGeometryChoice().equals(GeometryNames.RECT)) {
			as.setGeometry(new RectangleGeometry(Window.getClientHeight(),
					Window.getClientWidth(), 240, 400));
		} else if (as.getGeometryChoice().equals(GeometryNames.SQR)) {
			int size = Math.min(Window.getClientHeight(),
					Window.getClientWidth());
			as.setGeometry(new RectangleGeometry(size - 20, size - 20, 240, 240));
			Logger.getLogger("").log(
					Level.INFO,
					"Length of distribution array: "
							+ as.getGeometry().getDistribution().length);
		} else { // No valid mixer was selected
			Logger.getLogger("")
					.log(Level.WARNING, "Invalid geometry selected");
		}
	}

	/**
	 * Construct a specific {@link TreeViewModel} that can be used in the
	 * {@link CellBrowser} of the Fingerpaint application.
	 * 
	 * @param parent
	 *            A reference to the Fingerpaint class. Used to execute mixing
	 *            protocols.
	 * @param appState
	 *            Reference to the model that holds the state of the
	 *            application.
	 */
	public CustomTreeModel(Fingerpaint parent, ApplicationState appState) {
		this.fp = parent;
		this.as = appState;

		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						String selected = selectionModel.getSelectedObject();

						if (selected != null) {
							if (lastClickedLevel == NUM_LEVELS - 1) {
								as.setMixerChoice(selected);

								// "closes" Cellbrowser widget (clears whole
								// rootpanel)
								RootPanel.get().clear();

								createGeometry();
								fp.createMixingWidgets();
							} else if (lastClickedLevel == NUM_LEVELS - 2) {
								as.setGeometryChoice(selected);
							}
						}
					}
				});
	}

	/**
	 * Get the {@link com.google.gwt.view.client.TreeViewModel.NodeInfo} that
	 * provides the children of the specified value.
	 */
	public <T> NodeInfo<?> getNodeInfo(T value) {
		// When the Tree is being initialised, the last clicked level will
		// be -1,
		// in other cases, we need to load the level after the currently
		// clicked one.
		if (lastClickedLevel < 0) {
			// LEVEL 0. - Geometry
			// We passed null as the root value. Return the Geometries.

			// Create a data provider that contains the list of Geometries.
			ListDataProvider<String> dataProvider = new ListDataProvider<String>(
					Arrays.asList(ServerDataCache.getGeometries()));

			// Return a node info that pairs the data provider and the cell.
			return new DefaultNodeInfo<String>(dataProvider,
					new ClickableTextCell(), selectionModel,
					valueGeometryUpdater);
		} else if (lastClickedLevel == 0) {
			// LEVEL 1 - Mixer (leaf)

			// We want the children of the Geometry. Return the mixers.
			ListDataProvider<String> dataProvider = new ListDataProvider<String>(
					Arrays.asList(ServerDataCache
							.getMixersForGeometry((String) value)));

			// Use the shared selection model.
			return new DefaultNodeInfo<String>(dataProvider,
					new ClickableTextCell(), selectionModel, valueMixerUpdater);

		}
		return null;
	}

	/**
	 * Check if the specified value represents a leaf node. Leaf nodes cannot be
	 * opened.
	 */
	// You can define your own definition of leaf-node here.
	public boolean isLeaf(Object value) {
		return lastClickedLevel == NUM_LEVELS - 1;
	}
}
