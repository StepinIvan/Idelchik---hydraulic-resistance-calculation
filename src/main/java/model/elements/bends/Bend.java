package model.elements.bends;

import model.elements.AbstractHydraulicResistance;

public abstract class Bend extends AbstractHydraulicResistance {
    protected final double bendAngle;
    protected final double bendCurvatureRadius;

    protected Bend(String name, double bendAngle, double bendCurvatureRadius) {
        super(name);
        this.bendAngle = bendAngle;
        this.bendCurvatureRadius = bendCurvatureRadius;
    }

    public double getBendAngle() {
        return bendAngle;
    }
}
