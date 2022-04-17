package stage2.Tasks2;

public class Street {
    private final String name;
    private final StreetType type;

    private enum StreetType {
        AVENUE,
        STREET,
        BOULEVARD
    }

    public Street(String name, StreetType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public StreetType getType() {
        return type;
    }
}
