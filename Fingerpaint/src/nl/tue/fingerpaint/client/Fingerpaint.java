package nl.tue.fingerpaint.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Tessa Belder
 */
public class Fingerpaint implements EntryPoint {

	//Button to toggle between black and white drawing color
		private ToggleButton toggleColor;
		
		//Rectangular geometry to draw on
		private RectangleGeometry geom;
		
		//Vertical panel to contain geometry and button
		private HorizontalPanel panel = new HorizontalPanel();
		
		//Width of the menu in which buttons are displayed
		//on the right side of the window in pixels
		private static final int menuWidth = 125;
		
		//Height of address-bar / tabs / menu-bar in the
		//browser in pixels. If this is not taken into account,
		//a vertical scrollbar appears.
		private static final int topBarHeight = 50;

	/**
	 * This is the entry point method.
	 */
		public void onModuleLoad() {

			//Initialize geometry and add to panel
			panel.setWidth("100%");
			geom = new RectangleGeometry(Window.getClientHeight() - topBarHeight, Window.getClientWidth() - menuWidth);
			panel.add(geom.getCanvas());
			
			//Initialize toggleButton and add to panel
			createToggleButton();
			panel.add(toggleColor);
			panel.setCellWidth(toggleColor, Integer.toString(menuWidth));
			

			//Add panel to RootPanel
			RootPanel.get("canvas").add(panel);
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