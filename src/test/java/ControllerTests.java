import controller.Controller;
import exception.*;
import model.Criterion;
import model.Grade;
import model.Rubric;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerTests {

    private final Controller controller;
    private final List<Rubric> rubrics;
    private final List<Grade> grades;

    // Initializing some dummy data to be used when testing for different scenarios
    public ControllerTests() {
        grades = new ArrayList<>();
        rubrics = new ArrayList<>();
        Rubric rubric;
        for(int i = 0; i < 5; i++) {
            rubric = new Rubric(String.valueOf(i), new ArrayList<>());
            rubrics.add(rubric);
            grades.add(new Grade(String.valueOf(i), rubric, new HashMap<>()));
        }
        controller = new Controller(rubrics, grades);
    }

    // Testing adding a new Rubric
    @Test
    public void testCreateRubric() throws RubricNoNameException {
        assertEquals(new Rubric("Test", new ArrayList<>()), controller.createRubric("Test"));
    }

    // Testing adding a Rubric with no provided name
    @Test
    public void testCreateRubricNoName() {
        assertThrows(RubricNoNameException.class, () -> controller.createRubric(""));
    }

    // Testing adding a Criterion to a Rubric
    @Test
    public void testAddCriterion() throws RubricMaxCriteriaException, CriterionNoNameException {
        Criterion criterion = new Criterion("testCriterion");
        List<Criterion> expectedCriteria = new ArrayList<>();
        expectedCriteria.add(criterion);
        Rubric expected = rubrics.get(2);
        expected.setCriteria(expectedCriteria);
        assertEquals(expected, controller.addCriterion(new Criterion("testCriterion"), rubrics.get(2)));
    }

    // Testing adding a Criterion to a Rubric where the Rubric has 10 Criteria
    @Test
    public void testAddCriterionOver10() {
        List<Criterion> criteria = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            criteria.add(new Criterion("testCriterion"));
        }
        Rubric rubric = rubrics.get(1);
        rubric.setCriteria(criteria);
        assertThrows(RubricMaxCriteriaException.class, () -> controller.addCriterion(new Criterion("testCriterion"), rubric));
    }

    // Testing adding a Criterion with no name
    @Test
    public void testAddCriterionNoName() {
        assertThrows(CriterionNoNameException.class, () -> controller.addCriterion(new Criterion(), rubrics.get(3)));
    }

    // Testing retrieving all Rubrics
    @Test
    public void testGetRubrics() {
        assertEquals(rubrics, controller.getRubrics());
    }

    // Testing retrieving a Rubric by name
    @Test
    public void testGetRubric() throws RubricNotFoundException {
        assertEquals(rubrics.get(4), controller.getRubric("4"));
    }

    // Testing retrieving a Rubric that does not exist
    @Test
    public void testGetRubricNotFound() {
        assertThrows(RubricNotFoundException.class, () -> controller.getRubric("NotFoundTest"));
    }

    // Testing adding a new Grade
    @Test
    public void testAddGrade() throws RubricNotFoundException {
        Grade expected = new Grade("Test", rubrics.get(1), new HashMap<>());
        assertEquals(expected, controller.addGrade(expected));
    }

    // Testing adding a Grade without associated Rubric
    @Test
    public void testAddGradeNoRubric() {
        Grade grade = new Grade("Test", null, new HashMap<>());
        assertThrows(RubricNotFoundException.class, () -> controller.addGrade(grade));
    }

    // Testing adding a score to a Grade
    @Test
    public void testAddScore() throws CriterionNotFoundException, GradeNotFoundException, ScoreNotAllowedException {
        Rubric rubric = rubrics.get(2);
        Criterion criterion = new Criterion("Test");
        rubric.getCriteria().add(criterion);
        Grade grade = grades.get(2);
        grade.setRubric(rubric);
        Grade expected = grades.get(2);
        expected.getScores().put(criterion.getName(), 3);
        assertEquals(expected, controller.addScore(grade, criterion, 3));
    }

    // Testing adding a score to a Grade over allowed score
    @Test
    public void testAddScoreOver5() {
        Rubric rubric = rubrics.get(2);
        Criterion criterion = new Criterion("Test");
        rubric.getCriteria().add(criterion);
        Grade grade = grades.get(2);
        grade.setRubric(rubric);
        assertThrows(ScoreNotAllowedException.class, () -> controller.addScore(grade, criterion, 9));
    }

    // Testing adding a score to a Criterion that is not present in the Rubric of the Grade
    @Test
    public void testAddScoreCriterionNotInRubric() {
        Rubric rubric = rubrics.get(2);
        Criterion criterion = new Criterion("Test");
        rubric.getCriteria().add(criterion);
        Grade grade = grades.get(2);
        grade.setRubric(rubric);
        assertThrows(CriterionNotFoundException.class, () -> controller.addScore(grade, new Criterion("DifferentCriterion"), 3));
    }

    // Testing adding a score to a Criterion with a Grade that has not been added
    @Test
    public void testAddScoreGradeNotFound() {
        Rubric rubric = rubrics.get(2);
        Criterion criterion = new Criterion("Test");
        rubric.getCriteria().add(criterion);
        Grade grade = new Grade("Test", rubric, new HashMap<>());
        grade.setRubric(rubric);
        assertThrows(GradeNotFoundException.class, () -> controller.addScore(grade, criterion, 3));
    }

    // Testing getting Grades by Rubric
    @Test
    public void testGetGrades() throws RubricNotFoundException {
        Rubric rubric = rubrics.get(3);
        List<Grade> expected = new ArrayList<>();
        expected.add(grades.get(3));
        Grade grade = new Grade("Test1", rubric, new HashMap<>());
        controller.addGrade(grade);
        expected.add(grade);
        assertEquals(expected, controller.getGrades(rubric));
    }

    // Testing getting average score by Rubric with no corresponding scores
    @Test
    public void testGetAverageByRubricNoData() {
        assertThrows(NoDataException.class, () -> controller.getAverageByRubric(rubrics.get(3)));
    }

    // Testing getting average score by Rubric
    @Test
    public void testGetAverageByRubric() throws NoDataException {
        Map<String, Integer> scores = new HashMap<>();
        for(int i = 1; i < 6; i++) {
            scores.put("Test"+i, i);
        }
        Grade grade1 = grades.get(1);
        grade1.setScores(scores);
        Grade grade2 = grades.get(2);
        grade2.setScores(scores);
        grade2.setRubric(rubrics.get(1));
        assertEquals(3.0, controller.getAverageByRubric(rubrics.get(1)));
    }

    // Testing getting standard deviation by Rubric with no corresponding scores
    @Test
    public void testGetStandardDeviationByRubricNoData() {
        assertThrows(NoDataException.class, () -> controller.getStandardDeviationByRubric(rubrics.get(3)));
    }

    // Testing getting standard deviation by Rubric
    @Test
    public void testGetStandardDeviationByRubric() throws NoDataException {
        Map<String, Integer> scores = new HashMap<>();
        for(int i = 1; i < 6; i++) {
            scores.put("Test"+i, i);
        }
        Grade grade1 = grades.get(1);
        grade1.setScores(scores);
        Grade grade2 = grades.get(2);
        grade2.setScores(scores);
        grade2.setRubric(rubrics.get(1));
        assertEquals(1.4142135623730951, controller.getStandardDeviationByRubric(rubrics.get(1)));
    }

    // Testing getting max score by Rubric with no scores
    @Test
    public void testGetMaxScoreByRubricNoData() {
        assertThrows(NoDataException.class, () -> controller.getMaxByRubric(rubrics.get(3)));
    }

    // Testing getting max score by Rubric
    @Test
    public void testGetMaxScoreByRubric() throws NoDataException {
        Map<String, Integer> scores = new HashMap<>();
        for(int i = 1; i < 6; i++) {
            scores.put("Test"+i, i);
        }
        Grade grade1 = grades.get(1);
        grade1.setScores(scores);
        Grade grade2 = grades.get(2);
        grade2.setScores(scores);
        grade2.setRubric(rubrics.get(1));
        assertEquals(5, controller.getMaxByRubric(rubrics.get(1)));
    }

    // Testing getting max score by Criterion with no scores
    @Test
    public void testGetMinScoreByRubricNoData() {
        assertThrows(NoDataException.class, () -> controller.getMinByRubric(rubrics.get(3)));
    }

    // Testing getting max score by Criterion
    @Test
    public void testGetMinScoreByRubric() throws NoDataException {
        Map<String, Integer> scores = new HashMap<>();
        for(int i = 1; i < 6; i++) {
            scores.put("Test"+i, i);
        }
        Grade grade1 = grades.get(1);
        grade1.setScores(scores);
        Grade grade2 = grades.get(2);
        grade2.setScores(scores);
        grade2.setRubric(rubrics.get(1));
        assertEquals(1, controller.getMinByRubric(rubrics.get(1)));
    }

    // Testing getting average score by Criterion with no corresponding scores
    @Test
    public void testGetAverageByCriterionNoData() {
        assertThrows(NoDataException.class, () -> controller.getAverageByCriterion(new Criterion("Test")));
    }

    // Testing getting average score by Criterion
    @Test
    public void testGetAverageByCriterion() throws NoDataException {
        Map<String, Integer> scores1 = new HashMap<String, Integer>() {{put("Test", 2);}};
        Map<String, Integer> scores2 = new HashMap<String, Integer>() {{put("Test", 5);}};
        Grade grade1 = grades.get(1);
        grade1.setScores(scores1);
        Grade grade2 = grades.get(2);
        grade2.setScores(scores2);
        assertEquals(3.5, controller.getAverageByCriterion(new Criterion("Test")));
    }

    // Testing getting standard deviation by Criterion with no corresponding scores
    @Test
    public void testGetStandardDeviationByCriterionNoData() {
        assertThrows(NoDataException.class, () -> controller.getStandardDeviationByCriterion(new Criterion("Test")));
    }

    // Testing getting standard deviation by Criterion
    @Test
    public void testGetStandardDeviationByCriterion() throws NoDataException {
        Map<String, Integer> scores1 = new HashMap<String, Integer>() {{put("Test", 2);}};
        Map<String, Integer> scores2 = new HashMap<String, Integer>() {{put("Test", 5);}};
        Grade grade1 = grades.get(1);
        grade1.setScores(scores1);
        Grade grade2 = grades.get(2);
        grade2.setScores(scores2);
        assertEquals(1.5, controller.getStandardDeviationByCriterion(new Criterion("Test")));
    }

    // Testing getting max score by Criterion with no scores
    @Test
    public void testGetMaxScoreByCriterionNoData() {
        assertThrows(NoDataException.class, () -> controller.getMaxByCriterion(new Criterion("Test")));
    }

    // Testing getting max score by Criterion
    @Test
    public void testGetMaxScoreByCriterion() throws NoDataException {
        Map<String, Integer> scores1 = new HashMap<String, Integer>() {{put("Test", 2);}};
        Map<String, Integer> scores2 = new HashMap<String, Integer>() {{put("Test", 5);}};
        Grade grade1 = grades.get(1);
        grade1.setScores(scores1);
        Grade grade2 = grades.get(2);
        grade2.setScores(scores2);
        assertEquals(5, controller.getMaxByCriterion(new Criterion("Test")));
    }

    // Testing getting max score by Criterion with no scores
    @Test
    public void testGetMinScoreByCriterionNoData() {
        assertThrows(NoDataException.class, () -> controller.getMinByCriterion(new Criterion("Test")));
    }

    // Testing getting max score by Criterion
    @Test
    public void testGetMinScoreByCriterion() throws NoDataException {
        Map<String, Integer> scores1 = new HashMap<String, Integer>() {{put("Test", 2);}};
        Map<String, Integer> scores2 = new HashMap<String, Integer>() {{put("Test", 5);}};
        Grade grade1 = grades.get(1);
        grade1.setScores(scores1);
        Grade grade2 = grades.get(2);
        grade2.setScores(scores2);
        assertEquals(2, controller.getMinByCriterion(new Criterion("Test")));
    }
}
