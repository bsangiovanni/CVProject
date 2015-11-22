package contour_detector.test;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;
import contour_detector.Gaussian;

public class GaussianTest {

	public static void main(String[] args) {
		
		
		PgmUtilities pgmu = new PgmUtilities();
		
		PGM pgm = pgmu.readPGM("images/estate.pgm");
		PGM imgOut = pgmu.copyPGM(pgm);
		
		Gaussian gauss = new Gaussian();
		

		gauss.applyFilter(imgOut, imgOut);
//		pgmu.normalizeModule(imgOut.getPixels(), imgOut);
//		gauss.threshold(imgOut);
		
		pgmu.writePGM(imgOut, "estategauss2.pgm");

	}

}
