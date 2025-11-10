package model.elements.areaChanges;

import model.elements.AbstractHydraulicResistance;

public abstract class AreaChange extends AbstractHydraulicResistance {
    protected final double smallerDiameter;
    protected final double largerDiameter;

    protected AreaChange(String name, double smallerDiameter, double largerDiameter) {
        super(name);
        this.smallerDiameter = smallerDiameter;
        this.largerDiameter = largerDiameter;
    }

}
