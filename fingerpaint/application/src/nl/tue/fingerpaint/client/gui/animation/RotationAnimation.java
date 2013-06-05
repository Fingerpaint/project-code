package nl.tue.fingerpaint.client.gui.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;

/**
 * A {@code RotationAnimation} can animate the rotation of an element.
 * 
 * @author Group Fingerpaint
 */
public class RotationAnimation extends Animation {

	/**
	 * Constant to indicate that the rotation should go clockwise.
	 */
	public static final boolean ANIMATE_CLOCKWISE = true;
	/**
	 * Constant to indicate that the rotation should go counter clockwise.
	 */
	public static final boolean ANIMATE_COUNTERCLOCKWISE = false;

	/** Panel that is animated. */
	protected Element subject;
	/** Axis to animate */
	protected int axis;
	/** If animation is in progress. */
	protected boolean running = false;
	/** If animation is going clockwise or not. */
	protected boolean clockwise;
	/** Original angle of element. */
	protected double oldAngle;
	/** Target angle of element. */
	protected double newAngle;

	/**
	 * Construct a new object that can be used to animate the given element. The
	 * current rotation will be set to the given angle.
	 * 
	 * @param animationSubject
	 *            The element to be animated.
	 * @param angle
	 *            The rotation to set to the element right now.
	 */
	public RotationAnimation(Element animationSubject, double angle) {
		this.subject = animationSubject;
		oldAngle = angle;

		setAngle(mod(angle));
	}

	/**
	 * Perform an animation of the element to the given angle, taking the given
	 * number of milliseconds. The rotation will go either clockwise or counter
	 * clockwise, depending on the given parameter.
	 * 
	 * @param durationMillis
	 *            Time the animation takes.
	 * @param toAngle
	 *            Angle to which element should rotate.
	 * @param clockwise
	 *            If the animation should go clockwise ({@code true}) or counter
	 *            clockwise ({@code false}).
	 */
	public void doRotate(int durationMillis, int toAngle, boolean clockwise) {
		newAngle = toAngle;
		this.clockwise = clockwise;
		run(durationMillis);
	}

	@Override
	protected void onComplete() {
		super.onComplete();
		running = false;
		oldAngle = newAngle;
	}

	@Override
	protected void onStart() {
		super.onStart();
		running = true;
	}

	@Override
	protected void onUpdate(double progress) {
		if (clockwise) {
			setAngle(mod(oldAngle + mod(newAngle - oldAngle) * progress));
		} else {
			setAngle(mod(oldAngle
					- ((360 - mod(newAngle - oldAngle)) * progress)));
		}
	}

	/**
	 * Return the modulo 360 of the given number, that is guaranteed to be
	 * between 0 and 360.
	 * 
	 * @param num
	 *            The number of which the modulo is required.
	 * @return A number between 0 and 360, with {@code \result = num % 360}.
	 */
	protected double mod(double num) {
		return ((num % 360) + 360) % 360;
	}

	/**
	 * Set the rotation of the {@link #subject} by setting a number of CSS
	 * properties.
	 * 
	 * @param angle
	 *            The rotation in degrees to set. (Should be between 0 and 360.)
	 */
	protected void setAngle(double angle) {
		long iPart = (long) angle;
		double fPart = angle - iPart;
		String doubleStr = Double.toString(fPart).substring(2);
		doubleStr = iPart + (doubleStr.length() > 0 ? "." + doubleStr : "");

		// Below property names are in camel case: capital letters "X" are
		// replaced "-x"
		subject.getStyle().setProperty("WebkitTransform",
				"rotate(" + doubleStr + "deg)");
		subject.getStyle().setProperty("MozTransform",
				"rotate(" + doubleStr + "deg)");
		subject.getStyle().setProperty("MsTransform",
				"rotate(" + doubleStr + "deg)");
		subject.getStyle().setProperty("OTransform",
				"rotate(" + doubleStr + "deg)");
		subject.getStyle().setProperty("transform",
				"rotate(" + doubleStr + "deg)");
	}
}
