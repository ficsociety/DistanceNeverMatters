package apm.muei.ficsociety.backend.service.user;

import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apm.muei.ficsociety.backend.controller.dto.JoinGameDto;
import apm.muei.ficsociety.backend.domain.game.Game;
import apm.muei.ficsociety.backend.domain.game.GameRepository;
import apm.muei.ficsociety.backend.domain.game.GameState;
import apm.muei.ficsociety.backend.domain.marker.Marker;
import apm.muei.ficsociety.backend.domain.marker.MarkerRepository;
import apm.muei.ficsociety.backend.domain.model.Model;
import apm.muei.ficsociety.backend.domain.model.ModelRepository;
import apm.muei.ficsociety.backend.domain.player.Player;
import apm.muei.ficsociety.backend.domain.player.PlayerRepository;
import apm.muei.ficsociety.backend.domain.user.User;
import apm.muei.ficsociety.backend.domain.user.UserRepository;
import javassist.NotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MarkerRepository markerRepository;
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public User getUserByToken(String email) {
		String username;
		username = new StringTokenizer(email, "@").nextToken();

		if (email.endsWith(".es")) {
			username = username.concat("$");
		}

		Optional<User> user = userRepository.findById(username);
		if (!user.isPresent()) {

			return this.userRepository.save(new User(username));
		} else {
			return user.get();
		}
	}

	@Override
	public void joinGame(String token, JoinGameDto joinGameDto) throws NotFoundException {
		User user = getUserByToken(token);
		Optional<Marker> marker = markerRepository.findById(joinGameDto.getMarkerId());
		Optional<Model> model = modelRepository.findById(joinGameDto.getModelId());
		Game game = gameRepository.findByCode(joinGameDto.getCode());

		if (marker.isPresent() && model.isPresent() && game.getState().equals(GameState.PAUSED)) {
			Player player = new Player(user, marker.get(), model.get(), game);
			playerRepository.save(player);

			game.addPlayer(player);
			gameRepository.save(game);

		} else {
			throw new NotFoundException("Cant join");
		}

	}

	@Override
	public void leaveGame(String token, long code) throws NotFoundException {
		User user = getUserByToken(token);
		Game game = gameRepository.findByCode(code);

		Player player = playerRepository.findByUserAndGame(user, game);

		if (player != null) {
			game.getPlayers().remove(player);
			gameRepository.save(game);
			playerRepository.deleteById(player.getId());
		} else {
			throw new NotFoundException("player does not exist");
		}

	}
}
