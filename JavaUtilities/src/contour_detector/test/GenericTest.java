package contour_detector.test;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;
import contour_detector.Gaussian;
import contour_detector.Sobel;
import contour_detector.TemplateMatching;

public class GenericTest {

	public static void main(String[] args) {
		
		Sobel sobel = new Sobel();
		
		PgmUtilities pgmu = new PgmUtilities();
		
//		PGM pgm = pgmu.readPGM("images/Pavia1.pgm");
//		PGM imgOut = pgmu.copyPGM(pgm);
	
		//------SOBEL-----//
		
//		PGM pgm = pgmu.readPGM("filtered/inverno/InvernoUNAF.pgm"); //to change after noise test
//		PGM imgOut = pgmu.copyPGM(pgm);
//		
//		float[] phase = new float[pgm.getHeight()*pgm.getWidth()];
//
//		sobel.makeSobel(pgm, imgOut, phase);
//		pgmu.normalizeModule(imgOut.getPixels(), imgOut);
//		pgmu.writePGM(imgOut, "filtered/inverno/InvernoUNNFModuloSobel.pgm");
//		
//		pgmu.normalizePhase(phase, imgOut);
//		pgmu.writePGM(imgOut, "filtered/inverno/InvernoUNNFaseSobel.pgm");
		
		
		
		//-----DoG------//
		
//		PGM pgm = pgmu.readPGM("filtered/Pavia1/Pavia1UNNF.pgm"); //to change after noise test
//		PGM imgOut = pgmu.copyPGM(pgm);
//		
//		imgOut = pgmu.copyPGM(pgm);
//		Gaussian gauss = new Gaussian();
//		gauss.makeGaussian(pgm, imgOut);
//		pgmu.writePGM(imgOut, "filtered/Pavia1/Pavia1UNNFDoG.pgm");
		
		//----3 over 9----//
		PGM pgm = pgmu.readPGM("filtered/Pavia1/Pavia1UNNF.pgm"); //to change after noise test
		PGM imgOut = pgmu.copyPGM(pgm);
		
		TemplateMatching template = new TemplateMatching();
		template.applyFilter(pgm, imgOut);
		pgmu.writePGM(imgOut, "filtered/Pavia1/Pavia1UNNFThreeOverNine.pgm");
		
		//Make images with noise in "NoiseTest" and repeat them
		
	}
}
