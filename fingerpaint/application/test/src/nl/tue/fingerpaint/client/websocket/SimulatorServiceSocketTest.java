package nl.tue.fingerpaint.client.websocket;

import org.junit.Test;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;

/**
 * This test case tests the {@link SimulatorServiceSocket} and with it, all
 * classes in the {@link nl.tue.fingerpaint.client.websocket} package. It does
 * so by actually requesting a simulation from the simulation service that is
 * part of the Fingerpaint application as well. In- and output are validated to
 * ensure that everything is working.
 * 
 * @author Group Fingerpaint
 */
public class SimulatorServiceSocketTest extends GWTTestCase {

	/**
	 * Test if the Jsonizing works well.
	 */
	@Test
	public void testJsonizer() {
		// --- REQUEST --------------------------------------------------------
		double[] initDist = new double[] { 0, 0, 1, 1, 0, 0, 1, 1 };
		Step[] protocol = new Step[] { new Step("TL", 1.25),
				new Step("BR", 0.5) };
		Request request = new Request(0, 0, initDist, protocol, 1, false);
		Request.RequestJsonizer reqJsonizer = (Request.RequestJsonizer) GWT
				.create(Request.RequestJsonizer.class);
		String reqJson = reqJsonizer.asString(request);
		assertEquals(
				"Expected JSON did not match result when Jsonizing request.",
				"{\"geometry\":0,\"mixer\":0,\"distribution\":"
						+ "[0.0,0.0,1.0,1.0,0.0,0.0,1.0,1.0],\"protocol\":"
						+ "[{\"stepId\":\"TL\",\"stepDuration\":1.25},"
						+ "{\"stepId\":\"BR\",\"stepDuration\":0.5}]}", reqJson);

		// --- RESPONSE -------------------------------------------------------
		PerformancePoint[] performance = new PerformancePoint[] {
				new PerformancePoint(1, 12.0), new PerformancePoint(2, 3.3),
				new PerformancePoint(3, 1.4), new PerformancePoint(4, 1.05),
				new PerformancePoint(5, 0.8) };
		double[][] results = new double[][] { new double[] { 0.5, 0.5, 0.25,
				0.12, 0.7, 0.4, 0.3, 0.2 } };
		Response response = new Response(performance, results);
		Response.ResponseJsonizer respJsonizer = (Response.ResponseJsonizer) GWT
				.create(Response.ResponseJsonizer.class);
		String respJson = respJsonizer.asString(response);
		assertEquals(
				"Expected JSON did not match result when Jsonizing request.",
				"{\"performance\":[{\"step\":1,\"performance\":12.0},"
						+ "{\"step\":2,\"performance\":3.3},{\"step\":3,\"performance\":1.4},"
						+ "{\"step\":4,\"performance\":1.05},{\"step\":5,\"performance\":0.8}],"
						+ "\"results\":[[0.5,0.5,0.25,0.12,0.7,0.4,0.3,0.2]]}",
				respJson);
	}

	/**
	 * Test if we can call the service simulator.
	 */
	@Test
	public void testRequestSimulation() {
		double[] dist = { 1, 0, .8, 0.5 };
		Step[] protocol = { new Step("TL", 2.0), new Step("TR", 5.0) };
		Request request = new Request(0, 0, dist, protocol, 5, true);
		ResponseCallback callback = new ResponseCallback() {
			@Override
			public void onError(String message) {
				fail(message);
			}

			@Override
			public void onResponse(Response result) {
				fail(result.toString());
			}
		};
		SimulatorServiceSocket sss = SimulatorServiceSocket.getInstance();
		sss.requestSimulation(request, callback);
		Timer togglebuttonTimer = new Timer() {
			public void run() {
				fail("timer ran out");
			}
		};
		togglebuttonTimer.schedule(10000);
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
