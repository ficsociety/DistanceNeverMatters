package apm.muei.ficsociety.backend.service.game;

import java.util.List;

import apm.muei.ficsociety.backend.controller.dto.CreateGameDto;
import apm.muei.ficsociety.backend.controller.dto.GameDetailsDto;
import apm.muei.ficsociety.backend.controller.dto.UpdateStateDto;
import apm.muei.ficsociety.backend.domain.game.Game;
import javassist.NotFoundException;

public interface GameService {

	List<GameDetailsDto> getMasterGames(String token);

	Game createGame(String token, CreateGameDto gameDto);

	List<GameDetailsDto> getUserGames(String token);

	GameDetailsDto getGameByCode(long code) throws NotFoundException;

	void updateState(UpdateStateDto updateStateDto) throws NotFoundException;
}
