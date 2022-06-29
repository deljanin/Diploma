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

    public IntersectionData(IntersectionData obj) {
        this.id = obj.id;
        this.type = obj.type;
        this.arc1 = obj.arc1;
        this.arc2 = obj.arc2;
        this.x = obj.x;
        this.y = obj.y;
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
