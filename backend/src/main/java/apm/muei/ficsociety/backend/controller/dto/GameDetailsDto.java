package apm.muei.ficsociety.backend.controller.dto;

import java.util.Date;
import java.util.List;

import apm.muei.ficsociety.backend.domain.game.GameState;
import apm.muei.ficsociety.backend.domain.user.User;

public class GameDetailsDto {

	private String name;
	private String description;
	private Date date;
	private GameState state;
	private long code;
	private User master;
	private List<PlayerDto> players;

	public GameDetailsDto() {
	}

	public GameDetailsDto(String name, String description, User master, Date date, long code, GameState state,
			List<PlayerDto> players) {
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

	public List<PlayerDto> getPlayers() {
		return players;
	}

	public GameState getState() {
		return state;
	}

	public long getCode() {
		return code;
	}

}
