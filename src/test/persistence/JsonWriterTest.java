package persistence;


import model.DayType;
import model.Exercise;
import model.Schedule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// CITATION: Modelled after JsonSerializationDemo
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Schedule s = new Schedule();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            // Write an empty schedule
            Schedule s = new Schedule();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySchedule.json");
            writer.open();
            writer.write(s);
            writer.close();

            // Read empty schedule
            JsonReader reader = new JsonReader("./data/testWriterEmptySchedule.json");
            s = reader.read();
            assertEquals(0, s.getExercises(DayType.MONDAY).size());
            assertEquals(0, s.getExercises(DayType.TUESDAY).size());
            assertEquals(0, s.getExercises(DayType.WEDNESDAY).size());
            assertEquals(0, s.getExercises(DayType.THURSDAY).size());
            assertEquals(0, s.getExercises(DayType.FRIDAY).size());
            assertEquals(0, s.getExercises(DayType.SATURDAY).size());
            assertEquals(0, s.getExercises(DayType.SUNDAY).size());
        } catch (IOException e) {
            fail("Exception should not have been thrown!");
        }
    }

    @Test
    void testWriterGeneralSchedule() {
        try{
            Schedule s = new Schedule();
            Exercise e1 = new Exercise("running", 0, 0, 15);
            Exercise e2 = new Exercise("plank", 0, 0, 2);
            s.addExercise(e1, DayType.MONDAY);
            s.addExercise(e2, DayType.WEDNESDAY);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSchedule.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSchedule.json");
            s = reader.read();
            assertEquals(1, s.getExercises(DayType.MONDAY).size());
            assertEquals(0, s.getExercises(DayType.TUESDAY).size());
            assertEquals(1, s.getExercises(DayType.WEDNESDAY).size());

            assertEquals("running", s.getExercises(DayType.MONDAY).get(0).getExerciseName());
            assertEquals("plank", s.getExercises(DayType.WEDNESDAY).get(0).getExerciseName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
