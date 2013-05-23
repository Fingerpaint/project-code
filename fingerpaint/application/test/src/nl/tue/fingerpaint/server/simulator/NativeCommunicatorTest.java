package nl.tue.fingerpaint.server.simulator;

import static org.junit.Assert.*;

import nl.tue.fingerpaint.client.websocket.Step;

import org.junit.Test;

public class NativeCommunicatorTest {

	@Test
	public void test() {
		double[] distribution = new double[96000];
		for (int i = 0; i < 96000; i++) {
			if (i < 96000/2) {
				distribution[i] = 1;
			} else {
				distribution[i] = 0;
			}
		}
		System.out.println();
		NativeCommunicator c = new NativeCommunicator();
		System.out.print("Segregation: " + c.simulate(0, 0, distribution, 40, "TL") + " : ");
		
		for (int i = 0; i < 96000; i++) {
			System.out.print(distribution[i] + " ");
		}
	}

}