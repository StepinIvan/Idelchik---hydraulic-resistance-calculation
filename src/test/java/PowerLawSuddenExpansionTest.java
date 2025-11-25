import model.elements.areaChanges.PowerLowSuddenExpansion;
import model.elements.areaChanges.UniformSuddenExpansion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerLawSuddenExpansionTest {
    @Test
    public void LocalResistanceLeftBoardUpper () {
        PowerLowSuddenExpansion powerLowSuddenExpansion =
                new PowerLowSuddenExpansion(0.909728368, 12360.77446, 15e-6,
                        1, 0.5);
        double result = powerLowSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(2.6999999848333336, result);
    }
//    @Test
//    public void LocalResistanceLeftBoardLower () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(4, 1,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(0.001);
//        assertEquals(3000., result);
//    }
//    @Test
//    public void LocalResistanceRightBoardUpper () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(1, 4,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(4000);
//        assertEquals(0.81, result);
//    }
//    @Test
//    public void LocalResistanceRightBoardLower () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(4, 1,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(4000);
//        assertEquals(0.16, result);
//    }
//    @Test
//    public void LocalResistanceExactValue () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(1.595769122, 3.568248232,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(50);
//        assertEquals(1.6499999996673003, result);
//    }
//    @Test
//    public void LocalResistanceBilinearInterpolation1 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(1.784124116, 3.568248232,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(750);
//        assertEquals(1.3250000000000002, result);
//    }
//    @Test
//    public void LocalResistanceTest1 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(2, 4,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(10000000);
//        assertEquals(0.5700000000000001, result);
//    }
//    @Test
//    public void LocalResistanceBilinearInterpolation2 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(0.851907589, 1.128379167,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(42);
//        assertEquals(1.2400000001416756, result);
//    }
//    @Test
//    public void LocalResistanceLinearInterpolation2 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(0.504626504, 1.128379167,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(700);
//        assertEquals(1.420000001145616, result);
//    }
//    @Test
//    public void LocalResistanceTest2 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(0.504626504, 1.128379167,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(0.0001);
//        assertEquals(3000, result);
//    }
//    @Test
//    public void LocalResistanceTest3 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(0.504626504, 1.128379167,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(0.3);
//        assertEquals(100, result);
//    }
//    @Test
//    public void LocalResistanceTest4 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(0.504626504, 1.128379167,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(0.25);
//        assertEquals(125, result);
//    }
//    @Test
//    public void LocalResistanceTest5 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(0.504626504, 1.128379167,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(1100000);
//        assertEquals(0.6400000004868868, result);
//    }
//    @Test
//    public void LocalResistanceLinearInterpolation3 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(0.529256743, 1.128379167,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(50);
//        assertEquals(1.5999999995746028, result);
//    }
//    @Test
//    public void LocalResistanceTest6 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(0.252313252, 1.128379167,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(50);
//        assertEquals(1.95, result);
//    }
//    @Test
//    public void LocalResistanceTest7 () {
//        UniformSuddenExpansion uniformSuddenExpansion =
//                new UniformSuddenExpansion(0.909728368, 1.128379167,
//                        15e-6, 1);
//        double result = uniformSuddenExpansion.calculateLocalResistanceCoefficient(50);
//        assertEquals(1.05, result);
//    }
}
