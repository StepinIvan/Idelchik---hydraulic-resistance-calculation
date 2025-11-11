package model.elements.areaChanges;

import model.elements.AbstractHydraulicResistance;

public abstract class AreaChange extends AbstractHydraulicResistance {
    protected final double smallerDiameter;
    protected final double largerDiameter;
    protected final double length;

    protected AreaChange(String name, double smallerDiameter, double largerDiameter, double length) {
        super(name);
        this.smallerDiameter = smallerDiameter;
        this.largerDiameter = largerDiameter;
        this.length = length;
    }

}
