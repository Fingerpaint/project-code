package nl.tue.fingerpaint.server.simulator;

import java.io.File;

import nl.tue.fingerpaint.shared.simulator.SimulatorService;

/**
 * Class that is able to communicate with the native simulation-service code.
 * Use this class only indirectly through {@link SimulatorService}
 * 
 * @author Group Fingerpaint
 */
class NativeCommunicator {
	
	private static NativeCommunicator instance = new NativeCommunicator();
	
	private static String matrixDir = new File(new File(NativeCommunicator.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath()).getParent() + File.separator + "matrices" + File.separator).getPath().replace("%20", " ");
	
	static {
		System.load(new File(new File(NativeCommunicator.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath())
				.getParent() + File.separator + "lib" + File.separator + System.mapLibraryName("NativeCommunicator")).getPath().replace("%20", " "));
	}
	
	private NativeCommunicator() {
		
	}
	
	/**
	 * Return the singleton instance of this class.
	 * 
	 * @return singleton instance of the {@link NativeCommunicator}
	 */
	public static NativeCommunicator getInstance() {
		return instance;
	}
	
	/**
	 * Communicates with the native simulation-service code to simulate a
	 * step on a given concentration vector. The given vector is modified
	 * with the result.
	 * 
	 * @param geometry The geometry used for the simulation
	 * @param mixer The mixer used for the simulation
	 * @param concentrationVector
	 * 				The concentration vector that used for the simulation;
	 * 				note that results are returned in this parameter
	 * @param stepSize The size of the step that is performed
	 * @param stepName The name of the step to simulate
	 * @return The segregation of the simulation
	 */
	public synchronized double simulate(String geometry, 
			String mixer, 
			double[] concentrationVector, 
			double stepSize, 
			String stepName) {
		return simulate(
				geometry, mixer, concentrationVector, 
				stepSize, stepName, matrixDir);
	}
	
	/**
	 * Communicates with the native simulation-service code to simulate a
	 * step on a given concentration vector. The given vector is modified
	 * with the result.
	 * 
	 * @param geometry The geometry used for the simulation
	 * @param mixer The mixer used for the simulation
	 * @param concentrationVector
	 * 				The concentration vector that used for the simulation;
	 * 				note that results are returned in this parameter
	 * @param stepSize The size of the step that is performed
	 * @param stepName The name of the step to simulate
	 * @return The segregation of the simulation
	 */
	private native synchronized double simulate(String geometry, 
								String mixer, 
								double[] concentrationVector, 
								double stepSize, 
								String stepName,
								String dir);
}