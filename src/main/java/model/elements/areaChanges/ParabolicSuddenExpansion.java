package model.elements.areaChanges;

import lombok.Getter;
import model.elements.utils.AreaChangeCoefficients;
import model.elements.utils.Functions;
import model.elements.utils.LinearInterpolator;
import model.elements.utils.TubeCoefficients;

public class ParabolicSuddenExpansion extends AreaChange {
    private final double smallArea;
    private final double largeArea;
    private final double absolutRoughness;
    @Getter
    private double lambda;
    @Getter
    private final double areaRatio;
    @Getter
    private double localResistanceCoefficient;
    @Getter
    private double frictionResistanceCoefficient;

    public ParabolicSuddenExpansion(double smallerDiameter, double largerDiameter, double absolutRoughness,
                                    double length) {
        super("Расширение (внезапное) потока с параболическим распределением скоростей", smallerDiameter,
                largerDiameter, length);
        this.absolutRoughness = absolutRoughness;
        this.smallArea = Math.PI * Math.pow(smallerDiameter, 2.) / 4.;
        this.largeArea = Math.PI * Math.pow(largerDiameter, 2.) / 4.;
        areaRatio = smallArea / largeArea;
        validateParameters();
    }

    public double calculateHydraulicResistance(double re) {
        localResistanceCoefficient = calculateLocalResistanceCoefficient();
        frictionResistanceCoefficient = calculateFrictionResistanceCoefficient(re);
        return localResistanceCoefficient + frictionResistanceCoefficient / Math.pow((largeArea / smallArea), 2.);
    }
    public double calculateLocalResistanceCoefficient() {
        LinearInterpolator linePredictionKsiMParabolic = AreaChangeCoefficients.getLinePredictionKsiMParabolic();
        return linePredictionKsiMParabolic.interpolate(areaRatio);
    }

    public double calculateFrictionResistanceCoefficient(double re) {
        double relativeRoughness = absolutRoughness / largerDiameter;
        if (absolutRoughness == 0) {
            lambda = TubeCoefficients.calculateSmoothPipeLambda(re);
        } else {
            lambda = TubeCoefficients.calculateEvenGrainedPipeLambda(re, relativeRoughness);
            //TODO Add calculation of friction factor for not even grained roughness;
        }
        return lambda * length / largerDiameter;
    }
    @Override
    public String getElementType() {
        return AreaChangeType.PARABOLIC_SUDDEN_EXPANSION.toString();
    }

    @Override
    public void validateParameters() {
    }
}
