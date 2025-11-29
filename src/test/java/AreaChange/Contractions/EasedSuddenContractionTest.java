package AreaChange.Contractions;

import model.elements.areaChanges.EasedSuddenContraction;
import model.elements.areaChanges.SharpSuddenContraction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EasedSuddenContractionTest {
    @Test
    public void LocalResistanceLeftBoard () {
        EasedSuddenContraction easedSuddenContraction =
                new EasedSuddenContraction(1.,1e300,15e-6, 1.,0.);
        double result = easedSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.5, result);
    }
    @Test
    public void LocalResistanceRightBoard () {
        EasedSuddenContraction easedSuddenContraction =
                new EasedSuddenContraction(1.,1e300,15e-6, 1.,0.25);
        double result = easedSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.03, result);
    }
    @Test
    public void LocalResistanceExactValue () {
        EasedSuddenContraction easedSuddenContraction =
                new EasedSuddenContraction(1.,1e300,15e-6, 1.,0.04);
        double result = easedSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.26, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation1() {
        EasedSuddenContraction easedSuddenContraction =
                new EasedSuddenContraction(1.,1e300,15e-6, 1.,0.022);
        double result = easedSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.358, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation2() {
        EasedSuddenContraction easedSuddenContraction =
                new EasedSuddenContraction(1.,1e300,15e-6, 1.,0.088);
        double result = easedSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.138, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation3() {
        EasedSuddenContraction easedSuddenContraction =
                new EasedSuddenContraction(0.618038723,1.128379167,15e-6,
                        1.,0.045);
        double result = easedSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.1836000000365967, result);
    }
}
