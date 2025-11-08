package model.elements.bends.utils;

public class LinearInterpolator {
    private final double[] x;
    private final double[] y;

    public LinearInterpolator(double[] x, double[] y) {
        validateParameters(x, y);
        this.x = x.clone();
        this.y = y.clone();
    }

    private void validateParameters(double[] x, double[] y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("One of the arrays or both are null");
        }
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays length mismatch");
        }
        if (x.length == 0) {
            throw new IllegalArgumentException("Arrays must be nonzero");
        }
        for (int i = 0; i < x.length - 1; i++) {
            if (x[i + 1] <= x[i]) {
                throw new IllegalArgumentException("X array must be strictly sorted in ascending order");
            }
        }
    }

    public double interpolate(double userValueOfx) {
        if (userValueOfx <= x[0]) {
            return y[0];
        }
        if (userValueOfx >= x[x.length - 1]) {
            return y[y.length - 1];
        }
        int i = 0;
        while (userValueOfx > x[i + 1]) {
            i++;
        } //values order check
        double k = (userValueOfx - x[i]) / (x[i + 1] - x[i]);
        return y[i] + k * (y[i + 1] - y[i]);
    } //TODO Add unit tests
}
