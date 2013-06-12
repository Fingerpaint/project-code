package nl.tue.fingerpaint.client.model;

import java.io.Serializable;
import java.util.ArrayList;

import nl.tue.fingerpaint.shared.GeometryNames;

/**
 * This class symbolises a mixing program consisting of a number of mixing steps
 * 
 * @author Group Fingerpaint
 * 
 */
public class MixingProtocol implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 4249912306397874059L;
	/** the current mixing program */
	private ArrayList<MixingStep> program = new ArrayList<MixingStep>();
	/** The geometry that this protocol is defined for. */
	private String geometry;

	/**
	 * Construct a new protocol that applies to the rectangular geometry.
	 */
	public MixingProtocol() {
		this.geometry = GeometryNames.RECT_LONG;
	}
	
	/**
	 * Construct a new protocol that applies to the given geometry.
	 * 
	 * @param geometry
	 *            The long name of the geometry to which this protocol applies.
	 */
	public MixingProtocol(String geometry) {
		this.geometry = geometry;
	}

	/**
	 * Returns the MixingStep located at index {@code index}.
	 * 
	 * @param index
	 *            the index of the element to be returned
	 * @return the MixingStep located at index {@code index}
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if index < 0 or index >= getProgramSize()
	 */
	public MixingStep getStep(int index) {
		return program.get(index);
	}

	/**
	 * Adds a single mixing step to the end of the current program.
	 * 
	 * @param stepSize
	 *            The stepSize of the wall movement, should be divisible by 0.25
	 * @param direction
	 *            the direction of the wall movement, true is clockwise, false
	 *            counterclockwise
	 * @param wall
	 *            the wall that is moved, true for the top wall, false for the
	 *            bottom wall
	 */
	public void addStep(double stepSize, boolean direction, boolean wall) {
		MixingStep nextStep = new MixingStep(stepSize, direction, wall);
		program.add(nextStep);
	}

	/**
	 * Adds the mixing step step to the end of the current program.
	 * 
	 * @param step
	 *            the MixingStep to be added
	 * @throws NullPointerException
	 *             if step == null
	 */
	public void addStep(MixingStep step) {
		if (step == null) {
			throw new NullPointerException();
		}
		program.add(step);
	}

	/**
	 * Edits a single mixing step at index {@code index}.
	 * 
	 * @param index
	 *            the index of the step to be removed
	 * @param stepSize
	 *            The stepSize of the wall movement, should be divisible by 0.25
	 * @param direction
	 *            the direction of the wall movement, true is clockwise, false
	 *            counterclockwise
	 * @param wall
	 *            the wall that is moved, true for the top wall, false for the
	 *            bottom wall
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if {@code index} >= {@code getProgramSize()} or {@code index}
	 *             < 0
	 */
	public void editStep(int index, double stepSize, boolean direction,
			boolean wall) {
		MixingStep newStep = new MixingStep(stepSize, direction, wall);
		program.set(index, newStep);
	}

	/**
	 * Moves a single MixingStep from index oroginalIndex to newIndex.
	 * 
	 * @param originalIndex
	 *            The original index of the MixingStep
	 * @param newIndex
	 *            The index the MixingStep will be moved to
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if originalIndex < 0 or newIndex < 0 or originalIndex >=
	 *             getProgramSize() or newIndex >= getProgramSize()
	 */
	public void moveStep(int originalIndex, int newIndex) {
		if (originalIndex < 0 || newIndex < 0
				|| originalIndex >= getProgramSize()
				|| newIndex >= getProgramSize()) {
			throw new IndexOutOfBoundsException();
		}
		MixingStep movedStep = program.get(originalIndex);
		program.remove(originalIndex);
		program.add(newIndex, movedStep);
	}

	/**
	 * Removes a single step from the protocol.
	 * 
	 * @param index
	 *            the index of the step to be removed
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if {@code index} >= {@code getProgramSize()} or {@code index}
	 *             < 0
	 */
	public void removeStep(int index) {
		program.remove(index);
	}

	/**
	 * Returns the number of steps in the protocol.
	 * 
	 * @return the size of the program; i.e. the number of steps in it
	 */
	public int getProgramSize() {
		return program.size();
	}

	/**
	 * Returns all the current steps in the protocol.
	 * 
	 * @return An array list containing all current steps
	 */
	public ArrayList<MixingStep> getProgram() {
		return program;
	}

	/**
	 * Sets a new protocol.
	 * 
	 * @param program
	 *            The list of mixing steps to set as the current protocol
	 */
	public void setProgram(ArrayList<MixingStep> program) {
		this.program = program;
	}

	/**
	 * Returns the long name of the geometry this protocol is defined for.
	 * 
	 * @return The long name of the geometry this protocol is defined for.
	 */
	public String getGeometry() {
		return geometry;
	}

	/**
	 * Returns a string representation of this protocol.
	 */
	@Override
	public String toString() {
		String result = "";
		for (MixingStep ms : program) {
			result += ms.toString() + ", ";
		}

		// Prevent StringIndexOutOfBoundsException due to empty protocol
		if (result.length() < 2) {
			return "";
		}

		return result.substring(0, result.length() - 2);
	}
}
