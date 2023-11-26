package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;


public class Schedule implements Writable {

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
            EventLog.getInstance().logEvent(new Event("Retrieving Monday exercises"));
            return this.monExercises;
        } else if (day == DayType.TUESDAY) {
            EventLog.getInstance().logEvent(new Event("Retrieving Tuesday exercises"));
            return this.tueExercises;
        } else if (day == DayType.WEDNESDAY) {
            EventLog.getInstance().logEvent(new Event("Retrieving Wednesday exercises"));
            return this.wedExercises;
        } else if (day == DayType.THURSDAY) {
            EventLog.getInstance().logEvent(new Event("Retrieving Thursday exercises"));
            return this.thuExercises;
        } else if (day == DayType.FRIDAY) {
            EventLog.getInstance().logEvent(new Event("Retrieving Friday exercises"));
            return this.friExercises;
        } else if (day == DayType.SATURDAY) {
            EventLog.getInstance().logEvent(new Event("Retrieving Saturday exercises"));
            return this.satExercises;
        } else if (day == DayType.SUNDAY) {
            EventLog.getInstance().logEvent(new Event("Retrieving Sunday exercises"));
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
            EventLog.getInstance().logEvent(new Event("Adding " + exercise.getExerciseName() + " to " + day));
            return true;
        }
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
            EventLog.getInstance().logEvent(new Event("Removing " + exercise.getExerciseName() + " from " + day));
            return true;
        }
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
        EventLog.getInstance().logEvent(new Event("Swapped exercise lists for " + day1 + " and " + day2));
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        // At this point, you should have a JSONArray that has a key-identifier of the day
        // Each JSONArray contains multiple JSONObjects that are Exercises
        jsonObject.put("monExercises", exercisesToJson(monExercises));
        jsonObject.put("tueExercises", exercisesToJson(tueExercises));
        jsonObject.put("wedExercises", exercisesToJson(wedExercises));
        jsonObject.put("thuExercises", exercisesToJson(thuExercises));
        jsonObject.put("friExercises", exercisesToJson(friExercises));
        jsonObject.put("satExercises", exercisesToJson(satExercises));
        jsonObject.put("sunExercises", exercisesToJson(sunExercises));

        return jsonObject;
    }

    public JSONArray exercisesToJson(List<Exercise> exercises) {
        JSONArray jsonArray = new JSONArray();

        // Stores entire JSONObject of Exercise into the JSONArray for given day
        for (Exercise e : exercises) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }
}




