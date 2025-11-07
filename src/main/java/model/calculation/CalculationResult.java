package model.calculation;

import model.elements.bends.utils.Functions;
import model.elements.bends.utils.TubeCoefficients;

public class CalculationResult {
    public static void main(String[] args) {
        TubeCoefficients.calculateEvenGrainedPipeLambda(3000,0.045);
    }
}
