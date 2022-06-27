package data;

public class IntersectionData {
    public int id;
    public int type;
    public int arc1;
    public int arc2;
    public int x;
    public int y;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "IntersectionData{" +
                "id=" + id +
                ", type=" + type +
                ", arc1=" + arc1 +
                ", arc2=" + arc2 +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
