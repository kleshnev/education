package stage2.Tasks3;

public class Cell {
    private int x;
    private int y;
    private String value;
    boolean startOrEnd;

    public Cell(int x, int y, String value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x= x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String newValue) {
        value = newValue;
    }
}
