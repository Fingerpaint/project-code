package nl.tue.fingerpaint.client;

import nl.tue.fingerpaint.client.MixingStep;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class MixingStepTest extends GWTTestCase{
	
	MixingStep stepClass;
	
	private void init(){
		stepClass = new MixingStep();
	}
	
	@Test
	public void testGetStepSize(){
		init();
		assertEquals(stepClass.getStepSize(),0.25); //initialised on 0.25, convenient
	}
	
	@Test
	public void testSetStepSize(){
		init();
		stepClass.setStepSize(42.0);
		assertEquals(stepClass.getStepSize(),42.0);
	}

	@Test
	public void testRounding(){
		init();
		//for rounding down
		stepClass.setStepSize(1.1);
		assertEquals(stepClass.getStepSize(),1.0);
		//for rounding up
		stepClass.setStepSize(0.9);
		assertEquals(stepClass.getStepSize(),1.0);
	}
	
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
