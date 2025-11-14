package model.elements.areaChanges;

import model.elements.utils.AreaChangeCoefficients;
import model.elements.utils.TubeCoefficients;

public class UniformSuddenExpansion extends AreaChange {
    private final double smallArea;
    private final double largeArea;
    private final double absolutRoughness;
    private final double re;

    public UniformSuddenExpansion(double smallerDiameter, double largerDiameter, double absolutRoughness, double length,
                                  double re) {
        super("Расширение потока с равномерным распределением скоростей", smallerDiameter,
                largerDiameter, length);
        this.absolutRoughness = absolutRoughness;
        this.re = re;
        this.smallArea = Math.PI * Math.pow(smallerDiameter, 2.) / 4.;
        this.largeArea = Math.PI * Math.pow(largerDiameter, 2.) / 4.;
        validateParameters();
    }

    @Override
    public double calculateHydraulicResistance() {
        double localResistanceCoefficient = AreaChangeCoefficients.calculateUniformSuddenExpansionKsiM(re,
                smallArea / largeArea);
        double lambda;
        if (absolutRoughness == 0) {
            lambda = TubeCoefficients.calculateSmoothPipeLambda(re);
        } else {
            lambda = TubeCoefficients.calculateEvenGrainedPipeLambda(re, absolutRoughness / largerDiameter);
            //TODO Add calculation of friction factor for not even grained roughness;
        }
        double frictionResistanceCoefficient = lambda * length / largerDiameter;
        return localResistanceCoefficient + frictionResistanceCoefficient / Math.pow((largeArea / smallArea), 2.);
    }

    @Override
    public String getElementType() {
        return AreaChangeType.SUDDEN_AREA_CHANGE.toString();
    }

    @Override
    public void validateParameters() {
    }
}
