package nl.tue.fingerpaint.client.websocket;

import java.io.Serializable;

import org.jsonmaker.gwt.client.Jsonizer;

/**
 * <p>
 * A {@code Step} is, as the name suggests, one step in a protocol. A protocol
 * in turn is simply an ordered set of steps, that are executed one after
 * another. Every step has an ID, that indicates <i>what</i> actually happens
 * when it is executed and a duration, that indicates for <i>how long</i> the
 * step is executed.
 * </p>
 * 
 * <p>
 * An example: in a rectangular geometry, it is possible to move the top and
 * bottom wall to both the left and right side. There are hence four possible
 * steps, that have the IDs {@code TL}, {@code TR}, {@code BL} and {@code BR}.
 * These are simply abbreviations for "Top Left", et cetera.
 * </p>
 * 
 * @author Group Fingerpaint
 */
public class Step implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5428353384007944346L;
	// ---------- GLOBAL VARIABLES --------------------------------------------
	/**
	 * The ID of the step.
	 */
	protected String stepId;
	/**
	 * The time that the step is performed (some unit-less value). (The "D".)
	 */
	protected double step;

	// ---------- PUBLIC PART -------------------------------------------------
	/**
	 * Constructs a new {@link Step} that has default values filled in for all
	 * parameters. Using this constructor is not encouraged, you should probably
	 * use {@link #Step(String, double)}.
	 */
	public Step() {
		this("", 0);
	}

	/**
	 * A {@link Step} with given ID and duration will be created.
	 * 
	 * @param id
	 *            The ID of the step. This can be an arbitrary string, but it
	 *            needs to be recognised by the simulation service of course.
	 * @param duration
	 *            The time that the step will be performed. (The "D".)
	 */
	public Step(String id, double duration) {
		setStepId(id);
		setStepDuration(duration);
	}

	/**
	 * Get the ID of this step.
	 * 
	 * @return the ID of this step
	 */
	public String getStepId() {
		return stepId;
	}

	/**
	 * Change the ID of this step.
	 * 
	 * @param stepid
	 *            The new ID for this step.
	 */
	public void setStepId(String stepid) {
		this.stepId = stepid;
	}

	/**
	 * Get the duration of this step (the "D").
	 * 
	 * @return the time that this step is executed
	 */
	public double getStepDuration() {
		return step;
	}

	/**
	 * Change the duration of this step (the "D").
	 * 
	 * @param stepduration
	 *            the new "D" for this step
	 */
	public void setStepDuration(double stepduration) {
		this.step = stepduration;
	}

	// ---------- INNER CLASSES/INTERFACES ------------------------------------
	/**
	 * Interface that is used to create a JSON-object from the {@link Step}
	 * class.
	 * 
	 * @author Group Fingerpaint
	 */
	public interface StepJsonizer extends Jsonizer {
		// no implementation is needed, it just needs to be here
	}
}
