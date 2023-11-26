package ui;

import model.DayType;
import model.Event;
import model.EventLog;
import model.Exercise;
import model.Schedule;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

// CITATIONS: SimpleTableDemo.Java

public class FitnessAppGUI extends JFrame implements ActionListener {

    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private JButton addBodyWeightBtn;
    private JButton removeBtn;
    private JButton addCardioBtn;
    private JButton saveBtn;
    private JButton loadBtn;
    private JButton modifyBtn;
    private JButton swapBtn;

    private Schedule schedule;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String JSON_STORE = "./data/workroom.json";
    private static final String[] COLUMN_NAMES = {"Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday", "Sunday"};
    private static final String EXERCISE_NAME_MESSAGE = "Name of Exercise";
    private static final String DURATION_MESSAGE = "Duration of exercise";
    private static final String SETS_MESSAGE = "Sets?";
    private static final String REPS_MESSAGE = "Reps?";
    private static final String DAY_MESSAGE = "Which Day?";
    private static final String REMOVE_EXERCISE_MESSAGE = "Name of Exercise to Remove:";
    private static final String ON_WHICH_DAY_MESSAGE = "On which day?";
    private static final String EXERCISE_TYPE_MESSAGE = "Cardio or Bodyweight?";

    // EFFECTS: Constructs the FitnessAppGUI
    public FitnessAppGUI() {
        initialize();
        initializeUI();
        addComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: initializes the necessary objects
    private void initialize() {
        scheduleTable = new JTable();
        schedule = new Schedule();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        // Create an abstract WindowAdapter, so it is not required
        // to implement all methods in WindowListener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog();
            }
        });
    }



    // EFFECTS: initializes the UI
    private void initializeUI() {
        setTitle("Java Exercise Scheduler");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new BorderLayout());
        createButtons();
        //updateTable();
    }

    // EFFECTS: Creates the corresponding buttons for the UI
    private void createButtons() {
        addBodyWeightBtn = createButton("Add Body Weight Exercise", "addBodyWeightButton");
        addCardioBtn = createButton("Add Cardio Exercise", "addCardioButton");
        removeBtn = createButton("Remove Exercise", "removeButton");
        saveBtn = createButton("Save", "saveButton");
        loadBtn = createButton("Load", "loadButton");
        modifyBtn = createButton("Modify Exercise", "modifyButton");
        swapBtn = createButton("Swap", "swapButton");
    }

    // EFFECTS: Creates a button
    private JButton createButton(String label, String actionCommand) {
        JButton button = new JButton(label);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        return button;
    }

    // EFFECTS: Adds components to JFrame
    private void addComponents() {
        add(createButtonPanel(), BorderLayout.SOUTH);
        add(createScrollPane(), BorderLayout.CENTER);
    }

    // EFFECTS: Creates the button panel for the UI
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBodyWeightBtn);
        buttonPanel.add(addCardioBtn);
        buttonPanel.add(modifyBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(swapBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(loadBtn);
        return buttonPanel;
    }

    // EFFECTS: Creates the scroll pane
    private JScrollPane createScrollPane() {
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setPreferredSize(new Dimension(1000, 400));
        return scrollPane;
    }

    // EFFECTS: Updates the table with the new Schedule object
    private void updateTable() {
        tableModel = new DefaultTableModel(null, COLUMN_NAMES);

        for (DayType day : DayType.values()) {
            List<Exercise> exerciseList = schedule.getExercises(day);

            if (exerciseList != null && !exerciseList.isEmpty()) {
                for (Exercise exercise : exerciseList) {
                    Vector<Object> row = new Vector<>();
                    insertEmptyCells(day, row);

                    StringBuilder exercisesString = new StringBuilder();
                    exercisesString.append(exercise.getExerciseName()).append("\n");

                    if (exercise.getDuration() > 0) {
                        exercisesString.append(exercise.getDuration()).append(" minutes\n");
                    } else {
                        exercisesString.append(exercise.getSets()).append("x").append(exercise.getReps()).append("\n");
                    }

                    row.add(exercisesString.toString());
                    tableModel.addRow(row);
                }
            }
        }

        scheduleTable.setModel(tableModel);
        clean();
    }

    // EFFECTS: Replaces empty cells in a row with ""
    private static void insertEmptyCells(DayType day, Vector<Object> row) {
        for (DayType beforeDay : DayType.values()) {
            if (beforeDay.ordinal() < day.ordinal()) {
                row.add("");
            }
        }
    }

    // EFFECTS: Cleans up the table to remove gaps
    public void clean() {
        for (int j = 0; j < tableModel.getColumnCount(); j++) {
            int rowIndex = 0;  // Initialize rowIndex to the top of the column

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Object value = tableModel.getValueAt(i, j);

                if (value != null && !value.equals("")) {
                    // If the cell is non-empty, move its value to the current rowIndex
                    tableModel.setValueAt(value, rowIndex, j);

                    // If the rowIndex is less than the current row index, clear the cell in the current row
                    if (rowIndex < i) {
                        tableModel.setValueAt("", i, j);
                    }

                    // Move to the next row in the column
                    rowIndex++;
                }
            }
        }
    }

    // EFFECTS: Action listener for button events
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "addCardioButton":
                handleCardio();
                break;
            case "addBodyWeightButton":
                handleBodyWeight();
                break;
            case "removeButton":
                handleRemove();
                break;
            case "modifyButton":
                handleModify();
                break;
            case "swapButton":
                handleSwap();
                break;
            case "saveButton":
                handleSave();
                break;
            case "loadButton":
                handleLoad();
                break;
        }
    }

    // EFFECTS: Handles cardio button event
    private void handleCardio() {
        ExerciseInputDialog dialog = new ExerciseInputDialog(this, true);
        if (dialog.showDialog()) {
            DayType day = convertToDayType(JOptionPane.showInputDialog(DAY_MESSAGE));
            Exercise exercise = new Exercise(dialog.getExerciseName(), 0, 0, dialog.getDuration());
            schedule.addExercise(exercise, day);
            updateTable();
        }
    }

    // EFFECTS: Handles body weight button event
    private void handleBodyWeight() {
        ExerciseInputDialog dialog = new ExerciseInputDialog(this, false);
        if (dialog.showDialog()) {
            DayType day = convertToDayType(JOptionPane.showInputDialog(DAY_MESSAGE));
            Exercise exercise = new Exercise(dialog.getExerciseName(), dialog.getSets(), dialog.getReps(), 0);
            schedule.addExercise(exercise, day);
            updateTable();
        }
    }


    // EFFECTS: Handles the modify button event
    private void handleModify() {
        JOptionPane modifyOptions = new JOptionPane();
        String exerciseType = modifyOptions.showInputDialog(EXERCISE_TYPE_MESSAGE);
        if (exerciseType.equalsIgnoreCase("cardio")) {
            modifyCardio();
        } else if (exerciseType.equalsIgnoreCase("bodyweight")) {
            modifyBodyWeight();
        }
    }

    // EFFECTS: Modifies a cardio exercise
    private void modifyCardio() {
        JOptionPane cardioOptions = new JOptionPane();
        DayType day = convertToDayType(cardioOptions.showInputDialog(DAY_MESSAGE));
        String exerciseName = cardioOptions.showInputDialog(EXERCISE_NAME_MESSAGE);
        int duration = Integer.parseInt(cardioOptions.showInputDialog(DURATION_MESSAGE));
        Exercise exerciseTBD = findExercise(exerciseName, day);
        exerciseTBD.setDuration(duration);
        updateTable();
    }

    // EFFECTS: Modifies a bodyweight exercise
    private void modifyBodyWeight() {
        JOptionPane bodyWeightOptions = new JOptionPane();
        DayType day = convertToDayType(bodyWeightOptions.showInputDialog(DAY_MESSAGE));
        String exerciseName = bodyWeightOptions.showInputDialog(EXERCISE_NAME_MESSAGE);
        int sets = Integer.parseInt(bodyWeightOptions.showInputDialog(SETS_MESSAGE));
        int reps = Integer.parseInt(bodyWeightOptions.showInputDialog(REPS_MESSAGE));
        Exercise exerciseTBD = findExercise(exerciseName, day);
        exerciseTBD.setSets(sets);
        exerciseTBD.setReps(reps);
        updateTable();
    }

    // EFFECTS: Swaps the exercise lists between two given days
    private void handleSwap() {
        JOptionPane swapOptions = new JOptionPane();
        DayType day1 = convertToDayType(swapOptions.showInputDialog("First day to be swapped?"));
        DayType day2 = convertToDayType(swapOptions.showInputDialog("Second day to be swapped?"));
        schedule.swapExerciseDays(day1, day2);
        updateTable();
    }

    // EFFECTS: Handles remove button event
    private void handleRemove() {
        JOptionPane removeOptions = new JOptionPane();
        String exerciseName = removeOptions.showInputDialog(REMOVE_EXERCISE_MESSAGE);
        DayType day = convertToDayType(removeOptions.showInputDialog(ON_WHICH_DAY_MESSAGE));

        Exercise exerciseToRemove = findExercise(exerciseName, day);

        if (exerciseToRemove != null) {
            schedule.removeExercise(exerciseToRemove, day);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Exercise not found on the specified day.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // REQUIRES: String must be a real day of the week with no spelling errors
    // EFFECTS: converts the given string into a DayType
    public DayType convertToDayType(String day) {
        String lowerCaseDay = day.toLowerCase();
        //System.out.println(lowerCaseDay);

        switch (lowerCaseDay) {
            case "monday":
                return DayType.MONDAY;
            case "tuesday":
                return DayType.TUESDAY;
            case "wednesday":
                // System.out.println("Wednesday Returned!");
                return DayType.WEDNESDAY;
            case "thursday":
                // System.out.println("Thursday Returned!");
                return DayType.THURSDAY;
            case "friday":
                return DayType.FRIDAY;
            case "saturday":
                return DayType.SATURDAY;
            default:
                return DayType.SUNDAY;
        }
    }


    // EFFECTS: Returns the exercise, if found, for the given day
    private Exercise findExercise(String exerciseName, DayType day) {
        List<Exercise> exerciseList = schedule.getExercises(day);
        if (exerciseList != null) {
            for (Exercise exercise : exerciseList) {
                if (exercise.getExerciseName().equalsIgnoreCase(exerciseName)) {
                    return exercise;
                }
            }
        }
        return null;
    }

    // EFFECTS: Handles save button event
    private void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(schedule);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "File saved");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // EFFECTS: Handles load button event
    private void handleLoad() {
        try {
            schedule = jsonReader.read();
            updateTable();
            JOptionPane.showMessageDialog(this, "File loaded");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: Prints the eventLog to the console
    private void printLog() {
        EventLog el = EventLog.getInstance();
        System.out.println("Event Log:");
        for (Event e : el) {
            System.out.println(e);
        }
    }
}
