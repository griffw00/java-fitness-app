package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseTest {

    Exercise exercise;

    @BeforeEach
    void runBefore() {

        exercise = new Exercise("Running", 0, 0, 10);

    }

    @Test
    void testExerciseConstructor() {
        assertEquals("Running", exercise.getExerciseName());
        assertEquals(10, exercise.getDuration());
        assertEquals(0, exercise.getSets());
        assertEquals(0, exercise.getReps());
    }

}
