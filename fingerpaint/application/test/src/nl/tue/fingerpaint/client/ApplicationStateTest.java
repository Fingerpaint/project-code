package nl.tue.fingerpaint.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class ApplicationStateTest extends GWTTestCase{ 
	private ApplicationState as;
	private NumberSpinner nrStepsSpinner;
	private int DEFAULT = 1;
	
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
		String testValue = "Rectangle";
		as.setGeometryChoice(testValue);
		assertEquals(as.getGeometryChoice(), testValue); 
	}
	
	@Test
	public void testSetMixer() {
		init();
		String testValue = "Just some mixer";
		as.setMixerChoice(testValue);
		assertEquals(as.getMixerChoice(), testValue);
	}	
	
	// ----Tests for nrSteps parameter ------------------------------------------
	/*
	 * Test for the initialisation of nrSteps.
	 */
	@Test
	public void testNrStepsInit(){
		init();
		
		assertEquals("After initialisation from the ApplicationState class, the value of nrSteps should be 0.", 
				0, as.getNrSteps());
	}
	
	/*
	 * Test for the getter and setter for nrSteps.
	 */
	@Test
	public void testNrStepsSet(){
		init();
		
		int value = 5;
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
		
		int value = 5;
		nrStepsSpinner.setValue(value);
		assertEquals("After setValue, the value should be " + value + ".", value, as.getNrSteps());
	}

	
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}
	
	// Storage tests
//	@Test
//	public void testJsonize() {
//		init();
//		final String geometry = "Rectangle";
//		final String mixerChoice = "Mixer 6";
//		final int nrSteps = 37;
//		final int blackIndex = 100;
//		final int greyIndex = 38719;
//		final double greyValue = 0.32867346;
//
//		final MixingStep step1 = new MixingStep(4.25, true, false);
//		final MixingStep step2 = new MixingStep(2.75, false, true);
//		MixingProtocol protocol = new MixingProtocol();
//		protocol.addStep(step1);
//		protocol.addStep(step2);
//
//		double[] distribution = new double[96000];
//		for (int i = 0; i < distribution.length; i++) {
//			distribution[i] = 1.0;
//		}
//		distribution[blackIndex] = 0.0;
//		distribution[greyIndex] = greyValue;
//		RectangleDistribution distr = new RectangleDistribution(distribution);
//				
//		as.setGeometry(geometry);
//		as.setMixer(mixerChoice);
//		as.setProtocol(protocol);
//		as.setInitialDistribution(distr);
//		as.setNrSteps(nrSteps);
//		RectangleGeometry geom = null;
//		geom = new RectangleGeometry(240, 400);
//		as.setGegeom(geom);
//		
//		final String jsonString = as.jsonize();
//		
//		init();
//		as.setGegeom(geom);
//		as.unJsonize(jsonString);
//		
//		assertEquals("Geometry", geometry, as.getGeometryChoice());
//		assertEquals("MixerChoice", mixerChoice, as.getMixerChoice());
//		assertEquals("NrSteps", nrSteps, as.getNrSteps());
//		
//		MixingProtocol testProtocol = as.getProtocol();
//		MixingStep testStep1 = testProtocol.getStep(0);
//		MixingStep testStep2 = testProtocol.getStep(1);
//		
//		checkMixingStep(testStep1, step1.getStepSize(), step1.getDirection(), step1.getWall());
//		checkMixingStep(testStep2, step2.getStepSize(), step2.getDirection(), step2.getWall());
//		
//		double[] testDistribution = as.getInitialDistribution().getDistribution();
//		for (int i = 0; i < distribution.length; i++) {
//			double value;
//			String testCaseName;
//			if (i == blackIndex) {
//				value = 0.0;
//				testCaseName = "Black index";
//			} else if (i == greyIndex) {
//				value = greyValue;
//				testCaseName = "Grey index";
//			} else {
//				value = 1.0;
//				testCaseName = "White index";
//			}
//			assertEquals(testCaseName, value, testDistribution[i], 0.0000000000001);
//		}
//	}

	private void checkMixingStep(MixingStep step, double value,
			boolean clockwise, boolean top) {
		assertEquals("Step value", value, step.getStepSize(), 0.00000000001);
		assertEquals("Direction", clockwise, step.getDirection());
		assertEquals("Wall", top, step.getWall());
	}
	
	// TODO: Change Firefox 3.0 to Firefox Some Awesome Version
//	@Test
//	public void testStoreState() {
//		Storage storage = Storage.getLocalStorageIfSupported();
//		if (storage == null) {
//			fail("Local storage is not supported.");
//		}
//		
//		final String firstKey = "My First Key";
//		final String secondKey = "My Second Key";
//		final String firstData = "This first data is so awesome that I like it.";
//		final String secondData = "I wish I was somewhere else...";
//		
//		storage.setItem(firstKey, firstData);
//		storage.setItem(secondKey, secondData);
//		
//		storage = null;
//		
//		storage = Storage.getLocalStorageIfSupported();
//		
//		assertEquals("First item", firstData, storage.getItem(firstKey));
//		assertEquals("Second item", secondData, storage.getItem(secondKey));
//	}
	
//	@Test
//	public void testRemoveState() {
//		Storage storage = Storage.getLocalStorageIfSupported();
//		if (storage == null) {
//			fail("Local storage is not supported.");
//		}
//		
//		final String firstKey = "My First Key";
//		final String secondKey = "My Second Key";
//		final String firstData = "This first data is so awesome that I like it.";
//		final String secondData = "I wish I was somewhere else...";
//		
//		storage.setItem(firstKey, firstData);
//		storage.setItem(secondKey, secondData);
//		
//		storage = null;
//		
//		storage = Storage.getLocalStorageIfSupported();
//		
//		storage.removeItem(firstKey);
//		assertNull("First item deleted", storage.getItem(firstKey));
//		assertNotNull("Second item remains", storage.getItem(secondKey));
//		assertEquals("Second item remains correct", secondData, storage.getItem(secondKey));
//		storage.removeItem(secondKey);
//		assertNull("Second item removed", storage.getItem(secondKey));
//	}
}
