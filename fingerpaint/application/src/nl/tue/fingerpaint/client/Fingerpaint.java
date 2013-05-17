package nl.tue.fingerpaint.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Group Fingerpaint
 */
public class Fingerpaint implements EntryPoint {

	// Button to toggle between black and white drawing colour
	private ToggleButton toggleColor;

	// Button to load predefined distribution half black, half white
	// Needed for testing purposes for story 32
	private Button loadDistButton;

	// Rectangular geometry to draw on
	private Geometry geom;

	// Button to adapt the drawing tool
	// TODO: Change this to a button on which the current tool is drawn
	private Button toolSelectButton;

	// PopupPanel which contains options for selecting a different drawing tool
	private PopupPanel toolSelector;

	// The panel in the popup panel to seperate the toolSelector from the
	// toolSizer
	private HorizontalPanel popupPanelPanel;

	// The panel in the popup panel that contains the different drawing tools
	private VerticalPanel popupPanelMenu;

	// Button to select the square drawing tool
	// TODO: Change this to a button on which a square is drawn
	private ToggleButton squareDrawingTool;

	// Button to select the circle drawing tool
	// TODO: Change this to a button on which a circle is drawn
	private ToggleButton circleDrawingTool;

	// Horizontal panel to contain drawing canvas and menu bar
	private HorizontalPanel panel = new HorizontalPanel();

	// Vertical panel to contain all menu items
	private VerticalPanel menuPanel = new VerticalPanel();

	// Width of the menu in which buttons are displayed
	// on the right side of the window in pixels
	private static final int menuWidth = 125;

	// Height of address-bar / tabs / menu-bar in the
	// browser in pixels. If this is not taken into account,
	// a vertical scroll bar appears.
	private static final int topBarHeight = 50;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Initialise geometry
		geom = new RectangleGeometry(Window.getClientHeight() - topBarHeight,
				Window.getClientWidth() - menuWidth);

		// Initialise toggleButton and add to menuPanel
		createToggleButton();
		menuPanel.add(toggleColor);

		// Initialise the toolSelectButton and add to menuPanel
		createToolSelector();
		menuPanel.add(toolSelectButton);

		// Initialise the loadDistButton and add to menuPanel
		createLoadDistButton();
		menuPanel.add(loadDistButton);

		// TODO: Initialise other menu items and add them to menuPanel

		// Add canvas and menuPanel to the panel
		// Make the canvas the entire width of the screen except for the
		// menuWidth
		panel.setWidth("100%");
		panel.add(geom.getCanvas());
		panel.add(menuPanel);
		panel.setCellWidth(menuPanel, Integer.toString(menuWidth));

		// Add panel to RootPanel
		RootPanel.get().add(panel);
	}

	/*
	 * Initialises the toggleColor button. TODO: Use pictures instead of text on
	 * the button.
	 * 
	 * Note: If the button shows "black" it means the current drawing colour is
	 * black. Not 'toggle to black'.
	 */
	private void createToggleButton() {
		toggleColor = new ToggleButton("black", "white");
		toggleColor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				toggleColor();

			}
		});
	}

	/*
	 * Changes the current drawing colour from black to white, and from white to
	 * black.
	 */
	private void toggleColor() {
		if (toggleColor.isDown()) {
			geom.setColor(CssColor.make("white"));
		} else {
			geom.setColor(CssColor.make("black"));
		}
	}

	private void createToolSelector() {
		// --Initialise all elements--------------------------------
		toolSelector = new PopupPanel(true);
		popupPanelPanel = new HorizontalPanel();
		popupPanelMenu = new VerticalPanel();
		squareDrawingTool = new ToggleButton("not selected", "square");
		circleDrawingTool = new ToggleButton("not selected", "circle");

		squareDrawingTool.addClickHandler(new ClickHandler() {

			/*
			 * Select the square drawing tool when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {
				
				if (!squareDrawingTool.isDown()) {
					squareDrawingTool.setDown(true);
				} else {
					// TODO Change hard-coded 3 to 'size-slider.getValue()' or
					// something
					geom.setDrawingTool(new SquareDrawingTool(3));

					// TODO Deselect all other tools
					circleDrawingTool.setDown(false);
				}
			}
		});
		//Initial drawing tool is square
		squareDrawingTool.setDown(true);

		circleDrawingTool.addClickHandler(new ClickHandler() {

			/*
			 * Select the square drawing tool when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {
				
				if (!circleDrawingTool.isDown()) {
					circleDrawingTool.setDown(true);
				} else {
					// TODO Change hard-coded 3 to 'size-slider.getValue()' or
					// something
					geom.setDrawingTool(new CircleDrawingTool(3));

					squareDrawingTool.setDown(false);
				}
			}
		});

		// -- Add all Drawings Tools below ---------------------
		popupPanelMenu.add(squareDrawingTool);
		popupPanelMenu.add(circleDrawingTool);

		// --TODO: Add DrawingTool Size slider below ----------------
		popupPanelPanel.add(popupPanelMenu);

		// Add everything to the popup panel
		toolSelector.add(popupPanelPanel);

		// Create the button the triggers the popup panel
		toolSelectButton = new Button("Select Tool");
		toolSelectButton.addClickHandler(new ClickHandler() {

			/*
			 * Show the popupPanel when this button is clicked
			 */
			@Override
			public void onClick(ClickEvent event) {
				toolSelector
						.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth,
									int offsetHeight) {
								int left = (Window.getClientWidth()
										- offsetWidth - 75);
								int top = 40;
								toolSelector.setPopupPosition(left, top);
							}
						});
			}

		});

	}

	// --Methods for testing purposes only---------------------------------
	/*
	 * Initialises the Load Distribution button. This button only exists for
	 * testing purposes. When it is pressed, the distribution of the geometry is
	 * set to a colour bar from black to white, from left to right. This
	 * distribution is then drawn on the canvas, to demonstrate we can load an
	 * arbitrary distribution, with 256 gray scale colours.
	 */
	private void createLoadDistButton() {
		loadDistButton = new Button("Load Dist");
		loadDistButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RectangleDistribution dist = new RectangleDistribution();
				for (int x = 0; x < 400; x++) {
					for (int y = 0; y < 240; y++) {
						dist.setValue(x, y, (double) x / 400);
					}
				}

				geom.drawDistribution(dist);
			}
		});
	}
}