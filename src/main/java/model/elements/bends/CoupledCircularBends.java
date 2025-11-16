package model.elements.bends;

import model.elements.utils.BendCoefficients;
import model.elements.utils.TubeCoefficients;

public class CoupledCircularBends extends Bend {
    private final double absolutRoughness;
    private final double re;
    private final double diameter;

    public CoupledCircularBends(double diameter, double bendAngle, double bendCurvatureRadius, double absolutRoughness,
                                double re) {
        super("Сопряженные отводы (круглые)", bendAngle, bendCurvatureRadius);
        this.absolutRoughness = absolutRoughness;
        this.re = re;
        this.diameter = diameter;
        validateParameters();
    }

    @Override
    public double calculateHydraulicResistance() {
        double A =
        double localResistanceCoefficient;
        double frictionResistanceCoefficient;
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
