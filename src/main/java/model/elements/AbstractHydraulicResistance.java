package model.elements;

public abstract class AbstractHydraulicResistance implements HydraulicResistance {
    protected final String name;

    protected AbstractHydraulicResistance(String name) {
        this.name = name;
    }

    public abstract void validateParameters();

    public String getName() {
        return name;
    }
}
