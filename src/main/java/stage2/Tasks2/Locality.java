package stage2.Tasks2;

public record Locality(String name,int area, int type, int date, int population) {

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public int getType() {
        return type;
    }

    public int getDate() {
        return date;
    }

    public int getSquare() {
        return area;
    }
}
