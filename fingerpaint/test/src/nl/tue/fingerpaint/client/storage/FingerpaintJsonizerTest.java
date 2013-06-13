package nl.tue.fingerpaint.client.storage;

import java.util.HashMap;

import nl.tue.fingerpaint.shared.model.MixingProtocol;
import nl.tue.fingerpaint.shared.model.RectangleMixingStep;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link FingerpaintJsonizer}.
 * 
 * @author Group Fingerpaint
 */
public class FingerpaintJsonizerTest extends GWTTestCase {

	private int[] intArray;
	private HashMap<String, int[]> hashmap;
	private MixingProtocol prot;
	private ResultStorage res;

	/**
	 * A test to check whether the {@code intArrayFromString} and
	 * {@code toString} functions work correctly.
	 */
	@Test
	public void testJsonizeIntArray() {
		init();
		int[] result = FingerpaintJsonizer
				.intArrayFromString(FingerpaintJsonizer.toString(intArray));
		assertEquals(intArray, result);
	}

	/**
	 * A test to check whether the {@code hashMapFromString} and
	 * {@code toString} functions work correctly.
	 */
	@Test
	public void testJsonizeHashMap() {
		init();
		HashMap<String, Object> result = FingerpaintJsonizer
				.hashMapFromString(FingerpaintJsonizer.toString(hashmap));
		for (String key : result.keySet()) {
			int[] array = FingerpaintJsonizer.toIntArray((Object[]) result.get(key));
			assertTrue("Both result and initial hashmap have key " + key,
					hashmap.containsKey(key));
			int[] expected = hashmap.get(key);
			assertEquals(expected, array);
		}
	}

	/**
	 * A test to check whether the {@code protocolFromString} and
	 * {@code toString} functions work correctly.
	 */
	@Test
	public void testJsonizeProtocol() {
		init();
		MixingProtocol result = FingerpaintJsonizer
				.protocolFromString(FingerpaintJsonizer.toString(prot));
		assertEquals("The result mixing protocol", prot.toString(),
				result.toString());
	}

	/**
	 * A test to check whether the {@code resultStorageFromString} and
	 * {@code toString} functions work correctly.
	 */
	@Test
	public void testJsonizeResultStorage() {
		init();
		ResultStorage result = FingerpaintJsonizer
				.resultStorageFromString(FingerpaintJsonizer.toString(res));
		assertEquals(res.getDistribution(), result.getDistribution());
		assertEquals("The geometry of the resultStorage", res.getGeometry(),
				result.getGeometry());
		assertEquals("The mixer of the resultStorage", res.getMixer(),
				result.getMixer());
		assertEquals("The nrsteps of the resultStorage", res.getNrSteps(),
				result.getNrSteps());
		assertEquals("The protocol of the resultStorage", res.getProtocol(),
				result.getProtocol());
		assertEquals("The dist of the resultStorage",
				FingerpaintZipper.unzip(res.getZippedDistribution()), FingerpaintZipper.unzip(result.getZippedDistribution()));
		assertEquals(res.getSegregation(), result.getSegregation());
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

	// --- PRIVATE PART --------------------------------------------------
	/**
	 * Set up method for initialising the Simulation Object.
	 */
	private void init() {
		intArray = new int[100];
		for (int i = 0; i < 100; i++) {
			intArray[i] = 2 * i;
		}
		String[] keys = { "one", "two", "three", "four" };
		hashmap = new HashMap<String, int[]>();
		for (String key : keys) {
			hashmap.put(key, intArray);
		}
		prot = new MixingProtocol();
		RectangleMixingStep step0 = new RectangleMixingStep(0.5, true, false);
		RectangleMixingStep step1 = new RectangleMixingStep(1.75, true, true);
		RectangleMixingStep step2 = new RectangleMixingStep(1.0, false, false);
		prot.addStep(step0);
		prot.addStep(step1);
		prot.addStep(step2);

		res = new ResultStorage();
		res.setDistribution(new int[96000]);
		res.setGeometry("Rectangle");
		res.setMixer("Default");
		res.setMixingProtocol(prot);
		res.setNrSteps(10);
		res.setSegregation(new double[10]);
	}

	/**
	 * Checks whether the values in two integer arrays are the same.
	 * 
	 * @param array1
	 *            The expected array
	 * @param array2
	 *            The result array
	 */
	private void assertEquals(int[] array1, int[] array2) {
		for (int i = 0; i < array2.length; i++) {
			assertEquals("Value of the array at index " + i, array1[i],
					array2[i]);
		}
	}

	/**
	 * Checks whether the values in two double arrays are the same.
	 * 
	 * @param array1
	 *            The expected array
	 * @param array2
	 *            The result array
	 */
	private void assertEquals(double[] array1, double[] array2) {
		for (int i = 0; i < array2.length; i++) {
			assertEquals("Value of the array at index " + i, array1[i],
					array2[i]);
		}
	}

}
