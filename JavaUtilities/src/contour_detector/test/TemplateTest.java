package contour_detector.test;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;
import contour_detector.TemplateMatching;

public class TemplateTest {

	public static void main(String[] args) {

		
		PgmUtilities pgmu = new PgmUtilities();

		PGM pgm = pgmu.readPGM("images/estate.pgm");
		PGM imgOut = pgmu.copyPGM(pgm);

		TemplateMatching template = new TemplateMatching();

		template.makeFilter(pgm, imgOut);
		// pgmu.normalizeModule(imgOut.getPixels(), imgOut);
		
		pgmu.normalizeModule(imgOut.getPixels(), imgOut);
		pgmu.writePGM(imgOut, "EstateT.pgm");
	}

}
