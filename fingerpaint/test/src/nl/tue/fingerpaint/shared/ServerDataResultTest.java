package nl.tue.fingerpaint.shared;

import org.testng.annotations.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * Tests if the getters and setters of ServerDataResult function properly
 * 
 * @author Group Fingerpaint
 */
public class ServerDataResultTest extends GWTTestCase {

	ServerDataResult data;
	
	/**
	 * Tests if the constructors initialise properly
	 */
	@Test
	public void testConstructor() {
		//test the empty constructor
		data = new ServerDataResult();
		assertTrue("the empty constructor should create empty lists", assertArrayEquals(new String[]{}, data.getGeometries()));
		assertTrue("the empty constructor should create empty lists", assertArrayEquals(new String[]{}, data.getMixers()));
		assertTrue("the empty constructor should create empty lists", assertArrayEquals(new int[]{}, data.getMixerGeometryMapping()));
		
		//test the parameterised constructor
		String[] geos = new String[]{"har-har-har"};
		String[] mixers = new String[]{"kekeke"};
		int[] map = new int[]{42};
		data = new ServerDataResult(geos, mixers, map);
		assertTrue("the parameterised constructor should store the data", assertArrayEquals(geos, data.getGeometries()));
		assertTrue("the parameterised constructor should store the data", assertArrayEquals(mixers, data.getMixers()));
		assertTrue("the parameterised constructor should store the data", assertArrayEquals(map, data.getMixerGeometryMapping()));
	}
	
	private <C> boolean assertArrayEquals(C[] a, C[] b) {
		if (a.length != b.length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (!a[i].equals(b[i])) {
				return false;
			}
		}
		return true;
	}
	
	private boolean assertArrayEquals(int[] a, int[] b) {
		if (a.length != b.length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	
	/**
	 * Tests if the setters set the variables
	 */
	@Test
	public void testSetters(){
		
		data = new ServerDataResult();
		String[] geos = new String[]{"har-har-har"};
		String[] mixers = new String[]{"kekeke"};
		int[] map = new int[]{42};
		//set values
		data.setGeometries(geos);
		data.setMixerGeometryMapping(map);
		data.setMixers(mixers);
		
		assertTrue("the parameterised constructor should store the data", assertArrayEquals(geos, data.getGeometries()));
		assertTrue("the parameterised constructor should store the data", assertArrayEquals(mixers, data.getMixers()));
		assertTrue("the parameterised constructor should store the data", assertArrayEquals(map, data.getMixerGeometryMapping()));
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}
}
