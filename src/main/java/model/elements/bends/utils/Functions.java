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
        int[] indices = new int[2];
        for (int i = 0; i < valuesArray.length - 1; i++) {
            if (desiredValue >= valuesArray[i] && desiredValue <= valuesArray[i + 1]) {
                indices[0] = i;
                break;
            }
        }
        indices[1] = indices[0];
        return indices;
    }

    public static double interpolateLinear(double x1,double x2, double y1,double y2, double userValueOfx) {
        double k = (userValueOfx - x2) / (x2 - x1);
        return y1 + k * (y2 - y1);
    }
}
