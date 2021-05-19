import controller.Controller;
import exception.CriterionNoNameException;
import exception.RubricMaxCriteriaException;
import exception.RubricNoNameException;
import exception.RubricNotFoundException;
import model.Criterion;
import model.Rubric;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerTests {

    private final Controller controller;
    private final List<Rubric> rubrics;

    public ControllerTests() {
        rubrics = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            rubrics.add(new Rubric(String.valueOf(i), new ArrayList<>()));
        }
        controller = new Controller(rubrics);
    }

    @Test
    public void testCreateRubric() throws RubricNoNameException {
        assertEquals(new Rubric("Test", new ArrayList<>()), controller.createRubric("Test"));
    }

    @Test
    public void testCreateRubricNoName() {
        assertThrows(RubricNoNameException.class, () -> controller.createRubric(""));
    }

    @Test
    public void testAddCriterion() throws RubricMaxCriteriaException, CriterionNoNameException {
        Criterion criterion = new Criterion("testCriterion");
        List<Criterion> expectedCriteria = new ArrayList<>();
        expectedCriteria.add(criterion);
        Rubric expected = rubrics.get(2);
        expected.setCriteria(expectedCriteria);
        assertEquals(expected, controller.addCriterion(new Criterion("testCriterion"), rubrics.get(2)));
    }

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

    @Test
    public void testAddCriterionNoName() {
        assertThrows(CriterionNoNameException.class, () -> controller.addCriterion(new Criterion(), rubrics.get(3)));
    }

    @Test
    public void testGetRubrics() {
        assertEquals(rubrics, controller.getRubrics());
    }

    @Test
    public void testGetRubric() throws RubricNotFoundException {
        assertEquals(rubrics.get(4), controller.getRubric("4"));
    }

    @Test
    public void testGetRubricNotFound() {
        assertThrows(RubricNotFoundException.class, () -> controller.getRubric("NotFoundTest"));
    }


}
