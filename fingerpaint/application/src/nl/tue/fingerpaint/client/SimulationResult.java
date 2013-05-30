package nl.tue.fingerpaint.client;

import java.io.Serializable;

import org.jsonmaker.gwt.client.Jsonizer;

/**
 * Represents a the results of a {@link Simulation}.
 * 
 * @author Group Fingerpaint
 *
 */
public class SimulationResult implements Serializable {
	
	/**
	 * Randomly generated serial version uid
	 */
	private static final long serialVersionUID = -3437263386499901097L;
	
	private double[][] concentrationVectors;
	
	private double[] segregationPoints;
	
	public SimulationResult() {
		
	}

	/**
	 * Constructs a new SimulationResults of a {@link Simulation}.
	 * 
	 * @param simulation The simulation that has been run
	 * @param concentrationVectors The resulting concentration vectors
	 * @param segregationPoints The resulting segregation values
	 */
	public SimulationResult(final double[][] concentrationVectors,
						    final double[] segregationPoints) {
		if (concentrationVectors == null) {
			throw new NullPointerException(
					"Argument concentrationVectors cannot be null");
		}
		if (segregationPoints == null) {
			throw new NullPointerException(
					"Argument segragationPoints cannot be null");
		}
		
		this.concentrationVectors = concentrationVectors;
		this.segregationPoints = segregationPoints;
	}

	/**
	 * @return the concentrationVectors
	 */
	public double[][] getConcentrationVectors() {
		return concentrationVectors;
	}

	/**
	 * @return the segregationPoints
	 */
	public double[] getSegregationPoints() {
		return segregationPoints;
	}
	
//	private void writeObject(ObjectOutputStream o) throws IOException {
//
//		o.writeObject(simulation);
//		o.writeObject(concentrationVectors);
//		o.writeObject(segregationPoints);
//	}
//
//	private void readObject(ObjectInputStream o) throws IOException,
//			ClassNotFoundException {
//
//		simulation = (Simulation) o.readObject();
//		concentrationVectors = (double[][]) o.readObject();
//		segregationPoints = (double[]) o.readObject();
//	}
	
	public static interface SimulationResultJsonizer extends Jsonizer {}
}
