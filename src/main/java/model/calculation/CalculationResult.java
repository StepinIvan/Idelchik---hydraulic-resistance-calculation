package model.calculation;

import model.elements.areaChanges.ParabolicSuddenExpansion;
import model.elements.areaChanges.PowerLowSuddenExpansion;
import model.elements.areaChanges.UniformSuddenExpansion;
import model.elements.utils.BendCoefficients;
import model.elements.utils.TubeCoefficients;

public class CalculationResult {
    public static void main(String[] args) {
        TubeCoefficients.calculateEvenGrainedPipeLambda(3000,0.045);
        TubeCoefficients.calculateSmoothPipeLambda(3000);
        BendCoefficients.calculateKDelta(0.51,41000, 0.01);
        UniformSuddenExpansion areaChange = new UniformSuddenExpansion(1,1.41,15e-6,1,50000);
        areaChange.calculateHydraulicResistance();
        PowerLowSuddenExpansion areaChange1 = new PowerLowSuddenExpansion(1,1.4142135624,15e-6,1,50000,2);
        areaChange1.calculateHydraulicResistance();
        ParabolicSuddenExpansion areaChange2 = new ParabolicSuddenExpansion(1,1.4142135624,15e-6, 1., 50000);
        areaChange2.calculateHydraulicResistance();
    }
}
