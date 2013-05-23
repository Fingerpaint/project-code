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
	protected double[] segregation;
	/**
	 * Either just the distribution after performing the entire protocol, or all
	 * intermediate results plus the end result when asked for.
	 */
	protected double[][] results;

	// ---------- PUBLIC PART -------------------------------------------------
	/**
	 * Constructs a new {@link Response} that has default values filled in for
	 * all parameters. Using this constructor is not encouraged, you should
	 * probably use {@link #Response(double[], double[][])}.
	 */
	public Response() {
		this(new double[] {}, new double[][] {});
	}

	/**
	 * Construct a new {@link Response} with given performance data and
	 * result(s).
	 * 
	 * @param segregation
	 *            The segregation of the mixer/protocol.
	 * @param results
	 *            Either just the distribution after performing the entire
	 *            protocol, or all intermediate results plus the end result when
	 *            asked for in the request to the simulation service.
	 */
	public Response(double[] segregation, double[][] results) {
		setSegregation(segregation);
		setResults(results);
	}

	/**
	 * @return the performance
	 */
	public double[] getSegregation() {
		return segregation;
	}

	/**
	 * @param segregation
	 *            the segregation to set
	 */
	public void setSegregation(double[] segregation) {
		this.segregation = segregation;
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
