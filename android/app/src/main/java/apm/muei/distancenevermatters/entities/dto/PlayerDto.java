package apm.muei.distancenevermatters.entities.dto;

import apm.muei.distancenevermatters.entities.Marker;
import apm.muei.distancenevermatters.entities.Model;
import apm.muei.distancenevermatters.entities.Player;
import apm.muei.distancenevermatters.entities.User;

public class PlayerDto {

    private Marker marker;
    private Model model;
    private User user;

    public PlayerDto() {

    }

    public PlayerDto(Marker maker, Model mode, User user) {
        this.marker = marker;
        this.model = model;
        this.user = user;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PlayerDto{" +
                "marker=" + marker +
                ", model=" + model +
                ", user=" + user +
                '}';
    }
}