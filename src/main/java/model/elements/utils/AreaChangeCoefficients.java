package model.elements.utils;

public abstract class AreaChangeCoefficients {
    private static final double[] F0_F2_RATIO = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6};
    private static final double[] RE_VALUES = {10, 15, 20, 30, 40, 50, 100, 200, 500, 1000, 2000, 3000, 3300};
    private static final double[] SMALL_RE_VALUES = {0.0001, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9.9};
    private static final double[] SMALL_KSI_M_VALUES = {30.0 / 0.0001, 30.0, 30.0 / 2, 30.0 / 3, 30.0 / 4, 30.0 / 5,
            30.0 / 6, 30.0 / 7, 30.0 / 8, 30.0 / 9, 30.0 / 9.9};
    private static final LinearInterpolator linePredictionSmallKsiM =
            new LinearInterpolator(SMALL_RE_VALUES, SMALL_KSI_M_VALUES);

    private static final double[][] KSI_M_VALUES = {
            {3.10, 3.20, 3.00, 2.40, 2.15, 1.95, 1.70, 1.65, 1.70, 2.00, 1.60, 1.00, 0.81},
            {3.10, 3.20, 2.80, 2.20, 1.85, 1.65, 1.40, 1.30, 1.30, 1.60, 1.25, 0.70, 0.64},
            {3.10, 3.10, 2.60, 2.00, 1.60, 1.40, 1.20, 1.10, 1.10, 1.30, 0.95, 0.60, 0.50},
            {3.10, 3.00, 2.40, 1.80, 1.50, 1.30, 1.10, 1.00, 0.85, 1.05, 0.80, 0.40, 0.36},
            {3.10, 2.80, 2.30, 1.65, 1.35, 1.15, 0.90, 0.75, 0.65, 0.90, 0.65, 0.30, 0.25},
            {3.10, 2.70, 2.15, 1.55, 1.25, 1.05, 0.80, 0.60, 0.40, 0.60, 0.50, 0.20, 0.16}
    };
    private static final double[] M_VALUES = {1.0, 1.35, 2.0, 3.0, 4.0, 7.0, Double.MAX_VALUE};
    private static final double[] F0_F2_RATIO_POWER = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 1.0};

    private static final double[][] KSI_M_VALUES_POWER = {
            // F0/F2: 0    0.1   0.2   0.3   0.4   0.5   0.6   0.7   0.8   1.0
            {2.70, 2.42, 2.14, 1.90, 1.66, 1.45, 1.26, 1.09, 0.94, 0.70}, // m = 1.0
            {2.00, 1.74, 1.51, 1.29, 1.00, 0.93, 0.77, 0.65, 0.53, 0.36}, // m = 1.35
            {1.50, 1.28, 1.08, 0.89, 0.72, 0.59, 0.46, 0.35, 0.27, 0.16}, // m = 2.0
            {1.25, 1.04, 0.85, 0.68, 0.53, 0.41, 0.30, 0.20, 0.14, 0.07}, // m = 3.0
            {1.15, 0.95, 0.77, 0.62, 0.47, 0.35, 0.25, 0.17, 0.11, 0.05}, // m = 4.0
            {1.06, 0.86, 0.69, 0.53, 0.41, 0.29, 0.19, 0.12, 0.06, 0.02}, // m = 7.0
            {1.00, 0.82, 0.64, 0.48, 0.36, 0.25, 0.16, 0.09, 0.04, 0.00}  // m = ∞ (Double.MAX_VALUE)
    };
    public static double calculateUniformSuddenAreaChangeKsiM(double re, double areaRatio) {
        if (re <= 9.9) {
            return linePredictionSmallKsiM.interpolate(re);
        } else if (re > 9.9 && re < 10) {
            return Functions.interpolateLinear(9.9, 10, 30.0 / 9.9, 3.1, re);
        }
        //Граничные значения для Re
        double reMin = RE_VALUES[0];
        double reMax = RE_VALUES[RE_VALUES.length - 1];

        // Определяем индексы для отношения площадей с учетом границ
        int[] areaRatioIndices;
        if (areaRatio < F0_F2_RATIO[0]) {
            // За левой границей массива отношения площадей
            areaRatioIndices = new int[]{0, 0};
        } else if (areaRatio > F0_F2_RATIO[F0_F2_RATIO.length - 1]) {
            // За правой границей массива отношения площадей
            areaRatioIndices = new int[]{F0_F2_RATIO.length - 1, F0_F2_RATIO.length - 1};
        } else {
            // В пределах массива отношения площадей
            areaRatioIndices = Functions.lineSearchNeighborIndices(areaRatio, F0_F2_RATIO);
        }

        // Определяем индексы для Re с учетом границ
        int[] reIndices;
        if (re < reMin) {
            // За левой границей массива Re
            reIndices = new int[]{0, 0};
        } else if (re > reMax) {
            // За правой границей массива Re
            reIndices = new int[]{RE_VALUES.length - 1, RE_VALUES.length - 1};
        } else {
            // В пределах массива Re
            reIndices = Functions.binarySearchNearestIndices(re, RE_VALUES);
        }

        // Значения отношения площадей и Re для интерполяции
        double areaRatio1 = F0_F2_RATIO[areaRatioIndices[0]];
        double areaRatio2 = F0_F2_RATIO[areaRatioIndices[1]];
        double re1 = RE_VALUES[reIndices[0]];
        double re2 = RE_VALUES[reIndices[1]];

        // Значения ξм из таблицы
        double q11 = KSI_M_VALUES[areaRatioIndices[0]][reIndices[0]];
        double q12 = KSI_M_VALUES[areaRatioIndices[0]][reIndices[1]];
        double q21 = KSI_M_VALUES[areaRatioIndices[1]][reIndices[0]];
        double q22 = KSI_M_VALUES[areaRatioIndices[1]][reIndices[1]];

        if (areaRatioIndices[0] == areaRatioIndices[1] && reIndices[0] == reIndices[1]) {
            // Оба параметра за границами или на границах - возвращаем угловое значение
            return KSI_M_VALUES[areaRatioIndices[0]][reIndices[0]];
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
    public static double calculatePowerLowSuddenAreaChangeKsiM(double areaRatio, double m) {
        //Граничные значения для Re
        double areaRatioMin = F0_F2_RATIO_POWER[0];
        double areaRatioMax = F0_F2_RATIO_POWER[F0_F2_RATIO_POWER.length - 1];

        // Определяем индексы для m с учетом границ
        int[] mIndices;
        if (m < M_VALUES[0]) {
            // За левой границей массива m
            mIndices = new int[]{0, 0};
        } else if (m > M_VALUES[M_VALUES.length - 1]) {
            // За правой границей массива m
            mIndices = new int[]{M_VALUES.length - 1, M_VALUES.length - 1};
        } else {
            // В пределах массива m
            mIndices = Functions.lineSearchNeighborIndices(m, M_VALUES);
        }

        // Определяем индексы для отношения площадей с учетом границ
        int[] areaRatioIndices;
        if (areaRatio < areaRatioMin) {
            // За левой границей массива отношения площадей
            areaRatioIndices = new int[]{0, 0};
        } else if (areaRatio > areaRatioMax) {
            // За правой границей массива отношения площадей
            areaRatioIndices = new int[]{F0_F2_RATIO_POWER.length - 1, F0_F2_RATIO_POWER.length - 1};
        } else {
            // В пределах массива отношения площадей
            areaRatioIndices = Functions.binarySearchNearestIndices(areaRatio, F0_F2_RATIO_POWER);
        }

        // Значения отношения площадей и Re для интерполяции
        double m1 = M_VALUES[mIndices[0]];
        double m2 = M_VALUES[mIndices[1]];
        double areaRatio1 = F0_F2_RATIO_POWER[areaRatioIndices[0]];
        double areaRatio2 = F0_F2_RATIO_POWER[areaRatioIndices[1]];

        // Значения ξм из таблицы
        double q11 = KSI_M_VALUES_POWER[mIndices[0]][areaRatioIndices[0]];
        double q12 = KSI_M_VALUES_POWER[mIndices[0]][areaRatioIndices[1]];
        double q21 = KSI_M_VALUES_POWER[mIndices[1]][areaRatioIndices[0]];
        double q22 = KSI_M_VALUES_POWER[mIndices[1]][areaRatioIndices[1]];

        if (mIndices[0] == mIndices[1] && areaRatioIndices[0] == areaRatioIndices[1]) {
            // Оба параметра за границами или на границах - возвращаем угловое значение
            return KSI_M_VALUES_POWER[mIndices[0]][areaRatioIndices[0]];
        } else if (mIndices[0] == mIndices[1]) {
            // Отношение площадей за границами, интерполируем только по Re
            return Functions.interpolateLinear(areaRatio1, areaRatio2, q11, q12, areaRatio);
        } else if (areaRatioIndices[0] == areaRatioIndices[1]) {
            // Re за границами, интерполируем только по отношению площадей
            return Functions.interpolateLinear(m1, m2, q11, q21, m);
        } else {
            // Оба параметра в пределах массивов - билинейная интерполяция
            return Functions.bilinearInterpolation(m, areaRatio,
                    m1, m2, areaRatio1, areaRatio2,
                    q11, q12, q21, q22);
        }
    }
}
