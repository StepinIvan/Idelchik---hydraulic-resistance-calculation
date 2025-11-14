package model.calculation;

import model.elements.areaChanges.AreaChange;
import model.elements.areaChanges.ParabolicSuddenAreaChange;
import model.elements.areaChanges.PowerLowSuddenAreaChange;
import model.elements.areaChanges.UniformSuddenAreaChange;
import model.elements.utils.AreaChangeCoefficients;
import model.elements.utils.BendCoefficients;
import model.elements.utils.TubeCoefficients;

public class CalculationResult {
    public static void main(String[] args) {
        TubeCoefficients.calculateEvenGrainedPipeLambda(3000,0.045);
        TubeCoefficients.calculateSmoothPipeLambda(3000);
        BendCoefficients.calculateKDelta(0.51,41000, 0.01);
        UniformSuddenAreaChange areaChange = new UniformSuddenAreaChange(1,1.41,15e-6,1,50000);
        areaChange.calculateHydraulicResistance();
        PowerLowSuddenAreaChange areaChange1 = new PowerLowSuddenAreaChange(1,1.4142135624,15e-6,1,50000,2);
        areaChange1.calculateHydraulicResistance();
        ParabolicSuddenAreaChange areaChange2 = new ParabolicSuddenAreaChange(1,1.4142135624,15e-6, 1., 50000);
        areaChange2.calculateHydraulicResistance();
    }
}
