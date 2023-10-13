package model;

public class Exercise {

    private String exerciseName;
    private int sets;
    private int reps;
    private int duration;

    // CONSTRUCTOR
    // EFFECT: Creates an exercise with sets, reps, and duration in minutes
    //         For cardio exercises, sets and reps will be 0
    //         For bodyweight exercises, duration will be 0
    public Exercise(String exerciseName, int sets, int reps, int duration) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.duration = duration;
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

}
