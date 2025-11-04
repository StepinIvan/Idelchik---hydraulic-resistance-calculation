package model.elements.bends.utils;

public abstract class Functions {
    public static double bilinearInterpolation(double x, double y,
                                               double x1, double x2,
                                               double y1, double y2,
                                               double q11, double q12,
                                               double q21, double q22) {
        double r1 = ((x2 - x) / (x2 - x1)) * q11 + ((x - x1) / (x2 - x1)) * q21;
        double r2 = ((x2 - x) / (x2 - x1)) * q12 + ((x - x1) / (x2 - x1)) * q22;
        return ((y2 - y) / (y2 - y1)) * r1 + ((y - y1) / (y2 - y1)) * r2;
    }

    public static int[] lineSearchNeighborIndices(double desiredValue, double[] valuesArray) {
        boolean ascendingOrder = isAscendingOrder(valuesArray);
        // Проверка выхода за левую границу
        if (ascendingOrder && desiredValue <= valuesArray[0] ||
                !ascendingOrder && desiredValue >= valuesArray[0]) {
            return new int[]{0, 0};
        }
        // Проверка выхода за правую границу
        int lastIndex = valuesArray.length - 1;
        if (ascendingOrder && desiredValue >= valuesArray[lastIndex] ||
                !ascendingOrder && desiredValue <= valuesArray[lastIndex]) {
            return new int[]{lastIndex, lastIndex};
        }
        for (int i = 0; i < valuesArray.length - 1; i++) {
            if (ascendingOrder) {
                if (desiredValue >= valuesArray[i] && desiredValue <= valuesArray[i + 1]) {
                    return new int[]{i, i + 1};
                }
            } else {
                if (desiredValue <= valuesArray[i] && desiredValue >= valuesArray[i + 1]) {
                    return new int[]{i, i + 1};
                }
            }
        }
        return new int[]{0, 0};
    }

    public static double interpolateLinear(double x1,double x2, double y1,double y2, double userValueOfx) {
        double k = (userValueOfx - x2) / (x2 - x1);
        return y1 + k * (y2 - y1);
    }
    public static boolean isAscendingOrder(double[] array) {
        boolean ascending = true;
        boolean descending = true;

        for (int i = 1; i < array.length; i++) {
            double diff = array[i] - array[i - 1];

            if (diff < 0) {
                ascending = false;
            } else if (diff > 0) {
                descending = false;
            }
            if (!ascending && !descending) {
                return false;
            }
        }

        return ascending;
    }
}
