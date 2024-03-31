package domain.coordinate;

public enum Column {

    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6),
    H(7);

    private final int value;

    Column(int value) {
        this.value = value;
    }

    public String getLowerName() {
        return name().toLowerCase();
    }

    public int getValue() {
        return value;
    }
}
