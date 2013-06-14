package nl.tue.fingerpaint.shared.simulator;

import java.io.Serializable;

import nl.tue.fingerpaint.shared.GeometryNames;
import nl.tue.fingerpaint.shared.model.MixingProtocol;

/**
 * Defines all information necessary for a simulation run. Results are saved
 * into a {@link SimulationResult}.
 * 
 * @author Group Fingerpaint
 * 
 */
public class Simulation implements Serializable {

	/** Randomly generated version UID */
	private static final long serialVersionUID = -2535797540411490267L;
	/** The name of the mixer used */
	private String mixer;
	/** The protocol that has to be applied */
	private MixingProtocol protocol;
	/** The initial concentration distribution */
	private String concentrationVector;
	/** The number of times the protocol has to be run */
	private int protocolRuns;

	/**
	 * {@code true} if concentration vectors in between steps have to be
	 * returned, {@code false} otherwise
	 */
	private boolean intermediateVectors;

	/**
	 * Default constructor.
	 */
	public Simulation() {
		// Needed to implement Serializable
	}

	/**
	 * Creates a new SimulatorRequest.
	 * 
	 * @param geometry
	 *            The chosen geometry.
	 * @param mixer
	 *            The mixer that should be used.
	 * @param protocol
	 *            The name of the step that should be performed.
	 * @param concentrationVector
	 *            The used concentration vector.
	 * @param protocolRuns
	 *            How many times the protocol should be repeated.
	 * @param intermediateVectors
	 *            Whether or not intermediate concentration vectors should be
	 *            computed for each protocol run.
	 * @throws UnsupportedOperationException
	 *            If an unsupported geometry was selected.
	 */
	public Simulation(final String geometry, final String mixer,
			final MixingProtocol protocol, final String concentrationVector,
			final int protocolRuns, final boolean intermediateVectors) 
	        throws UnsupportedOperationException {
		if (geometry != GeometryNames.RECT) {
			throw new UnsupportedOperationException("Invalid geometry selected.");
		}
		if (mixer == null) {
			throw new NullPointerException("Argument mixer cannot be null");
		}
		if (protocol == null) {
			throw new NullPointerException("Argument protocol cannot be null");
		}
		if (concentrationVector == null) {
			throw new NullPointerException(
					"Argument concentrationVector cannot be null");
		}
		this.mixer = mixer;
		this.protocol = protocol;
		this.concentrationVector = concentrationVector;
		this.protocolRuns = protocolRuns;
		this.intermediateVectors = intermediateVectors;
	}

	/**
	 * @return the mixer
	 */
	public String getMixer() {
		return mixer;
	}

	/**
	 * @return the protocol
	 */
	public MixingProtocol getProtocol() {
		return protocol;
	}

	/**
	 * @return the concentrationVector
	 */
	public String getConcentrationVector() {
		return concentrationVector;
	}

	/**
	 * @return the protocolRuns
	 */
	public int getProtocolRuns() {
		return protocolRuns;
	}

	/**
	 * @return the intermediateVectors boolean
	 */
	public boolean calculatesIntermediateVectors() {
		return intermediateVectors;
	}
}
