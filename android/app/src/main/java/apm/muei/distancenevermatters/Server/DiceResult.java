package apm.muei.distancenevermatters.Server;

import java.util.List;

import apm.muei.distancenevermatters.entities.dto.DiceResultDto;

public class DiceResult {

    private String user;
    private List<DiceResultDto> resutlDice;

    public DiceResult(String user, List<DiceResultDto> resutlDice ) {
        this.user = user;
        this.resutlDice = resutlDice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<DiceResultDto> getResutlDice() {
        return resutlDice;
    }

    public void setResutlDice(List<DiceResultDto> resutlDice) {
        this.resutlDice = resutlDice;
    }
}
