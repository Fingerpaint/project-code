package nl.tue.fingerpaint.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Tessa Belder
 */
public class Fingerpaint implements EntryPoint {
	// Class to remember which Geometry and Mixer the user has selected
	static private UserChoice uc;

	// Label that displays the userChoice values
	static private Label mixingDetails = new Label();

	// Button to toggle between black and white drawing color
	private static ToggleButton toggleColor;

	// Rectangular geometry to draw on
	private static Geometry geom;

	// Horizontal panel to contain drawing canvas and menubar
	private static HorizontalPanel panel = new HorizontalPanel();

	// Vertical panel to contain all menu items
	private static VerticalPanel menuPanel = new VerticalPanel();

	// Width of the menu in which buttons are displayed
	// on the right side of the window in pixels
	private static final int menuWidth = 125;

	// Height of address-bar / tabs / menu-bar in the
	// browser in pixels. If this is not taken into account,
	// a vertical scrollbar appears.
	private static final int topBarHeight = 50;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// initialize the UC
		uc = new UserChoice();

		// Create a model for the cellbrowser.
		TreeViewModel model = new CustomTreeModel();
		/*
		 * Create the browser using the model. We specify the default value of
		 * the hidden root node as "null".
		 */
		CellBrowser tree = (new CellBrowser.Builder<Object>(model, null))
				.build();
		
		((CustomTreeModel) model).setCellBrowser(tree);

		// Add the tree to the root layout panel.
		RootLayoutPanel.get().add(tree);

		// ------------Start original part---------------------------

