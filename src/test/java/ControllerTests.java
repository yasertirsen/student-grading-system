import controller.Controller;
import model.Rubric;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTests {

    private final Controller controller = new Controller();

    @Test
    public void testCreateRubric() {
        assertEquals(new Rubric("Test", new ArrayList<>()), controller.createRubric("Test"));
    }
}
