package apm.muei.distancenevermatters.entities.dto;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

import apm.muei.distancenevermatters.entities.GameState;
import apm.muei.distancenevermatters.entities.User;

public class GameDetailsDto {

    @Expose
    private String name;
    @Expose
    private String description;
    private Date date;
    @Expose
    private GameState state;
    @Expose
    private long code;
    @Expose
    private User master;
    @Expose
    private List<User> players;

    public GameDetailsDto() {
    }

    public GameDetailsDto(String name, String description, User master, Date date, long code, GameState state,
                          List<User> players) {
        super();
        this.name = name;
        this.description = description;
        this.master = master;
        this.date = date;
        this.state = state;
        this.code = code;
        this.players = players;
    }

    public GameDetailsDto(String name, String description, User master, GameState state) {
        super();
        this.name = name;
        this.description = description;
        this.master = master;
        this.state = state;
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

    public Date getDate() {
        return date;
    }

    public List<User> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return state;
    }

    public long getCode() {
        return code;
    }
}
