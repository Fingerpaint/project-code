package nl.tue.fingerpaint.client.simulator;

import static org.junit.Assert.*;

import nl.tue.fingerpaint.client.websocket.Request;
import nl.tue.fingerpaint.client.websocket.Response;
import nl.tue.fingerpaint.client.websocket.Step;

import org.junit.Test;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SimulatorServiceTest extends GWTTestCase {

	@Test
	public void test() {
		SimulatorServiceAsync service = GWT.create(SimulatorService.class);
		AsyncCallback<Response> callback = new AsyncCallback<Response>() {
		    @Override
			public void onSuccess(Response result) {
		    	System.out.println(result);
		    }
		
		    @Override
			public void onFailure(Throwable caught) {
		    	caught.printStackTrace();
		    }
		  };
		  double[] dist = {1,0,.8,0.5};
		  Step[] protocol = {new Step("TL", 2.0), new Step("TR", 5.0)};
		  Request request = new Request(0, 0, dist, protocol, 5, true);
		  
		  service.simulate(request, callback);
	
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
