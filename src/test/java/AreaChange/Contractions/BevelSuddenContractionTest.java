package AreaChange.Contractions;

import model.elements.areaChanges.BevelSuddenContraction;
import model.elements.areaChanges.ParabolicSuddenExpansion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BevelSuddenContractionTest {
    @Test
    public void LocalResistanceLeftUpperBoard () {
        BevelSuddenContraction bevelSuddenContraction =
                new BevelSuddenContraction(1, 1e300,
                        15e-6, 1,0,0.01);
        double result = bevelSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.5, result);
    }
    @Test
    public void LocalResistanceLeftLowerBoard () {
        BevelSuddenContraction bevelSuddenContraction =
                new BevelSuddenContraction(1, 1e300,
                        15e-6, 1,0,2);
        double result = bevelSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.5, result);
    }
    @Test
    public void LocalResistanceRightUpperBoard () {
        BevelSuddenContraction bevelSuddenContraction =
                new BevelSuddenContraction(1, 1e300,
                        15e-6, 1,190,0.01);
        double result = bevelSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.5, result);
    }
    @Test
    public void LocalResistanceRightLowerBoard () {
        BevelSuddenContraction bevelSuddenContraction =
                new BevelSuddenContraction(1, 1e300,
                        15e-6, 1,190,2);
        double result = bevelSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.5, result);
    }
    @Test
    public void LocalResistanceExactValue () {
        BevelSuddenContraction bevelSuddenContraction =
                new BevelSuddenContraction(1, 1e300,
                        15e-6, 1,40,0.075);
        double result = bevelSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.26, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation1 () {
        BevelSuddenContraction bevelSuddenContraction =
                new BevelSuddenContraction(1, 1e300,
                        15e-6, 1,40,0.055);
        double result = bevelSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.316, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation2 () {
        BevelSuddenContraction bevelSuddenContraction =
                new BevelSuddenContraction(1, 1e300,
                        15e-6, 1,80,0.075);
        double result = bevelSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.265, result);
    }
    @Test
    public void LocalResistanceBilinearInterpolation() {
        BevelSuddenContraction bevelSuddenContraction =
                new BevelSuddenContraction(1, 1e300,
                        15e-6, 1,70,0.055);
        double result = bevelSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.2995, result);
    }
}
