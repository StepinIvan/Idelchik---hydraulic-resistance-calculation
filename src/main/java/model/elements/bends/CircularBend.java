package model.elements.bends;

import model.elements.bends.utils.BendCoefficients;
import model.elements.bends.utils.TubeCoefficients;

public class CircularBend extends Bend {
    private final double absolutRoughness;
    private final double re;
    private final double diameter;

    public CircularBend(double diameter, double bendAngle, double bendCurvatureRadius, double absolutRoughness,
                        double re) {
        super("Круглый отвод", bendAngle, bendCurvatureRadius);
        this.absolutRoughness = absolutRoughness;
        this.re = re;
        this.diameter = diameter;
        validateParameters();
    }

    @Override
    public double calculateHydraulicResistance() {
        double A1 = BendCoefficients.calculateA1(bendAngle);
        double B1 = BendCoefficients.calculateB1(bendCurvatureRadius / diameter);
        double localResistanceCoefficient;
        double frictionResistanceCoefficient;
        double lambda;
        if (absolutRoughness == 0) {
            localResistanceCoefficient = A1 * B1; //C1 = 1 for circular bend
            lambda = TubeCoefficients.calculateSmoothPipeLambda(re);
        } else {
            lambda = TubeCoefficients.calculateEvenGrainedPipeLambda(re, absolutRoughness / diameter);
            //TODO Add calculation of friction factor for not even grained roughness;
            double kDelta = BendCoefficients.calculateKDelta(bendCurvatureRadius / diameter, re,
                    absolutRoughness);
            double kRe = BendCoefficients.calculateKRe(bendCurvatureRadius / diameter, re);
            localResistanceCoefficient = kDelta * kRe * A1 * B1;
        }
        frictionResistanceCoefficient = 0.0175 * bendAngle * lambda * bendCurvatureRadius / diameter;
        return localResistanceCoefficient + frictionResistanceCoefficient;
    }

    @Override
    public String getElementType() {
        return BendType.CIRCULAR.toString();
    }

    @Override
    public void validateParameters() {
    }
}
