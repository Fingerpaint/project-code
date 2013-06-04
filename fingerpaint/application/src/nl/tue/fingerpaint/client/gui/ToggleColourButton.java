package nl.tue.fingerpaint.client.gui;

import nl.tue.fingerpaint.client.model.ApplicationState;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CustomButton;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * Button that can be used to change the drawing colour.
 * 
 * @author Group Fingerpaint
 */
public class ToggleColourButton extends CustomButton implements
		HasValue<Boolean>, IsEditor<LeafValueEditor<Boolean>> {

	/** Reference to the model. Used to change the drawing colour. */
	protected ApplicationState as;
	/** Foreground colour. */
	protected CssColor fgCol;
	/** Background colour. */
	protected CssColor bgCol;

	/** Editor... Like in {@link ToggleButton}. */
	private LeafValueEditor<Boolean> editor;

	/**
	 * Construct a new button that can be used to toggle between black and
	 * white.
	 * 
	 * @param appState
	 *            Reference to the model, used to change drawing colour.
	 */
	public ToggleColourButton(ApplicationState appState) {
		super("henk", "ingrid");
		this.as = appState;
		this.fgCol = CssColor.make("#000000");
		this.bgCol = CssColor.make("#ffffff");
	}

	@Override
	protected void onClick() {
		super.onClick();

		if (isDown()) {
			as.getGeometry().setColor(bgCol);
		} else {
			as.getGeometry().setColor(fgCol);
		}
		ValueChangeEvent.fire(this, isDown());
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Boolean> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public LeafValueEditor<Boolean> asEditor() {
		if (editor == null) {
			editor = TakesValueEditor.of(this);
		}
		return editor;
	}

	@Override
	public Boolean getValue() {
		return isDown();
	}

	@Override
	public void setValue(Boolean value) {
		setValue(value, false);
	}

	@Override
	public void setValue(Boolean value, boolean fireEvents) {
		if (value == null) {
			value = Boolean.FALSE;
		}
		boolean oldValue = isDown();
		setDown(value);
		if (fireEvents) {
			ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
		}
	}

}
