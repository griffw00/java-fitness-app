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

    public String getExerciseName() {
        return this.exerciseName;
    }

    public int getSets() {
        return this.sets;
    }

    public int getReps() {
        return reps;
    }

    public int getDuration() {
        return duration;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
