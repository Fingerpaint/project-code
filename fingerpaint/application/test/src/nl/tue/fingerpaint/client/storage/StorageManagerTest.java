package nl.tue.fingerpaint.client.storage;

import java.util.ArrayList;

import nl.tue.fingerpaint.client.MixingProtocol;
import nl.tue.fingerpaint.client.MixingStep;
import nl.tue.fingerpaint.client.RectangleDistribution;
import nl.tue.fingerpaint.shared.GeometryNames;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class StorageManagerTest extends GWTTestCase {

	//private MixingProtocol protocol;
	private ArrayList<MixingStep> program;

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

		StorageManager.INSTANCE.putDistribution(geometry, key,
				dist.getDistribution());

		// Receive and test the initial distribution
		double[] received = StorageManager.INSTANCE.getDistribution(geometry,
				key);
		RectangleDistribution receivedDist = new RectangleDistribution(received);
		for (int i = 0; i < received.length; i++) {
			if (receivedDist.getIndex(x1, y1) == i) {
				assertEquals("Non-standard value number 1", v1, received[i],
						0.000000001);
			} else if (receivedDist.getIndex(x2, y2) == i) {
				assertEquals("Non-standard value number 2", v2, received[i],
						0.000000001);
			} else {
				assertEquals("Standard value", 1.0, received[i], 0.0000000001);
			}
		}
	}

	@Test
	public void testSaveProtocol() {
		MixingProtocol protocol = new MixingProtocol();
		// Create and save a protocol
		final String key = "testdist1";
		final String geometry = GeometryNames.RECT_SHORT;
		initProtocol(protocol);
		StorageManager.INSTANCE.putProtocol(geometry, key, protocol);

		// Receive and test the protocol
		MixingProtocol receivedProtocol = StorageManager.INSTANCE.getProtocol(
				geometry, key);

		for (int i = 0; i < program.size(); i++) {
			assertEquals("Step size of step " + i,
					program.get(i).getStepSize(), receivedProtocol.getStep(i)
							.getStepSize(), 0.000001);
			assertEquals("Top wall of step " + i, program.get(i).getWall(),
					receivedProtocol.getStep(i).getWall());
			assertEquals("Direction of step " + i, program.get(i)
					.getDirection(), receivedProtocol.getStep(i).getDirection());
		}
	}

	@Test
	public void testSaveResult() {
		MixingProtocol protocol = new MixingProtocol();
		final String geom = GeometryNames.RECT_LONG;
		final String mix = "Default";
		final int steps = 20;
		final double[] segr = new double[] { 0.1, 0.2, 0.3, 0.7, 0.6, 0.8, 0.8,
				0.4, 0.9, 0.4, 0.8, 0.2, 0.6, 0.3, 0.7, 0.5, 0.9, 0.4, 0.8, 0.1 };
		
		
		final String key = "result1";
		final ResultStorage rs = new ResultStorage();
		double[] distribution = new double[96000];
		for (int i = 532; i < 9428; i++) {
			distribution[i] = 1.0;
		}
		rs.setDistribution(distribution);
		rs.setGeometry(geom);
		rs.setMixer(mix);
		rs.setNrSteps(steps);
		initProtocol(protocol);
		rs.setMixingProtocol(protocol);
		rs.setSegregation(segr);

		StorageManager.INSTANCE.putResult(key, rs);

		// Receive and test the protocol
		ResultStorage receivedResult = StorageManager.INSTANCE.getResult(key);
		
		double[] receivedDist = receivedResult.getDistribution();
		//RectangleDistribution receivedDist = new RectangleDistribution(received);
		for (int i = 0; i < receivedDist.length; i++) {
			double expectedValue = (i > 531 && i < 9428) ? 1.0 : 0.0;
			assertEquals("Value on index " +i, expectedValue, receivedDist[i]);
		}
		
		assertEquals("The geometry of the result", geom, receivedResult.getGeometry());
		assertEquals("The mixer of the result", mix, receivedResult.getMixer());
		assertEquals("Nr of steps of the result", steps, receivedResult.getNrSteps());
		
		MixingProtocol receivedProtocol = receivedResult.getMixingProtocol();
		for (int i = 0; i < program.size(); i++) {
			assertEquals("Step size of step " + i,
					program.get(i).getStepSize(), receivedProtocol.getStep(i)
							.getStepSize(), 0.000001);
			assertEquals("Top wall of step " + i, program.get(i).getWall(),
					receivedProtocol.getStep(i).getWall());
			assertEquals("Direction of step " + i, program.get(i)
					.getDirection(), receivedProtocol.getStep(i).getDirection());
		}
		
		double[] receivedSegregation = receivedResult.getSegregation();
		assertEquals("Length of the segregation", segr.length, receivedSegregation.length);
		for (int i = 0; i < segr.length; i++) {
			assertEquals("Segregation value at index " +i, segr[i], receivedSegregation[i]);
		}
	}

	private void initProtocol(MixingProtocol protocol) {
	//	protocol = new MixingProtocol();

		MixingStep step1 = new MixingStep(1.25, true, true);
		MixingStep step2 = new MixingStep(2.50, true, false);
		MixingStep step3 = new MixingStep(3.75, false, true);
		MixingStep step4 = new MixingStep(4.0, false, false);

		program = new ArrayList<MixingStep>();
		program.add(step1);
		program.add(step2);
		program.add(step3);
		program.add(step4);

		protocol.setProgram(program);
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}
}
