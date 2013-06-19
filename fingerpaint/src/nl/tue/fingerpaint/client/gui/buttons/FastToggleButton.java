package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.FastPressElement;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * <p>
 * A {@code FastButton} is an element that can be used like a {@link ToggleButton},
 * but on touch devices it will respond immediately to touches instead of with
 * a delay. This type of button should be used anywhere instead of "regular"
 * buttons to ensure a "snappy" experience and fix weird bugs.
 * </p>
 * 
 * <p>
 * Note: this class basically wraps a {@link ToggleButton} element that can be
 * accessed trough the {@link #getToggleButton()} field.
 * </p>
 * 
 * @author Group Fingerpaint
 */
public class FastToggleButton extends FastPressElement {

	/** {@code ToggleButton} that is wrapped. */
	private ToggleButton toggleButton;
	
	/**
	 * Create a new button with no text in it.
	 */
	public FastToggleButton() {
		this("", "");
	}
	
	/**
	 * Create a new button with the given text in it.
	 * 
	 * @param upText Text to show when the toggle button is not down.
	 * @param downText Text to show when the toggle button is down.
	 */
	public FastToggleButton(String upText, String downText) {
		this(new ToggleButton(upText, downText));
	}
	
	/**
	 * Create a new {@code FastToggleButton} that wraps the given
	 * {@link ToggleButton}.
	 * 
	 * @param child
	 *            The {@code ToggleButton} to wrap.
	 */
	public FastToggleButton(ToggleButton child) {
		super();
		this.toggleButton = child;
		initWidget(child);
	}
	
	@Override
	public Element getElement() {
		return toggleButton.getElement();
	}
	
	/**
	 * Return a reference to the actual {@link ToggleButton} wrapped by this class.
	 * 
	 * @return a reference to the wrapped {@link ToggleButton}
	 */
	public ToggleButton getToggleButton() {
		return toggleButton;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		toggleButton.setEnabled(enabled);
	}
	
	@Override
	public void onHoldPressDownStyle() {
		// ignored
	}

	@Override
	public void onHoldPressOffStyle() {
		// ignored
	}

	@Override
	public void onDisablePressStyle() {
		// ignored
	}

	@Override
	public void onEnablePressStyle() {
		// ignored
	}

}
