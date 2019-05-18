package apm.muei.distancenevermatters.entities;

import java.io.Serializable;

public class Player implements Serializable {

    private Marker marker;
    private Model model;
    private User user;

    public Player(User user, Marker marker, Model model) {
        super();
        this.marker = marker;
        this.model = model;
        this.user = user;
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

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

