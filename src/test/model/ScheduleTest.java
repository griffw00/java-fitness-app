package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.CardioExercise;
import model.BodyWeightExercise;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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

        e1 = new CardioExercise("Running", 10);
        e2 = new CardioExercise("Walking", 20);
        e3 = new BodyWeightExercise("Push-ups", 3, 10);
        e4 = new BodyWeightExercise("Sit-ups", 3, 20);
        e5 = new BodyWeightExercise("Pull-ups", 3, 8);
        e6 = new BodyWeightExercise("Jumping jacks", 5, 10);

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
        assertEquals(3, testSchedule.getExercises(DayType.WEDNESDAY).size());
        assertEquals(6, testSchedule.getExercises(DayType.FRIDAY).size());
        assertEquals(0, testSchedule.getExercises(DayType.TUESDAY).size());
        assertTrue(testSchedule.getExercises(DayType.MONDAY).contains(e1));
        assertFalse(testSchedule.getExercises(DayType.MONDAY).contains(e2));
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

        assertEquals(2, testSchedule.getExercises(DayType.MONDAY).size());
        assertFalse(testSchedule.getExercises(DayType.MONDAY).contains(e1));



    }

}