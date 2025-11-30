package model.elements.bends;

import model.elements.utils.BendCoefficients;
import model.elements.utils.TubeCoefficients;

public class CoupledCircularBends extends Bend {
    private final double absolutRoughness;
    private final double re;
    private final double diameter;
    private final double lDRatio;

    public CoupledCircularBends(double diameter, double bendAngle, double bendCurvatureRadius, double absolutRoughness,
                                double re, double lDRatio) {
        super("Сопряженные отводы (круглые) S образной формы", bendAngle, bendCurvatureRadius);
        this.absolutRoughness = absolutRoughness;
        this.re = re;
        this.diameter = diameter;
        this.lDRatio = lDRatio;
        validateParameters();
    }

    public double calculateHydraulicResistance() {
        CircularBend circularBend = new CircularBend(diameter,bendAngle,bendCurvatureRadius,absolutRoughness, re);
        double A = BendCoefficients.calculateACoupledBends(bendAngle, lDRatio);
        double localResistanceCoefficient = A * circularBend.calculateLocalHydraulicResistance();
        double frictionResistanceCoefficient = 0;
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
