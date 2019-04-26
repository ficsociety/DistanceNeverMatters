package apm.muei.ficsociety.backend.domain.game;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import apm.muei.ficsociety.backend.domain.user.User;

public interface GameRepository extends JpaRepository<Game, Long> {

	List<Game> findByMaster(User user);

	boolean existsByCode(long code);

	Game findByCode(long code);
}
