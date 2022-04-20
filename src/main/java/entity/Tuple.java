package entity;

public class Tuple {
    private Individual first;
    private Individual second;

    public Tuple(Individual first, Individual second) {
        this.first = first;
        this.second = second;
    }

    public Individual getFirst() {
        return first;
    }

    public void setFirst(Individual first) {
        this.first = first;
    }

    public Individual getSecond() {
        return second;
    }

    public void setSecond(Individual second) {
        this.second = second;
    }
}
