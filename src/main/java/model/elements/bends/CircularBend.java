package model.elements.bends;

import lombok.Getter;
import model.elements.utils.BendCoefficients;
import model.elements.utils.LinearInterpolator;
import model.elements.utils.TubeCoefficients;

public class CircularBend extends Bend {
    private final double absolutRoughness;
    private final double diameter;
    @Getter
    private double relativeRoughness;
    @Getter
    private double lambda;

    public CircularBend(double diameter, double bendAngle, double bendCurvatureRadius, double absolutRoughness) {
        super("Круглый отвод", bendAngle, bendCurvatureRadius);
        this.absolutRoughness = absolutRoughness;
        this.diameter = diameter;
        relativeRoughness = absolutRoughness / diameter;
        validateParameters();
    }

    public double calculateHydraulicResistance(double re) {
        double localResistanceCoefficient = calculateLocalHydraulicResistance(re);
        double frictionResistanceCoefficient = calculateFrictionResistanceCoefficient(re);
        return localResistanceCoefficient + frictionResistanceCoefficient;
    }
    public double calculateLocalHydraulicResistance(double re) {
        LinearInterpolator linePredictionA1 = BendCoefficients.getLinePredictionA1();
        LinearInterpolator linePredictionB1 = BendCoefficients.getLinePredictionB1();
        double A1 = linePredictionA1.interpolate(bendAngle);
        double B1 = linePredictionB1.interpolate(bendCurvatureRadius / diameter);
        double localResistanceCoefficient;
        if (absolutRoughness == 0) {
            localResistanceCoefficient = A1 * B1; //C1 = 1 for circular bend
        } else {
            double kDelta = BendCoefficients.calculateKDelta(bendCurvatureRadius / diameter, re,
                    absolutRoughness);
            double kRe = BendCoefficients.calculateKRe(bendCurvatureRadius / diameter, re);
            localResistanceCoefficient = kDelta * kRe * A1 * B1;
        }
        return localResistanceCoefficient;
    }
    public double calculateFrictionResistanceCoefficient(double re) {
        if (absolutRoughness == 0) {
            lambda = TubeCoefficients.calculateSmoothPipeLambda(re);
        } else {
            lambda = TubeCoefficients.calculateEvenGrainedPipeLambda(re, relativeRoughness);
            //TODO Add calculation of friction factor for not even grained roughness;
        }
        return 0.0175 * bendAngle * lambda * bendCurvatureRadius / diameter;
    }
    @Override
    public String getElementType() {
        return BendType.CIRCULAR.toString();
    }

    @Override
    public void validateParameters() {
    }
}
