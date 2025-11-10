package model.elements.utils;

public abstract class TubeCoefficients {
    private static final double[] RE_SMOOTH_PIPES = {
            100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
            1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000,
            2500, 3000, 4000, 5000, 6000, 8000, 10000, 15000,
            20000, 30000, 40000, 50000, 60000, 80000, 100000, 150000, 200000, 300000,
            400000, 500000, 600000, 800000, 1000000, 1500000, 2000000, 3000000, 4000000,
            5000000, 8000000, 10000000, 15000000, 20000000, 30000000, 60000000, 80000000, 100000000
    };

    private static final double[] LAMBDA_SMOOTH_PIPES = {
            0.640, 0.320, 0.213, 0.160, 0.128, 0.107, 0.092, 0.080, 0.071, 0.064,
            0.058, 0.053, 0.049, 0.046, 0.043, 0.040, 0.038, 0.036, 0.034, 0.032,
            0.034, 0.040, 0.040, 0.038, 0.036, 0.033, 0.032, 0.028,
            0.026, 0.024, 0.022, 0.021, 0.020, 0.019, 0.018, 0.017, 0.016, 0.015,
            0.014, 0.013, 0.013, 0.012, 0.012, 0.011, 0.011, 0.010, 0.010,
            0.009, 0.009, 0.008, 0.008, 0.008, 0.007, 0.007, 0.006, 0.006
    };
    private static final double[] ROUGHNESS_EVEN_GRAINED_PIPES = {
            0.05, 0.04, 0.03, 0.02, 0.015, 0.01, 0.008, 0.006, 0.004, 0.002, 0.001,
            0.0008, 0.0006, 0.0004, 0.0002, 0.0001, 0.00005
    };

    private static final double[] RE_EVEN_GRAINED_PIPES = {
            2e3, 3e3, 4e3, 6e3, 1e4, 2e4, 4e4, 6e4, 1e5, 2e5,
            4e5, 6e5, 1e6, 2e6, 4e6, 6e6, 1e7, 2e7, 1e8
    };

