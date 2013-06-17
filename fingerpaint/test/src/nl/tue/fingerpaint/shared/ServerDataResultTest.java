package nl.tue.fingerpaint.shared;

import static org.junit.Assert.*;

import org.junit.Test;

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
		assertArrayEquals("the empty constructor should create empty lists", new String[]{}, data.getGeometries());
		assertArrayEquals("the empty constructor should create empty lists", new String[]{}, data.getMixers());
		assertArrayEquals("the empty constructor should create empty lists", new int[]{}, data.getMixerGeometryMapping());
		
		//test the parameterised constructor
		String[] geos = new String[]{"har-har-har"};
		String[] mixers = new String[]{"kekeke"};
		int[] map = new int[]{42};
		data = new ServerDataResult(geos, mixers, map);
		assertArrayEquals("the parameterised constructor should store the data", geos, data.getGeometries());
		assertArrayEquals("the parameterised constructor should store the data", mixers, data.getMixers());
		assertArrayEquals("the parameterised constructor should store the data", map, data.getMixerGeometryMapping());
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
		
		assertArrayEquals("the parameterised constructor should store the data", geos, data.getGeometries());
		assertArrayEquals("the parameterised constructor should store the data", mixers, data.getMixers());
		assertArrayEquals("the parameterised constructor should store the data", map, data.getMixerGeometryMapping());
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}
}
