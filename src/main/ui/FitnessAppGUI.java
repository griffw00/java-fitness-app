package ui;

import model.DayType;
import model.Exercise;
import model.Schedule;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

// import static jdk.javadoc.internal.doclets.toolkit.util.Utils.toLowerCase;

// CITATIONS: SimpleTableDemo.Java

public class FitnessAppGUI extends JFrame implements ActionListener {

    //SWING Objects
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private JButton addBodyWeightBtn;
    private JButton removeBtn;
    private JButton addCardioBtn;
    private JButton saveBtn;
    private JButton loadBtn;
    private JButton cleanBtn;

    //Other Objects
    private Schedule schedule;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/workroom.json";

    public FitnessAppGUI() {
        initialize();
        initializeUI();
        // Add buttons to a panel
        JPanel buttonPanel = getjPanel();
        // Add components to the JFrame
        add(buttonPanel, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setPreferredSize(new Dimension(1100, 600));
        add(scrollPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private JPanel getjPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBodyWeightBtn);
        buttonPanel.add(addCardioBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(loadBtn);
        buttonPanel.add(cleanBtn);
        return buttonPanel;
    }

    // EFFECTS: initializes all the necessary objects for this class
    public void initialize() {
        scheduleTable = new JTable();
        schedule =  new Schedule();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    public void initializeUI() {
        setTitle("Java Exercise Scheduler");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new BorderLayout());  // Change to BorderLayout for better control
        createButtons();
        updateTable();
    }

    public void updateTable() {
        String[] columnNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);

        // Iterate over the columns (DayType values)
        for (DayType day : DayType.values()) {
            List<Exercise> exerciseList = schedule.getExercises(day);

            if (exerciseList != null && !exerciseList.isEmpty()) {
                // Create a row for each exercise
                for (Exercise exercise : exerciseList) {
                    Vector<Object> row = new Vector<>();

                    // Add empty cells for days before the exercise's day
                    for (DayType beforeDay : DayType.values()) {
                        if (beforeDay.ordinal() < day.ordinal()) {
                            row.add("");
                        }
                    }

                    // Add the exercise data to the correct column
                    StringBuilder exercisesString = new StringBuilder();
                    exercisesString.append(exercise.getExerciseName()).append("\n");

                    // Check if it's a cardio exercise
                    if (exercise.getDuration() > 0) {
                        exercisesString.append(exercise.getDuration()).append(" minutes\n");
                    } else {
                        // It's a bodyweight exercise
                        exercisesString.append(exercise.getSets()).append("x").append(exercise.getReps()).append("\n");
                    }

                    row.add(exercisesString.toString());

                    // Add the row to the table model
                    tableModel.addRow(row);
                }
            }
        }

        // Set the updated table model
        scheduleTable.setModel(tableModel);
    }


    // EFFECTS: creates the required buttons
    public void createButtons() {
        addBodyWeightBtn = new JButton("Add Body Weight Exercise");
        addBodyWeightBtn.setActionCommand("addBodyWeightButton");
        addBodyWeightBtn.addActionListener(this);
        addCardioBtn = new JButton("Add Cardio Exercise");
        addCardioBtn.setActionCommand("addCardioButton");
        addCardioBtn.addActionListener(this);
        removeBtn = new JButton("Remove Exercise");
        removeBtn.setActionCommand("removeButton");
        removeBtn.addActionListener(this);
        saveBtn = new JButton("Save");
        saveBtn.setActionCommand("saveButton");
        saveBtn.addActionListener(this);
        loadBtn = new JButton("Load");
        loadBtn.setActionCommand("loadButton");
        loadBtn.addActionListener(this);
        cleanBtn = new JButton("Clean");
        loadBtn.setActionCommand("cleanButton");
        loadBtn.addActionListener(this);
    }

    // EFFECTS: Handles button events
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("addCardioButton" == e.getActionCommand()) {
            handleCardio();
        } else if ("addBodyWeightButton" == e.getActionCommand()) {
            handleBodyWeight();
        } else if ("removeButton" == e.getActionCommand()) {
            handleRemove();
        } else if ("saveButton" == e.getActionCommand()) {
            handleSave();
        } else if ("loadButton" == e.getActionCommand()) {
            handleLoad();
        } else if ("cleanButton" == e.getActionCommand()) {
            handleClean();
        }
    }

