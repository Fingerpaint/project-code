package nl.tue.fingerpaint.client;

import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

public class ApplicationStateTest extends GWTTestCase{ 
	private ApplicationState as;
	private NumberSpinner nrStepsSpinner;
	private double DEFAULT = 1.0;
	
	private void init(){
		as = new ApplicationState();
	}
	
	private void initSpinner(){
		nrStepsSpinner = new NumberSpinner(DEFAULT, 1.0, 1.0, 50.0, true);
		as.setNrSteps(DEFAULT);
		
		nrStepsSpinner.setSpinnerListener(new NumberSpinnerListener() {
			
			@Override
			public void onValueChange(double value) {
				as.setNrSteps(value);				
			}
		});
	}
	
	@Test
	public void testSetGeometry() {
		init();
		GeometryNames testValue = GeometryNames.Rectangle;
		as.setGeometry(testValue);
		assertEquals(as.getGeometryChoice(), testValue); 
	}
	
	@Test
	public void testSetMixer() {
		init();
		RectangleMixers testValue = RectangleMixers.ExampleMixerName1;
		as.setMixer(testValue);
		assertEquals(as.getMixerChoice(), testValue);
	}	
	
	// ----Tests for nrSteps parameter ------------------------------------------
	/*
	 * Test for the initialisation of nrSteps.
	 */
	@Test
	public void testNrStepsInit(){
		init();
		
		assertEquals("After initialisation from the ApplicationState class, the value of nrSteps should be 0.0.", 
				0.0, as.getNrSteps());
	}
	
	/*
	 * Test for the getter and setter for nrSteps.
	 */
	@Test
	public void testNrStepsSet(){
		init();
		
		double value = 5.0;
		as.setNrSteps(value);		
		assertEquals("After setNrSteps, nrSteps should be " + value + ".", 
				value, as.getNrSteps());
	}
	
	/*
	 * Test for the initialisation of the numberspinner for nrSteps.
	 */
	@Test
	public void testNrStepsSpinnerInit(){
		init();
		initSpinner();
		
		assertEquals("After initialisation of the spinner, nrSteps should be "+ DEFAULT + ".", DEFAULT, 
				as.getNrSteps());
	}
	
	/*
	 * Test for the numberspinnerlistener that has been attached to the numberspinner of nrSteps.
	 * In this test, setValue is called, which in turn should call the event handler of the 
	 * attached numberspinnerlistener.
	 */
	@Test
	public void testNrStepsChanged(){
		init();
		initSpinner();
		
		double value = 5.0;
		nrStepsSpinner.setValue(value);
		assertEquals("After setValue, the value should be " + value + ".", value, as.getNrSteps());
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