    private static final double[][] LAMBDA_DATA_EVEN_GRAINED_PIPES = {
            // Re: 2e3-2e5 (первые 10 столбцов)
            {0.032, 0.052, 0.060, 0.063, 0.069, 0.072, 0.072, 0.072, 0.072, 0.072,
                    0.072, 0.072, 0.072, 0.072, 0.072, 0.072, 0.072, 0.072, 0.072},
            {0.032, 0.044, 0.052, 0.055, 0.060, 0.065, 0.065, 0.065, 0.065, 0.065,
                    0.065, 0.065, 0.065, 0.065, 0.065, 0.065, 0.065, 0.065, 0.065},
            {0.032, 0.040, 0.044, 0.046, 0.050, 0.056, 0.057, 0.057, 0.057, 0.057,
                    0.057, 0.057, 0.057, 0.057, 0.057, 0.057, 0.057, 0.057, 0.057},
            {0.032, 0.040, 0.040, 0.041, 0.042, 0.044, 0.048, 0.049, 0.049, 0.049,
                    0.049, 0.049, 0.049, 0.049, 0.049, 0.049, 0.049, 0.049, 0.049},
            {0.032, 0.040, 0.040, 0.038, 0.037, 0.039, 0.042, 0.044, 0.044, 0.044,
                    0.044, 0.044, 0.044, 0.044, 0.044, 0.044, 0.044, 0.044, 0.044},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.032, 0.035, 0.036, 0.038, 0.038,
                    0.038, 0.038, 0.038, 0.038, 0.038, 0.038, 0.038, 0.038, 0.038},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.030, 0.032, 0.033, 0.035, 0.035,
                    0.035, 0.035, 0.035, 0.035, 0.035, 0.035, 0.035, 0.035, 0.035},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.028, 0.028, 0.029, 0.030, 0.032,
                    0.032, 0.032, 0.032, 0.032, 0.032, 0.032, 0.032, 0.032, 0.032},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.027, 0.025, 0.025, 0.026, 0.028,
                    0.028, 0.028, 0.028, 0.028, 0.028, 0.028, 0.028, 0.028, 0.028},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.027, 0.023, 0.021, 0.021, 0.021,
                    0.023, 0.023, 0.023, 0.023, 0.023, 0.023, 0.023, 0.023, 0.023},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.027, 0.023, 0.021, 0.018, 0.017,
                    0.018, 0.018, 0.020, 0.020, 0.020, 0.020, 0.020, 0.020, 0.020},
            // Для меньшей относительной шероховатости
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.027, 0.023, 0.021, 0.018, 0.017,
                    0.016, 0.017, 0.018, 0.019, 0.019, 0.019, 0.019, 0.019, 0.019},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.027, 0.023, 0.021, 0.018, 0.017,
                    0.015, 0.016, 0.017, 0.017, 0.017, 0.017, 0.017, 0.017, 0.017},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.027, 0.023, 0.021, 0.018, 0.017,
                    0.014, 0.014, 0.014, 0.015, 0.016, 0.016, 0.016, 0.016, 0.016},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.027, 0.023, 0.021, 0.018, 0.017,
                    0.014, 0.013, 0.012, 0.012, 0.013, 0.014, 0.014, 0.014, 0.014},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.027, 0.023, 0.021, 0.018, 0.017,
                    0.014, 0.013, 0.012, 0.011, 0.011, 0.011, 0.012, 0.012, 0.012},
            {0.032, 0.040, 0.040, 0.038, 0.033, 0.027, 0.023, 0.021, 0.018, 0.017,
                    0.014, 0.013, 0.012, 0.011, 0.010, 0.010, 0.010, 0.010, 0.011}
    };

    public static double calculateEvenGrainedPipeLambda(double re, double relativeRoughness) {//TODO Calculation for Re < 2000
        //Граничные значения для Re
        double reMin = RE_EVEN_GRAINED_PIPES[0];
        double reMax = RE_EVEN_GRAINED_PIPES[RE_EVEN_GRAINED_PIPES.length - 1];

        // Определяем индексы для шероховатости с учетом границ
        int[] deltaIndices;
        if (relativeRoughness > ROUGHNESS_EVEN_GRAINED_PIPES[0]) {
            // За левой границей массива шероховатости
            deltaIndices = new int[]{0, 0};
        } else if (relativeRoughness < ROUGHNESS_EVEN_GRAINED_PIPES[ROUGHNESS_EVEN_GRAINED_PIPES.length - 1]) {
            // За правой границей массива шероховатости
            deltaIndices = new int[]{ROUGHNESS_EVEN_GRAINED_PIPES.length - 1, ROUGHNESS_EVEN_GRAINED_PIPES.length - 1};
        } else {
            // В пределах массива шероховатости
            deltaIndices = Functions.lineSearchNeighborIndices(relativeRoughness, ROUGHNESS_EVEN_GRAINED_PIPES);
        }

        // Определяем индексы для Re с учетом границ
        int[] reIndices;
        if (re < reMin) {
            // За левой границей массива Re
            reIndices = new int[]{0, 0};
        } else if (re > reMax) {
            // За правой границей массива Re
            reIndices = new int[]{RE_EVEN_GRAINED_PIPES.length - 1, RE_EVEN_GRAINED_PIPES.length - 1};
        } else {
            // В пределах массива Re
            reIndices = Functions.binarySearchNearestIndices(re, RE_EVEN_GRAINED_PIPES);
        }

        // Значения шероховатости и Re для интерполяции
        double rough1 = ROUGHNESS_EVEN_GRAINED_PIPES[deltaIndices[0]];
        double rough2 = ROUGHNESS_EVEN_GRAINED_PIPES[deltaIndices[1]];
        double re1 = RE_EVEN_GRAINED_PIPES[reIndices[0]];
        double re2 = RE_EVEN_GRAINED_PIPES[reIndices[1]];

        // Значения λ из таблицы
        double q11 = LAMBDA_DATA_EVEN_GRAINED_PIPES[deltaIndices[0]][reIndices[0]];
        double q12 = LAMBDA_DATA_EVEN_GRAINED_PIPES[deltaIndices[0]][reIndices[1]];
        double q21 = LAMBDA_DATA_EVEN_GRAINED_PIPES[deltaIndices[1]][reIndices[0]];
        double q22 = LAMBDA_DATA_EVEN_GRAINED_PIPES[deltaIndices[1]][reIndices[1]];

        if (deltaIndices[0] == deltaIndices[1] && reIndices[0] == reIndices[1]) {
            // Оба параметра за границами или на границах - возвращаем угловое значение
            return LAMBDA_DATA_EVEN_GRAINED_PIPES[deltaIndices[0]][reIndices[0]];
        } else if (deltaIndices[0] == deltaIndices[1]) {
            // Шероховатость за границами, интерполируем только по Re
            return Functions.interpolateLinear(re1, re2, q11, q12, re);
        } else if (reIndices[0] == reIndices[1]) {
            // Re за границами, интерполируем только по шероховатости
            return Functions.interpolateLinear(rough1, rough2, q11, q21, relativeRoughness);
        } else {
            // Оба параметра в пределах массивов - билинейная интерполяция
            return Functions.bilinearInterpolation(relativeRoughness, re,
                    rough1, rough2, re1, re2,
                    q11, q12, q21, q22);
        }
    }


    public static double calculateSmoothPipeLambda(double re) {
        if (re <= RE_SMOOTH_PIPES[0]) {
            return LAMBDA_SMOOTH_PIPES[0];
        }
        if (re >= RE_SMOOTH_PIPES[RE_SMOOTH_PIPES.length - 1]) {
            return LAMBDA_SMOOTH_PIPES[LAMBDA_SMOOTH_PIPES.length - 1];
        }
        // Бинарный поиск для нахождения интервала
        int[] indices = Functions.binarySearchNearestIndices(re, RE_SMOOTH_PIPES);
        return Functions.interpolateLinear(RE_SMOOTH_PIPES[indices[0]], RE_SMOOTH_PIPES[indices[1]],
                LAMBDA_SMOOTH_PIPES[indices[0]], LAMBDA_SMOOTH_PIPES[indices[1]], re);

    }
}