    // EFFECTS: handles the add cardio action event
    public void handleCardio() {
        JOptionPane cardioOptions = new JOptionPane();
        DayType day = convertToDayType(cardioOptions.showInputDialog("Which Day?"));
        String exerciseName = cardioOptions.showInputDialog("Name of Exercise");
        int duration = Integer.parseInt(cardioOptions.showInputDialog("Duration of exercise"));
        Exercise exercise = new Exercise(exerciseName, 0, 0, duration);
        schedule.addExercise(exercise, day);
        updateTable();
    }

    // EFFECTS: handles the add body weight action event
    public void handleBodyWeight() {
        JOptionPane bodyWeightOptions = new JOptionPane();
        DayType day = convertToDayType(bodyWeightOptions.showInputDialog("Which Day?"));
        String exerciseName = bodyWeightOptions.showInputDialog("Name of Exercise");
        int sets = Integer.parseInt(bodyWeightOptions.showInputDialog("Sets?"));
        int reps = Integer.parseInt(bodyWeightOptions.showInputDialog("Reps?"));
        Exercise exercise = new Exercise(exerciseName, sets, reps, 0);
        schedule.addExercise(exercise, day);
        updateTable();
    }

    // REQUIRES: String must be a real day of the week with no spelling errors
    // EFFECTS: converts the given string into a DayType
    public DayType convertToDayType(String day) {
        String lowerCaseDay = day.toLowerCase();
        System.out.println(lowerCaseDay);
        if (lowerCaseDay.equals("monday")) {
            return DayType.MONDAY;
        } else if (lowerCaseDay.equals("tuesday")) {
            return DayType.TUESDAY;
        } else if (lowerCaseDay.equals("wednesday")) {
            // System.out.println("Wednesday Returned!");
            return DayType.WEDNESDAY;
        } else if (lowerCaseDay.equals("thursday")) {
            // System.out.println("Thursday Returned!");
            return DayType.THURSDAY;
        } else if (lowerCaseDay.equals("friday")) {
            return DayType.FRIDAY;
        } else if (lowerCaseDay.equals("saturday")) {
            return DayType.SATURDAY;
        } else {
            return DayType.SUNDAY;
        }
    }

    // EFFECTS: Removes the given exercise from JTable
    public void handleRemove() {
        JOptionPane removeOptions = new JOptionPane();
        String exerciseName = removeOptions.showInputDialog("Name of Exercise to Remove:");
        DayType day = convertToDayType(removeOptions.showInputDialog("On which day?"));

        Exercise exerciseToRemove = findExercise(exerciseName, day);

        if (exerciseToRemove != null) {
            schedule.removeExercise(exerciseToRemove, day);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Exercise not found on the specified day.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: Finds the given exercise for the provided day
    // NOTE: This method is necessary as removeExercise() takes in Exercise and DayType
    //       instead of String and DayType
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

    // EFFECTS: Saves the state of the JTable to file
    public void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(schedule);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "File saved");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: Loads the state of the JTable to file
    public void handleLoad() {
        try {
            schedule = jsonReader.read();
            updateTable();
            JOptionPane.showMessageDialog(this, "File loaded");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // EFFECTS: Cleans up the table to remove gaps
    public void handleClean() {
        for (int i = 1; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                if (tableModel.getValueAt(i, j) == null || tableModel.getValueAt(i, j).equals("")) {
                    // If the cell is empty, move the value from the cell above it
                    tableModel.setValueAt(tableModel.getValueAt(i - 1, j), i, j);
                    tableModel.setValueAt("", i - 1, j);  // Clear the cell above
                }
            }
        }
    }

}
