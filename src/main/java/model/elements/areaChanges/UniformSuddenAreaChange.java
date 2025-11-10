package model.elements.areaChanges;

import model.elements.bends.BendType;

public class UniformSuddenAreaChange extends AreaChange {
    private final double absolutRoughness;
    private final double re;

    public UniformSuddenAreaChange(double smallerDiameter, double largerDiameter, double absolutRoughness,
                                   double re) {
        super("Расширение потока с равномерным распределением скоростей", smallerDiameter,
                largerDiameter);
        this.absolutRoughness = absolutRoughness;
        this.re = re;
        validateParameters();
    }

    @Override
    public double calculateHydraulicResistance() {
        double localResistanceCoefficient = 0;
        double frictionResistanceCoefficient = 0;
        return localResistanceCoefficient + frictionResistanceCoefficient;
    }

    @Override
    public String getElementType() {
        return AreaChangeType.SUDDEN_AREA_CHANGE.toString();
    }

    @Override
    public void validateParameters() {
    }
}
