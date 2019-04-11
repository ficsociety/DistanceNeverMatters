package apm.muei.distancenevermatters.entities.dto;

import apm.muei.distancenevermatters.entities.GameState;

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
