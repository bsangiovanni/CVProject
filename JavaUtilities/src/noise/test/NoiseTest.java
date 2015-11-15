package noise.test;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;
import noise.AverageFilter;
import noise.MedianFilter;
import noise.NagaoFilter;
import noise.Noise;

public class NoiseTest {
	
	public static void main(String[] args) {
		
		
PgmUtilities pgmu = new PgmUtilities();
		
		PGM pgm = pgmu.readPGM("images/estate.pgm");
		PGM imgOut = pgmu.copyPGM(pgm);
		Noise noise = new Noise();
		AverageFilter aFilter= new AverageFilter();
		MedianFilter mFilter = new MedianFilter();
		NagaoFilter nFilter = new NagaoFilter();
		
		// SALT AND PEPPER
		
		noise.addSaltPepper(pgm, imgOut);
		pgmu.writePGM(imgOut, "filtered/estate/EstateSP.pgm");
//		
//		aFilter.makeAverageFilter(imgOut, imgOut, 3);
//		aFilter.makeAverageFilter(imgOut, imgOut, 3);
//		mFilter.makeMedianFilter(imgOut, imgOut, 3);
//		mFilter.makeMedianFilter(imgOut, imgOut, 3);
		nFilter.makeNagaoFilter(imgOut, imgOut);
		pgmu.writePGM(imgOut, "filtered/estate/EstateSPNF2.pgm");
		

		// UNIFORM NOISE
		
//		noise.addUniformNoise(pgm, imgOut);
//		pgmu.writePGM(imgOut, "filtered/Pavia1/Pavia1UN.pgm");
//		
//		aFilter.makeAverageFilter(imgOut, imgOut, 3);
//		aFilter.makeAverageFilter(imgOut, imgOut, 3);
//		mFilter.makeMedianFilter(imgOut, imgOut, 3);
//		mFilter.makeMedianFilter(imgOut, imgOut, 3);
//		nFilter.makeNagaoFilter(imgOut, imgOut);
//		pgmu.writePGM(imgOut, "filtered/Pavia1/Pavia1UNNF.pgm");
		
		
		
	}

}
