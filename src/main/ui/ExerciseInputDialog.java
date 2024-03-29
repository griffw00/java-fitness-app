package ui;

import javax.swing.*;
import java.awt.*;

public class ExerciseInputDialog extends JDialog {

    private JTextField nameField;
    private JSlider setsSlider;
    private JSlider repsSlider;
    private JSlider durationSlider;
    private JButton okButton;

    private boolean isConfirmed = false;

    private boolean isCardio;

    // EFFECTS: Constructs an ExerciseInputDialog object
    public ExerciseInputDialog(JFrame parent, boolean isCardio) {
        super(parent, "Exercise Input", true);
        this.isCardio = isCardio;
        initialize();
    }

    // EFFECTS: initializes with the input dialog
    private void initialize() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        nameField = new JTextField();
        initializeSliders();

        addSliderToPanel(panel, "Name:", nameField);
        if (isCardio) {
            setsSlider.setVisible(false);
            repsSlider.setVisible(false);
            addSliderToPanel(panel, "Duration (min):", durationSlider);
        } else {
            durationSlider.setVisible(false);
            addSliderToPanel(panel, "Sets:", setsSlider);
            addSliderToPanel(panel, "Reps:", repsSlider);
        }

        okButton = new JButton("OK");
        addButtonToPanel(panel, okButton);
        okButton.addActionListener(e -> {
            isConfirmed = true;
            dispose();
        });

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    // EFFECTS: initializes sliders with ticks and labels
    private void initializeSliders() {
        setsSlider = new JSlider(0, 10);
        setsSlider.setMajorTickSpacing(5);
        setsSlider.setPaintTicks(true);
        setsSlider.setPaintLabels(true);
        repsSlider = new JSlider(0, 20);
        repsSlider.setMajorTickSpacing(5);
        repsSlider.setPaintTicks(true);
        repsSlider.setPaintLabels(true);
        durationSlider = new JSlider(0, 60);
        durationSlider.setMajorTickSpacing(10);
        durationSlider.setPaintTicks(true);
        durationSlider.setPaintLabels(true);
    }

    // EFFECTS: Adds cardio and body weight sliders to panel
    private void addSliderToPanel(JPanel panel, String label, JComponent component) {
        panel.add(new JLabel(label));
        panel.add(component);
    }

    // EFFECTS: Adds OK button to panel
    private void addButtonToPanel(JPanel panel, JButton button) {
        panel.add(button);
        panel.add(new JPanel());  // Empty panel for alignment
    }

    public boolean showDialog() {
        setVisible(true);
        return isConfirmed;
    }

    public String getExerciseName() {
        return nameField.getText();
    }

    public int getSets() {
        return setsSlider.getValue();
    }

    public int getReps() {
        return repsSlider.getValue();
    }

    public int getDuration() {
        return durationSlider.getValue();
    }
}
