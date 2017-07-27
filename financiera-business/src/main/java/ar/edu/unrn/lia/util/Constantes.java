package ar.edu.unrn.lia.util;

import java.util.Random;

public class Constantes {
	public static final String GLOBAL_CLAVE = "ab";

	public static String generate_password(){
		StringBuilder pass = new StringBuilder();
		Random random = new Random();
		random.ints(8, 0, "0123456789ABCDEF".length())
				.forEach(index -> pass.append("0123456789ABCDEF".charAt(index)));
		return pass.toString();
	}
	
}
