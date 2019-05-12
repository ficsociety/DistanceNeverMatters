package apm.muei.ficsociety.backend.domain.game;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import apm.muei.ficsociety.backend.domain.map.Map;
import apm.muei.ficsociety.backend.domain.player.Player;
import apm.muei.ficsociety.backend.domain.user.User;

@Entity
@Table(name = "game")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	private Date date;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "uid")
	private User master;
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private GameState state;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "map")
	private Map map;
	@Column(unique = true)
	private long code;
	@OneToMany(orphanRemoval = true)
	private Set<Player> players;

	public Game() {
	}

	public Game(String name, String description, User master, Map map) {
		super();
		this.name = name;
		this.description = description;
		this.master = master;
		this.date = new Date();
		this.state = GameState.PAUSED;
		this.map = map;
		this.players = new HashSet<>();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

	public User getMaster() {
		return master;
	}

	public GameState getState() {
		return state;
	}

	public Map getMap() {
		return map;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
