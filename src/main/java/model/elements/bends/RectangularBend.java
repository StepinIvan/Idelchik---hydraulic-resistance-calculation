package model.elements.bends;

import model.elements.bends.utils.BendCoefficients;
import model.elements.bends.utils.TubeCoefficients;

public class RectangularBend extends Bend {
    private final double absolutRoughness;
    private final double re;
    private final double a0;
    private final double b0;

    public RectangularBend(double a0, double b0, double bendAngle, double bendCurvatureRadius, double absolutRoughness,
                           double re) {
        super("Прямоугольный отвод", bendAngle, bendCurvatureRadius);
        this.absolutRoughness = absolutRoughness;
        this.re = re;
        this.a0 = a0;
        this.b0 = b0;
        validateParameters();
    }

    @Override
    public double calculateHydraulicResistance() {
        double A1 = BendCoefficients.calculateA1(bendAngle);
        double B1 = BendCoefficients.calculateB1(bendCurvatureRadius / b0);
        double C1 = BendCoefficients.calculateC1(a0 / b0);
        double localResistanceCoefficient;
        double frictionResistanceCoefficient;
        double lambda;
        double hydraulicDiameter = 2 * a0 * b0 / (a0 + b0);
        if (absolutRoughness == 0) {
            localResistanceCoefficient = A1 * B1 * C1;
            lambda = TubeCoefficients.calculateSmoothPipeLambda(re);
        } else {
            lambda = TubeCoefficients.calculateEvenGrainedPipeLambda(re, absolutRoughness /
                    hydraulicDiameter);//TODO Add calculation of friction factor for not even grained roughness;
            double kDelta = BendCoefficients.calculateKDelta(bendCurvatureRadius / hydraulicDiameter, re,
                    absolutRoughness);
            double kRe = BendCoefficients.calculateKRe(bendCurvatureRadius / hydraulicDiameter, re);
            localResistanceCoefficient = kDelta * kRe * A1 * B1 * C1;
        }
        frictionResistanceCoefficient = 0.0175 * bendAngle * lambda * bendCurvatureRadius / hydraulicDiameter;
        return localResistanceCoefficient + frictionResistanceCoefficient;
    }

    @Override
    public String getElementType() {
        return BendType.RECTANGULAR.toString();
    }

    @Override
    public void validateParameters() {
    }
}
