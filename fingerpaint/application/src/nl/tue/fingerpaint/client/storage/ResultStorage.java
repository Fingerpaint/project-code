package nl.tue.fingerpaint.client.storage;

import nl.tue.fingerpaint.client.MixingProtocol;

import org.jsonmaker.gwt.client.Jsonizer;
import org.jsonmaker.gwt.client.annotation.Transient;

/**
 * Class that holds everything needed to store a mixing result. Can be JSONized
 * using the ResultStorageJsonizer
 * 
 * @author Group Fingerpaint
 */
public class ResultStorage {

	/** The long version of the Geometry name */
	private String geometry;
	/** The name of the mixer used */
	private String mixer;
	/** The initial distribution */
	private double[] distribution;
	/** String representation of the protocol used */
	private String protocol;
	/** The array holding the segregation values */
	private double[] segregation;
	/** The amount of times the protocol was executed */
	private int nrSteps;

	/**
	 * Gives the long name of the geometry used
	 * 
	 * @return The long name of the geometry used
	 */
	public String getGeometry() {
		return geometry;
	}

	/**
	 * Sets the geometry name
	 * 
	 * @param geometry
	 *            The geometry to set
	 */
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	/**
	 * Gives the name of the mixer used
	 * 
	 * @return The name of the mixer used
	 */
	public String getMixer() {
		return mixer;
	}

	/**
	 * Sets the mixer name
	 * 
	 * @param mixer
	 *            The mixer to set
	 */
	public void setMixer(String mixer) {
		this.mixer = mixer;
	}

	/**
	 * Gives the initial concentration distribution that was used
	 * 
	 * @return The initial concentration distribution that was used
	 */
	public double[] getDistribution() {
		return distribution;
	}

	/**
	 * Sets the initial concentration distribution
	 * 
	 * @param distribution
	 *            The concentration distribution to set
	 */
	public void setDistribution(double[] distribution) {
		this.distribution = distribution;
	}

	/**
	 * Gives a string representation of the protocol that was used
	 * 
	 * @return String representation of the protocol that was used
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Sets the protocol
	 * 
	 * @param protocol
	 *            String representation of the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * Gives the protocol object that was used
	 * 
	 * @return The protocol that was used
	 */
	@Transient
	public MixingProtocol getMixingProtocol() {
		return MixingProtocol.fromString(protocol);
	}

	/**
	 * Sets the protocol
	 * 
	 * @param protocol
	 *            The protocol object to set
	 */
	@Transient
	public void setMixingProtocol(MixingProtocol protocol) {
		this.protocol = protocol.toString();
	}

	/**
	 * Gives the segregation points
	 * 
	 * @return Double array with the segregation points
	 */
	public double[] getSegregation() {
		return segregation;
	}

	/**
	 * Sets the segregation points
	 * 
	 * @param segregation
	 *            Double array containing the segregation points to set
	 */
	public void setSegregation(double[] segregation) {
		this.segregation = segregation;
	}

	/**
	 * Gives the number of times the protocol was executed
	 * 
	 * @return The number of times the protocol was executed
	 */
	public int getNrSteps() {
		return nrSteps;
	}

	/**
	 * Sets the number of times the protocol was executed
	 * 
	 * @param nrSteps
	 *            The amount to set
	 */
	public void setNrSteps(int nrSteps) {
		this.nrSteps = nrSteps;
	}

	/** ResultStorage JSONizer Interface */
	public interface ResultStorageJsonizer extends Jsonizer {
		// Empty on purpose
	}

}
