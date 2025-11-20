import model.elements.areaChanges.UniformSuddenExpansion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniformSuddenAreaChangeTest {
    @Test
    public void LocalResistanceLeftBoardUpper () {
        UniformSuddenExpansion uniformSuddenExpansion =
                new UniformSuddenExpansion(1, 4,
                        15e-6, 1);
        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(0.001);
        assertEquals(3000., result);
    }
    @Test
    public void LocalResistanceLeftBoardLower () {
        UniformSuddenExpansion uniformSuddenExpansion =
                new UniformSuddenExpansion(4, 1,
                        15e-6, 1);
        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(0.001);
        assertEquals(3000., result);
    }
    @Test
    public void LocalResistanceRightBoardUpper () {
        UniformSuddenExpansion uniformSuddenExpansion =
                new UniformSuddenExpansion(1, 4,
                        15e-6, 1);
        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(4000);
        assertEquals(0.81, result);
    }
    @Test
    public void LocalResistanceRightBoardLower () {
        UniformSuddenExpansion uniformSuddenExpansion =
                new UniformSuddenExpansion(4, 1,
                        15e-6, 1);
        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(4000);
        assertEquals(0.16, result);
    }
    @Test
    public void LocalResistanceExactValue () {
        UniformSuddenExpansion uniformSuddenExpansion =
                new UniformSuddenExpansion(1.595769122, 3.568248232,
                        15e-6, 1);
        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(50);
        assertEquals(1.6499999996673003, result);
    }
}
