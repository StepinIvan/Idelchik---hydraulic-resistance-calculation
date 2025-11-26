package model.calculation;

import model.elements.areaChanges.*;
import model.elements.utils.BendCoefficients;
import model.elements.utils.TubeCoefficients;

public class CalculationResult {
    public static void main(String[] args) {
        TubeCoefficients.calculateEvenGrainedPipeLambda(3000,0.045);
        TubeCoefficients.calculateSmoothPipeLambda(3000);
        BendCoefficients.calculateKDelta(0.51,41000, 0.01);
        UniformSuddenExpansion areaChange = new UniformSuddenExpansion(1,10,15e-6,1);
        areaChange.calculateHydraulicResistance(1);
        PowerLowSuddenExpansion areaChange1 = new PowerLowSuddenExpansion(1,1.4142135624,15e-6,1,50000);
        areaChange1.calculateHydraulicResistance(100);
        ParabolicSuddenExpansion areaChange2 = new ParabolicSuddenExpansion(1,1.4142135624,15e-6, 1.);
        areaChange2.calculateHydraulicResistance(1);
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
