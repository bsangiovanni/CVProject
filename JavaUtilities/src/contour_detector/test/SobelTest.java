package contour_detector.test;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;
import contour_detector.Sobel;

public class SobelTest {
	public static void main(String[] args) {
		
		Sobel sobel = new Sobel();
		
		PgmUtilities pgmu = new PgmUtilities();
		
		PGM pgm = pgmu.readPGM("images/estate.pgm");
		PGM imgOut = pgmu.copyPGM(pgm);

		sobel.applyFilter(pgm, imgOut);
		pgmu.normalizeModule(imgOut.getPixels(), imgOut);

//		pgmu.EqualizerModule(histogram, imgOut, imgOut.getPixels());
		pgmu.writePGM(imgOut, "moduloEstate3.pgm");
		
		sobel.normalizeSobelPhase(imgOut);
//
//		int[] histogram = pgmu.histogramPGM(imgOut);
//		pgmu.EqualizerModule(histogram, imgOut, imgOut.getPixels());
		pgmu.writePGM(imgOut, "faseEstate3.pgm");

//		
//		
//		pgmu.normalizeModule(histogram, imgOut);
//		pgmu.writePGM(imgOut, "moduloEstate.pgm");

		
		
	}
}
