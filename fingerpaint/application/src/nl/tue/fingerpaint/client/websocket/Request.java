package nl.tue.fingerpaint.client.websocket;

import org.jsonmaker.gwt.client.Jsonizer;

/**
 * A {@code Request} is an object that can be sent to the simulator service
 * using the {@link SimulatorServiceSocket}. Basically, this is a message
 * requesting a simulation with certain parameters.
 * 
 * @author Group Fingerpaint
 */
public class Request {

	// ---------- GLOBAL VARIABLES --------------------------------------------
	/**
	 * The geometry is an ID indicating which geometry is used.
	 */
	protected int geometry;
	/**
	 * The matrix that should be used: there can be multiple matrices per
	 * geometry. For example, for the circle you can have different shapes in
	 * the mixer and that shape can be built from different materials.
	 */
	protected int mixer;
	/**
	 * A vector indicating the initial distribution of the fluids in the
	 * geometry indicated earlier. It depends on the geometry how the vector
	 * maps to actual positions in the "grid" that is laid over the geometry.
	 * This vector is an array with floats between 0 and 1.
	 */
	protected double[] distribution;
	/**
	 * The protocol defines what actions are performed. It depends on the
	 * geometry what actions are available. Each action can be performed for a
	 * certain amount of time, which is called the 'step' in below object.
	 */
	protected Step[] protocol;
	/**
	 * Number of times protocol should be repeated.
	 */
	protected int numsteps;
	/**
	 * Indicate if intermediate results should be sent back or not.
	 */
	protected boolean intermediateResults;

	// ---------- PUBLIC PART -------------------------------------------------
	/**
	 * Constructs a new {@link Request} that is ready to be sent to the
	 * simulator service. Of course, after creation parameters can be changed
	 * before sending. Also, a request can be altered and resent.
	 * 
	 * @param geometry
	 *            An ID indicating which geometry is used.
	 * @param mixer
	 *            An ID indicating which matrix for the given geometry should be
	 *            used. This can be useful when there are multiple mixers with
	 *            the same geometry.
	 * @param distribution
	 *            The initial distribution of fluids in the mixer. Should be a
	 *            vector with values between {@code 0} and {@code 1}.
	 * @param protocol
	 *            The sequence of actions that should be performed in the
	 *            simulation.
	 * @param numsteps
	 *            The number of times that the protocol should be repeated.
	 * @param intermediateResults
	 *            If intermediate results (the result of applying just one step
	 *            of the protocol) should also be returned, instead of just the
	 *            end result that is obtained by applying <i>all</i> steps in
	 *            the protocol.
	 */
	public Request(int geometry, int mixer, double[] distribution,
			Step[] protocol, int numsteps, boolean intermediateResults) {
		setGeometry(geometry);
		setMixer(mixer);
		setDistribution(distribution);
		setProtocol(protocol);
		setNumSteps(numsteps);
		setIntermediateResults(intermediateResults);
	}

	/**
	 * Get the ID of the geometry that is chosen in this request.
	 * 
	 * @return the ID of the chosen geometry
	 */
	public int getGeometry() {
		return geometry;
	}

	/**
	 * Change the ID of the chosen geometry for this request.
	 * 
	 * @param geometry
	 *            The new geometry ID.
	 */
	public void setGeometry(int geometry) {
		this.geometry = geometry;
	}

	/**
	 * Get the ID of the chosen mixer for this request.
	 * 
	 * @return the ID of the chosen mixer
	 */
	public int getMixer() {
		return mixer;
	}

	/**
	 * Change the ID of the chosen mixer for this request.
	 * 
	 * @param mixer
	 *            The new mixer ID.
	 */
	public void setMixer(int mixer) {
		this.mixer = mixer;
	}

	/**
	 * Get the initial distribution for this request.
	 * 
	 * @return the initial distribution
	 */
	public double[] getDistribution() {
		return distribution;
	}

	/**
	 * Change the initial distribution for this request.
	 * 
	 * @param distribution
	 *            The new initial distribution.
	 */
	public void setDistribution(double[] distribution) {
		this.distribution = distribution;
	}

	/**
	 * Get the protocol for this request.
	 * 
	 * @return the protocol
	 */
	public Step[] getProtocol() {
		return protocol;
	}

	/**
	 * Change the protocol for this request.
	 * 
	 * @param protocol
	 *            The new protocol.
	 */
	public void setProtocol(Step[] protocol) {
		this.protocol = protocol;
	}

	/**
	 * Get the number of times the protocol is executed.
	 * 
	 * @return the number of times the protocol is executed
	 */
	public int getNumsteps() {
		return numsteps;
	}

	/**
	 * Change the number of times the protocol is executed.
	 * 
	 * @param numsteps
	 *            The new number of times the protocol is executed.
	 */
	public void setNumSteps(int numsteps) {
		this.numsteps = numsteps;
	}

	/**
	 * Get if intermediate results are returned or not.
	 * 
	 * @return if intermediate results are returned
	 */
	public boolean hasIntermediateResults() {
		return intermediateResults;
	}

	/**
	 * Change if intermediate results are returned or not.
	 * 
	 * @param intermediateResults
	 *            The new indication if intermediate results should be returned
	 *            by the simulation service.
	 */
	public void setIntermediateResults(boolean intermediateResults) {
		this.intermediateResults = intermediateResults;
	}

	// ---------- INNER CLASSES/INTERFACES ------------------------------------
	/**
	 * Interface that is used to create a JSON-object from the {@link Request}
	 * class.
	 * 
	 * @author Group Fingerpaint
	 */
	public interface RequestJsonizer extends Jsonizer {
		// no implementation is needed, it just needs to be here
	}
}
