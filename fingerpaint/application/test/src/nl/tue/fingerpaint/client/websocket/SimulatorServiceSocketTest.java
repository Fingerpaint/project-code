package nl.tue.fingerpaint.client.websocket;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;

public class SimulatorServiceSocketTest extends GWTTestCase {

	@Test
	public void testRequestSimulation() {
		double[] dist = {1,0,.8,0.5};
		Step[] protocol = {new Step("TL", 2.0), new Step("TR", 5.0)};
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