package controller;

import exception.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.Criterion;
import model.Grade;
import model.Rubric;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public Grade addGrade(Grade grade) throws RubricNotFoundException {
        if(grade.getRubric() != null) {
            grades.add(grade);
            return grade;
        }
        throw new RubricNotFoundException("Grade must have a rubric");
    }

    public Grade addScore(Grade grade, Criterion criterion, int score) throws CriterionNotFoundException, GradeNotFoundException, ScoreNotAllowedException {
        if(score <=5) {
            if(grade.getRubric().getCriteria().contains(criterion)) {
                try {
                    int index = grades.indexOf(grade);
                    grade.getScores().put(criterion.getName(), score);
                    grades.set(index, grade);
                    return grade;

                } catch (IndexOutOfBoundsException e) {
                    throw new GradeNotFoundException("Student grades were not fount");
                }
            } else
                throw new CriterionNotFoundException("Criterion not present in rubric");
        } else
            throw new ScoreNotAllowedException("Score must be from 1 to 5");
    }

    public List<Grade> getGrades(Rubric rubric) {
        List<Grade> gradesByRubric = new ArrayList<>();
        for(Grade grade : grades) {
            if(grade.getRubric() == rubric)
                gradesByRubric.add(grade);
        }
        return gradesByRubric;
    }

    public double getAverageByCriterion(Criterion criterion) throws NoDataException {
        double total = 0.0;
        List<Double> scores = getAllScoresByCriterion(criterion);
        for(double score : scores) {
            total += score;
        }
        return total/scores.size();
    }

    public double getStandardDeviationByCriterion(Criterion criterion) throws NoDataException {
        double average = getAverageByCriterion(criterion);
        double sd = 0.0;
        List<Double> scores = getAllScoresByCriterion(criterion);
        for(double score : scores) {
            sd += Math.pow(score - average, 2);
        }
        return Math.sqrt(sd/scores.size());
    }

    public double getMaxByCriterion(Criterion criterion) throws NoDataException {
        return Collections.max(getAllScoresByCriterion(criterion));
    }

    public double getMinByCriterion(Criterion criterion) throws NoDataException {
        return Collections.min(getAllScoresByCriterion(criterion));
    }

    private List<Double> getAllScoresByCriterion(Criterion criterion) throws NoDataException {
        List<Double> scores = new ArrayList<>();
        for(Grade grade : grades) {
            if(grade.getScores().containsKey(criterion.getName())) {
                scores.add(Double.valueOf(grade.getScores().get(criterion.getName())));
            }
        }
        if(scores.isEmpty())
            throw new NoDataException("Cannot provide summary due to lack of data");
        return scores;
    }

    public double getAverageByRubric(Rubric rubric) throws NoDataException {
        double total = 0.0;
        List<Double> scores = getAllScoresByRubric(rubric);
        for(double score : scores) {
            total += score;
        }
        return total/scores.size();
    }

    public double getStandardDeviationByRubric(Rubric rubric) throws NoDataException {
        double average = getAverageByRubric(rubric);
        double sd = 0.0;
        List<Double> scores = getAllScoresByRubric(rubric);
        for(double score : scores) {
            sd += Math.pow(score - average, 2);
        }
        return Math.sqrt(sd/scores.size());
    }

    public double getMaxByRubric(Rubric rubric) throws NoDataException {
        return Collections.max(getAllScoresByRubric(rubric));
    }

    public double getMinByRubric(Rubric rubric) throws NoDataException {
        return Collections.min(getAllScoresByRubric(rubric));
    }

    private List<Double> getAllScoresByRubric(Rubric rubric) throws NoDataException {
        List<Double> scores = new ArrayList<>();
        for(Grade grade : grades) {
            if(grade.getRubric() == rubric && !grade.getScores().isEmpty())
                for (Map.Entry<String, Integer> entry : grade.getScores().entrySet()) {
                    scores.add(Double.valueOf(entry.getValue()));
                }
        }
        if(scores.isEmpty())
            throw new NoDataException("Cannot provide summary due to lack of data");
        return scores;
    }
}
