package persistence;

import model.DayType;
import model.Exercise;
import model.Schedule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// CITATION: modelled after JSONSerializationDemo

// Represents a reader that reads a Schedule from JSON data stored in a file

public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader from source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads schedule from file and returns it;
    // throws IOException if an error occurs while reading file
    public Schedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSchedule(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Schedule from JSONObject and returns it
    private Schedule parseSchedule(JSONObject jsonObject) {
        Schedule s = new Schedule();
        s.setExercises(DayType.MONDAY, jsonArrayToList(jsonObject.getJSONArray("monExercises")));
        s.setExercises(DayType.TUESDAY, jsonArrayToList(jsonObject.getJSONArray("tueExercises")));
        s.setExercises(DayType.WEDNESDAY, jsonArrayToList(jsonObject.getJSONArray("wedExercises")));
        s.setExercises(DayType.THURSDAY, jsonArrayToList(jsonObject.getJSONArray("thuExercises")));
        s.setExercises(DayType.FRIDAY, jsonArrayToList(jsonObject.getJSONArray("friExercises")));
        s.setExercises(DayType.SATURDAY, jsonArrayToList(jsonObject.getJSONArray("satExercises")));
        s.setExercises(DayType.SUNDAY, jsonArrayToList(jsonObject.getJSONArray("sunExercises")));

        return s;
    }

    // EFFECTS: returns a list of Exercises stored in JSONArray
    private List<Exercise> jsonArrayToList(JSONArray jsonArray) {
        List<Exercise> exerciseList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            // Casted Object to JSONObject via suggestion of TA
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Exercise tempExercise = parseExercise(jsonObject);
            exerciseList.add(tempExercise);
        }
        return exerciseList;
    }

    // EFFECTS: parses Exercise from JSONObject and returns it
    private Exercise parseExercise(JSONObject jsonObject) {
        String exerciseName = jsonObject.getString("exerciseName");
        int duration = jsonObject.getInt("duration");
        int sets = jsonObject.getInt("sets");
        int reps = jsonObject.getInt("reps");
        Boolean isCompleted = jsonObject.getBoolean("isCompleted");
        Exercise exercise = new Exercise(exerciseName, sets, reps, duration);
        exercise.setCompletionStatus(isCompleted);
        return exercise;
    }
}
