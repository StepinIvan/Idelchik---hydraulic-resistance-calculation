package model.calculation;

import model.elements.areaChanges.*;
import model.elements.utils.BendCoefficients;
import model.elements.utils.TubeCoefficients;

public class CalculationResult {
    public static void main(String[] args) {
        TubeCoefficients.calculateEvenGrainedPipeLambda(3000,0.045);
        TubeCoefficients.calculateSmoothPipeLambda(3000);
        BendCoefficients.calculateKDelta(0.51,41000, 0.01);
        UniformSuddenExpansion areaChange = new UniformSuddenExpansion(1,1.41,15e-6,1);
        areaChange.calculateHydraulicResistance(50000);
        PowerLowSuddenExpansion areaChange1 = new PowerLowSuddenExpansion(1,1.4142135624,15e-6,1,50000,2);
        areaChange1.calculateHydraulicResistance();
        ParabolicSuddenExpansion areaChange2 = new ParabolicSuddenExpansion(1,1.4142135624,15e-6, 1., 50000);
        areaChange2.calculateHydraulicResistance();
        SharpSuddenContraction areaChange3 = new SharpSuddenContraction(1,1.4142135624,15e-6, 1., 50000);
        areaChange3.calculateHydraulicResistance();
        EasedSuddenContraction areaChange4 = new EasedSuddenContraction(1,1.4142135624,15e-6, 1., 50000,
                0.04);
        areaChange4.calculateHydraulicResistance();
        BevelSuddenContraction areaChange5 = new BevelSuddenContraction(1,1.4142135624,15e-6, 1., 50000,
                40, 0.025);
        areaChange5.calculateHydraulicResistance();
    }
}
