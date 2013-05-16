package nl.tue.fingerpaint.client;

import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

public class ApplicationStateTest extends GWTTestCase{ 
	ApplicationState uc;
	
	
	private void init(){
		uc = new ApplicationState();	
	}
	
	@Test
	public void testSetGeometry() {
		init();
		GeometryNames testValue = GeometryNames.Rectangle;
		uc.setGeometry(testValue);
		assertEquals(uc.getGeometryChoice(), testValue); 
	}
	
	@Test
	public void testSetMixer() {
		init();
		RectangleMixers testValue = RectangleMixers.ExampleMixerName1;
		uc.setMixer(testValue);
		assertEquals(uc.getMixerChoice(), testValue);
	}	

	@Override
	public String getModuleName() {
		//return "nl.tue.fingerpaint.Fingerpaint";
		return "selectMixingGeometry.SelectMixingGeometry";
	}

}
