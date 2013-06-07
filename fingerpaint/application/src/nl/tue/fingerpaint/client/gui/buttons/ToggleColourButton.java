package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.model.ApplicationState;
import nl.tue.fingerpaint.shared.utils.Colour;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;

/**
 * Button that can be used to change the drawing colour.
 * 
 * @author Group Fingerpaint
 */
public class ToggleColourButton extends Button implements ClickHandler {

	/** Style name (class) used on this button. */
	public static final String STYLENAME = "gwt-ToggleColourButton";
	/** Style name of element that represents foreground colour. */
	public static final String STYLENAME_FG_EL = "gwt-ToggleColourButtonForeground";
	/** Style name of element that represents background colour. */
	public static final String STYLENAME_BG_EL = "gwt-ToggleColourButtonBackground";

	/** Reference to the model. Used to change the drawing colour. */
	protected ApplicationState as;
	/** Foreground colour. */
	protected Colour fgCol;
	/** Element that shows foreground colour. */
	protected DivElement fgColEl;
	/** Background colour. */
	protected Colour bgCol;
	/** Element that show background colour. */
	protected DivElement bgColEl;

	/**
	 * Construct a new button that can be used to toggle between black and
	 * white.
	 * 
	 * @param appState
	 *            Reference to the model, used to change drawing colour.
	 */
	public ToggleColourButton(ApplicationState appState) {
		super();
		this.as = appState;
		this.fgCol = Colour.BLACK;
		this.bgCol = Colour.WHITE;

		addClickHandler(this);

		// Set class of button element
		setStyleName(STYLENAME);

		// Create element that shows foreground colour, set it up and add it
		fgColEl = DivElement.as(DOM.createElement("div"));
		fgColEl.setPropertyString("className", STYLENAME_FG_EL);
		fgColEl.getStyle().setBackgroundColor(fgCol.toString());
		getElement().appendChild(fgColEl);

		// Create element that shows background colour, set it up and add it
		bgColEl = DivElement.as(DOM.createElement("div"));
		bgColEl.setPropertyString("className", STYLENAME_BG_EL);
		bgColEl.getStyle().setBackgroundColor(bgCol.toString());
		getElement().appendChild(bgColEl);
		
		// Set the debug ID for the toggle colour button.
		this.ensureDebugId("toggleColor");
	}

	@Override
	public void onClick(ClickEvent event) {
		toggleColour();
		as.getGeometry().setColor(fgCol);
	}

	/**
	 * Return the currently selected colour.
	 * 
	 * @return The currently selected ("foreground") colour.
	 */
	public Colour getSelectedColour() {
		return fgCol;
	}

	/**
	 * Change the background (currently not selected) colour.
	 * 
	 * @param backgroundColour New background (not selected) colour.
	 */
	public void setBackgroundColour(Colour backgroundColour) {
		this.bgCol = backgroundColour;
		update();
	}
	
	/**
	 * Change the foreground (currently selected) colour.
	 * 
	 * @param foregroundColour New foreground (selected) colour.
	 */
	public void setForegroundColour(Colour foregroundColour) {
		this.fgCol = foregroundColour;
		update();
	}
	
	/**
	 * Swaps the foreground and background colour.
	 */
	public void toggleColour() {
		Colour tmpCol = fgCol;
		fgCol = bgCol;
		bgCol = tmpCol;

		update();
	}

	/**
	 * Set the background colour properties for the foreground and background
	 * elements (using the global {@link #fgCol} and {@link #bgCol}).
	 */
	protected void update() {
		fgColEl.getStyle().setBackgroundColor(fgCol.toString());
		bgColEl.getStyle().setBackgroundColor(bgCol.toString());
	}
}
