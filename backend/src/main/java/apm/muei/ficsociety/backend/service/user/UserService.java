package apm.muei.ficsociety.backend.service.user;

import apm.muei.ficsociety.backend.controller.dto.JoinGameDto;
import apm.muei.ficsociety.backend.domain.user.User;
import javassist.NotFoundException;

public interface UserService {

	User getUserByToken(String token);

	void joinGame(String token, JoinGameDto joinGameDto) throws NotFoundException;

	void leaveGame(String token, long code) throws NotFoundException;
}
