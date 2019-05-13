package apm.muei.distancenevermatters.entities;

public class Player {

    private long id;
    private Marker marker;
    private Model model;
    private User user;


    public Player() {
    }

    public Player(User user, Marker marker, Model model) {
        super();
        this.marker = marker;
        this.model = model;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public Marker getMarker() {
        return marker;
    }

    public Model getModel() {
        return model;
    }

    public User getUser() {
        return user;
    }

}

