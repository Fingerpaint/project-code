package nl.tue.fingerpaint.server.simulator;

import java.lang.reflect.Field;

public class NativeCommunicator {
	
	static {
		System.loadLibrary("NativeCommunicator");  
    }  
	
	public native double simulate(int geometry, 
								int matrix, 
								double[] distribution, 
								double step, 
								String stepid);
}