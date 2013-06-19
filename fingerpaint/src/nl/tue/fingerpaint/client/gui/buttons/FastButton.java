package nl.tue.fingerpaint.client.gui.buttons;

import io.ashton.fastpress.client.fast.FastPressElement;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;

/**
 * <p>
 * A {@code FastButton} is an element that can be used like a {@link Button},
 * but on touch devices it will respond immediately to touches instead of with
 * a delay. This type of button should be used anywhere instead of "regular"
 * buttons to ensure a "snappy" experience and fix weird bugs.
 * </p>
 * 
 * <p>
 * Note: this class basically wraps a {@link Button} element that can be
 * accessed trough the {@link #getButton()} method.
 * </p>
 * 
 * @author Group Fingerpaint
 */
public class FastButton extends FastPressElement {

	/** Button that is wrapped. */
	private Button button;
	
	/**
	 * Create a new button with no text in it.
	 */
	public FastButton() {
		this("");
	}
	
	/**
	 * Create a new button with the given text in it.
	 * 
	 * @param btnText Text to display in the button.
	 */
	public FastButton(String btnText) {
		this(new Button(btnText));
	}
	
	/**
	 * Create a new {@code FastButton} that wraps the given
	 * {@link Button}.
	 * 
	 * @param child
	 *            The {@code Button} to wrap.
	 */
	public FastButton(Button child) {
		super();
		this.button = child;
		initWidget(child);
	}
	
	/**
	 * Return a reference to the actual {@link Button} wrapped by this class.
	 * 
	 * @return a reference to the wrapped {@link Button}
	 */
	public Button getButton() {
		return button;
	}
	
	@Override
	public Element getElement() {
		return button.getElement();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
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
