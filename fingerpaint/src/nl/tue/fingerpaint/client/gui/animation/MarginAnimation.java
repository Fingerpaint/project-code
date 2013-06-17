package nl.tue.fingerpaint.client.gui.animation;

import nl.tue.fingerpaint.shared.utils.Utils;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A {@code MarginAnimation} can animate the margin of an element.
 * 
 * @author Group Fingerpaint
 */
public class MarginAnimation extends Animation {

	/** Panel that is animated. */
	private Element subject;
	/** Direction(s) to animate */
	private int animateDirections;
	/** If animation is in progress. */
	private boolean running = false;
	/** Original margins on the element. Margins are in the order top, right, bottom, left in this array. */
	private double[] srcMargins;
	/** Margin size to animate to. */
	private double targetMarginSize;
	/**
	 * Callback on finish of animation. {@code null} when no callback is
	 * present.
	 */
	private AsyncCallback<Boolean> onCompleteCallback;

	/**
	 * Construct a new object that can be used to animate the given element.
	 * 
	 * @param animationSubject
	 *            The element to be animated.
	 * @param animationDirections
	 *            Which margins to animate. Should be a logical combination of
	 *            {@link Direction Directions}, for example {@code 
	 *            Direction.TOP | Direction.RIGHT}.
	 */
	public MarginAnimation(Element animationSubject, int animationDirections) {
		this.subject = animationSubject;
		this.srcMargins = new double[4];
		setAnimationDirections(animationDirections);
	}

	/**
	 * Change which margin(s) is (are) animated.
	 * 
	 * @param directions
	 *            The new margins to be animated. Should be a logical
	 *            combination of {@link Direction Directions}, for example
	 *            {@code Direction.TOP | Direction.RIGHT}.
	 */
	public void setAnimationDirections(int directions) {
		if (!running) {
			this.animateDirections = directions;
		}
	}

	/**
	 * Perform an animation of all animated margins to the given size.
	 * 
	 * @param size
	 *            Wanted size of the margins.
	 * @param durationMillis
	 *            Time the animation takes.
	 */
	public void doAnimate(int size, int durationMillis) {
		doAnimate(size, durationMillis, null);
	}

	/**
	 * Perform an animation of all animated margins to the given size.
	 * 
	 * @param size
	 *            Wanted size of the margins.
	 * @param durationMillis
	 *            Time the animation takes.
	 * @param onComplete
	 *            Callback to call when animation is done. Ignored when
	 *            {@code null}. This callback will (guaranteed) only be called
	 *            with a {@code true} argument on the
	 *            {@code AsyncCallback#onSuccess(Object)} callback.
	 */
	public void doAnimate(int size, int durationMillis, AsyncCallback<Boolean> onComplete) {
		this.onCompleteCallback = onComplete;
		
		srcMargins[0] = Utils.parseIntFromCss(subject.getStyle().getMarginTop());
		srcMargins[1] = Utils.parseIntFromCss(subject.getStyle().getMarginRight());
		srcMargins[2] = Utils.parseIntFromCss(subject.getStyle().getMarginBottom());
		srcMargins[3] = Utils.parseIntFromCss(subject.getStyle().getMarginLeft());
		targetMarginSize = size;
		
		run(durationMillis);
	}

	@Override
	protected void onComplete() {
		super.onComplete();
		if (onCompleteCallback != null) {
			onCompleteCallback.onSuccess(true);
		}
		running = false;
	}

	@Override
	protected void onStart() {
		super.onStart();
		running = true;
	}

	@Override
	protected void onUpdate(double progress) {	
		if ((animateDirections & Direction.TOP.getId()) > 0) {
			subject.getStyle().setMarginTop(srcMargins[0] + (targetMarginSize -
					srcMargins[0]) * progress, Unit.PX);
		}
		
		if ((animateDirections & Direction.RIGHT.getId()) > 0) {
			subject.getStyle().setMarginRight(srcMargins[1] + (targetMarginSize -
					srcMargins[1]) * progress, Unit.PX);
		}
		
		if ((animateDirections & Direction.BOTTOM.getId()) > 0) {
			subject.getStyle().setMarginBottom(srcMargins[2] + (targetMarginSize -
					srcMargins[2]) * progress, Unit.PX);
		}
		
		if ((animateDirections & Direction.LEFT.getId()) > 0) {
			subject.getStyle().setMarginLeft(srcMargins[3] + (targetMarginSize -
					srcMargins[3]) * progress, Unit.PX);
		}
	}

}
