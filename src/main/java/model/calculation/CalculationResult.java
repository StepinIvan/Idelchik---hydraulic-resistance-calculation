package model.calculation;

import model.elements.bends.utils.BendCoefficients;
import model.elements.bends.utils.Functions;
import model.elements.bends.utils.TubeCoefficients;

public class CalculationResult {
    public static void main(String[] args) {
        TubeCoefficients.calculateEvenGrainedPipeLambda(3000,0.045);
        TubeCoefficients.calculateSmoothPipeLambda(3000);
        BendCoefficients.calculateKDelta(0.51,41000, 0.01);
    }
}
