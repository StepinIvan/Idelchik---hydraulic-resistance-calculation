package model.elements.areaChanges;

import model.elements.utils.AreaChangeCoefficients;
import model.elements.utils.TubeCoefficients;

public class PowerLowSuddenAreaChange extends AreaChange {
    private final double smallArea;
    private final double largeArea;
    private final double absolutRoughness;
    private final double re;

    public PowerLowSuddenAreaChange(double smallerDiameter, double largerDiameter, double absolutRoughness, double length,
                                    double re) {
        super("Расширение потока с равномерным распределением скоростей", smallerDiameter,
                largerDiameter, length);
        this.absolutRoughness = absolutRoughness;
        this.re = re;
        this.smallArea = Math.PI * Math.pow(smallerDiameter, 2.) / 4.;
        this.largeArea = Math.PI * Math.pow(largerDiameter, 2.) / 4.;
        validateParameters();
    }

    @Override
    public double calculateHydraulicResistance() {
        double m = 0;
        double N = Math.pow(2 * m + 1, 2) * (m + 1) / (4 * Math.pow(m, 2) * (m + 2));
        double M = Math.pow(2 * m + 1, 3) * Math.pow((m + 1), 3) / (4 * Math.pow(m, 4) * (2 * m + 3) * (m + 3));
        double localResistanceCoefficient = 1 / (Math.pow(smallArea / largeArea, 2)) + N + 2*M/(smallArea / largeArea);
        double lambda;
        if (absolutRoughness == 0) {
            lambda = TubeCoefficients.calculateSmoothPipeLambda(re);
        } else {
            lambda = TubeCoefficients.calculateEvenGrainedPipeLambda(re, absolutRoughness / largerDiameter);
            //TODO Add calculation of friction factor for not even grained roughness;
        }
        double frictionResistanceCoefficient = lambda * length / largerDiameter;
        return localResistanceCoefficient + frictionResistanceCoefficient / Math.pow((largeArea / smallArea), 2.);
    }

    @Override
    public String getElementType() {
        return AreaChangeType.SUDDEN_AREA_CHANGE.toString();
    }

    @Override
    public void validateParameters() {
    }
}
