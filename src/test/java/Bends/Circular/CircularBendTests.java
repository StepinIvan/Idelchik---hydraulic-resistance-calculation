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
    @Test
    public void B1LeftBoard () {
        LinearInterpolator linePredictionB1 = BendCoefficients.getLinePredictionB1();
        double result = linePredictionB1.interpolate(0.2);
        assertEquals(1.18, result);
    }
    @Test
    public void B1RightBoard () {
        LinearInterpolator linePredictionB1 = BendCoefficients.getLinePredictionB1();
        double result = linePredictionB1.interpolate(140);
        assertEquals(0.03, result);
    }
    @Test
    public void B1ExactValue () {
        LinearInterpolator linePredictionB1 = BendCoefficients.getLinePredictionB1();
        double result = linePredictionB1.interpolate(20);
        assertEquals(0.05, result);
    }
    @Test
    public void B1LinearInterpolation () {
        LinearInterpolator linePredictionB1 = BendCoefficients.getLinePredictionB1();
        double result = linePredictionB1.interpolate(22);
        assertEquals(0.048, result);
    }
    @Test
    public void C1LeftBoard () {
        LinearInterpolator linePredictionC1 = BendCoefficients.getLinePredictionC1();
        double result = linePredictionC1.interpolate(0.2);
        assertEquals(1.3, result);
    }
    @Test
    public void C1RightBoard () {
        LinearInterpolator linePredictionC1 = BendCoefficients.getLinePredictionC1();
        double result = linePredictionC1.interpolate(10);
        assertEquals(1., result);
    }
    @Test
    public void C1ExactValue () {
        LinearInterpolator linePredictionC1 = BendCoefficients.getLinePredictionC1();
        double result = linePredictionC1.interpolate(6);
        assertEquals(0.98, result);
    }
    @Test
    public void C1Interpolation1 () {
        LinearInterpolator linePredictionC1 = BendCoefficients.getLinePredictionC1();
        double result = linePredictionC1.interpolate(1.1);
        assertEquals(0.98, result);
    }
    @Test
    public void C1Interpolation2 () {
        LinearInterpolator linePredictionC1 = BendCoefficients.getLinePredictionC1();
        double result = linePredictionC1.interpolate(5.7);
        assertEquals(0.971, result);
    }
    @Test
    public void kDeltaLeftUpperBoard () {
        double result = BendCoefficients.calculateKDelta(0.4, 10,
                0.);
        assertEquals(1.0, result);
    }
    @Test
    public void kDeltaFirstRowTest1() {
        double result = BendCoefficients.calculateKDelta(0.51, 3500,
                0.);
        assertEquals(1.0, result);
    }
    @Test
    public void kDeltaFirstRowTest2() {
        double result = BendCoefficients.calculateKDelta(0.51, 45000,
                0.);
        assertEquals(1.0, result);
    }
    @Test
    public void kDeltaFirstRowTest3() {
        double result = BendCoefficients.calculateKDelta(0.58, 4000,
                0.);
        assertEquals(1.0, result);
    }
    @Test
    public void kDeltaFirstRowTest4() {
        double result = BendCoefficients.calculateKDelta(0.58, 45000,
                0.);
        assertEquals(1.0, result);
    }
    @Test
    public void kDeltaFirstRowTest5() {
        double result = BendCoefficients.calculateKDelta(0.58, 2000000,
                0.);
        assertEquals(1.0, result);
    }
    @Test
    public void kDeltaSecondRowTest1() {
        double result = BendCoefficients.calculateKDelta(0.52, 3500,
                0.0001);
        assertEquals(1.0, result);
    }
    @Test
    public void kDeltaSecondRowTest2() {
        double result = BendCoefficients.calculateKDelta(0.52, 45000,
                0.0001);
        assertEquals(1.05, result);
    }
    @Test
    public void kDeltaSecondRowTest3() {
        double result = BendCoefficients.calculateKDelta(0.58, 3500,
                0.0001);
        assertEquals(1., result);
    }
    @Test
    public void kDeltaSecondRowTest4() {
        double result = BendCoefficients.calculateKDelta(0.58, 2000000,
                0.0001);
        assertEquals(1.1, result);
    }
    @Test
    public void kDeltaThirdRowTest1() {
        double result = BendCoefficients.calculateKDelta(0.52, 3500,
                0.01);
        assertEquals(1., result);
    }
    @Test
    public void kDeltaThirdRowTest2() {
        double result = BendCoefficients.calculateKDelta(0.52, 45000,
                0.01);
        assertEquals(1.5, result);
    }
    @Test
    public void kDeltaThirdRowTest3() {
        double result = BendCoefficients.calculateKDelta(0.58, 3500,
                0.01);
        assertEquals(1., result);
    }
    @Test
    public void kDeltaThirdRowTest4() {
        double result = BendCoefficients.calculateKDelta(0.58, 45000,
                0.01);
        assertEquals(2., result);
    }
    @Test
    public void kDeltaThirdRowTest5() {
        double result = BendCoefficients.calculateKDelta(0.58, 2000000,
                0.01);
        assertEquals(2., result);
    }
    //TODO for roughness ratio
//    @Test
//    public void kDeltaSecondRowTest4() {
//        double result = BendCoefficients.calculateKDelta(0.58, 3500,
//                0.0001);
//        assertEquals(1., result);
//    }
}
