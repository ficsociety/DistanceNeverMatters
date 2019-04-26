package apm.muei.ficsociety.backend.domain.player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import apm.muei.ficsociety.backend.domain.game.Game;
import apm.muei.ficsociety.backend.domain.marker.Marker;
import apm.muei.ficsociety.backend.domain.model.Model;
import apm.muei.ficsociety.backend.domain.user.User;

@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	private Marker marker;
	@ManyToOne
	private Model model;
	@ManyToOne
	private User user;
	@ManyToOne
	private Game game;

	public Player() {
	}

	public Player(User user, Marker marker, Model model, Game game) {
		super();
		this.marker = marker;
		this.model = model;
		this.user = user;
		this.game = game;
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

	public Game getGame() {
		return game;
	}

}
