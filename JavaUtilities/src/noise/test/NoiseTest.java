package noise.test;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;
import noise.AverageFilter;
import noise.MedianFilter;
import noise.Noise;

public class NoiseTest {
	
	public static void main(String[] args) {
		
		
PgmUtilities pgmu = new PgmUtilities();
		
		PGM pgm = pgmu.readPGM("images/estate.pgm");
		PGM imgOut = pgmu.copyPGM(pgm);
		Noise uniformNoise = new Noise();
		AverageFilter aFilter= new AverageFilter();
		MedianFilter mFilter = new MedianFilter();
		
		// SALT AND PEPPER
		
//		uniformNoise.addSaltPepper(pgm, imgOut);
//		pgmu.writePGM(imgOut, "EstateSP.pgm");
//		
//		aFilter.makeAverageFilter(imgOut, imgOut, 3);
//		aFilter.makeAverageFilter(imgOut, imgOut, 3);
//		mFilter.makeMedianFilter(imgOut, imgOut, 3);
//		mFilter.makeMedianFilter(imgOut, imgOut, 3);
//		pgmu.writePGM(imgOut, "EstateSPMF.pgm");
		

		// UNIFORM NOISE
		
		uniformNoise.addUniformNoise(pgm, imgOut);
		pgmu.writePGM(imgOut, "EstateUN.pgm");
		
		aFilter.makeAverageFilter(imgOut, imgOut, 9);
		aFilter.makeAverageFilter(imgOut, imgOut, 9);
//		mFilter.makeMedianFilter(imgOut, imgOut, 5);
//		mFilter.makeMedianFilter(imgOut, imgOut, 5);
		pgmu.writePGM(imgOut, "EstateUNAF.pgm");
		
		
	}

}
