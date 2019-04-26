package apm.muei.ficsociety.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.api.client.util.Lists;
import com.google.common.collect.Iterables;

import apm.muei.ficsociety.backend.controller.dto.GameDetailsDto;
import apm.muei.ficsociety.backend.controller.dto.JoinGameDto;
import apm.muei.ficsociety.backend.service.game.GameService;
import apm.muei.ficsociety.backend.service.user.UserService;
import javassist.NotFoundException;

@RestController
public class UserController {

	@Autowired
	private GameService gameService;

	@Autowired
	private UserService userService;

	@GetMapping("/games")
	public List<GameDetailsDto> getAllGames(@RequestHeader String token) {
		List<GameDetailsDto> masterGames = gameService.getMasterGames(token);
		List<GameDetailsDto> playerGames = gameService.getUserGames(token);

		return Lists.newArrayList(Iterables.concat(masterGames, playerGames));
	}

	@GetMapping("/user/master/games")
	public List<GameDetailsDto> getMasterGames(@RequestHeader String token) {
		return gameService.getMasterGames(token);
	}

	@GetMapping("/user/games")
	public List<GameDetailsDto> getUserGames(@RequestHeader String token) {
		return gameService.getUserGames(token);
	}

	@PostMapping("/game/join")
	public void joinGame(@RequestHeader String token, @RequestBody JoinGameDto joinGameDto) {
		try {
			userService.joinGame(token, joinGameDto);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "identificadores incorrectos");
		}
	}

	@DeleteMapping("/game/leave/{code}")
	public void leaveGame(@RequestHeader String token, @PathVariable long code) {
		try {
			userService.leaveGame(token, code);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "player does not exist");
		}
	}
}
