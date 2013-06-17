package nl.tue.fingerpaint.shared.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * unit tests for the Colour utils class
 * 
 * @author Group Fingerpaint
 *
 */
public class ColourTest extends GWTTestCase {
	
	/** instance of the to be tested colour */
	Colour colour;
	
	/**
	 * test proper colour initialisation
	 */
	@Test
	public void testConstructor() {
		colour = new Colour(253, 254, 255);
		assertEquals("red should be " + 253, 253, colour.getRed());
		assertEquals("green should be " + 254, 254, colour.getGreen());
		assertEquals("blue should be " + 255, 255, colour.getBlue());
	}

	/**
	 * tests if the rgb setters set colours properly
	 */
	@Test
	public void testSetters(){
		colour = new Colour(1,1,1);
		
		//change the rgb values
		colour.setBlue(125);
		colour.setGreen(111);
		colour.setRed(42);
		
		//and test if the changes got through
		assertEquals("red should be " + 42, 42, colour.getRed());
		assertEquals("green should be " + 111, 111, colour.getGreen());
		assertEquals("blue should be " + 125, 125, colour.getBlue());
	}
	
	/**
	 * tests if rgb values are correctly translated to hexadecimal values
	 */
	@Test
	public void testHex(){
		colour = new Colour(16,0,255);
		assertEquals("the hexadecimal representation of (16, 0, 255) should be #1000ff, but is "
				+ colour.toHexString(),
				"#1000ff", colour.toHexString());
	}
	
	/**
	 * tests if the equals() method equals identical colours, but not different ones
	 */
	@Test
	public void testEquals(){
		colour = new Colour(13, 54, 63);
		Colour colour2 = new Colour(63, 13, 54);
		Colour colour3 = new Colour(13, 54, 63);
		assertTrue("Identical colours should be equal",colour.equals(colour3));
		assertFalse("Different colour should not be equal", colour.equals(colour2));
		assertFalse("null should not be equal to non-null", colour.equals(null));
		assertFalse("colours should not be equal to non-colour objects", colour.equals(this));
	}
	
	/**
	 * tests if the pad function adds zeroes correctly
	 */
	@Test
	public void testPad(){
		assertEquals("padding should pad until the correct length", "0002", Colour.pad("2", 4));
			assertEquals("the padding function should not pad strings " +
					"larger than the requested size", Colour.pad("42", 1), "42");
	}
	
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}
	
}
