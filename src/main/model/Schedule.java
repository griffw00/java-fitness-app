package model;

import java.util.ArrayList;
import java.util.List;


public class Schedule {

    private List<Exercise> monExercises;
    private List<Exercise> tueExercises;
    private List<Exercise> wedExercises;
    private List<Exercise> thuExercises;
    private List<Exercise> friExercises;
    private List<Exercise> satExercises;
    private List<Exercise> sunExercises;

    public Schedule() {
        this.monExercises = new ArrayList<>();
        this.tueExercises = new ArrayList<>();
        this.wedExercises = new ArrayList<>();
        this.thuExercises = new ArrayList<>();
        this.friExercises = new ArrayList<>();
        this.satExercises = new ArrayList<>();
        this.sunExercises = new ArrayList<>();
    }

    //EFFECTS: returns the exercise list for any given day of the week
    public List<Exercise> getExercises(DayType day) {
        if (day == DayType.MONDAY) {
            return this.monExercises;
        } else if (day == DayType.TUESDAY) {
            return this.tueExercises;
        } else if (day == DayType.WEDNESDAY) {
            return this.wedExercises;
        } else if (day == DayType.THURSDAY) {
            return this.thuExercises;
        } else if (day == DayType.FRIDAY) {
            return this.friExercises;
        } else if (day == DayType.SATURDAY) {
            return this.satExercises;
        } else if (day == DayType.SUNDAY) {
            return this.sunExercises;
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: If exercise does not already exist on given day, add exercise and return true
    public Boolean addExercise(Exercise exercise, DayType day) {
        List<Exercise> exerciseList = getExercises(day);
        List<String> exerciseNames = new ArrayList<>();
        for (Exercise e : exerciseList) {
            exerciseNames.add(e.getExerciseName());
        }
        if (!exerciseNames.contains(exercise.getExerciseName())) {
            exerciseList.add(exercise);
            System.out.println("Exercise has been added to " + day + "!");
            return true;
        }
        System.out.println("Exercise already added added for " + day + "!");
        return false;
    }

    //MODIFIES:
    //EFFECTS: Remove the exercise from given day
    public Boolean removeExercise(Exercise exercise, DayType day) {
        List<Exercise> exerciseList = getExercises(day);
        List<String> exerciseNames = new ArrayList<>();
        for (Exercise e : exerciseList) {
            exerciseNames.add(e.getExerciseName());
        }
        if (exerciseNames.contains(exercise.getExerciseName())) {
            exerciseList.remove(exercise);
            System.out.println("Exercise has been removed from " + day);
            return true;
        }
        System.out.println("Exercise does not exist in " + day);
        return false;
    }

    //MODIFIES: this
    //EFFECTS: Sets the exercise of the given day to the given list
    public void setExercises(DayType day, List<Exercise> list) {
        if (day == DayType.MONDAY) {
            monExercises = list;
        } else if (day == DayType.TUESDAY) {
            tueExercises = list;
        } else if (day == DayType.WEDNESDAY) {
            wedExercises = list;
        } else if (day == DayType.THURSDAY) {
            thuExercises = list;
        } else if (day == DayType.FRIDAY) {
            friExercises = list;
        } else if (day == DayType.SATURDAY) {
            satExercises = list;
        } else if (day == DayType.SUNDAY) {
            sunExercises = list;
        }
    }

    //MODIFIES: this
    //EFFECTS: Swaps the exercise list between two days
    public void swapExerciseDays(DayType day1, DayType day2) {
        List<Exercise> tempDay1 = getExercises(day1);
        List<Exercise> tempDay2 = getExercises(day2);
        setExercises(day1, tempDay2);
        setExercises(day2, tempDay1);
        System.out.println(day1 + " exercises and " + day2 + " exercises have been swapped");
    }
}




