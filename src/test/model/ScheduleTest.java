package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;


class ScheduleTest {
    Schedule testSchedule;
    Exercise e1;
    Exercise e2;
    Exercise e3;
    Exercise e4;
    Exercise e5;
    Exercise e6;

    @BeforeEach
    void runBefore() {
        testSchedule = new Schedule();

        e1 = new Exercise("running", 0, 0, 10);
        e2 = new Exercise("walking", 0, 0, 20);
        e3 = new Exercise("push ups", 3, 10, 0);
        e4 = new Exercise("sit ups", 3, 20, 0);
        e5 = new Exercise("pull ups", 3, 8, 0);
        e6 = new Exercise("jumping jacks", 5, 10, 0);

    }

    @Test
    void testScheduleConstructor() {

        assertEquals(0, testSchedule.getExercises(DayType.MONDAY).size());
        assertEquals(0, testSchedule.getExercises(DayType.TUESDAY).size());
        assertEquals(0, testSchedule.getExercises(DayType.WEDNESDAY).size());
        assertEquals(0, testSchedule.getExercises(DayType.THURSDAY).size());
        assertEquals(0, testSchedule.getExercises(DayType.FRIDAY).size());
        assertEquals(0, testSchedule.getExercises(DayType.SATURDAY).size());
        assertEquals(0, testSchedule.getExercises(DayType.SUNDAY).size());
    }

    @Test
    void testGetExercises() {
        testSchedule.addExercise(e1, DayType.MONDAY);
        testSchedule.addExercise(e2, DayType.MONDAY);
        testSchedule.addExercise(e3, DayType.MONDAY);

        List<Exercise> testList = new ArrayList<>();
        testList.add(e1);
        testList.add(e2);
        testList.add(e3);

        assertEquals(testList, testSchedule.getExercises(DayType.MONDAY));
        assertEquals(3, testSchedule.getExercises(DayType.MONDAY).size());
        assertEquals(0, testSchedule.getExercises(DayType.WEDNESDAY).size());
    }

