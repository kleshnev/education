package stage2.Tasks2;

public class Locality {
    private String square;
    private String name;
    private String date;
    private int population;
    private int type;

    private enum LocalityType {
        CITY("Город"),
        VILLAGE("Деревня"),
        COUNTRYSIDE("Село");
        private final String stringValue;

        LocalityType(final String s) {
            stringValue = s;
        }

        public String toString() {
            return stringValue;
        }
    }

    public Locality(String square, String name,
                    int type, String date, int population) {

        this.square = square;
        this.name = name;
        this.type = type;
        this.date = date;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public int getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getSquare() {
        return square;
    }
}
