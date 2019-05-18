package apm.muei.ficsociety.backend.service.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apm.muei.ficsociety.backend.controller.dto.CreateGameDto;
import apm.muei.ficsociety.backend.controller.dto.GameDetailsDto;
import apm.muei.ficsociety.backend.controller.dto.PlayerDto;
import apm.muei.ficsociety.backend.controller.dto.UpdateStateDto;
import apm.muei.ficsociety.backend.domain.game.CodeGenerator;
import apm.muei.ficsociety.backend.domain.game.Game;
import apm.muei.ficsociety.backend.domain.game.GameRepository;
import apm.muei.ficsociety.backend.domain.map.Map;
import apm.muei.ficsociety.backend.domain.map.MapRepository;
import apm.muei.ficsociety.backend.domain.marker.Marker;
import apm.muei.ficsociety.backend.domain.marker.MarkerRepository;
import apm.muei.ficsociety.backend.domain.model.Model;
import apm.muei.ficsociety.backend.domain.model.ModelRepository;
import apm.muei.ficsociety.backend.domain.player.Player;
import apm.muei.ficsociety.backend.domain.player.PlayerRepository;
import apm.muei.ficsociety.backend.domain.user.User;
import apm.muei.ficsociety.backend.service.user.UserService;
import javassist.NotFoundException;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private MapRepository mapRepository;
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private MarkerRepository markerRepository;

	@Override
	public Game createGame(String token, CreateGameDto gameDto) {

		Optional<Map> map = mapRepository.findById(gameDto.getMapId());
		User master = userService.getUserByToken(token);

		Game game = new Game(gameDto.getName(), gameDto.getDescription(), master, map.get());

		game = gameRepository.save(game);

		for (Entry<String, Long> entry : gameDto.getMarkersModels().entrySet()) {
			Optional<Marker> marker = markerRepository.findById(Long.valueOf(entry.getKey()));
			Optional<Model> model = modelRepository.findById(entry.getValue());

			if (marker.isPresent() && model.isPresent()) {
				Player player = playerRepository.save(new Player(master, marker.get(), model.get(), game));
				game.addPlayer(player);
			}
		}
		game.setCode(getCode());

		return this.gameRepository.save(game);
	}

	private long getCode() {
		long result = 0;
		boolean repeated = true;

		while (repeated) {
			result = CodeGenerator.generate();
			repeated = gameRepository.existsByCode(result);
		}

		return result;
	}

	@Override
	public List<GameDetailsDto> getMasterGames(String token) {
		List<GameDetailsDto> result = new ArrayList<>();

		User user = userService.getUserByToken(token);
		List<Game> games = this.gameRepository.findByMaster(user);

		for (Game game : games) {
			result.add(gameToGameDetailsDto(game));
		}
		return result;
	}

	@Override
	public List<GameDetailsDto> getUserGames(String token) {
		List<GameDetailsDto> result = new ArrayList<>();

		User user = userService.getUserByToken(token);

		for (Player player : playerRepository.findByUser(user)) {
			if (!player.getGame().getMaster().equals(user)) {
				result.add(gameToGameDetailsDto(player.getGame()));
			}
		}

		return result;
	}

	@Override
	public GameDetailsDto getGameByCode(long code) throws NotFoundException {
		Game game = this.gameRepository.findByCode(code);
		if (game == null)
			throw new NotFoundException("Invalid code");
		return gameToGameDetailsDto(game);
	}

	private GameDetailsDto gameToGameDetailsDto(Game game) {
		List<PlayerDto> players = new ArrayList<>();

		for (Player player : game.getPlayers()) {
			players.add(new PlayerDto(player));
		}

		return new GameDetailsDto(game.getName(), game.getDescription(), game.getMaster(), game.getDate(),
				game.getCode(), game.getState(), game.getMap(), players);

	}

	@Override
	public void updateState(UpdateStateDto updateStateDto) throws NotFoundException {
		Game game = this.gameRepository.findByCode(updateStateDto.getCode());
		if (game == null) {
			throw new NotFoundException("Invalid code");
		} else {
			game.setState(updateStateDto.getState());
			gameRepository.save(game);
		}
	}

	@Override
	public void deleteGame(long code) throws NotFoundException {
		Game game = this.gameRepository.findByCode(code);

		if (game == null) {
			throw new NotFoundException("Invalid code");
		} else {
			this.gameRepository.delete(game);
		}
	}

	@Override
	public void updateGame(GameDetailsDto gameDetails) {
		Game game = this.gameRepository.findByCode(gameDetails.getCode());

		game.setState(gameDetails.getState());
		game.setName(gameDetails.getName());
		game.setDescription(gameDetails.getDescription());

		List<Player> players = new ArrayList<>(game.getPlayers());

		for (Player player : players) {

			if (removePlayer(gameDetails, player)) {
				game.getPlayers().remove(player);
				playerRepository.delete(player);
			}
		}

		this.gameRepository.save(game);

	}

	private boolean removePlayer(GameDetailsDto gameDetails, Player player) {

		for (PlayerDto playerDto : gameDetails.getPlayers()) {
			if (playerDto.getUser().equals(player.getUser()) && playerDto.getMarker().equals(player.getMarker())) {
				return false;
			}

		}

		return true;
	}
}
