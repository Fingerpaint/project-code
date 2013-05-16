package nl.tue.fingerpaint.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class MixingProtocolTest extends GWTTestCase {

	MixingProtocol program;
	MixingStep step0;
	MixingStep step1;
	MixingStep step2;

	/*
	 * Setup method
	 */
	private void init() {
		program = new MixingProtocol();
		step0 = new MixingStep(0.5, true, false);
		step1 = new MixingStep(1.75, true, true);
		step2 = new MixingStep(1.0, false, false);
		program.addStep(step0);
		program.addStep(step1);
		program.addStep(step2);
	}

	/*
	 * Tests if the addStep function correctly appends a given step
	 */
	@Test
	public void testAddStep() {
		init();
		MixingStep newStep = new MixingStep(2.0, false, false);
		program.addStep(newStep);
		// test if the new step is appended
		assertEquals(newStep, program.getStep(3));
		// assert that the rest of the program is unchanged
		assertEquals(step0, program.getStep(0));
		assertEquals(step1, program.getStep(1));
		assertEquals(step2, program.getStep(2));
	}

	/*
	 * Tests if the removeStep function correctly removes a certain step
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

	/*
	 * Tests if the stepSize is correctly edited for the given MixingStep
	 */
	@Test
	public void testEditStepSize() {
		init();
		MixingStep edited = program.getStep(1);
		// should change the stepsize of step1 to 2.25
		program.editStep(1, 2.25, edited.movesForward(),edited.isTopWall());
		assertEquals(2.25, program.getStep(1).getStepSize());
	}

	/*
	 * Tests if the stepDirection is correctly edited for the given MixingStep
	 */
	@Test
	public void testEditStepDirection() {
		init();
		MixingStep edited = program.getStep(0);
		// should change the stepDirection of step0 equal to false
		program.editStep(0, edited.getStepSize(), false,edited.isTopWall());
		assertEquals(false, program.getStep(0).movesForward());
	}

	/*
	 * Tests if the stepWall is correctly edited for the given MixingStep
	 */
	@Test
	public void testEditStepWall() {
		init();
		MixingStep edited = program.getStep(2);
		// should change the stepWall of step2 equal to true
		program.editStep(2, edited.getStepSize(), true, edited.isTopWall());
		assertEquals(true, program.getStep(2).movesForward());
	}

	/*
	 * Tests if a step can be moved to a lower index correctly
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

	/*
	 * Tests if a step can be moved to a higher index correctly
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

	// exception tests----------------------------------------------------------

	/*
	 * Tests if the getStep throws a correct IndexOutOfBoundsException
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

	/*
	 * Tests if the addStep throws a correct NullPointerException
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

	/*
	 * Tests if the EditStep throws a correct IndexOutOfBoundsException
	 */
	@Test
	public void testEditStepException() {
		init();
		try {
			// out of bounds index, rest is bogus
			program.editStep(3, 666, false, false);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/*
	 * Tests if the MoveStep throws a correct IndexOutOfBoundsException 
	 * for an out of bounds initialIndex
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
	
	/*
	 * Tests if the MoveStep throws a correct IndexOutOfBoundsException 
	 * for an out of bounds newIndex
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

	/*
	 * Tests if the RemoveStep throws a correct IndexOutOfBoundsException
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

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
