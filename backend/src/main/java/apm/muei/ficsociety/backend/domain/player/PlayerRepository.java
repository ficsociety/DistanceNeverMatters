package apm.muei.ficsociety.backend.domain.player;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import apm.muei.ficsociety.backend.domain.game.Game;
import apm.muei.ficsociety.backend.domain.user.User;

public interface PlayerRepository extends JpaRepository<Player, Long> {

	List<Player> findByUser(User user);

	Player findByUserAndGame(User user, Game game);

}
