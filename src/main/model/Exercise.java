package model;

import org.json.JSONObject;
import persistence.Writable;

public class Exercise implements Writable {

    private String exerciseName;
    private int sets;
    private int reps;
    private int duration;
    private boolean isCompleted;

    // CONSTRUCTOR
    // EFFECT: Creates an exercise with sets, reps, and duration in minutes
    //         For cardio exercises, sets and reps will be 0
    //         For bodyweight exercises, duration will be 0
    public Exercise(String exerciseName, int sets, int reps, int duration) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.duration = duration;
        this.isCompleted = false;
    }

    // EFFECT: returns the exercise name
    public String getExerciseName() {
        return this.exerciseName;
    }

    // EFFECT: returns the sets of the exercise
    public int getSets() {
        return this.sets;
    }

    // EFFECT: returns the reps of the exercise
    public int getReps() {
        return reps;
    }

    // EFFECT: returns the duration of the exercise
    public int getDuration() {
        return duration;
    }

    // EFFECT: returns the completion status of this exercise
    public boolean getIsCompleted() {
        return isCompleted;
    }

    // MODIFIES: this
    // EFFECT: sets the reps of the exercise
    public void setReps(int reps) {
        this.reps = reps;
    }

    // MODIFIES: this
    // EFFECT: sets the sets of the exercise
    public void setSets(int sets) {
        this.sets = sets;
    }

    // MODIFIES: this
    // EFFECTS: sets the duration of the exercise
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // MODIFIES: this
    // EFFECTS: sets the completion status of the exercise
    public void setCompletionStatus(boolean status) {
        this.isCompleted = status;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("exerciseName", exerciseName);
        json.put("duration", duration);
        json.put("sets", sets);
        json.put("reps", reps);
        return json;
    }
}
