# Video Demo:  
https://youtu.be/s-rwmlVxqO8

# Task 1

**Fitness Planner**

# Task 2

This application will allow the user to create an exercise schedule for each day of the week (i.e. Monday to Sunday).
This application is intended for anyone that wants to get into exercising and wants to keep track of their
current exercise plan. I am passionate about this project because I regularly exercise to stay healthy and keep track 
of my exercises using the native Notes app on iPhone. However, my quality of life would be improved if I had access to 
an application that is tailored for documenting my exercise plan. 

**For example:**

*Monday*
- 3 x 10 push-ups [x]
- 3 x 8 pull-ups []
- 15 minutes cardio []

*Tuesday*
- Rest

*Wednesday*
- 3 x 10 push-ups [x]
- 3 x 15 sit-ups []
- 3 x 20 jumping jacks []
- 15 minutes cardio []

*etc.*


# Task 3

*User Stories*:
- As a user, I want to be able to add any exercise to any given day of the week
- As a user, I want to be able to view all my exercises for any given day of the week
- As a user, I want to be able to modify the intensity of my exercises for any given day of the week
- As a user, I want to be able to indicate the exercises I have completed
- As a user, I want to be able to swap the exercises between two days
- As a user, I want to be able to save my exercise schedule 
- As a user, I want to be able to load my exercise schedule, if I so choose

NOTE: I added one additional user story where the user can swap exercises 
between two days 

An example of text with **bold** and *italic* fonts. 

## A subtitle

# Instructions for User

- Please hit start application at the loading screen. 
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by hitting the add button. Please follow the instructions displayed in the JOptionPane.
- You can generate the second required action related to the user story "removing X from Y" by hitting the remove button. Please follow the instructions displayed in the JOptionPane. 
- Additionally, you can modify an exercise by hitting the Modify Exercise button. Please follow the instructions displayed in the JOptionPane. 
- The visual component is displayed by default. All relevant exercises for each day is displayed. 
- You can save the state of my application by hitting the save button. 
- You can reload the state of my application by hitting the load button. 

# Phase 4: Task 2
- NOTE: I am first adding an exercise to Monday, and then removing that exercise from Monday. Following the addition of adding/removing an exercise, updateTable() is called which loops through each day in the schedule to create the JTable. Hence, there are many messages of "Retrieving exercises from x" printed to the console.
- There are instances of "Retrieving exercises from x" before add/remove behaviour occur due to addExercise and removeExercise calling getExercise in their method bodies.    
<br>
- Event Log:   
<br>
- Sun Nov 26 15:47:30 PST 2023
- Retrieving Monday exercises
- Sun Nov 26 15:47:30 PST 2023
- **Adding Pushups to MONDAY**
- Sun Nov 26 15:47:30 PST 2023
- Retrieving Monday exercises
- Sun Nov 26 15:47:30 PST 2023
- Retrieving Tuesday exercises
- Sun Nov 26 15:47:30 PST 2023
- Retrieving Wednesday exercises
- Sun Nov 26 15:47:30 PST 2023
- Retrieving Thursday exercises
- Sun Nov 26 15:47:30 PST 2023
- Retrieving Friday exercises
- Sun Nov 26 15:47:30 PST 2023
- Retrieving Saturday exercises
- Sun Nov 26 15:47:30 PST 2023
- Retrieving Sunday exercises
- Sun Nov 26 15:47:35 PST 2023
- Retrieving Monday exercises
- Sun Nov 26 15:47:35 PST 2023
- Retrieving Monday exercises
- Sun Nov 26 15:47:35 PST 2023
- **Removing Pushups from MONDAY**
- Sun Nov 26 15:47:35 PST 2023
- Retrieving Monday exercises
- Sun Nov 26 15:47:35 PST 2023
- Retrieving Tuesday exercises
- Sun Nov 26 15:47:35 PST 2023
- Retrieving Wednesday exercises
- Sun Nov 26 15:47:35 PST 2023
- Retrieving Thursday exercises
- Sun Nov 26 15:47:35 PST 2023
- Retrieving Friday exercises
- Sun Nov 26 15:47:35 PST 2023
- Retrieving Saturday exercises
- Sun Nov 26 15:47:35 PST 2023
- Retrieving Sunday exercises

- Process finished with exit code 0
- 
# Phase 4: Task 3
After creating my UML diagram, I realize that although JButton was one of my most used types, I never created a class for it. Instead, I opted to have multiple fields of type JButton. If I were to refactor the project, I would make a class that is dedicated for a Button. This would make sense because not only do I use buttons in my FitnessAppGUI class, I also use buttons in my ExerciseInputDialog class as well. Instantiating button objects instead of storing them in fields would reduce the coupling of the program. 
