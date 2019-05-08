package apm.muei.distancenevermatters.entities.dto;

import java.util.Date;
import java.util.List;

import apm.muei.distancenevermatters.entities.GameState;
import apm.muei.distancenevermatters.entities.User;

public class GameDetailsDto {

    private String name;
    private String description;
    //private Date date;
    private GameState gameState;
    private long code;
    private User master;
    private List<User> players;

    public GameDetailsDto() {
    }

    public GameDetailsDto(String name, String description, User master, long code, GameState gameState,
                          List<User> players) {
        super();
        this.name = name;
        this.description = description;
        this.master = master;
        //this.date = date;
        this.gameState = gameState;
        this.code = code;
        this.players = players;
    }

    public GameDetailsDto(String name, String description, User master, GameState gameState) {
        super();
        this.name = name;
        this.description = description;
        this.master = master;
        this.gameState = gameState;
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

    /*public Date getDate() {
        return date;
    }*/

    public List<User> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    public long getCode() {
        return code;
    }
}
