package AreaChange.Expansions;

import model.elements.areaChanges.ParabolicSuddenExpansion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParabolicSuddenAreaChangeTest {
    @Test
    public void LocalResistanceLeftBoard () {
        ParabolicSuddenExpansion parabolicSuddenExpansion =
                new ParabolicSuddenExpansion(1, 1e300,
                        15e-6, 1);
        double result = parabolicSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(2., result);
    }
    @Test
    public void LocalResistanceRightBoard () {
        ParabolicSuddenExpansion parabolicSuddenExpansion =
                new ParabolicSuddenExpansion(1.2, 1,
                        15e-6, 1);
        double result = parabolicSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(0.34, result);
    }
    @Test
    public void LocalResistanceExactValue () {
        ParabolicSuddenExpansion parabolicSuddenExpansion =
                new ParabolicSuddenExpansion(1.009253009, 1.128379167,
                        15e-6, 1);
        double result = parabolicSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(0.509999999626242, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation1() {
        ParabolicSuddenExpansion parabolicSuddenExpansion =
                new ParabolicSuddenExpansion(0.731273279, 1.128379167,
                        15e-6, 1);
        double result = parabolicSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(1.0640000001679866, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation2() {
        ParabolicSuddenExpansion parabolicSuddenExpansion =
                new ParabolicSuddenExpansion(0.996557497, 1.128379167,
                        15e-6, 1);
        double result = parabolicSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(0.5339999999042387, result);
    }
}
