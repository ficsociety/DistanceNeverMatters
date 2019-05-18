package apm.muei.ficsociety.backend.controller.dto;

import apm.muei.ficsociety.backend.domain.marker.Marker;
import apm.muei.ficsociety.backend.domain.model.Model;
import apm.muei.ficsociety.backend.domain.player.Player;
import apm.muei.ficsociety.backend.domain.user.User;

public class PlayerDto {

	private Marker marker;
	private Model model;
	private User user;

	public PlayerDto() {

	}

	public PlayerDto(Player player) {
		this.marker = player.getMarker();
		this.model = player.getModel();
		this.user = player.getUser();
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

}
