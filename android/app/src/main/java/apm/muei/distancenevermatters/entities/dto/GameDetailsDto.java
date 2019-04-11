package apm.muei.distancenevermatters.entities.dto;

import java.util.Date;
import java.util.List;

import apm.muei.distancenevermatters.entities.GameState;
import apm.muei.distancenevermatters.entities.User;

public class GameDetailsDto {

    private String name;
    private String description;
    private Date date;
    private GameState state;
    private long code;
    private User master;
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
