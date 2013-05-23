package nl.tue.fingerpaint.client;

import java.io.Serializable;

/**
 * Represents a the results of a {@link Simulation}.
 * 
 * @author Lasse Blaauwbroek
 *
 */
public class SimulationResult implements Serializable {
	
	/**
	 * Randomly generated serial version uid
	 */
	private static final long serialVersionUID = -3437263386499901097L;

	private Simulation simulation;
	
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
	public SimulationResult(final Simulation simulation, 
						   final double[][] concentrationVectors,
						   final double[] segregationPoints) {
		if (simulation == null) {
			throw new NullPointerException(
					"Argument simulation cannot be null");
		}
		if (concentrationVectors == null) {
			throw new NullPointerException(
					"Argument concentrationVectors cannot be null");
		}
		if (segregationPoints == null) {
			throw new NullPointerException(
					"Argument segragationPoints cannot be null");
		}
		
		this.simulation = simulation;
		this.concentrationVectors = concentrationVectors;
		this.segregationPoints = segregationPoints;
	}
	
	/**
	 * @return the simulation
	 */
	public Simulation getSimulation() {
		return simulation;
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
}
