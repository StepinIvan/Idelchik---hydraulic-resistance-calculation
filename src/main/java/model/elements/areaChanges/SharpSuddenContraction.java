package model.elements.areaChanges;

import lombok.Getter;
import model.elements.utils.AreaChangeCoefficients;
import model.elements.utils.LinearInterpolator;
import model.elements.utils.TubeCoefficients;

public class SharpSuddenContraction extends AreaChange {
    @Getter
    private final double smallArea;
    @Getter
    private final double largeArea;
    private final double absolutRoughness;
    @Getter
    private final double areaRatio;
    @Getter
    private double lambda;

    public SharpSuddenContraction(double smallerDiameter, double largerDiameter, double absolutRoughness,
                                  double length) {
        super("Сужение (внезапное) потока с острой кромкой", smallerDiameter,
                largerDiameter, length);
        this.absolutRoughness = absolutRoughness;
        this.smallArea = Math.PI * Math.pow(smallerDiameter, 2.) / 4.;
        this.largeArea = Math.PI * Math.pow(largerDiameter, 2.) / 4.;
        areaRatio = smallArea / largeArea;
        validateParameters();
    }

    public double calculateHydraulicResistance(double re) {
        double localResistanceCoefficient = calculateLocalResistanceCoefficient();
        double frictionResistanceCoefficient = calculateFrictionResistanceCoefficient(re);
        return localResistanceCoefficient + frictionResistanceCoefficient;
    }
    public double calculateLocalResistanceCoefficient() {
        LinearInterpolator linePredictionASharpContraction = AreaChangeCoefficients.getLinePredictionASharpContraction();
        return 0.5 * linePredictionASharpContraction.interpolate(areaRatio);
    }

    public double calculateFrictionResistanceCoefficient(double re) {
        double relativeRoughness = absolutRoughness / largerDiameter;
        if (absolutRoughness == 0) {
            lambda = TubeCoefficients.calculateSmoothPipeLambda(re);
        } else {
            lambda = TubeCoefficients.calculateEvenGrainedPipeLambda(re, relativeRoughness);
            //TODO Add calculation of friction factor for not even grained roughness;
        }
        return lambda * length / smallerDiameter;
    }
    @Override
    public String getElementType() {
        return AreaChangeType.SUDDEN_AREA_CHANGE.toString();
    }

    @Override
    public void validateParameters() {
    }
}
