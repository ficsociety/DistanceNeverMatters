package apm.muei.ficsociety.backend.controller.dto;

import apm.muei.ficsociety.backend.domain.game.GameState;

public class UpdateStateDto {

	private GameState state;
	private long code;

	public UpdateStateDto() {

	}

	public UpdateStateDto(GameState state, long code) {
		this.state = state;
		this.code = code;
	}

	public GameState getState() {
		return state;
	}

	public long getCode() {
		return code;
	}

}
