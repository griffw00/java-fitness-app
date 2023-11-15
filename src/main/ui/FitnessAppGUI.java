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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //Other Objects
    private Schedule schedule;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/workroom.json";

    public FitnessAppGUI() {
        initialize();
        initializeUI();
        // Add buttons to a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBodyWeightBtn);
        buttonPanel.add(addCardioBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(loadBtn);
        // Add components to the JFrame
        add(buttonPanel, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setPreferredSize(new Dimension(1100, 600));  // Adjust the preferred size of the scroll pane
        add(scrollPane, BorderLayout.CENTER);  // Add the scroll pane to the center of the BorderLayout
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: initializes all the necessary objects for this class
    public void initialize() {
        scheduleTable = new JTable();
        schedule =  new Schedule();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    public void initializeUI() {
        setTitle("JFit");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));  // Adjust the preferred size
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new BorderLayout());  // Change to BorderLayout for better control
        createButtons();
        updateTable();
    }

    public void updateTable() {
        String[] columnNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        Object[][] data = new Object[1][columnNames.length];

        for (int i = 0; i < columnNames.length; i++) {
            DayType day = DayType.values()[i];
            List<Exercise> exerciseList = schedule.getExercises(day);
            StringBuilder exercisesString = new StringBuilder();
            if (exerciseList != null) {
                for (Exercise exercise : exerciseList) {
                    exercisesString.append(exercise.getExerciseName()).append("\n");
                }
            }
            data[0][i] = exercisesString.toString();
        }

        // Update the existing table model
        if (tableModel == null) {
            tableModel = new DefaultTableModel(data, columnNames);
            scheduleTable.setModel(tableModel);
        } else {
            // Assuming the tableModel is set up with a single row and columnNames
            for (int i = 0; i < columnNames.length; i++) {
                tableModel.setValueAt(data[0][i], 0, i);
            }
        }
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("addCardioButton" == e.getActionCommand()) {
            handleCardio();
        } else if ("addBodyWeightButton" == e.getActionCommand()) {
            // do something
        } else if ("removeButton" == e.getActionCommand()) {
            // do something else
        } else if ("saveButton" == e.getActionCommand()) {
            // do something else
        } else if ("loadButton" == e.getActionCommand()) {
            // do something else
        }
    }

    public void handleCardio() {
        JOptionPane cardioOptions = new JOptionPane();
        DayType day = convertToDayType(cardioOptions.showInputDialog("Which Day?"));
        String exerciseName = cardioOptions.showInputDialog("Name of Exercise");
        int duration = Integer.parseInt(cardioOptions.showInputDialog("Duration of exercise"));
        Exercise exercise = new Exercise(exerciseName, 0, 0, duration);
        schedule.addExercise(exercise, day);
        updateTable();
        repaint();
    }

    public DayType convertToDayType(String day) {
        String lowerCaseDay = day.toLowerCase();
        if (lowerCaseDay == "monday") {
            return DayType.MONDAY;
        } else if (lowerCaseDay == "tuesday") {
            return DayType.TUESDAY;
        } else if (lowerCaseDay == "wednesday") {
            return DayType.WEDNESDAY;
        } else if (lowerCaseDay == "thursday") {
            return DayType.THURSDAY;
        } else if (lowerCaseDay == "friday") {
            return DayType.FRIDAY;
        } else if (lowerCaseDay == "saturday") {
            return DayType.SATURDAY;
        }
        return DayType.SUNDAY;
    }
}
