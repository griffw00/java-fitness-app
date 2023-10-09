package model;

public class CardioExercise implements Exercise {

    private String exerciseName;
    private int duration;

    // CONSTRUCTOR
    // EFFECTS: Creates a cardio exercise with a name and duration in minutes
    // For example: 10 minutes of Running
    public CardioExercise(String exerciseName, int duration) {
        this.exerciseName = exerciseName;
        this.duration = duration;
    }

    public String getExerciseName() {
        return this.exerciseName;
    }

    public int getDuration() {

        return this.duration;
    }
}
