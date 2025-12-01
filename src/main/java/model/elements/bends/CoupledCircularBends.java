package model.elements.bends;

import model.elements.utils.BendCoefficients;

public class CoupledCircularBends extends Bend {
    private final double absolutRoughness;
    private final double diameter;
    private final double lDRatio;

    public CoupledCircularBends(double diameter, double bendAngle, double bendCurvatureRadius, double absolutRoughness,
                                double lDRatio) {
        super("Сопряженные отводы (круглые) S образной формы", bendAngle, bendCurvatureRadius);
        this.absolutRoughness = absolutRoughness;
        this.diameter = diameter;
        this.lDRatio = lDRatio;
        validateParameters();
    }

    public double calculateHydraulicResistance() {
        return 0;
    }

    @Override
    public String getElementType() {
        return BendType.CIRCULAR.toString();
    }

    @Override
    public void validateParameters() {
    }
}
