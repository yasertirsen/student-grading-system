package controller;

import exception.CriterionNoNameException;
import exception.RubricMaxCriteriaException;
import exception.RubricNoNameException;
import exception.RubricNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.Criterion;
import model.Grade;
import model.Rubric;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Controller {

    private List<Rubric> rubrics;
    private List<Grade> grades;

    public Rubric createRubric(String name) throws RubricNoNameException {
        if(!StringUtils.isEmpty(name)) {
            Rubric rubric = new Rubric(name, new ArrayList<>());
            rubrics.add(rubric);
            return rubric;
        }
        else
            throw new RubricNoNameException("Rubric name must not be empty");
    }

    public Rubric addCriterion(Criterion criterion, Rubric rubric) throws RubricMaxCriteriaException, CriterionNoNameException {
        if(rubric.getCriteria().size() < 10) {
            if(!StringUtils.isEmpty(criterion.getName())) {
                rubrics.get(rubrics.indexOf(rubric)).getCriteria().add(criterion);
                return rubrics.get(rubrics.indexOf(rubric));
            } else
                throw new CriterionNoNameException("Criterion must contain a name");
        } else
            throw new RubricMaxCriteriaException("Rubric can only have 10 criteria max");
    }

    public List<Rubric> getRubrics() {
        return rubrics;
    }

    public Rubric getRubric(String name) throws RubricNotFoundException {
        for(Rubric rubric : rubrics) {
            if(rubric.getName().equalsIgnoreCase(name))
                return rubric;
        }
        throw new RubricNotFoundException("Rubric Not Found");
    }

    public Grade addGrade(Grade grade) {
        grades.add(grade);
        return grade;
    }
}
