# Scrum sprint backlog and task estimation

## Tasks

| ID  | Name  | Estimation  | Justification for Estimation  | Status  |
|---|---|---|---|---|
| 1 | Create Gradle project |  1 point | Configuration of gradle properties when creating a project for the first time.  | Completed  |
| 2 | Create Rubric, Grade and Criterion |  1 point | Familiarity with crating POJOs. Some logic is required to find out the relationship between the objects, but it should not be too time-consuming.  | Completed  |
| 3 | Create Caller class  |  1 point | This involves creating a class with a main method.  |  Completed |
| 4 | Create Controller class  |  1 point | This story only involves creating the Controller class itself without adding any logic.  |  Completed |
| 5 | Create new Rubric  | 1 point  | This task involves adding a method that will create a new Rubric object. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 6 | Add Criterion to Rubric  | 3 points  | This task involves adding a method that accepts a Criterion and a Rubric and adds Criterion to the Rubric if logic associated with it, like a max of 10 Criterion is valid. This task is estimated higher than above tasks because it requires the extra mentioned logic and exception handling for incorrectly entered data. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 7 | Get all Rubrics  | 1 points  | Create a method to get all Rubrics as a list in the Controller. This task has less points than task 6 because there is no need for complex logic or conditions when retrieving the list of Rubrics. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 8 | Get Rubric by name  | 2 points  | Create a method in the Controller to accept a name and search Rubric. This task is estimated higher that task 7 because of the extra error and searching of Rubric. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 9 | Create Grade  | 2 points  | Create a method in the Controller to create a Grade. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 10 | Add score of Criterion to Grade  | 5 points  | Create a method in the Controller to add a score of a Criterion to the Grade scores hashmap. The method should receive a Grade, Criterion and score. This method is estimated higher than task 9 because of the checking of criterion existence in the rubric, if score is from 1 to 5 and if the grade has been created. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 11 | Get all Grades by Rubric  | 2 points  | Create a method in the Controller that accepts a Rubric and gets all Grades associated with the Rubric. This will require additional logic to filter Grades by Rubric. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 12 | Get summary calculation for Rubric  | 5 points  | Create methods in the Controller that accepts a Rubric name and returns the average, standard deviation, minimum and maximum score respectively. This requires creating 4 methods which increases the estimate for this story compared to task 11. It also requires researching how to perform calculations in Java correctly. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 13 | Get summary calculation for Criterion  | 3 points  | Create methods in the Controller that accepts a Rubric and Criterion name and returns the average, standard deviation, minimum and maximum score for the Criterion in the Rubric respectively. This essentially the same as task 12 but with filtering by criterion instead of rubric, thus most of the code will be duplicated which gives it a lower estimate. Tests will have to be created before logic is implemented per TDD rules.  | Completed  |
| 14 | Implement functionality in Caller class | 2 points | Initialize controller and call methods to simulate a working project. This will involve creating some dummy data to simulate the project's functionality. | Completed  |

# Test-Driven development

Test Driven Development is a software development process that relies on tests created based on software requirements
before solution is fully developed. It also involves continuously tracking all software development by testing against all test cases.
This is different from software being developed first and test cases created later. 

![TDD Diagram](https://marsner.com/wp-content/uploads/test-driven-development-TDD.png)

[This commit](https://github.com/yasertirsen/student-grading-system/commit/555645efdbd41ea3d675e2ee9a98efd401209f89)
is an example of using TDD when working on this project. As seen on the commit, tests are implemented, and the logic
is developed in the controller class. Tests were created before implementing the software solution, at that point the
tests were failing. The purpose of creating a failing test is to make sure logic implemented accounts for all cases needed
to produce an appropriate software solution.

# Test coverage metric

The tool used in this project to identify the test coverage was IntelliJ's test coverage tool. Code coverage in IntelliJ 
IDEA allows you to see the extent to which your code has been executed. It also lets you verify the extent to which your 
code is covered by unit tests, so that you can estimate how effective these tests are. The tool can be chosen when
trying to run tests class as shown below. You can choose "Run with Coverage" to receive test coverage details.

![IntelliJ test menu](https://i.imgur.com/XdE1WpW.png)

The screenshot below shows the initial analysis of the test coverage tool.

![Test coverage before](https://i.imgur.com/foVqTgC.png)

As shown above, there are some gaps when it comes to test coverage. IntelliJ allows us to identify those gaps by 
inspecting the code as shown below. Green highlight means the code is covered, while red means otherwise.

![Test coverage example](https://i.imgur.com/3p0BvYB.png)

After looking at the coverage, I noticed that there are some unnecessary methods that were not being used, so I refactored 
the code. The output of the coverage metric tool changed to the result below.

![Test coverage after](https://i.imgur.com/SnbEGiV.png)

# Team version-control

The Gitflow Workflow defines a strict branching model designed around the project release. This provides a robust 
framework for managing larger projects. One of the essential features of Gitflow is the master and develop branches; 
instead of having a single mater branch, Gitflow uses two branches to record the history of the project, master and develop.

When working on the project, tasks/feature branches were checked out from the develop branch. Every task in the sprint backlog had
its own branch. After work is completed on a task branch, a Pull Request is created to merge changes to the develop branch. 
The master branch only stores the official release of a project, in this case, when all tasks in the backlog are completed
and merged to the develop branch.

# Code-review checklist

Code Review is the act of consciously and systematically convening with one's fellow programmers to check each other's 
code for mistakes, and has been repeatedly shown to accelerate and streamline the process of software development like 
few other practices can. As this project was individual, I had to review my own PRs. Here is a list of things
to look for when reviewing a PR.

- Design: This involves making sure the solution implemented is fit for purpose and integrates well with the rest of the system.
- Functionality: The reviewer must be able to simulate how the user would interact with the functionality by just reading the code.
- Complexity: This involves checking if the complexity of the implemented solution is valid. The solution must be easily understandable by other developers. This ensures preventing bugs when changing the solution in the future.
- Scope: The solution implemented should be contained and only solves what is required without any additional unnecessary implementations.
- Tests: The reviewer should ask for unit, integration, or end-to-end tests appropriately based on the solution. This is especially important if the standard is TDD. Tests also need to be well-designed that they actually test a feature correctly and fail when feature is broken.
- Naming: This involves making sure naming of things is appropriate and follows the team's coding standards.
- Comments: The reviewer should check that comments are clear and useful, and mostly explain why instead of what.
- Documentation: This involves making sure documentation reflects new changes and deprecated code is removed.
  
