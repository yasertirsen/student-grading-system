package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@Data
public class Grade {
    private String student;
    private Rubric rubric;
    private Map<String, Integer> scores;
}
