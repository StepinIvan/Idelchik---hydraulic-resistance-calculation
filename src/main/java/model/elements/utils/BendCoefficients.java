package model.elements.utils;

import lombok.Getter;

public abstract class BendCoefficients {
    private static final double TRANSITION_REGION = 1000.;
    private static final double[] BEND_ANGLE_FOR_A1 = {0., 20., 30., 45., 60., 75., 90., 110., 130., 150., 180.};
    private static final double[] A1 = {0., 0.31, 0.45, 0.6, 0.78, 0.9, 1., 1.13, 1.20, 1.28, 1.40};
    private static final double[] R0_D0_RATIO = {0.50, 0.60, 0.70, 0.80, 0.90, 1.00, 1.25, 1.50, 2.0, 4.0, 6.0, 8.0,
            10.0, 20.0, 30.0, 40.0
    };
    private static final double[] B1 = {1.18, 0.77, 0.51, 0.37, 0.28, 0.21, 0.19, 0.17, 0.15, 0.11, 0.09, 0.07, 0.07,
            0.05, 0.04, 0.03
    };
    private static final double[] A0_B0_RATIO = {0.25, 0.50, 0.75, 1.0, 1.5, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};

    private static final double[] C1 = {1.30, 1.17, 1.09, 1.00, 0.90, 0.85, 0.85, 0.90, 0.95, 0.98, 1.00, 1.00};
    @Getter
    private static final LinearInterpolator linePredictionA1 = new LinearInterpolator(BEND_ANGLE_FOR_A1, A1);
    @Getter
    private static final LinearInterpolator linePredictionB1 = new LinearInterpolator(R0_D0_RATIO, B1);
    private static final LinearInterpolator linePredictionC1 = new LinearInterpolator(A0_B0_RATIO, C1);
    private static final double RE_LOW = 3_000.0;//It'll be used further for warning
    private static final double RE_MID = 40_000.0;
    private static final double RE_HIGH = 200_000.0;
    private static final double ROUGHNESS_THRESHOLD = 0.001;
    private static final double R0_D0_THRESHOLD = 0.55;
    private static final double[] RE_VALUES = {0.1, 0.14, 0.2, 0.3, 0.4, 0.6,
            0.8, 1.0, 1.4, 2.0, 3.0, 4.0};
    private static final double[][] K_RE_TABLE = {
            {1.40, 1.33, 1.26, 1.19, 1.14, 1.09, 1.06, 1.04, 1.0, 1.0, 1.0, 1.0},
            {1.67, 1.58, 1.49, 1.40, 1.34, 1.26, 1.21, 1.19, 1.17, 1.14, 1.06, 1.0},
            {2.00, 1.89, 1.77, 1.64, 1.56, 1.46, 1.38, 1.30, 1.15, 1.02, 1.0, 1.0}
    };
    @Getter
    private static final double[] ANGLES_COUPLED_BENDS = {15, 30, 45, 60, 75, 90, 120};
    @Getter
    private static final double[] L_D_RATIO_COUPLED_BENDS = {0, 1, 2, 3, 4, 6, 8, 10, 12, 14, 16, 18, 20, 25, 40};
    @Getter
    private static final double[][] A_COUPLED_BENDS = {
            {0.20, 0.42, 0.60, 0.78, 0.94, 1.16, 1.20, 1.15, 1.08, 1.05, 1.02, 1.0, 1.10, 1.25, 2.0},
            {0.40, 0.65, 0.88, 1.16, 1.20, 1.18, 1.12, 1.06, 1.06, 1.15, 1.28, 1.40, 1.50, 1.70, 2.0},
            {0.60, 1.06, 1.20, 1.23, 1.20, 1.08, 1.03, 1.08, 1.17, 1.30, 1.42, 1.55, 1.65, 1.80, 2.0},
            {1.05, 1.38, 1.37, 1.28, 1.15, 1.06, 1.16, 1.30, 1.42, 1.54, 1.66, 1.76, 1.85, 1.95, 2.0},
            {1.50, 1.58, 1.46, 1.30, 1.27, 1.30, 1.37, 1.47, 1.57, 1.68, 1.75, 1.80, 1.88, 1.97, 2.0},
            {1.70, 1.67, 1.40, 1.37, 1.38, 1.47, 1.55, 1.63, 1.70, 1.76, 1.82, 1.88, 1.92, 1.98, 2.0},
            {1.78, 1.64, 1.48, 1.55, 1.62, 1.70, 1.75, 1.82, 1.88, 1.90, 1.92, 1.95, 1.97, 1.99, 2.0}
    };
    public static double calculateKRe(double r0d0, double re) {
        double reNormalized = re * 1e-5;

        // Определение индекса R0/D0
        int r0d0Index = (r0d0 <= 0.55) ? 0 : (r0d0 <= 0.70) ? 1 : 2;

        // Поиск по Re (проверка выхода за диапазон)
        if (reNormalized <= RE_VALUES[0]) {
            return K_RE_TABLE[r0d0Index][0];
        }
        if (reNormalized >= RE_VALUES[RE_VALUES.length - 1]) {
            return K_RE_TABLE[r0d0Index][RE_VALUES.length - 1];
        }
        //// Линейный поиск по Re
        int[] indices = Functions.lineSearchNeighborIndices(reNormalized, RE_VALUES);
        double k = (reNormalized - RE_VALUES[indices[0]]) / (RE_VALUES[indices[1]] - RE_VALUES[indices[0]]);
        return K_RE_TABLE[r0d0Index][indices[0]] + k * (K_RE_TABLE[r0d0Index][indices[1]] -
                K_RE_TABLE[r0d0Index][indices[0]]);
    }

