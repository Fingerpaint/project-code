package nl.tue.fingerpaint.client.websocket;

import org.jsonmaker.gwt.client.Jsonizer;

/**
 * 
 * 
 * @author Group Fingerpaint
 */
public class Response {

	// ---------- GLOBAL VARIABLES --------------------------------------------
	/**
	 * A set of measuring points that together form a plot of the performance of
	 * the mixer/protocol.
	 */
	protected PerformancePoint[] performance;
	/**
	 * Either just the distribution after performing the entire protocol, or all
	 * intermediate results plus the end result when asked for.
	 */
	protected double[][] results;

	// ---------- PUBLIC PART -------------------------------------------------
	/**
	 * Construct a new {@link Response} with given performance data and
	 * result(s).
	 * 
	 * @param performance
	 *            The performance of the mixer/protocol.
	 * @param results
	 *            Either just the distribution after performing the entire
	 *            protocol, or all intermediate results plus the end result when
	 *            asked for in the request to the simulation service.
	 */
	public Response(PerformancePoint[] performance, double[][] results) {
		setPerformance(performance);
		setResults(results);
	}

	/**
	 * @return the performance
	 */
	public PerformancePoint[] getPerformance() {
		return performance;
	}

	/**
	 * @param performance
	 *            the performance to set
	 */
	public void setPerformance(PerformancePoint[] performance) {
		this.performance = performance;
	}

	/**
	 * @return the results
	 */
	public double[][] getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(double[][] results) {
		this.results = results;
	}

	// ---------- INNER CLASSES/INTERFACES ------------------------------------
	/**
	 * Interface that is used to create a JSON-object from the {@link Request}
	 * class.
	 * 
	 * @author Group Fingerpaint
	 */
	public interface ResponseJsonizer extends Jsonizer {
		// no implementation is needed, it just needs to be here
	}

	/**
	 * Just one point in the set of performance data.
	 * 
	 * @author Group Fingerpaint
	 */
	public class PerformancePoint {

		// ---------- GLOBAL VARIABLES ----------------------------------------
		/**
		 * The step at which the performance was measured. This is the
		 * X-coordinate in a performance plot.
		 */
		protected int step;
		/**
		 * The (logarithm of the) segregation at the
		 * {@link PerformancePoint#step} when it was measured. This is the
		 * Y-coordinate in a performance plot.
		 */
		protected double performance;

		// ---------- PUBLIC PART ---------------------------------------------
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
}
