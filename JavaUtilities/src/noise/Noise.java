package noise;

import java.util.Random;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

public class Noise {
	
	private PgmUtilities pgmu = new PgmUtilities();
	
	/**
	 * Add a Uniform noise to a specify image. Change K to modify the intensity of the disturbance
	 */
	
	public void addUniformNoise(PGM imgIn, PGM imgOut){
		int k=30;
		int[] pixels = imgIn.getPixels();
		for (int i = 0; i < pixels.length; i++) {
			
			Random rand = new Random();
			pixels[i]=  pixels[i] +(int)(2*k*(rand.nextDouble() + 0.5));
		}
		
		pgmu.normalizeModule(pixels, imgOut);
		
		imgOut.setPixels(pixels);
		
	}
	
	/**
	 * Add a Salt and Pepper noise to a specify image. Change th1, th2 and k to modify the percentage of black and white, and their intensity.
	 * @param imgIn
	 * @param imgOut
	 */
	
	public void addSaltPepper(PGM imgIn, PGM imgOut){
		
		double th1 = 0.98;
		double th2 = 0.05;
		int k = 5;
		
		int[] pixels = imgIn.getPixels();
		for (int i = 0; i < pixels.length; i++) {
			Random rand = new Random();
			double rnd = rand.nextDouble();
			if (rnd >= th1){
				pixels[i] = 255;
			} else if (rnd <= th2){
				pixels[i]=0;
			} else {
				pixels[i]=  pixels[i] +(int)((2*(k-th2)/(th1-th2))*(rnd - 0.5));
			}
		}
		pgmu.normalizeModule(pixels, imgOut);
		
		imgOut.setPixels(pixels);
		
	}
	
	

}
