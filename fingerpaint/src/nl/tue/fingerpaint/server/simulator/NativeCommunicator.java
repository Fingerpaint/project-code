package nl.tue.fingerpaint.server.simulator;

import nl.tue.fingerpaint.shared.simulator.SimulatorService;

/**
 * Class that is able to communicate with the native simulation-service code.
 * Use this class only indirectly through {@link SimulatorService}
 * 
 * @author Group Fingerpaint
 */
class NativeCommunicator {
	
	private static NativeCommunicator instance = new NativeCommunicator();
	
	static {
		System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary("NativeCommunicator");  
		
    }
	
//	public static void loadLibraryFromResource() {
//	    InputStream in = MyClass.class.getResourceAsStream(name);
//	    byte[] buffer = new byte[1024];
//	    int read = -1;
//	    File temp = File.createTempFile(name, "");
//	    FileOutputStream fos = new FileOutputStream(temp);
//
//	    while((read = in.read(buffer)) != -1) {
//	        fos.write(buffer, 0, read);
//	    }
//	    fos.close();
//	    in.close();
//
//	    System.load(temp.getAbsolutePath());
//	}
	
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
	public native synchronized double simulate(String geometry, 
								String mixer, 
								double[] concentrationVector, 
								double stepSize, 
								String stepName);
}