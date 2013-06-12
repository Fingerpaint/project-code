package nl.tue.fingerpaint.client.gui.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.touch.client.Point;
import com.google.gwt.user.client.Window;

/**
 * A {@code SlideAnimation} can slide an absolute positioned element out of the screen.
 * It uses {@link Window#getClientHeight()} and {@link Window#getClientWidth()} to
 * automatically determine where to animate a panel to.
 * 
 * @author Group Fingerpaint
 */
public class SlideAnimation extends Animation {

	/** Panel that is animated. */
	protected Element subject;
	/** Direction to move panel in. */
	protected Direction direction;
	/** If animation is in progress. */
	protected boolean running = false;
	/** If element is hidden or not. */
	protected boolean hidden = false;
	/** On-screen original position of the element. */
	protected Point elPos;
	
	/**
	 * Construct a new object that can be used to animate the given element.
	 * 
	 * @param animationSubject The element to be animated. Should be positioned absolutely.
	 * @param animationDirection The side of the window to which the panel should move
	 *                           until it is not visible anymore.
	 */
	public SlideAnimation(Element animationSubject, Direction animationDirection) {
		this.subject = animationSubject;
		this.direction = animationDirection;
		this.elPos = new Point(animationSubject.getAbsoluteLeft(), animationSubject.getAbsoluteTop());
	}
	
	/**
	 * Change direction of animation. Cannot be changed while an animation is running.
	 * 
	 * @param direction The new direction.
	 */
	public void setDirection(Direction direction) {
		if (!running) {
			this.direction = direction;
		}
	}
	
	/**
	 * Perform a sliding animation to show the element, that is, restore its position before
	 * a call to {@link #doSlideOut(int)}. If the element is not hidden by such a call,
	 * nothing happens.
	 * 
	 * @param durationMillis Time the animation takes.
	 */
	public void doSlideIn(int durationMillis) {
		if (subject.getAbsoluteLeft() != elPos.getX() || subject.getAbsoluteTop() != elPos.getY()) {
			hidden = true;
			run(durationMillis);
		}
	}
	
	/**
	 * Perform a sliding animation to hide the element on the selected side of the screen.
	 * 
	 * @param durationMillis Time the animation takes.
	 */
	public void doSlideOut(int durationMillis) {
		if (subject.getAbsoluteLeft() >= 0 &&
				subject.getAbsoluteLeft() < Window.getClientWidth() &&
				subject.getAbsoluteTop() >= 0 &&
				subject.getAbsoluteTop() < Window.getClientHeight()) {
			hidden = false;
			run(durationMillis);
		}
	}
	
	@Override
	protected void onComplete() {
		super.onComplete();
		running = false;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		running = true;
	}
	
	@Override
	protected void onUpdate(double progress) {
		if (hidden) {
			progress = 1.0 - progress;
		}
		
		switch (direction) {
		case TOP :
			subject.getStyle().setTop(elPos.getY() * progress, Unit.PX);
			break;
		case RIGHT :
			subject.getStyle().setLeft(elPos.getX() +
					(Window.getClientWidth() - elPos.getX()) * progress, Unit.PX);
			break;
		case BOTTOM :
			subject.getStyle().setTop(elPos.getY() +
					(Window.getClientHeight() - elPos.getY()) * progress, Unit.PX);
			break;
		case LEFT :
			subject.getStyle().setLeft(elPos.getX() * progress, Unit.PX);
			break;
		}
	}

}
