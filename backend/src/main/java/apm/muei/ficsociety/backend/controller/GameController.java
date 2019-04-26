package apm.muei.ficsociety.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import apm.muei.ficsociety.backend.controller.dto.CreateGameDto;
import apm.muei.ficsociety.backend.controller.dto.GameDetailsDto;
import apm.muei.ficsociety.backend.controller.dto.UpdateStateDto;
import apm.muei.ficsociety.backend.service.game.GameService;
import javassist.NotFoundException;

@RestController
public class GameController {

	@Autowired
	private GameService gameService;

	@PostMapping("/games")
	public long createGame(@RequestHeader String token, @RequestBody CreateGameDto createGameDto) {
		return gameService.createGame(token, createGameDto).getCode();
	}

	@GetMapping("/game/{code}")
	public GameDetailsDto findByCode(@PathVariable long code) {
		GameDetailsDto gameDetailsDto;
		try {
			gameDetailsDto = gameService.getGameByCode(code);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provide correct code");
		}

		return gameDetailsDto;
	}

	@PostMapping("/game/state")
	public void updateState(@RequestBody UpdateStateDto updateStateDto) {
		try {
			gameService.updateState(updateStateDto);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provide correct code");
		}
	}
}
