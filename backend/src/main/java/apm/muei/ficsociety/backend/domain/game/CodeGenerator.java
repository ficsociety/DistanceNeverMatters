package apm.muei.ficsociety.backend.domain.game;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CodeGenerator {

	private static Logger LOGGER = Logger.getLogger("GameRepository");

	public static long generate() {
		Random rand;
		long result = 0;
		try {
			rand = SecureRandom.getInstance("SHA1PRNG");

			result = rand.nextInt(60000);

			return result;
		} catch (NoSuchAlgorithmException e) {
			LOGGER.log(Level.WARNING, "No se ha encontrado el algoritmo para generar el código aleatorio", e);
		}

		return 0;
	}

}
