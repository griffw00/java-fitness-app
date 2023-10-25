package persistence;

// CITATION: Modelled after JsonSerializationDemo

import model.DayType;
import org.junit.jupiter.api.Test;
import model.Schedule;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Schedule s = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySchedule.json");
        try {
            Schedule s = reader.read();
            assertEquals(0, s.getExercises(DayType.MONDAY).size());
            assertEquals(0, s.getExercises(DayType.TUESDAY).size());
            assertEquals(0, s.getExercises(DayType.WEDNESDAY).size());
            assertEquals(0, s.getExercises(DayType.THURSDAY).size());
            assertEquals(0, s.getExercises(DayType.FRIDAY).size());
            assertEquals(0, s.getExercises(DayType.SATURDAY).size());
            assertEquals(0, s.getExercises(DayType.SUNDAY).size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSchedule() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSchedule.json");
        try {
            Schedule s = reader.read();
            assertEquals(2, s.getExercises(DayType.MONDAY).size());
            assertEquals(0, s.getExercises(DayType.TUESDAY).size());
            assertEquals(0, s.getExercises(DayType.WEDNESDAY).size());
            assertEquals(0, s.getExercises(DayType.THURSDAY).size());
            assertEquals(1, s.getExercises(DayType.FRIDAY).size());
            assertEquals(0, s.getExercises(DayType.SATURDAY).size());
            assertEquals(0, s.getExercises(DayType.SUNDAY).size());

            assertEquals("swimming", s.getExercises(DayType.MONDAY).get(0).getExerciseName());
            assertEquals("pushups", s.getExercises(DayType.MONDAY).get(1).getExerciseName());
            assertEquals("running", s.getExercises(DayType.FRIDAY).get(0).getExerciseName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
