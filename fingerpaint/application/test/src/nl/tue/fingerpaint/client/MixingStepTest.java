package nl.tue.fingerpaint.client;

import nl.tue.fingerpaint.client.MixingStep;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class MixingStepTest extends GWTTestCase{
	
	MixingStep stepClass;
	
	/*
	 * Setup method
	 */
	private void init(){
		stepClass = new MixingStep(1, true, true);
	}
	
	/*
	 * Tests if the getter returns the stepsize it was initialised with
	 */
	@Test
	public void testGetStepSize(){
		init();
		assertEquals(1.0, stepClass.getStepSize()); //initialised on 1.0, convenient
	}
	
	/*
	 * Tests if the method setStepSize sets the stepsize correctly
	 */
	@Test
	public void testSetStepSize(){
		init();
		stepClass.setStepSize(42.0);
		assertEquals(42.0, stepClass.getStepSize());
	}

	/*
	 * Tests if the implemented rounding function rounds up and down correctly to produce a stepSize that is a multiple of
	 * {@code MixingStep.tepUnit}
	 */
	@Test
	public void testRounding(){
		init();
		//for rounding down
		stepClass.setStepSize(1.1);
		assertEquals(1.0, stepClass.getStepSize());
		//for rounding up
		stepClass.setStepSize(0.9);
		assertEquals(1.0, stepClass.getStepSize());
	}
	
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
