package nl.tue.fingerpaint.client.gui.buttons;

import nl.tue.fingerpaint.client.gui.GuiState;
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

	/** Identifier for the ToggleColourButton in the main menu. */
	public static final String TOGGLE_COLOUR = "toggleColour";
	/** Identifier for the ToggleColourButton in the tool menu. */
	public static final String TOGGLE_COLOUR_TOOL_MENU = "toolMenuToggleColour";
	
	/** Style name (class) used on this button. */
	public static final String STYLENAME = "gwt-ToggleColourButton";
	/** Style name of element that represents foreground colour. */
	public static final String STYLENAME_FG_EL = "gwt-ToggleColourButtonForeground";
	/** Style name of element that represents background colour. */
	public static final String STYLENAME_BG_EL = "gwt-ToggleColourButtonBackground";

	/** Constant use for {@link #update}. */
	protected static final int UPDATE_BOTH = 0;
	/** Constant use for {@link #update}. */
	protected static final int UPDATE_BG = 1;
	/** Constant use for {@link #update}. */
	protected static final int UPDATE_FG = 2;
	
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
	/** Identifier of this instance. */
	protected String identifier;

	/**
	 * Construct a new button that can be used to toggle between black and
	 * white.
	 * 
	 * @param appState
	 *            Reference to the model, used to change drawing colour.
	 * @param identifier
	 *            Identifier for this object.
	 */
	public ToggleColourButton(ApplicationState appState, String identifier) {
		super();
		this.as = appState;
		this.identifier = identifier;
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
		ensureDebugId("toggleColor");
	}

	/**
	 * Toggles the colour used for drawing.
	 * @param event The event that has fired.
	 */
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
		update(UPDATE_BG);
	}
	
	/**
	 * Change the foreground (currently selected) colour.
	 * 
	 * @param foregroundColour New foreground (selected) colour.
	 */
	public void setForegroundColour(Colour foregroundColour) {
		this.fgCol = foregroundColour;
		update(UPDATE_FG);
	}
	
	/**
	 * Swaps the foreground and background colour.
	 */
	public void toggleColour() {
		Colour tmpCol = fgCol;
		fgCol = bgCol;
		bgCol = tmpCol;

		update(UPDATE_BOTH);
	}

	/**
	 * Set the background colour properties for the foreground and background
	 * elements (using the global {@link #fgCol} and {@link #bgCol}).
	 * 
	 * @param updateWhat
	 *            A constant to indicate if only the foreground colour, only
	 *            the background colour or both should be updated.
	 */
	protected void update(int updateWhat) {
		boolean updateBg = (updateWhat == UPDATE_BOTH || updateWhat == UPDATE_BG);
		boolean updateFg = (updateWhat == UPDATE_BOTH || updateWhat == UPDATE_FG);
		
		if (updateFg) {
			fgColEl.getStyle().setBackgroundColor(fgCol.toString());
		}
		if (updateBg) {
			bgColEl.getStyle().setBackgroundColor(bgCol.toString());
		}
		
		// There are two ToggleColourButtons, update both
		if (identifier.equals(TOGGLE_COLOUR)) {
			if (updateFg && !GuiState.toolMenuToggleColour.fgCol.equals(fgCol)) {
				GuiState.toolMenuToggleColour.setForegroundColour(fgCol);
			}
			if (updateBg && !GuiState.toolMenuToggleColour.bgCol.equals(bgCol)) {
				GuiState.toolMenuToggleColour.setBackgroundColour(bgCol);
			}
		} else {
			if (updateFg && !GuiState.toggleColour.fgCol.equals(fgCol)) {
				GuiState.toggleColour.setForegroundColour(fgCol);
			}
			if (updateBg && !GuiState.toggleColour.bgCol.equals(bgCol)) {
				GuiState.toggleColour.setBackgroundColour(bgCol);
			}
		}
		
		// Update the colours of the drawing tool buttons
		GuiState.squareDrawingTool.setColour(fgCol);
		GuiState.circleDrawingTool.setColour(fgCol);
	}
}
