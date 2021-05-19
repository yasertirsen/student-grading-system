# Scrum sprint backlog and task estimation

## Tasks

| ID  | Name  | Estimation  | Justification for Estimation  | Status  |
|---|---|---|---|---|
| 1 | Create Gradle project |  1 point | Configuration of gradle properties when creating a project for the first time.  | Completed  |
| 2 | Create Rubric, Grade and Criterion |  1 point | Familiarity with crating POJOs. Some logic is required to find out the relationship between the objects, but it should not be too time-consuming.  | Completed  |
| 3 | Create Caller class  |  1 point | This involves creating a class with a main method.  |  Completed |
| 4 | Create Controller class  |  1 point | This story only involves creating the Controller class itself without adding any logic.  |  Completed |
| 5 | Create new Rubric  | 1 point  | This task involves adding a method that will create a new Rubric object. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 6 | Add Criterion to Rubric  | 3 points  | This task involves adding a method that accepts a Criterion and a Rubric and adds Criterion to the Rubric if logic associated with it like a max of 10 Criterion is valid. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 7 | Get all Rubrics  | 1 points  | Create a method to get all Rubrics as a list in the Controller. This task has less points than task 6 because there is no need for complex logic or conditions when retrieving the list of Rubrics. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 8 | Get Rubric by name  | 2 points  | Create a method in the Controller to accept a name and search Rubric. Error handling should be included. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 9 | Create Grade  | 2 points  | Create a method in the Controller to create a Grade. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 10 | Add score of Criterion to Grade  | 3 points  | Create a method in the Controller to add a score of a Criterion to the Grade scores hashmap. The method should receive a Grade, Criterion and score. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 11 | Get all Grades by Rubric  | 2 points  | Create a method in the Controller that accepts a Rubric and gets all Grades associated with the Rubric. This will require additional logic to filter Grades by Rubric. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 12 | Get summary calculation for Rubric  | 3 points  | Create methods in the Controller that accepts a Rubric name and returns the average, standard deviation, minimum and maximum score respectively. This requires creating 4 methods which increases the estimate for this story compared to task 11. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 13 | Get summary calculation for Criterion  | 5 points  | Create methods in the Controller that accepts a Rubric and Criterion name and returns the average, standard deviation, minimum and maximum score for the Criterion in the Rubric respectively. This requires creating 4 methods and filtering through the Rubric which increases the estimate for this story compared to task 12. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 14 | Implement functionality in Caller class | 2 points | Initialize controller and call methods to simulate a working project. This will involve creating some dummy data to simulate the project's functionality. | Not Commenced  |
