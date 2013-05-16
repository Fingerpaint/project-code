package nl.tue.fingerpaint.client;

import nl.tue.fingerpaint.client.MixingStep;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class MixingStepTest extends GWTTestCase{
	
	MixingStep stepClass;
	
	private void init(){
		stepClass = new MixingStep(1, true, true);
	}
	
	@Test
	public void testGetStepSize(){
		init();
		assertEquals(0.25, stepClass.getStepSize()); //initialised on 0.25, convenient
	}
	
	@Test
	public void testSetStepSize(){
		init();
		stepClass.setStepSize(42.0);
		assertEquals(42.0, stepClass.getStepSize());
	}

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
