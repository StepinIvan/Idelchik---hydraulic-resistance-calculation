package model.elements.utils;

import lombok.Getter;

public abstract class AreaChangeCoefficients {
    @Getter
    private static final double[] F0_F2_RATIO_UNIFORM = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6};
    @Getter
    private static final double[] reValues = {10, 15, 20, 30, 40, 50, 100, 200, 500, 1000, 2000, 3000, 3300};
    private static final double[] SMALL_RE_VALUES = {0.01, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9
            , 1, 2, 3, 4, 5, 6, 7, 8, 9, 9.9};
    private static final double[] SMALL_KSI_M_VALUES = {30.0 / 0.01,30.0 / 0.1,30.0 / 0.2,30.0 / 0.3,30.0 / 0.4,
            30.0 / 0.5, 30.0 / 0.6,30.0 / 0.7,30.0 / 0.8,30.0 / 0.9, 30.0, 30.0 / 2, 30.0 / 3, 30.0 / 4, 30.0 / 5,
            30.0 / 6, 30.0 / 7, 30.0 / 8, 30.0 / 9, 30.0 / 9.9};
    private static final double[] F0_F2_RATIO_PARABOLIC = {0., 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 1.};
    private static final double[] KSI_M_PARABOLIC = {2.00, 1.75, 1.51, 1.30, 1.10, 0.92, 0.78, 0.63, 0.51, 0.34};
    private static final double[] F0_F1_SHARP = {0, 0.2, 0.4, 0.6, 0.8, 0.9, 1.0};
    private static final double[] A_SHARP = {1.0, 0.850, 0.680, 0.503, 0.300, 0.178, 0};
    private static final double[] R_D_RATIO = {0., 0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.08, 0.12, 0.16, 0.20};
    private static final double[] KSI_COLLECTOR_END_WALL =
            {0.5, 0.44, 0.37, 0.31, 0.26, 0.22, 0.20, 0.15, 0.09, 0.06, 0.03};
    @Getter
    private static final LinearInterpolator linePredictionSmallKsiM =
            new LinearInterpolator(SMALL_RE_VALUES, SMALL_KSI_M_VALUES);
    @Getter
    private static final LinearInterpolator linePredictionKsiMParabolic =
            new LinearInterpolator(F0_F2_RATIO_PARABOLIC, KSI_M_PARABOLIC);
    @Getter
    private static final LinearInterpolator linePredictionASharpContraction =
            new LinearInterpolator(F0_F1_SHARP, A_SHARP);
    @Getter
    private static final LinearInterpolator linePredictionCollectorEndWallKsi =
            new LinearInterpolator(R_D_RATIO, KSI_COLLECTOR_END_WALL);

    @Getter
    private static final double[][] KSI_M_VALUES = {
            {3.10, 3.20, 3.00, 2.40, 2.15, 1.95, 1.70, 1.65, 1.70, 2.00, 1.60, 1.00, 0.81},
            {3.10, 3.20, 2.80, 2.20, 1.85, 1.65, 1.40, 1.30, 1.30, 1.60, 1.25, 0.70, 0.64},
            {3.10, 3.10, 2.60, 2.00, 1.60, 1.40, 1.20, 1.10, 1.10, 1.30, 0.95, 0.60, 0.50},
            {3.10, 3.00, 2.40, 1.80, 1.50, 1.30, 1.10, 1.00, 0.85, 1.05, 0.80, 0.40, 0.36},
            {3.10, 2.80, 2.30, 1.65, 1.35, 1.15, 0.90, 0.75, 0.65, 0.90, 0.65, 0.30, 0.25},
            {3.10, 2.70, 2.15, 1.55, 1.25, 1.05, 0.80, 0.60, 0.40, 0.60, 0.50, 0.20, 0.16}
    };
    @Getter
    private static final double[] M_VALUES = {1.0, 1.35, 2.0, 3.0, 4.0, 7.0, Double.MAX_VALUE};
    @Getter
    private static final double[] F0_F2_RATIO_POWER = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 1.0};
    @Getter
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
    @Getter
    private static final double[] L_D_RATIO = {0.025, 0.050, 0.075, 0.10, 0.15, 0.60};
    @Getter
    private static final double[] ALPHA_CONICAL_BELL = {0, 10, 20, 30, 40, 60, 100, 140, 180};
    @Getter
    private static final double[][] KSI_CONICAL_BELL = {
            {0.50, 0.47, 0.45, 0.43, 0.41, 0.40, 0.42, 0.45, 0.50},  // для l/D_r = 0.025
            {0.50, 0.45, 0.41, 0.36, 0.33, 0.30, 0.35, 0.42, 0.50},  // для l/D_r = 0.050
            {0.50, 0.42, 0.35, 0.30, 0.26, 0.23, 0.30, 0.40, 0.50},  // для l/D_r = 0.075
            {0.50, 0.39, 0.32, 0.25, 0.22, 0.18, 0.27, 0.38, 0.50},  // для l/D_r = 0.10
            {0.50, 0.37, 0.27, 0.20, 0.16, 0.15, 0.25, 0.37, 0.50},  // для l/D_r = 0.15
            {0.50, 0.27, 0.18, 0.13, 0.11, 0.12, 0.23, 0.36, 0.50}   // для l/D_r = 0.60
    };
}
