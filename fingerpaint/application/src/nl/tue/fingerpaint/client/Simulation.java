package nl.tue.fingerpaint.client;

import java.io.Serializable;

/**
 * Defines all information necessary for a simulation run. Results are saved
 * into a {@link SimulationResult}.
 * 
 * @author Lasse Blaauwbroek
 * 
 */
public class Simulation implements Serializable {

	/**
	 * Randomly generated version uid
	 */
	private static final long serialVersionUID = -2535797540411490267L;

	private String mixer;

	private MixingProtocol protocol;

	private double[] concentrationVector;

	private int protocolRuns;

	private boolean intermediateVectors;

	public Simulation() {
		
	}
	
	/**
	 * Creates a new SimulatorRequest.
	 * 
	 * @param mixer
	 *            The mixer that should be used
	 * @param protocol
	 *            The name of the step that should be performed
	 * @param concentrationVector
	 *            The used concentration vector
	 * @param protocolRuns
	 *            How many times the protocol should be repeated
	 * @param intermediateVectors
	 *            Wheter or not intermediate concentration vectors should be
	 *            computed for each protocol run
	 */
	public Simulation(final String mixer, final MixingProtocol protocol,
			final double[] concentrationVector, final int protocolRuns,
			final boolean intermediateVectors) {
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
	public double[] getConcentrationVector() {
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

//	private void writeObject(ObjectOutputStream o) throws IOException {
//
//		o.writeObject(mixer);
//		o.writeObject(protocol);
//		o.writeObject(concentrationVector);
//		o.writeInt(protocolRuns);
//		o.writeBoolean(intermediateVectors);
//	}
//
//	private void readObject(ObjectInputStream o) throws IOException,
//			ClassNotFoundException {
//
//		mixer = (String) o.readObject();
//		protocol = (MixingProtocol) o.readObject();
//		concentrationVector = (double[]) o.readObject();
//		protocolRuns = o.readInt();
//		intermediateVectors = o.readBoolean();
//	}
}
