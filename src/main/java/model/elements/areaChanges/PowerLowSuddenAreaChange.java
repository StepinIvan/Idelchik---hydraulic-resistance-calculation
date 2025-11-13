package model.elements.areaChanges;

import model.elements.utils.AreaChangeCoefficients;
import model.elements.utils.Functions;
import model.elements.utils.TubeCoefficients;

public class PowerLowSuddenAreaChange extends AreaChange {
    private final double smallArea;
    private final double largeArea;
    private final double absolutRoughness;
    private final double re;
    private final double m;

    public PowerLowSuddenAreaChange(double smallerDiameter, double largerDiameter, double absolutRoughness, double length,
                                    double re, double m) {
        super("Расширение потока с равномерным распределением скоростей", smallerDiameter,
                largerDiameter, length);
        this.absolutRoughness = absolutRoughness;
        this.re = re;
        this.smallArea = Math.PI * Math.pow(smallerDiameter, 2.) / 4.;
        this.largeArea = Math.PI * Math.pow(largerDiameter, 2.) / 4.;
        this.m = m;//TODO restriction for m. m must be greater or equal 1
        validateParameters();
    }

    @Override
    public double calculateHydraulicResistance() {
        double localResistanceCoefficient = AreaChangeCoefficients.calculatePowerLowSuddenAreaChangeKsiM(
                smallArea / largeArea, m);
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
