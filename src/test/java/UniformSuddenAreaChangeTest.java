import model.elements.areaChanges.UniformSuddenExpansion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniformSuddenAreaChangeTest {
    @Test
    public void LocalResistanceLeftBoard () {
        UniformSuddenExpansion uniformSuddenExpansion =
                new UniformSuddenExpansion(0.5, 1.4142,
                        15e-6, 1);
        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(2);
        assertEquals(3.10, result);
    }
}
