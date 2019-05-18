package apm.muei.distancenevermatters.entities.dto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import apm.muei.distancenevermatters.entities.GameState;
import apm.muei.distancenevermatters.entities.Map;
import apm.muei.distancenevermatters.entities.Player;
import apm.muei.distancenevermatters.entities.User;

public class GameDetailsDto implements Serializable {

    private String name;
    private String description;
    //private Date date;
    private GameState state;
    private long code;
    private User master;
    private Map map;
    private List<Player> players;

    public GameDetailsDto() {
    }

    public GameDetailsDto(String name, String description, User master, long code, GameState state, Map map,
                          List<Player> players) {
        super();
        this.name = name;
        this.description = description;
        this.master = master;
        //this.date = date;
        this.state = state;
        this.map = map;
        this.code = code;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getMaster() {
        return master;
    }

//    public Date getDate() {
//        return date;
//    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameState getState() {
        return state;
    }

    public long getCode() {
        return code;
    }

    public Map getMap() {
        return map;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "GameDetailsDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", code=" + code +
                ", master=" + master +
                ", map=" + map +
                ", players=" + players +
                '}';
    }
}