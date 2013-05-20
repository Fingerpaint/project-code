package nl.tue.fingerpaint.client.websocket;

import java.io.Serializable;

import org.jsonmaker.gwt.client.Jsonizer;

/**
 * 
 * 
 * @author Group Fingerpaint
 */
public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9110096334273417225L;
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
	 * Constructs a new {@link Response} that has default values filled in for
	 * all parameters. Using this constructor is not encouraged, you should
	 * probably use {@link #Response(PerformancePoint[], double[][])}.
	 */
	public Response() {
		this(new PerformancePoint[] {}, new double[][] {});
	}

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
}
