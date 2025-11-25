package model.elements.areaChanges;

import lombok.Getter;
import model.elements.utils.AreaChangeCoefficients;
import model.elements.utils.Functions;
import model.elements.utils.LinearInterpolator;
import model.elements.utils.TubeCoefficients;

public class PowerLowSuddenExpansion extends AreaChange {
    @Getter
    private final double smallArea;
    @Getter
    private final double largeArea;
    private final double absolutRoughness;
    private final double m;
    @Getter
    private double localResistanceCoefficient;
    @Getter
    private double frictionResistanceCoefficient;
    @Getter
    private double lambda;
    private double areaRatio;

    public PowerLowSuddenExpansion(double smallerDiameter, double largerDiameter, double absolutRoughness, double length,
                                   double m) {
        super("Расширение (внезапное) потока с распределением скоростей по степенному закону", smallerDiameter,
                largerDiameter, length);
        this.absolutRoughness = absolutRoughness;
        this.smallArea = Math.PI * Math.pow(smallerDiameter, 2.) / 4.;
        this.largeArea = Math.PI * Math.pow(largerDiameter, 2.) / 4.;
        this.m = m;//TODO restriction for m. m must be greater or equal 1
        areaRatio = smallArea / largeArea;
        validateParameters();
    }

    public double calculateHydraulicResistance(double re) {
        localResistanceCoefficient = calculateLocalResistanceCoefficient();
        frictionResistanceCoefficient = calculateFrictionResistanceCoefficient(re);
        return localResistanceCoefficient + frictionResistanceCoefficient / Math.pow((largeArea / smallArea), 2.);
    }

    public double calculateLocalResistanceCoefficient() {
        double[] f0f2Ratio = AreaChangeCoefficients.getF0_F2_RATIO_POWER();
        double[] mValues = AreaChangeCoefficients.getM_VALUES();
        double[][] ksiValuesPower = AreaChangeCoefficients.getKSI_M_VALUES_POWER();
        //Граничные значения для отношения площадей
        double areaRatioMin = f0f2Ratio[0];
        double areaRatioMax = f0f2Ratio[f0f2Ratio.length - 1];

        // Определяем индексы для m с учетом границ
        int[] mIndices;
        if (m < mValues[0]) {
            // За левой границей массива m
            mIndices = new int[]{0, 0};
        } else if (m > mValues[mValues.length - 1]) {
            // За правой границей массива m
            mIndices = new int[]{mValues.length - 1, mValues.length - 1};
        } else {
            // В пределах массива m
            mIndices = Functions.lineSearchNeighborIndices(m, mValues);
        }

        // Определяем индексы для отношения площадей с учетом границ
        int[] areaRatioIndices;
        if (areaRatio < areaRatioMin) {
            // За левой границей массива отношения площадей
            areaRatioIndices = new int[]{0, 0};
        } else if (areaRatio > areaRatioMax) {
            // За правой границей массива отношения площадей
            areaRatioIndices = new int[]{f0f2Ratio.length - 1, f0f2Ratio.length - 1};
        } else {
            // В пределах массива отношения площадей
            areaRatioIndices = Functions.binarySearchNearestIndices(areaRatio, f0f2Ratio);
        }

        // Значения отношения площадей и m для интерполяции
        double m1 = mValues[mIndices[0]];
        double m2 = mValues[mIndices[1]];
        double areaRatio1 = f0f2Ratio[areaRatioIndices[0]];
        double areaRatio2 = f0f2Ratio[areaRatioIndices[1]];

        // Значения ξм из таблицы
        double q11 = ksiValuesPower[mIndices[0]][areaRatioIndices[0]];
        double q12 = ksiValuesPower[mIndices[0]][areaRatioIndices[1]];
        double q21 = ksiValuesPower[mIndices[1]][areaRatioIndices[0]];
        double q22 = ksiValuesPower[mIndices[1]][areaRatioIndices[1]];

        if (mIndices[0] == mIndices[1] && areaRatioIndices[0] == areaRatioIndices[1]) {
            // Оба параметра за границами или на границах - возвращаем угловое значение
            return ksiValuesPower[mIndices[0]][areaRatioIndices[0]];
        } else if (mIndices[0] == mIndices[1]) {
            // m за границами, интерполируем только по отношению площадей
            return Functions.interpolateLinear(areaRatio1, areaRatio2, q11, q12, areaRatio);
        } else if (areaRatioIndices[0] == areaRatioIndices[1]) {
            // Отношение площадей за границами, интерполируем только по m
            return Functions.interpolateLinear(m1, m2, q11, q21, m);
        } else {
            // Оба параметра в пределах массивов - билинейная интерполяция
            return Functions.bilinearInterpolation(m, areaRatio,
                    m1, m2, areaRatio1, areaRatio2,
                    q11, q12, q21, q22);
        }
    }

    public double calculateFrictionResistanceCoefficient(double re) {
        double relativeRoughness = absolutRoughness / largerDiameter;
        if (absolutRoughness == 0) {
            lambda = TubeCoefficients.calculateSmoothPipeLambda(re);
        } else {
            lambda = TubeCoefficients.calculateEvenGrainedPipeLambda(re, relativeRoughness);
            //TODO Add calculation of friction factor for not even grained roughness;
        }
        return lambda * length / largerDiameter;
    }

    @Override
    public String getElementType() {
        return AreaChangeType.POWER_LAW_SUDDEN_EXPANSION.toString();
    }

    @Override
    public void validateParameters() {
    }
}