    public static double calculateKDelta(double r0d0Ratio, double re, double relativeRoughness) {
        if (relativeRoughness == 0.0) {//TODO <?
            return 1.0;
        }
        boolean isLowR0D0 = r0d0Ratio <= R0_D0_THRESHOLD;
        if (isLowR0D0) {
            return calculateForLowR0D0(re, relativeRoughness);
        } else {
            return calculateForHighR0D0(re, relativeRoughness);
        }
    }

    private static double calculateForLowR0D0(double re, double relativeRoughness) {
        if (relativeRoughness <= 0.001) {
            if (re <= RE_MID) {
                return 1.0;
            } else if (re > RE_MID && re <= RE_MID + TRANSITION_REGION) {
                return Functions.blend(1., 1.0 + 0.5 * 1000 * relativeRoughness, RE_MID,
                        RE_MID + TRANSITION_REGION, re);
            } else {
                return 1.0 + 0.5 * 1000 * relativeRoughness;
            }
        } else {
            if (re <= RE_MID) {
                return 1.0;
            } else if (re > RE_MID && re <= RE_MID + TRANSITION_REGION) {
                return Functions.blend(1., 1.5, RE_MID,
                        RE_MID + TRANSITION_REGION, re);
            } else {
                return 1.5;
            }
        }

    }

    private static double calculateForHighR0D0(double re, double relativeRoughness) {
        //TODO check smoothing for lambdaRough / lambdaSmooth regions
        if (relativeRoughness <= 0.001) {
            double lambdaRough;
            double lambdaSmooth;
            lambdaRough = TubeCoefficients.calculateEvenGrainedPipeLambda(re, relativeRoughness);
            lambdaSmooth = TubeCoefficients.calculateSmoothPipeLambda(re);
            double lambdaRoughBoundaryMid = TubeCoefficients.calculateEvenGrainedPipeLambda(
                    RE_MID + TRANSITION_REGION, relativeRoughness);
            double lambdaSmoothBoundaryMid = TubeCoefficients.calculateSmoothPipeLambda(RE_MID + TRANSITION_REGION);
            double lambdaRoughBoundaryHigh = TubeCoefficients.calculateEvenGrainedPipeLambda(
                    RE_HIGH + TRANSITION_REGION, relativeRoughness);
            double lambdaSmoothBoundaryHigh = TubeCoefficients.calculateSmoothPipeLambda(
                    RE_HIGH + TRANSITION_REGION);
            if (re <= RE_MID) {
                return 1.0;
            } else if (re > RE_MID && re <= RE_MID + TRANSITION_REGION) {
                return Functions.blend(1., lambdaRoughBoundaryMid / lambdaSmoothBoundaryMid, RE_MID,
                        RE_MID + TRANSITION_REGION, re);
            } else if (re > RE_MID + TRANSITION_REGION && re <= RE_HIGH){
                return lambdaRough / lambdaSmooth;
            } else if (re > RE_HIGH && re <= RE_HIGH + TRANSITION_REGION) {
                return Functions.blend(lambdaRoughBoundaryHigh / lambdaSmoothBoundaryHigh,
                        1.0 + 1000 * relativeRoughness, RE_HIGH, RE_HIGH + TRANSITION_REGION, re);
            } else {
                return 1.0 + 1000 * relativeRoughness;
            }
        } else {
            if (re <= RE_MID) {
                return 1.0;
            } else if (re > RE_MID && re <= RE_MID + TRANSITION_REGION) {
                return Functions.blend(1., 2., RE_MID,
                        RE_MID + TRANSITION_REGION, re);
            } else {
                return 2.;
            }
        }
    }

