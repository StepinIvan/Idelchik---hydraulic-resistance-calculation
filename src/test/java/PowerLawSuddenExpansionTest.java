import model.elements.areaChanges.PowerLowSuddenExpansion;
import model.elements.areaChanges.UniformSuddenExpansion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerLawSuddenExpansionTest {
    @Test
    public void LocalResistanceLeftUpperBoard () {
        PowerLowSuddenExpansion powerLowSuddenExpansion =
                new PowerLowSuddenExpansion(0.909728368, 12360.77446, 15e-6,
                        1, 0.5);
        double result = powerLowSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(2.6999999848333336, result);
    }
    @Test
    public void LocalResistanceLeftLowerBoard () {
        PowerLowSuddenExpansion powerLowSuddenExpansion =
                new PowerLowSuddenExpansion(0.909728368, 12360.77446, 15e-6,
                        1, 1.7976931E308);
        double result = powerLowSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(0.9999999914135682, result);
    }
    @Test
    public void LocalResistanceRightUpperBoard () {
        PowerLowSuddenExpansion powerLowSuddenExpansion =
                new PowerLowSuddenExpansion(1.909728368, 0.909728368, 15e-6,
                        1, 0.1);
        double result = powerLowSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(0.7, result);
    }
    @Test
    public void LocalResistanceRightLowerBoard () {
        PowerLowSuddenExpansion powerLowSuddenExpansion =
                new PowerLowSuddenExpansion(1.909728368, 0.909728368, 15e-6,
                        1, 1.7976931E308);
        double result = powerLowSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(3.878561360970423E-10, result);
    }
    @Test
    public void LocalResistanceExactValue () {
        PowerLowSuddenExpansion powerLowSuddenExpansion =
                new PowerLowSuddenExpansion(0.797884561, 1.128379167,
                        15e-6, 1,3);
        double result = powerLowSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(0.4099999996351109, result);
    }
    @Test
    public void LocalResistanceBilinearInterpolation1 () {
        PowerLowSuddenExpansion powerLowSuddenExpansion =
                new PowerLowSuddenExpansion(0.529256743, 1.128379167,
                        15e-6, 1,3.7);
        double result = powerLowSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(0.7627999997345523, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation1 () {
        PowerLowSuddenExpansion powerLowSuddenExpansion =
                new PowerLowSuddenExpansion(0.529256743, 1.128379167,
                        15e-6, 1,3.);
        double result = powerLowSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(0.81599999971073, result);
    }
    @Test
    public void LocalResistanceLinearInterpolation2 () {
        PowerLowSuddenExpansion powerLowSuddenExpansion =
                new PowerLowSuddenExpansion(0.504626504, 1.128379167,
                        15e-6, 1,3.7);
        double result = powerLowSuddenExpansion.calculateLocalResistanceCoefficient();
        assertEquals(0.7940000005241193, result);
    }
}