		/*
		 * // Initialise geometry geom = new
		 * RectangleGeometry(Window.getClientHeight() - topBarHeight,
		 * Window.getClientWidth() - menuWidth);
		 * 
		 * // Initialise toggleButton and add to menuPanel createToggleButton();
		 * menuPanel.add(toggleColor);
		 * 
		 * // TODO: Initialise other menu items and add them to menuPanel
		 * 
		 * // Add canvas and menuPanel to the panel // Make the canvas the
		 * entire width of the screen except for the // menuWidth
		 * panel.setWidth("100%"); panel.add(geom.getCanvas());
		 * panel.add(menuPanel); panel.setCellWidth(menuPanel,
		 * Integer.toString(menuWidth));
		 * 
		 * // Add panel to RootPanel RootPanel.get().add(panel);
		 */
	}

	/**
	 * The model that defines the nodes in the tree.
	 */
	private static class CustomTreeModel implements TreeViewModel {
		private final List<GeometryNames> geometries = new ArrayList<GeometryNames>();

		private final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		private CellBrowser cb;

		private void setupGeometryValues() {
			// add all instances of GeometryNames to geometries
			for (GeometryNames gm : GeometryNames.values()) {
				geometries.add(gm);
			}
		}

		private void setUserChoiceValues(String selected) {
			// TODO: This structure will change when GeometryNames and
			// MixerNames are stored on the server.
			// The switch sort of simulates that MixerNames are linked to a
			// Geometry, but currently they
			// are enum classes and are not connected.

			for (GeometryNames gn : GeometryNames.values()) {
				switch (gn) {
				case Rectangle:
					for (RectangleMixers rm : RectangleMixers.values()) {
						if ((rm.toString()).equals(selected)) {
							uc.setGeometry(gn);
							uc.setMixer(rm);
						}
					}
					break;
				case exampleGeometry1:
					for (ExampleGeometryMixers egm : ExampleGeometryMixers
							.values()) {
						if ((egm.toString()).equals(selected)) {
							uc.setGeometry(gn);
							uc.setMixer(egm);
						}
					}
					break;
				}
			}
		}

		public CustomTreeModel() {

			setupGeometryValues();

			selectionModel
					.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
						@Override
						public void onSelectionChange(SelectionChangeEvent event) {
							String selected = selectionModel
									.getSelectedObject();

							setUserChoiceValues(selected);

							if (selected != null) {
								// "closes" Cellbrowser widget 
								//TODO: Make decent close-code
								RootPanel.get().clear();
																
								
								{
//									// Loads hardcoded 240 x 400 canvas
//									canvas = Canvas.createIfSupported();
//
//									if (canvas == null) {
//										RootPanel
//												.get()
//												.add(new Label(
//														"Sorry, your browser doesn't support "
//																+ "the HTML5 Canvas element"));
//										return;
//									}
//
//									{// TODO: When integrating with the
//										// draw-Requirement use
//										// uc.getMixerChoice()
//										// and uc.getGeometryChoice() as
//										// parameters
//										// to make an appropriate canvas.
//
//										canvas.setStyleName("mainCanvas");
//										canvas.setWidth(canvasWidth + "px");
//										canvas.setCoordinateSpaceWidth(canvasWidth);
//
//										canvas.setHeight(canvasHeight + "px");
//										canvas.setCoordinateSpaceHeight(canvasHeight);
//
//										RootPanel.get().add(canvas);
//									}
								}
								{
									// Initialise geometry
									geom = new RectangleGeometry(Window
											.getClientHeight() - topBarHeight,
											Window.getClientWidth() - menuWidth);

									// Initialise toggleButton and add to
									// menuPanel
									createToggleButton();
									menuPanel.add(toggleColor);

									// TODO: Initialise other menu items and add
									// them to menuPanel

									// Add canvas and menuPanel to the panel
									// Make the canvas the entire width of the
									// screen except for the
									// menuWidth
									panel.setWidth("100%");
									panel.add(geom.getCanvas());
									panel.add(menuPanel);
									panel.setCellWidth(menuPanel,
											Integer.toString(menuWidth));

									// Add panel to RootPanel
									RootPanel.get().add(panel);
								}

								if (uc.getGeometryChoice() != null
										&& uc.getMixerChoice() != null) {
									mixingDetails.setText("Geometry: "
											+ uc.getGeometryChoice().toString()
											+ ", Mixer: "
											+ uc.getMixerChoice().toString());
								} else {// This should never happen. Just to be
										// safe
									mixingDetails
											.setText("Geometry and/or Mixer was not selected succesfully");
								}

								RootPanel.get().add(mixingDetails);
								//context = canvas.getContext2d(); //Hugo context
							}
						}
					});
		}

		/**
		 * Get the {@link NodeInfo} that provides the children of the specified
		 * value.
		 */
		public <T> NodeInfo<?> getNodeInfo(T value) {
			if (value == null) {
				// LEVEL 0. - Geometry
				// We passed null as the root value. Return the Geometries.

				// Create a data provider that contains the list of Geometries.
				ListDataProvider<GeometryNames> dataProvider = new ListDataProvider<GeometryNames>(
						geometries);

				// Create a cell to display a Geometry.
				Cell<GeometryNames> cell = new AbstractCell<GeometryNames>() {
					@Override
					public void render(Context context, GeometryNames value,
							SafeHtmlBuilder sb) {
						if (value != null) {
							sb.appendEscaped(value.toString());
						}
					}
				};
				// Return a node info that pairs the data provider and the cell.
				return new DefaultNodeInfo<GeometryNames>(dataProvider, cell);
			} else if (value instanceof GeometryNames) {
				// LEVEL 1 - Mixer (leaf)
				// TODO: In the future a switch on "value" is needed.
				// Appropriate mixers are then loaded for each geometry.
				// Currently ExampleMixerNames1 through 4 is loaded for each
				// geometry.

				// Construct a List<String> of MixerNames. This is needed for
				// the DefaultNodeInfo to use TextCell()
				// (it only works for strings)
				List<String> mixerlist = new ArrayList<String>();

				switch ((GeometryNames) value) {
				case Rectangle:
					for (RectangleMixers rm : RectangleMixers.values()) {
						mixerlist.add(rm.toString());
					}
					break;
				case exampleGeometry1:
					for (ExampleGeometryMixers egm : ExampleGeometryMixers
							.values()) {
						mixerlist.add(egm.toString());
					}
					break;
				}

				// We want the children of the Geometry. Return the mixers.
				ListDataProvider<String> dataProvider = new ListDataProvider<String>(
						mixerlist);

				// Use the shared selection model.
				return new DefaultNodeInfo<String>(dataProvider,
						new TextCell(), selectionModel, null);

			}
			return null;
		}

		/**
		 * Check if the specified value represents a leaf node. Leaf nodes
		 * cannot be opened.
		 */
		// You can define your own definition of leaf-node here.
		public boolean isLeaf(Object value) {
			// works because all non-leaf nodes are objects, only
			// leaf nodes are String.
			return value instanceof String;
		}

		public void setCellBrowser(CellBrowser cellbrowser) {
			this.cb = cellbrowser;
		}
	}

	/*
	 * Initialises the toggleColor button. TODO: Use pictures instead of text on
	 * the button.
	 * 
	 * Note: If the button shows "black" it means the current drawing color is
	 * black. Not 'toggle to black'.
	 */
	private static void createToggleButton() {
		toggleColor = new ToggleButton("black", "white");
		toggleColor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				toggleColor();

			}
		});
	}

	/*
	 * Changes the current drawing color from black to white, and from white to
	 * black.
	 */
	private static void toggleColor() {
		if (toggleColor.isDown()) {
			geom.setColor(CssColor.make("white"));
		} else {
			geom.setColor(CssColor.make("black"));
		}
	}
}