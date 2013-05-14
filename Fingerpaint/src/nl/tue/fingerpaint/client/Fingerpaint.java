package nl.tue.fingerpaint.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Tessa Belder
 */
public class Fingerpaint implements EntryPoint {

	// Button to toggle between black and white drawin color
	private ToggleButton toggleColor;

	// Rectangular geometry to draw on
	private RectangleGeometry geom;

	// Vertical panel to contain geometry and button
	private VerticalPanel panel = new VerticalPanel();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		//Initialize geometry and add to panel
		geom = new RectangleGeometry();
		panel.add(geom.getCanvas());

		//Initialize toggleButton and add to panel
		createToggleButton();
		panel.add(toggleColor);

		//Add panel to RootPanel
		RootPanel.get().add(panel);
	}

	/*
	 * Initialises the toggleColor button.
	 * TODO: Use pictures instead of text on the button.
	 * 
	 * Note: If the button shows "black" it means the current drawing
	 * color is black. Not 'toggle to black'.
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
	 * Changes the current drawing color from black to white,
	 * and from white to black.
	 */
	private void toggleColor() {
		if (toggleColor.isDown()) {
			geom.setColor(CssColor.make("white"));
		} else {
			geom.setColor(CssColor.make("black"));
		}
	}
}