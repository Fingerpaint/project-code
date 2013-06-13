package nl.tue.fingerpaint.shared.model;

import nl.tue.fingerpaint.shared.model.MixingProtocol;
import nl.tue.fingerpaint.shared.model.MixingStep;
import nl.tue.fingerpaint.shared.model.RectangleMixingStep;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT jUnit tests for the class {@link MixingProtocol}. Only tests
 * RectangleMixingSteps.
 * 
 * @author Group Fingerpaint
 */
public class MixingProtocolTest extends GWTTestCase {
	/** The mixing protocol to be tested in this test class. */
	private MixingProtocol program;

	/** First mixing step of the above mixing protocol. */
	private MixingStep step0;

	/** Second mixing step of the above mixing protocol. */
	private MixingStep step1;

	/** Third mixing step of the above mixing protocol. */
	private MixingStep step2;

	/**
	 * A test to check whether a new mixing protocol is initialised correctly.
	 * That is, the mixing protocol should be empty after initialisation.
	 */
	@Test
	public void testConstructor() {
		program = new MixingProtocol();
		assertEquals("Size of new program", 0, program.getProgramSize());
	}

	/**
	 * A test to check whether the addStep function correctly appends a given
	 * mixing step.
	 */
	@Test
	public void testAddStep() {
		init();
		MixingStep newStep = new RectangleMixingStep(2.0, false, false);
		program.addStep(newStep);
		// test if the new step is appended
		assertEquals(newStep, program.getStep(3));
		// assert that the rest of the program is unchanged
		assertEquals(step0, program.getStep(0));
		assertEquals(step1, program.getStep(1));
		assertEquals(step2, program.getStep(2));
	}

	/**
	 * A test to check whether the removeStep function correctly removes a
	 * certain mixing step.
	 */
	@Test
	public void testRemoveStep() {
		init();
		// should remove step1
		program.removeStep(1);
		// check if the total size is correct
		assertEquals(2, program.getProgramSize());
		// check if the first element is unchanged
		assertEquals(step0, program.getStep(0));
		// check if the last element is moved up correctly
		assertEquals(step2, program.getStep(1));
	}

	/**
	 * A test to check whether a mixing step can be moved to a lower index
	 * correctly.
	 */
	@Test
	public void testMoveStepForward() {
		init();
		// move step1 one down
		program.moveStep(1, 0);
		// check if the order is correct
		assertEquals(step1, program.getStep(0));
		assertEquals(step0, program.getStep(1));
		assertEquals(step2, program.getStep(2));
	}

	/**
	 * A test to check whether a mixing step can be moved to a higher index
	 * correctly.
	 */
	@Test
	public void testMoveStepBack() {
		init();
		// move step1 one up
		program.moveStep(1, 2);
		// check if the order is correct
		assertEquals(step0, program.getStep(0));
		assertEquals(step2, program.getStep(1));
		assertEquals(step1, program.getStep(2));
	}

	/**
	 * A test to check whether the {@code toString} and {@code fromString}
	 * functions work properly.
	 */
	@Test
	public void testToString() {
		init();
		MixingProtocol result = MixingProtocol.fromString(program.toString());
		assertEquals("Size of the program", program.getProgramSize(),
				result.getProgramSize());
		for (int i = 0; i < program.getProgramSize(); i++) {
			assertEquals("MixingStep " + i + " of the program", program
					.getStep(i).toString(), result.getStep(i).toString());
		}
	}

	// Exception tests----------------------------------------------
	/**
	 * A test to check whether the getStep function throws a correct
	 * IndexOutOfBoundsException, for an out of bounds retrieve index.
	 */
	@Test
	public void testGetStepException() {
		init();
		try {
			// out of bounds index
			program.getStep(3);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * A test to check whether the addStep function throws a correct
	 * NullPointerException, for a mixing step that is null.
	 */
	@Test
	public void testAddStepException() {
		init();
		try {
			program.addStep(null);
			fail("NullPointerException expected");
		} catch (NullPointerException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * A test to check whether the moveStep function throws a correct
	 * IndexOutOfBoundsException, for an out of bounds initialIndex.
	 */
	@Test
	public void testMoveStepException1() {
		init();
		try {
			// out of bounds index for the initialIndex
			program.moveStep(3, 1);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * A test to check whether the moveStep function throws a correct
	 * IndexOutOfBoundsException, for an out of bounds newIndex.
	 */
	@Test
	public void testMoveStepException2() {
		init();
		try {
			// out of bounds index for the initialIndex
			program.moveStep(1, 3);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * A test to check whether the removeStep function throws a correct
	 * IndexOutOfBoundsException, for an out of bounds retrieve index.
	 */
	@Test
	public void testRemoveStepException() {
		init();
		try {
			// out of bounds index
			program.removeStep(3);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * Returns the module name for the GWT test.
	 */
	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

	// --- PRIVATE PART --------------------------------------------------
	/*
	 * Setup method for the mixing protocol tested in this test class.
	 */
	private void init() {
		program = new MixingProtocol();
		step0 = new RectangleMixingStep(0.5, true, false);
		step1 = new RectangleMixingStep(1.75, true, true);
		step2 = new RectangleMixingStep(1.0, false, false);
		program.addStep(step0);
		program.addStep(step1);
		program.addStep(step2);
	}

}
