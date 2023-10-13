package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {

    Exercise cardioExercise;
    Exercise bodyWeightExercise;

    @BeforeEach
    void runBefore() {

        cardioExercise = new Exercise("Running", 0, 0, 10);
        bodyWeightExercise = new Exercise("Pushups", 3, 12, 0);

    }

    @Test
    void testExerciseConstructor() {
        assertEquals("Running", cardioExercise.getExerciseName());
        assertEquals(10, cardioExercise.getDuration());
        assertEquals(0, cardioExercise.getSets());
        assertEquals(0, cardioExercise.getReps());
        assertFalse(cardioExercise.getIsCompleted());
    }

    @Test
    void testSetReps() {
        bodyWeightExercise.setReps(10);
        assertEquals(10, bodyWeightExercise.getReps());
    }

    @Test
    void testSetSets() {
        bodyWeightExercise.setSets(4);
        assertEquals(4, bodyWeightExercise.getSets());
    }

    @Test
    void testSetDuration() {
        cardioExercise.setDuration(20);
        assertEquals(20, cardioExercise.getDuration());
    }

    @Test
    void testSetCompletionStatus() {
        cardioExercise.setCompletionStatus(true);
        assertTrue(cardioExercise.getIsCompleted());
    }

}
