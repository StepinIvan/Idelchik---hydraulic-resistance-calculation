package model.elements.bends;

import lombok.Getter;
import model.elements.utils.BendCoefficients;
import model.elements.utils.LinearInterpolator;
import model.elements.utils.TubeCoefficients;

public class RectangularBend extends Bend {
    private final double absolutRoughness;
    private final double a0;
    private final double b0;
    @Getter
    private double relativeRoughness;
    @Getter
    private double lambda;
    @Getter
    private double hydraulicDiameter;

    public RectangularBend(double a0, double b0, double bendAngle, double bendCurvatureRadius, double absolutRoughness) {
        super("Прямоугольный отвод", bendAngle, bendCurvatureRadius);
        this.absolutRoughness = absolutRoughness;
        this.a0 = a0;
        this.b0 = b0;
        hydraulicDiameter = 2 * a0 * b0 / (a0 + b0);
        relativeRoughness = absolutRoughness / hydraulicDiameter;
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
        LinearInterpolator linePredictionC1 = BendCoefficients.getLinePredictionC1();
        double A1 = linePredictionA1.interpolate(bendAngle);
        double B1 = linePredictionB1.interpolate(bendCurvatureRadius / hydraulicDiameter);
        double C1 = linePredictionC1.interpolate(a0 / b0);
        double localResistanceCoefficient;
        if (absolutRoughness == 0) {
            localResistanceCoefficient = A1 * B1 * C1;
        } else {
            double kDelta = BendCoefficients.calculateKDelta(bendCurvatureRadius / hydraulicDiameter, re,
                    absolutRoughness);
            double kRe = BendCoefficients.calculateKRe(bendCurvatureRadius / hydraulicDiameter, re);
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
        return 0.0175 * bendAngle * lambda * bendCurvatureRadius / hydraulicDiameter;
    }
    @Override
    public String getElementType() {
        return BendType.RECTANGULAR.toString();
    }

    @Override
    public void validateParameters() {
    }
}
