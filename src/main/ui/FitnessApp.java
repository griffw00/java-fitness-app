package ui;

import model.*;
import java.util.Scanner;


// Fitness schedule application
public class FitnessApp {
    private Schedule schedule;
    private Scanner input;

    // EFFECTS: runs the fitness schedule application
    public FitnessApp() {
        runFitness();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runFitness() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // EFFECTS: initializes a schedule and user input objects
    private void init() {

        schedule = new Schedule();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: Displays the available schedule functions
    private void displayMenu() {
        System.out.println("\nSelect a function:");
        System.out.println("\ta -> Add exercise");
        System.out.println("\tr -> Remove exercise");
        System.out.println("\tv -> View exercises");
        System.out.println("\tm -> Modify exercise");
        System.out.println("\ts -> Swap exercise days");
        System.out.println("\tc -> Complete exercise");
        System.out.println("\tq -> Quit");
    }


    // MODIFIES: this
    // EFFECTS: processes user command for main menu
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddExercise();
        } else if (command.equals("r")) {
            doRemoveExercise();
        } else if (command.equals("v")) {
            doViewExercises();
        } else if (command.equals("m")) {
            doModifyExercise();
        } else if (command.equals("s")) {
            doSwapExerciseDays();
        } else if (command.equals("c")) {
            doCompleteExercise();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds an exercise to the given day
    private void doAddExercise() {
        displayDayMenu();
        DayType dayInput = translateStringToDay(input.nextInt());
        displayExerciseMenu();
        String exerciseType = input.next();
        if (exerciseType.equals("c")) {
            addCardioExercise(dayInput);
        } else if (exerciseType.equals("b")) {
            addBodyWeightExercise(dayInput);
        } else {
            throw new IllegalArgumentException("Invalid input. Please enter a valid exercise");
        }
    }

    // MODIFIES: this
    // EFFECTS: If cardio exercise does not already exist on given day,
    //         add it to the day
    private void addCardioExercise(DayType dayInput) {
        System.out.println("\tPlease provide exercise name");
        String name = input.next();
        System.out.println("\tPlease provide exercise duration");
        int duration = input.nextInt();
        Exercise exercise = new Exercise(name, 0, 0,duration);
        int prevSize = schedule.getExercises(dayInput).size();
        schedule.addExercise(exercise, dayInput);
        if (prevSize < schedule.getExercises(dayInput).size()) {
            System.out.println("Exercise has been added for " + dayInput);
        } else {
            System.out.println("Exercise already exists for " + dayInput);
        }
    }

    // MODIFIES: this
    // EFFECTS: If body weight exercise does not already exist on given day,
    //          add it to the given day
    private void addBodyWeightExercise(DayType dayInput) {
        System.out.println("\tPlease provide exercise name");
        String name = input.next();
        System.out.println("\tPlease provide exercise sets");
        int sets = input.nextInt();
        System.out.println("\tPlease provide exercise reps");
        int reps = input.nextInt();
        Exercise exercise = new Exercise(name, sets, reps, 0);
        int prevSize = schedule.getExercises(dayInput).size();
        schedule.addExercise(exercise, dayInput);
        if (prevSize < schedule.getExercises(dayInput).size()) {
            System.out.println("Exercise has been added for " + dayInput);
        } else {
            System.out.println("Exercise already exists for " + dayInput);
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes given exercise from the day
    private void doRemoveExercise() {
        displayDayMenu();
        DayType dayInput = translateStringToDay(input.nextInt());
        System.out.println("What is the exact name of the exercise you would like to remove?");
        String name = input.next();
        Exercise exercise = null;
        for (Exercise e: schedule.getExercises(dayInput)) {
            if (e.getExerciseName().equals(name)) {
                exercise = e;
            }
        }
        if (exercise != null) {
            schedule.removeExercise(exercise, dayInput);
            System.out.println("Exercise has been removed from " + dayInput);
        } else {
            System.out.println("Exercise does not exist in " + dayInput);
        }
    }

    // MODIFIES: this
    // EFFECTS: Views the exercise list for a given day
    private void doViewExercises() {
        displayDayMenu();
        DayType dayInput = translateStringToDay(input.nextInt());
        System.out.println("Here is the list of exercises for " + dayInput);
        for (Exercise e : schedule.getExercises(dayInput)) {
            String sets = (e.getSets() == 0) ? "N/A" : String.valueOf(e.getSets());
            String reps = (e.getReps() == 0) ? "N/A" : String.valueOf(e.getReps());
            String duration = (e.getDuration() == 0) ? "N/A" : String.valueOf(e.getDuration());
            System.out.println(e.getExerciseName() + "-> " + "sets:" + sets + " reps:" + reps + " "
                    + "duration:" + duration + " completion status:" + e.getIsCompleted());
        }
    }

    // MODIFIES: this
    // EFFECTS: modifies a specific exercise for a given day
    private void doModifyExercise() {
        displayDayMenu();
        DayType dayInput = translateStringToDay(input.nextInt());
        displayExerciseMenu();
        String exerciseType = input.next();
        if (exerciseType.equals("c")) {
            modifyCardioExercise(dayInput);
        } else if (exerciseType.equals("b")) {
            modifyBodyWeightExercise(dayInput);
        }
    }

    // MODIFIES: this
    // EFFECTS: modifies a cardio exercise on a given day
    private void modifyCardioExercise(DayType dayInput) {
        System.out.println("Please provide the exact name of the exercise");
        String name = input.next();
        System.out.println("Please provide the new duration");
        int duration = input.nextInt();
        Exercise exerciseTBD = null;
        for (Exercise e : schedule.getExercises(dayInput)) {
            if (e.getExerciseName().equals(name)) {
                exerciseTBD = e;
            }
        }
        exerciseTBD.setDuration(duration);
        System.out.println("Exercise has been modified!");
    }

    // MODIFIES: this
    // EFFECTS: Modifies a bodyweight exercise on a given day
    private void modifyBodyWeightExercise(DayType dayInput) {
        System.out.println("Please provide the exact name of the exercise");
        String name = input.next();
        System.out.println("Please provide the new sets");
        int sets = input.nextInt();
        System.out.println("Please provide the new reps");
        int reps = input.nextInt();
        Exercise exerciseTBD = null;
        for (Exercise e : schedule.getExercises(dayInput)) {
            if (e.getExerciseName().equals(name)) {
                exerciseTBD = e;
            }
        }
        exerciseTBD.setSets(sets);
        exerciseTBD.setReps(reps);
        System.out.println("Exercise has been modified!");
    }

    // MODIFIES: this
    // EFFECTS: swaps the exercise lists between two day1 and day2
    private void doSwapExerciseDays() {
        displaySwapDay1Menu();
        DayType day1Input = translateStringToDay(input.nextInt());
        displaySwapDay2Menu();
        DayType day2Input = translateStringToDay(input.nextInt());
        schedule.swapExerciseDays(day1Input, day2Input);
        System.out.println(day1Input + " exercises and " + day2Input + " exercises have been swapped");
    }

    // MODIFIES: this
    // EFFECTS: marks the designated exercise as completed
    private void doCompleteExercise() {
        displayDayMenu();
        DayType dayInput = translateStringToDay(input.nextInt());
        System.out.println("Provide the name of the exercise you have completed");
        String name = input.next();
        boolean found = false;
        for (Exercise e : schedule.getExercises(dayInput)) {
            if (e.getExerciseName().equals(name)) {
                e.setCompletionStatus(true);
                found = true;
                System.out.println("Good work!");
                break;
            }
            if (!found) {
                System.out.println("Exercise does not exist!");
            }
        }
    }

    // REQUIRES: String must an integer from 1 to 7
    // EFFECTS: Converts a string to it's corresponding day of the week
    private DayType translateStringToDay(int x) {
        if (x == 1) {
            return DayType.MONDAY;
        } else if (x == 2) {
            return DayType.TUESDAY;
        } else if (x == 3) {
            return DayType.WEDNESDAY;
        } else if (x == 4) {
            return DayType.THURSDAY;
        } else if (x == 5) {
            return DayType.FRIDAY;
        } else if (x == 6) {
            return DayType.SATURDAY;
        } else if (x == 7) {
            return DayType.SUNDAY;
        }
        throw new IllegalArgumentException("Invalid input. Please enter a number between 1 and 7.");
    }

    // EFFECTS: Displays the available days
    private void displayDayMenu() {
        System.out.println("\nSelect a day:");
        System.out.println("\t1 -> Monday");
        System.out.println("\t2 -> Tuesday");
        System.out.println("\t3 -> Wednesday");
        System.out.println("\t4 -> Thursday");
        System.out.println("\t5 -> Friday");
        System.out.println("\t6 -> Saturday");
        System.out.println("\t7 -> Sunday");
    }

    // EFFECTS: Displays the available days
    private void displaySwapDay1Menu() {
        System.out.println("\nPlease select the 1st day:");
        System.out.println("\t1 -> Monday");
        System.out.println("\t2 -> Tuesday");
        System.out.println("\t3 -> Wednesday");
        System.out.println("\t4 -> Thursday");
        System.out.println("\t5 -> Friday");
        System.out.println("\t6 -> Saturday");
        System.out.println("\t7 -> Sunday");
    }

    // EFFECTS: Displays the available days
    private void displaySwapDay2Menu() {
        System.out.println("\nPlease select the 2nd day:");
        System.out.println("\t1 -> Monday");
        System.out.println("\t2 -> Tuesday");
        System.out.println("\t3 -> Wednesday");
        System.out.println("\t4 -> Thursday");
        System.out.println("\t5 -> Friday");
        System.out.println("\t6 -> Saturday");
        System.out.println("\t7 -> Sunday");
    }

    // EFFECTS: Displays the available exercise types
    private void displayExerciseMenu() {
        System.out.println("\nCardio exercise or Bodyweight exercise?");
        System.out.println("\tc -> Cardio");
        System.out.println("\tb -> Bodyweight");
    }

}
