package nl.tue.fingerpaint.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class MixingProgramTest extends GWTTestCase {

	MixingProgram programClass;
	MixingStep step0;
	MixingStep step1;
	MixingStep step2;

	/*
	 * Setup method
	 */
	private void init() {
		programClass = new MixingProgram();
		step0 = new MixingStep(0.5, true, false);
		step1 = new MixingStep(1.75, true, true);
		step2 = new MixingStep(1.0, false, false);
	}

	/*
	 * Tests if the addStep function correctly appends a given step
	 */
	@Test
	public void testAddStep() {
		init();
		MixingStep newStep = new MixingStep(2.0, false, false);
		programClass.addStep(newStep);
		// test if the new step is appended
		assertEquals(newStep, programClass.getStep(3));
		// assert that the rest of the program is unchanged
		assertEquals(step0, programClass.getStep(0));
		assertEquals(step1, programClass.getStep(1));
		assertEquals(step2, programClass.getStep(2));
	}

	/*
	 * Tests if the removeStep function correctly removes a certain step
	 */
	@Test
	public void testRemoveStep() {
		init();
		// should remove step1
		programClass.removeStep(1);
		// check if the total size is correct
		assertEquals(2, programClass.getProgramSize());
		// check if the first element is unchanged
		assertEquals(step0, programClass.getStep(0));
		// check if the last element is moved up correctly
		assertEquals(step2, programClass.getStep(1));
	}

	/*
	 * Tests if the stepSize is correctly edited for the given MixingStep
	 */
	@Test
	public void testEditStepSize() {
		init();
		MixingStep edited = programClass.getStep(1);
		// should change the stepsize of step1 to 2.25
		programClass.editStep(1, 2.25, edited.movesForward(),edited.isTopWall());
		assertEquals(2.25, programClass.getStep(1).getStepSize());
	}

	/*
	 * Tests if the stepDirection is correctly edited for the given MixingStep
	 */
	@Test
	public void testEditStepDirection() {
		init();
		MixingStep edited = programClass.getStep(0);
		// should change the stepDirection of step0 equal to false
		programClass.editStep(0, edited.getStepSize(), false,edited.isTopWall());
		assertEquals(false, programClass.getStep(0).movesForward());
	}

	/*
	 * Tests if the stepWall is correctly edited for the given MixingStep
	 */
	@Test
	public void testEditStepWall() {
		init();
		MixingStep edited = programClass.getStep(2);
		// should change the stepWall of step2 equal to true
		programClass.editStep(2, edited.getStepSize(), true, edited.isTopWall());
		assertEquals(true, programClass.getStep(2).movesForward());
	}

	/*
	 * Tests if a step can be moved to a lower index correctly
	 */
	@Test
	public void testMoveStepForward() {
		init();
		// move step1 one down
		programClass.moveStep(1, 0);
		// check if the order is correct
		assertEquals(step1, programClass.getStep(0));
		assertEquals(step0, programClass.getStep(1));
		assertEquals(step2, programClass.getStep(2));
	}

	/*
	 * Tests if a step can be moved to a higher index correctly
	 */
	@Test
	public void testMoveStepBackward() {
		init();
		// move step1 one up
		programClass.moveStep(1, 2);
		// check if the order is correct
		assertEquals(step0, programClass.getStep(0));
		assertEquals(step2, programClass.getStep(1));
		assertEquals(step1, programClass.getStep(2));
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
			programClass.getStep(3);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
		fail("IndexOutOfBoundsException expected");
	}

	/*
	 * Tests if the addStep throws a correct NullPointerException
	 */
	@Test
	public void testAddStepException() {
		init();
		try {
			programClass.addStep(null);
		} catch (NullPointerException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
		fail("NullPointerException expected");
	}

	/*
	 * Tests if the EditStep throws a correct IndexOutOfBoundsException
	 */
	@Test
	public void testEditStepException() {
		init();
		try {
			// out of bounds index, rest is bogus
			programClass.editStep(3, 666, false, false);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
		fail("IndexOutOfBoundsException expected");
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
			programClass.moveStep(3, 1);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
		fail("IndexOutOfBoundsException expected");
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
			programClass.moveStep(1, 3);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
		fail("IndexOutOfBoundsException expected");
	}

	/*
	 * Tests if the RemoveStep throws a correct IndexOutOfBoundsException
	 */
	@Test
	public void testRemoveStepException() {
		init();
		try {
			// out of bounds index
			programClass.getStep(3);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail(e.toString());
		}
		fail("IndexOutOfBoundsException expected");
	}

	@Override
	public String getModuleName() {
		return "nl.tue.fingerpaint.Fingerpaint";
	}

}