    @Test
    void testSetExercises() {
        List<Exercise> testList = new ArrayList<>();
        testList.add(e1);
        testList.add(e2);
        testList.add(e3);
        testSchedule.setExercises(DayType.MONDAY, testList);

        assertEquals(3, testSchedule.getExercises(DayType.MONDAY).size());
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e1));
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e2));
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e3));

    }

    @Test
    void testAddExercise() {
        // MONDAY SCHEDULE
        testSchedule.addExercise(e1, DayType.MONDAY);
        testSchedule.addExercise(e3, DayType.MONDAY);
        testSchedule.addExercise(e5, DayType.MONDAY);

        //WEDNESDAY SCHEDULE
        testSchedule.addExercise(e2, DayType.WEDNESDAY);
        testSchedule.addExercise(e4, DayType.WEDNESDAY);
        testSchedule.addExercise(e6, DayType.WEDNESDAY);

        //FRIDAY SCHEDULE
        testSchedule.addExercise(e1, DayType.FRIDAY);
        testSchedule.addExercise(e2, DayType.FRIDAY);
        testSchedule.addExercise(e3, DayType.FRIDAY);
        testSchedule.addExercise(e4, DayType.FRIDAY);
        testSchedule.addExercise(e5, DayType.FRIDAY);
        testSchedule.addExercise(e6, DayType.FRIDAY);

        assertEquals(3, testSchedule.getExercises(DayType.MONDAY).size());
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e1));
        assertFalse(testSchedule.getExercises(DayType.MONDAY).contains(e2));
        assertEquals(3, testSchedule.getExercises(DayType.WEDNESDAY).size());
        assertTrue(testSchedule.getExercises(DayType.WEDNESDAY).contains(e2));
        assertFalse(testSchedule.getExercises(DayType.WEDNESDAY).contains(e3));
        assertEquals(6, testSchedule.getExercises(DayType.FRIDAY).size());
        assertTrue(testSchedule.getExercises(DayType.FRIDAY).contains(e1));
        assertTrue(testSchedule.getExercises(DayType.FRIDAY).contains(e2));
        assertTrue(testSchedule.getExercises(DayType.FRIDAY).contains(e3));
        assertTrue(testSchedule.getExercises(DayType.FRIDAY).contains(e4));
        assertTrue(testSchedule.getExercises(DayType.FRIDAY).contains(e5));
        assertTrue(testSchedule.getExercises(DayType.FRIDAY).contains(e6));
        assertEquals(0, testSchedule.getExercises(DayType.TUESDAY).size());

    }

    @Test
    void testAddDuplicate() {
        // MONDAY SCHEDULE
        testSchedule.addExercise(e1, DayType.MONDAY);
        testSchedule.addExercise(e1, DayType.MONDAY);

        // Can't add duplicate exercises for the same day
        assertEquals(1, testSchedule.getExercises(DayType.MONDAY).size());
        assertFalse(testSchedule.addExercise(e1, DayType.MONDAY));

        //SUNDAY
        testSchedule.addExercise(e1, DayType.SUNDAY);
        testSchedule.addExercise(e1, DayType.SUNDAY);
        assertEquals(1, testSchedule.getExercises(DayType.SUNDAY).size());
        assertFalse(testSchedule.addExercise(e1, DayType.SUNDAY));

    }

    @Test
    void testRemoveExercise() {
        // MONDAY SCHEDULE
        testSchedule.addExercise(e1, DayType.MONDAY);
        testSchedule.addExercise(e3, DayType.MONDAY);
        testSchedule.addExercise(e5, DayType.MONDAY);
        testSchedule.removeExercise(e1, DayType.MONDAY);
        assertFalse(testSchedule.removeExercise(e6, DayType.MONDAY));

        assertEquals(2, testSchedule.getExercises(DayType.MONDAY).size());
        assertFalse(testSchedule.getExercises(DayType.MONDAY).contains(e1));
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e3));
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e5));

    }

    @Test
    void testSwapDiffDays() {
        testSchedule.addExercise(e1, DayType.MONDAY);
        testSchedule.addExercise(e2, DayType.TUESDAY);

        testSchedule.swapExerciseDays(DayType.MONDAY, DayType.TUESDAY);

        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e2));
        assertTrue(testSchedule.getExercises(DayType.TUESDAY).contains(e1));
        assertEquals(1, testSchedule.getExercises(DayType.MONDAY).size());
        assertEquals(1, testSchedule.getExercises(DayType.TUESDAY).size());

    }

    @Test
    void testSwapDiffDaysMultipleExercises() {
        testSchedule.addExercise(e1, DayType.MONDAY);
        testSchedule.addExercise(e2, DayType.MONDAY);
        testSchedule.addExercise(e6, DayType.MONDAY);
        testSchedule.addExercise(e4, DayType.TUESDAY);
        testSchedule.addExercise(e5, DayType.TUESDAY);

        assertEquals(3, testSchedule.getExercises(DayType.MONDAY).size());
        assertEquals(2, testSchedule.getExercises(DayType.TUESDAY).size());

        testSchedule.swapExerciseDays(DayType.MONDAY, DayType.TUESDAY);

        //CHECK MONDAY
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e4));
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e5));
        assertEquals(2, testSchedule.getExercises(DayType.MONDAY).size());
        assertFalse(testSchedule.getExercises(DayType.MONDAY).contains(e1));
        assertFalse(testSchedule.getExercises(DayType.MONDAY).contains(e2));
        assertFalse(testSchedule.getExercises(DayType.MONDAY).contains(e6));

        //CHECK TUESDAY
        assertTrue(testSchedule.getExercises(DayType.TUESDAY).contains(e1));
        assertTrue(testSchedule.getExercises(DayType.TUESDAY).contains(e2));
        assertEquals(3, testSchedule.getExercises(DayType.TUESDAY).size());
        assertTrue(testSchedule.getExercises(DayType.TUESDAY).contains(e6));
        assertFalse(testSchedule.getExercises(DayType.TUESDAY).contains(e4));
        assertFalse(testSchedule.getExercises(DayType.TUESDAY).contains(e5));

    }

    @Test
    void testSwapSameDays() {
        testSchedule.addExercise(e1, DayType.MONDAY);
        testSchedule.addExercise(e2, DayType.MONDAY);
        testSchedule.addExercise(e3, DayType.MONDAY);

        assertEquals(3, testSchedule.getExercises(DayType.MONDAY).size());
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e1));
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e2));
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e3));
        assertFalse(testSchedule.getExercises(DayType.MONDAY).contains(e4));

        testSchedule.swapExerciseDays(DayType.MONDAY, DayType.MONDAY);

        //NOTHING HAPPENS, SAME TESTS
        assertEquals(3, testSchedule.getExercises(DayType.MONDAY).size());
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e1));
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e2));
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e3));
        assertFalse(testSchedule.getExercises(DayType.MONDAY).contains(e4));
    }
}

