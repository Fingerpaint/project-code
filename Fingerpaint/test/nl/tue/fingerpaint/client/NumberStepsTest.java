package nl.tue.fingerpaint.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class NumberStepsTest extends GWTTestCase{

	NumberSteps nrStepsClass;
	
	private void init()
	{
		nrStepsClass = new NumberSteps();		
	}
	
	@Test
	public void testInitNrSteps() 
	{
		init();
		// After initializing, the value of nrSteps should be 1.
		assertEquals(nrStepsClass.getNrSteps(), 1);
	}
	
	@Test
	public void testSetNrSteps() 
	{
		init();
		int value = 5;
		
		// Setting nrSteps to 'value' should result in the actual value being 'value' when
		// retrieved with getNrSteps().
		nrStepsClass.setNrSteps(value);
		assertEquals(nrStepsClass.getNrSteps(), value);
	}
	
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
