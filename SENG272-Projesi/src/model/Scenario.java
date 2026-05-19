package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A measurement scenario: named context with a full dimension / metric tree.
 */
public class Scenario {
    private final String name;
    private final List<Dimension> dimensions;

    public Scenario(String name) {
        this.name = name;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(Dimension d) {
        dimensions.add(d);
    }

    public String getName() {
        return name;
    }

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    /** Deep copy so each wizard run mutates its own metrics independently of templates. */
    public Scenario deepCopy() {
        Scenario s = new Scenario(this.name);
        for (Dimension d : dimensions) {
            s.addDimension(d.copy());
        }
        return s;
    }
}
