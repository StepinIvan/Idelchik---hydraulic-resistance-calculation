import model.elements.areaChanges.SharpSuddenContraction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharpSuddenContractionTest {
    @Test
    public void LocalResistanceLeftBoard () {
        SharpSuddenContraction sharpSuddenContraction =
                new SharpSuddenContraction(1.,1e300,15e-6, 1.);
        double result = sharpSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.5, result);
    }
    @Test
    public void LocalResistanceRightBoard () {
        SharpSuddenContraction sharpSuddenContraction =
                new SharpSuddenContraction(2.,1.,15e-6, 1.);
        double result = sharpSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.0, result);
    }
    @Test
    public void LocalResistanceExactValue () {
        SharpSuddenContraction sharpSuddenContraction =
                new SharpSuddenContraction(0.713649646,1.128379167,
                        15e-6, 1.);
        double result = sharpSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.3400000001909037, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation1() {
        SharpSuddenContraction sharpSuddenContraction =
                new SharpSuddenContraction(0.564189584,1.128379167,
                        15e-6, 1.);
        double result = sharpSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.4037499998116768, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation2() {
        SharpSuddenContraction sharpSuddenContraction =
                new SharpSuddenContraction(1.058513486,1.128379167,
                        15e-6, 1.);
        double result = sharpSuddenContraction.calculateLocalResistanceCoefficient();
        assertEquals(0.10119999958481239, result);
    }
}
