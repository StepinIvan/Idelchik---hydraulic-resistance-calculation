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
        boolean ascendingOrder = isAscendingOrder(valuesArray);
        for (int i = 0; i < valuesArray.length - 1; i++) {
            if (ascendingOrder) {
                if (desiredValue >= valuesArray[i] && desiredValue <= valuesArray[i + 1]) {
                    indices[0] = i;
                    break;
                }
            } else {
                if (desiredValue <= valuesArray[i] && desiredValue >= valuesArray[i + 1]) {
                    indices[0] = i;
                    break;
                }
            }

        }
        indices[1] = indices[0] + 1;
        return indices;
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
//    public static int binarySearch(int[] array, int elementToSearch) {
//        int firstIndex = 0;
//        int lastIndex = array.length - 1;
//
//        while(firstIndex <= lastIndex) {
//            int middleIndex = (firstIndex + lastIndex) / 2;
//            if (array[middleIndex] == elementToSearch) {
//                return middleIndex;
//            } else if (array[middleIndex] < elementToSearch) {
//                firstIndex = middleIndex + 1;
//            } else if (array[middleIndex] > elementToSearch) {
//                lastIndex = middleIndex - 1;
//            }
//        }
//        return -1;
//    }
}
