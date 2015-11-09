package noise.test;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;
import noise.Noise;

public class NoiseTest {
	
	public static void main(String[] args) {
		
		
PgmUtilities pgmu = new PgmUtilities();
		
		PGM pgm = pgmu.readPGM("images/estate.pgm");
		PGM imgOut = pgmu.copyPGM(pgm);
		
		Noise uniformNoise = new Noise();
		
		uniformNoise.addSaltPepper(pgm, imgOut);
		
		pgmu.writePGM(imgOut, "EstateSP.pgm");
		
	}

}