    public static double calculateA1(double bendAngle) {
        return linePredictionA1.interpolate(bendAngle);
    }

    public static double calculateB1(double r0d0Ratio) {
        return linePredictionB1.interpolate(r0d0Ratio);
    }

    public static double calculateC1(double a0b0Ratio) {
        return linePredictionC1.interpolate(a0b0Ratio);
    }
    public static double calculateACoupledBends(double bendsAngle, double lDRatio) {
        double lDRatioMin = L_D_RATIO_COUPLED_BENDS[0];
        double lDRatioMax = L_D_RATIO_COUPLED_BENDS[L_D_RATIO_COUPLED_BENDS.length - 1];
        int[] bendAngleIndices;
        if (bendsAngle < ANGLES_COUPLED_BENDS[0]) {
            bendAngleIndices = new int[]{0, 0};
        } else if (bendsAngle > ANGLES_COUPLED_BENDS[ANGLES_COUPLED_BENDS.length - 1]) {
            bendAngleIndices = new int[]{ANGLES_COUPLED_BENDS.length - 1, ANGLES_COUPLED_BENDS.length - 1};
        } else {
            bendAngleIndices = Functions.lineSearchNeighborIndices(bendsAngle, ANGLES_COUPLED_BENDS);
        }

        int[] lDRatioIndices;
        if (lDRatio < lDRatioMin) {
            lDRatioIndices = new int[]{0, 0};
        } else if (lDRatio > lDRatioMax) {
            lDRatioIndices = new int[]{L_D_RATIO_COUPLED_BENDS.length - 1, L_D_RATIO_COUPLED_BENDS.length - 1};
        } else {
            lDRatioIndices = Functions.binarySearchNearestIndices(lDRatio, L_D_RATIO_COUPLED_BENDS);
        }

        double bendAngle1 = ANGLES_COUPLED_BENDS[bendAngleIndices[0]];
        double bendAngle2 = ANGLES_COUPLED_BENDS[bendAngleIndices[1]];
        double lDRatio1 = L_D_RATIO_COUPLED_BENDS[lDRatioIndices[0]];
        double lDRatio2 = L_D_RATIO_COUPLED_BENDS[lDRatioIndices[1]];

        double q11 = A_COUPLED_BENDS[bendAngleIndices[0]][lDRatioIndices[0]];
        double q12 = A_COUPLED_BENDS[bendAngleIndices[0]][lDRatioIndices[1]];
        double q21 = A_COUPLED_BENDS[bendAngleIndices[1]][lDRatioIndices[0]];
        double q22 = A_COUPLED_BENDS[bendAngleIndices[1]][lDRatioIndices[1]];

        if (bendAngleIndices[0] == bendAngleIndices[1] && lDRatioIndices[0] == lDRatioIndices[1]) {
            return A_COUPLED_BENDS[bendAngleIndices[0]][lDRatioIndices[0]];
        } else if (bendAngleIndices[0] == bendAngleIndices[1]) {
            return Functions.interpolateLinear(lDRatio1, lDRatio2, q11, q12, lDRatio);
        } else if (lDRatioIndices[0] == lDRatioIndices[1]) {
            return Functions.interpolateLinear(bendAngle1, bendAngle2, q11, q21, bendsAngle);
        } else {
            return Functions.bilinearInterpolation(bendsAngle, lDRatio,
                    bendAngle1, bendAngle2, lDRatio1, lDRatio2,
                    q11, q12, q21, q22);
        }
    }
}
