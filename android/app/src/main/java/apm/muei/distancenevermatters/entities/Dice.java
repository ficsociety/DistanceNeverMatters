package apm.muei.distancenevermatters.entities;

public class Dice {
    private Long id;
    private String name;
    private int value;
    private int img;

    public Dice(Long id, String name, int value, int img) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getImg() {
        return img;
    }
}
