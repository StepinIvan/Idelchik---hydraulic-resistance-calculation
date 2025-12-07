package Bends.Circular;

import model.elements.utils.BendCoefficients;
import model.elements.utils.LinearInterpolator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CircularBendTests {
    @Test
    public void A1LeftBoard () {
        LinearInterpolator linePredictionA1 = BendCoefficients.getLinePredictionA1();
        double result = linePredictionA1.interpolate(-10);
        assertEquals(0., result);
    }
    @Test
    public void A1RightBoard () {
        LinearInterpolator linePredictionA1 = BendCoefficients.getLinePredictionA1();
        double result = linePredictionA1.interpolate(200);
        assertEquals(1.4, result);
    }
    @Test
    public void A1ExactValue () {
        LinearInterpolator linePredictionA1 = BendCoefficients.getLinePredictionA1();
        double result = linePredictionA1.interpolate(110);
        assertEquals(1.13, result);
    }
    @Test
    public void A1LinearInterpolation () {
        LinearInterpolator linePredictionA1 = BendCoefficients.getLinePredictionA1();
        double result = linePredictionA1.interpolate(132);
        assertEquals(1.208, result);
    }
}
