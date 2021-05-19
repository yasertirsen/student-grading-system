import controller.Controller;
import exception.*;
import model.Criterion;
import model.Grade;
import model.Rubric;

import java.util.HashMap;

public class GradingSystem {
    public static void main(String[] args) throws RubricNoNameException,
            CriterionNoNameException, RubricMaxCriteriaException,
            RubricNotFoundException, GradeNotFoundException, CriterionNotFoundException,
            ScoreNotAllowedException, NoDataException {
        Controller controller = new Controller();

        Rubric qaRubric = controller.createRubric("Quality Assurance");

        controller.addCriterion(new Criterion("Test Driven Development"), qaRubric);
        controller.addCriterion(new Criterion("Gitflow"), qaRubric);
        System.out.println(controller.getRubric("Quality Assurance") + "\n");

        Grade homer = new Grade("Homer Simpson", qaRubric, new HashMap<>());
        Grade lisa = new Grade("Lisa Simpson", qaRubric, new HashMap<>());
        controller.addGrade(homer);
        controller.addGrade(lisa);

        controller.addScore(homer, qaRubric.getCriteria().get(0), 2);
        controller.addScore(homer, qaRubric.getCriteria().get(1), 3);
        controller.addScore(lisa, qaRubric.getCriteria().get(0), 4);
        controller.addScore(lisa, qaRubric.getCriteria().get(1), 5);

        for(Grade grade : controller.getGrades(qaRubric)) {
            System.out.println(grade + "\n");
        }

        System.out.println("Summary Calculation for Criterion: " + qaRubric.getCriteria().get(1).getName());
        System.out.println("Average: " + controller.getAverageByCriterion(qaRubric.getCriteria().get(1)));
        System.out.println("Standard Deviation: " +
                controller.getStandardDeviationByCriterion(qaRubric.getCriteria().get(1)));
        System.out.println("Min: " + controller.getMinByCriterion(qaRubric.getCriteria().get(1)));
        System.out.println("Max: " + controller.getMaxByCriterion(qaRubric.getCriteria().get(1)));

        System.out.println("---------------------------");

        System.out.println("Summary Calculation for Rubric: " + qaRubric.getName());
        System.out.println("Average: " + controller.getAverageByRubric(qaRubric));
        System.out.println("Standard Deviation: " +
                controller.getStandardDeviationByRubric(qaRubric));
        System.out.println("Min: " + controller.getMinByRubric(qaRubric));
        System.out.println("Max: " + controller.getMaxByRubric(qaRubric));

    }
}
