package contour_detector.test;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;
import contour_detector.TemplateMatching;

public class TemplateTest {

	public static void main(String[] args) {

		
		PgmUtilities pgmu = new PgmUtilities();

		PGM pgm = pgmu.readPGM("images/piante.pgm");
		PGM imgOut = pgmu.copyPGM(pgm);

		TemplateMatching template = new TemplateMatching();

		template.applyFilter(pgm, imgOut);

//		pgmu.normalizeModule(imgOut.getPixels(), imgOut);
//		int[] histogram = pgmu.histogramPGM(imgOut);
//		pgmu.EqualizerModule(histogram, imgOut, imgOut.getPixels());
		pgmu.writePGM(imgOut, "pianteT.pgm");
	}

}
