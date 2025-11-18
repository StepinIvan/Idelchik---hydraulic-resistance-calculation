package model.elements.areaChanges;

import lombok.Getter;
import model.elements.utils.AreaChangeCoefficients;
import model.elements.utils.Functions;
import model.elements.utils.LinearInterpolator;
import model.elements.utils.TubeCoefficients;

public class UniformSuddenExpansion extends AreaChange {
    @Getter
    private final double smallArea;
    @Getter
    private final double largeArea;
    private final double absolutRoughness;
    @Getter
    private double localResistanceCoefficient;
    @Getter
    private double frictionResistanceCoefficient;
    @Getter
    private double lambda;

    public UniformSuddenExpansion(double smallerDiameter, double largerDiameter, double absolutRoughness,
                                  double length) {
        super("Расширение (внезапное) потока с равномерным распределением скоростей", smallerDiameter,
                largerDiameter, length);
        this.absolutRoughness = absolutRoughness;
        this.smallArea = Math.PI * Math.pow(smallerDiameter, 2.) / 4.;
        this.largeArea = Math.PI * Math.pow(largerDiameter, 2.) / 4.;
        validateParameters();
    }

    public double calculateHydraulicResistance(double re) {
        localResistanceCoefficient = calculateLocalResistanceCoefficient(re);
        frictionResistanceCoefficient = calculateFrictionResistanceCoefficient(re);
        return localResistanceCoefficient + frictionResistanceCoefficient / Math.pow((largeArea / smallArea), 2.);
    }

    public double calculateLocalResistanceCoefficient(double re) {
        double areaRatio = smallArea / largeArea;
        double[] reValues = AreaChangeCoefficients.getReValues();
        double[] f0f2Ratio = AreaChangeCoefficients.getF0_F2_RATIO_UNIFORM();
        double[][] ksiMValues = AreaChangeCoefficients.getKSI_M_VALUES();
        LinearInterpolator linePredictionSmallKsiM = AreaChangeCoefficients.getLinePredictionSmallKsiM();
        if (re <= 9.9) {
            return linePredictionSmallKsiM.interpolate(re);
        } else if (re > 9.9 && re < 10) {
            return Functions.interpolateLinear(9.9, 10, 30.0 / 9.9, 3.1, re);
        }
        //Граничные значения для Re
        double reMin = reValues[0];
        double reMax = reValues[reValues.length - 1];

        // Определяем индексы для отношения площадей с учетом границ
        int[] areaRatioIndices;
        if (areaRatio < f0f2Ratio[0]) {
            // За левой границей массива отношения площадей
            areaRatioIndices = new int[]{0, 0};
        } else if (areaRatio > f0f2Ratio[f0f2Ratio.length - 1]) {
            // За правой границей массива отношения площадей
            areaRatioIndices = new int[]{f0f2Ratio.length - 1, f0f2Ratio.length - 1};
        } else {
            // В пределах массива отношения площадей
            areaRatioIndices = Functions.lineSearchNeighborIndices(areaRatio, f0f2Ratio);
        }

        // Определяем индексы для Re с учетом границ
        int[] reIndices;
        if (re < reMin) {
            // За левой границей массива Re
            reIndices = new int[]{0, 0};
        } else if (re > reMax) {
            // За правой границей массива Re
            reIndices = new int[]{reValues.length - 1, reValues.length - 1};
        } else {
            // В пределах массива Re
            reIndices = Functions.binarySearchNearestIndices(re, reValues);
        }

        // Значения отношения площадей и Re для интерполяции
        double areaRatio1 = f0f2Ratio[areaRatioIndices[0]];
        double areaRatio2 = f0f2Ratio[areaRatioIndices[1]];
        double re1 = reValues[reIndices[0]];
        double re2 = reValues[reIndices[1]];

        // Значения ξм из таблицы
        double q11 = ksiMValues[areaRatioIndices[0]][reIndices[0]];
        double q12 = ksiMValues[areaRatioIndices[0]][reIndices[1]];
        double q21 = ksiMValues[areaRatioIndices[1]][reIndices[0]];
        double q22 = ksiMValues[areaRatioIndices[1]][reIndices[1]];

        if (areaRatioIndices[0] == areaRatioIndices[1] && reIndices[0] == reIndices[1]) {
            // Оба параметра за границами или на границах - возвращаем угловое значение
            return ksiMValues[areaRatioIndices[0]][reIndices[0]];
        } else if (areaRatioIndices[0] == areaRatioIndices[1]) {
            // Отношение площадей за границами, интерполируем только по Re
            return Functions.interpolateLinear(re1, re2, q11, q12, re);
        } else if (reIndices[0] == reIndices[1]) {
            // Re за границами, интерполируем только по отношению площадей
            return Functions.interpolateLinear(areaRatio1, areaRatio2, q11, q21, areaRatio);
        } else {
            // Оба параметра в пределах массивов - билинейная интерполяция
            return Functions.bilinearInterpolation(areaRatio, re,
                    areaRatio1, areaRatio2, re1, re2,
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
        return AreaChangeType.UNIFORM_SUDDEN_EXPANSION.toString();
    }

    @Override
    public void validateParameters() {
    }
}
