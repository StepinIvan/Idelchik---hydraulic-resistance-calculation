package model.elements.areaChanges;

import lombok.Getter;
import model.elements.utils.AreaChangeCoefficients;
import model.elements.utils.Functions;
import model.elements.utils.LinearInterpolator;
import model.elements.utils.TubeCoefficients;


public class BevelSuddenContraction extends AreaChange {
    private final double smallArea;
    private final double largeArea;
    private final double absolutRoughness;
    private final double angle;
    private final double bevelLength;
    @Getter
    private double lambda;
    @Getter
    private final double areaRatio;
    @Getter
    private final double lDRatio;

    public BevelSuddenContraction(double smallerDiameter, double largerDiameter, double absolutRoughness, double length,
                                  double angle, double bevelLength) {
        super("Сужение (внезапное) потока с срезанной под углом кромкой", smallerDiameter,
                largerDiameter, length);
        this.absolutRoughness = absolutRoughness;
        this.smallArea = Math.PI * Math.pow(smallerDiameter, 2.) / 4.;
        this.largeArea = Math.PI * Math.pow(largerDiameter, 2.) / 4.;
        this.angle = angle;
        this.bevelLength = bevelLength;
        areaRatio = smallArea / largeArea;
        lDRatio = this.bevelLength / smallerDiameter;
        validateParameters();
    }

    public double calculateHydraulicResistance(double re) {
        double localResistanceCoefficient = calculateLocalResistanceCoefficient();
        double frictionResistanceCoefficient = calculateFrictionResistanceCoefficient(re);
        return localResistanceCoefficient + frictionResistanceCoefficient;
    }
    public double calculateLocalResistanceCoefficient() {
        LinearInterpolator linePredictionASharpContraction = AreaChangeCoefficients.getLinePredictionASharpContraction();
        return calculateConicalBellKsi(angle, lDRatio) *
                linePredictionASharpContraction.interpolate(areaRatio);
    }

    public double calculateFrictionResistanceCoefficient(double re) {
        double relativeRoughness = absolutRoughness / smallerDiameter;
        if (absolutRoughness == 0) {
            lambda = TubeCoefficients.calculateSmoothPipeLambda(re);
        } else {
            lambda = TubeCoefficients.calculateEvenGrainedPipeLambda(re, relativeRoughness);
            //TODO Add calculation of friction factor for not even grained roughness;
        }
        return lambda * length / smallerDiameter;
    }
    public static double calculateConicalBellKsi(double alpha, double lDRatio) {
        double[] alphaConicalBell = AreaChangeCoefficients.getALPHA_CONICAL_BELL();
        double[] lDRatioTable = AreaChangeCoefficients.getL_D_RATIO();
        double[][] ksiConicalBell = AreaChangeCoefficients.getKSI_CONICAL_BELL();
        //Граничные значения для alpha
        double alphaMin = alphaConicalBell[0];
        double alphaMax = alphaConicalBell[alphaConicalBell.length - 1];

        // Определяем индексы для lDRatio с учетом границ
        int[] lDRatioIndices;
        if (lDRatio < lDRatioTable[0]) {
            // За левой границей массива lDRatio
            lDRatioIndices = new int[]{0, 0};
        } else if (lDRatio > lDRatioTable[lDRatioTable.length - 1]) {
            // За правой границей массива lDRatio
            lDRatioIndices = new int[]{lDRatioTable.length - 1, lDRatioTable.length - 1};
        } else {
            // В пределах массива lDRatio
            lDRatioIndices = Functions.lineSearchNeighborIndices(lDRatio, lDRatioTable);
        }

        // Определяем индексы для alpha с учетом границ
        int[] alphaIndices;
        if (alpha < alphaMin) {
            // За левой границей массива отношения площадей
            alphaIndices = new int[]{0, 0};
        } else if (alpha > alphaMax) {
            // За правой границей массива отношения площадей
            alphaIndices = new int[]{alphaConicalBell.length - 1, alphaConicalBell.length - 1};
        } else {
            // В пределах массива отношения площадей
            alphaIndices = Functions.binarySearchNearestIndices(alpha, alphaConicalBell);
        }

        // Значения alpha и lDRatio для интерполяции
        double lDRatio1 = lDRatioTable[lDRatioIndices[0]];
        double lDRatio2 = lDRatioTable[lDRatioIndices[1]];
        double alpha1 = alphaConicalBell[alphaIndices[0]];
        double alpha2 = alphaConicalBell[alphaIndices[1]];

        // Значения ξм из таблицы
        double q11 = ksiConicalBell[lDRatioIndices[0]][alphaIndices[0]];
        double q12 = ksiConicalBell[lDRatioIndices[0]][alphaIndices[1]];
        double q21 = ksiConicalBell[lDRatioIndices[1]][alphaIndices[0]];
        double q22 = ksiConicalBell[lDRatioIndices[1]][alphaIndices[1]];

        if (lDRatioIndices[0] == lDRatioIndices[1] && alphaIndices[0] == alphaIndices[1]) {
            // Оба параметра за границами или на границах - возвращаем угловое значение
            return ksiConicalBell[lDRatioIndices[0]][alphaIndices[0]];
        } else if (lDRatioIndices[0] == lDRatioIndices[1]) {
            // Отношение площадей за границами, интерполируем только по Re
            return Functions.interpolateLinear(alpha1, alpha2, q11, q12, alpha);
        } else if (alphaIndices[0] == alphaIndices[1]) {
            // Re за границами, интерполируем только по отношению площадей
            return Functions.interpolateLinear(lDRatio1, lDRatio2, q11, q21, lDRatio);
        } else {
            // Оба параметра в пределах массивов - билинейная интерполяция
            return Functions.bilinearInterpolation(lDRatio, alpha,
                    lDRatio1, lDRatio2, alpha1, alpha2,
                    q11, q12, q21, q22);
        }
    }
    @Override
    public String getElementType() {
        return AreaChangeType.BEVEL_SUDDEN_CONTRACTION.toString();
    }

    @Override
    public void validateParameters() {
    }
}
