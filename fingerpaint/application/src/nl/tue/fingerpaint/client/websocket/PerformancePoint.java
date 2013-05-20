package nl.tue.fingerpaint.client.websocket;

import java.io.Serializable;

/**
 * Just one point in the set of performance data that is returned from the
 * simulation service.
 * 
 * @author Group Fingerpaint
 */
public class PerformancePoint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6773527529652764917L;
	// ---------- GLOBAL VARIABLES ----------------------------------------
	/**
	 * The step at which the performance was measured. This is the X-coordinate
	 * in a performance plot.
	 */
	protected int step;
	/**
	 * The (logarithm of the) segregation at the {@link PerformancePoint#step}
	 * when it was measured. This is the Y-coordinate in a performance plot.
	 */
	protected double performance;

	// ---------- PUBLIC PART ---------------------------------------------
	/**
	 * Constructs a new {@link PerformancePoint} that has default values filled in for all
	 * parameters. Using this constructor is not encouraged, you should probably
	 * use {@link #PerformancePoint(int, double)}.
	 */
	public PerformancePoint() {
		this(0, 0);
	}
	
	/**
	 * Construct a new {@link PerformancePoint}.
	 * 
	 * @param step
	 *            The step at which the measurement was taken.
	 * @param performance
	 *            The segregation at the time of measuring.
	 */
	public PerformancePoint(int step, double performance) {
		setStep(step);
		setPerformance(performance);
	}

	/**
	 * Get the step.
	 * 
	 * @return the step
	 */
	public int getStep() {
		return step;
	}

	/**
	 * Change the step.
	 * 
	 * @param step
	 *            the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}

	/**
	 * Get the performance.
	 * 
	 * @return the performance
	 */
	public double getPerformance() {
		return performance;
	}

	/**
	 * Change the performance.
	 * 
	 * @param performance
	 *            the performance to set
	 */
	public void setPerformance(double performance) {
		this.performance = performance;
	}
}
