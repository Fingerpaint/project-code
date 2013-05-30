package nl.tue.fingerpaint.client.storage;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.MixingProtocol;
import nl.tue.fingerpaint.client.MixingStep;
import nl.tue.fingerpaint.client.RectangleDistribution;
import nl.tue.fingerpaint.shared.GeometryNames;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class StorageManagerTest extends GWTTestCase {

	@Test
	public void testSaveInitialDistribution() {
		// Create and store an initial distribution
		final int x1 = 200;
		final int y1 = 100;
		final double v1 = 0.5;
		
		final int x2 = 50;
		final int y2 = 30;
		final double v2 = 0.34817;
		
		final String key = "testdist1";
		final String geometry = GeometryNames.RECT_SHORT;
		
		RectangleDistribution dist = new RectangleDistribution();
		dist.setValue(x1, y1, v1);
		dist.setValue(x2, y2, v2);
		
		StorageManager.INSTANCE.putDistribution(geometry, key, dist.getDistribution());
		
		// Receive and test the initial distribution
		double[] received = StorageManager.INSTANCE.getDistribution(geometry, key);
		RectangleDistribution receivedDist = new RectangleDistribution(received);
		for (int i = 0; i < received.length; i++) {
			if (receivedDist.getIndex(x1, y1) == i) {
				assertEquals("Non-standard value number 1", v1, received[i], 0.000000001);
			} else if (receivedDist.getIndex(x2, y2) == i) {
				assertEquals("Non-standard value number 2", v2, received[i], 0.000000001);
			} else {
				assertEquals("Standard value", 1.0, received[i], 0.0000000001);
			}
		}
	}
	
	@Test
	public void testSaveProtocol() {
		// Create and save a protocol
		final String key = "testdist1";
		final String geometry = GeometryNames.RECT_SHORT;
		
		final MixingProtocol protocol = new MixingProtocol();
		
		final MixingStep step1 = new MixingStep(1.25,  true,  true);
		final MixingStep step2 = new MixingStep(2.50,  true, false);
		final MixingStep step3 = new MixingStep(3.75, false,  true);
		final MixingStep step4 = new MixingStep( 4.0, false, false);
		
		ArrayList<MixingStep> program = new ArrayList<MixingStep>();
		program.add(step1);
		program.add(step2);
		program.add(step3);
		program.add(step4);
		
		protocol.setProgram(program);
		
		StorageManager.INSTANCE.putProtocol(geometry, key, protocol);
		
		// Receive and test the protocol
		MixingProtocol receivedProtocol = StorageManager.INSTANCE.getProtocol(geometry, key);
		
		for (int i = 0; i < program.size(); i++) {
			assertEquals("Step size of step " + i, program.get(i).getStepSize(), receivedProtocol.getStep(i).getStepSize(), 0.000001);
			assertEquals("Top wall of step " + i, program.get(i).getWall(), receivedProtocol.getStep(i).getWall());
			assertEquals("Direction of step " + i, program.get(i).getDirection(), receivedProtocol.getStep(i).getDirection());
		}
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}
}
