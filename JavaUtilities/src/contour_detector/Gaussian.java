package contour_detector;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

public class Gaussian {

	private PgmUtilities utility = new PgmUtilities();
	
	private int n=9;
	private int varianceA=1;
	private int varianceB=2;		


	public float[][] makeMask(int n, int varianceA, int varianceB) {
		this.n=n;
		this.varianceA=varianceA;
		this.varianceB=varianceB;
		
		int x, y;
		float[][] mask1 = new float[n][n];
		float[][] mask2 = new float[n][n];

		for (int i = 0; i < mask1.length; i++) {
			for (int j = 0; j < mask1.length; j++) {
				x = Math.abs((mask1.length / 2) - i);
				y = Math.abs((mask1.length / 2) - j);


				float espo = -(((x * x) + (y * y)) / (varianceA* 2.f) );
				mask1[i][j] = (float) ((1 / (2 * Math.PI * varianceA)) * (Math
						.exp(espo)));

			}

		}

		for (int i = 0; i < mask2.length; i++) {
			for (int j = 0; j < mask2.length; j++) {
				x = Math.abs((mask2.length / 2) - i);
				y = Math.abs((mask2.length / 2) - j);


				float espo = -(((x * x) + (y * y)) / (varianceB* 2.f) );
				mask2[i][j] = (float) ((1 / (2 * Math.PI * varianceB)) * (Math
						.exp(espo)));

			}

		}
		
		
		float[][] maskTot = new float[n][n];
		
		for (int i = 0; i < maskTot.length; i++) {
			for (int j = 0; j < maskTot.length; j++) {
				maskTot[i][j]=(mask1[i][j]-mask2[i][j]);
			}
		}
		

		return maskTot;
	}

	public void makeGaussian(PGM imgIn, PGM imgOut) {
		int width = imgIn.getWidth();
		int height = imgIn.getHeight();
		int[] pixels = imgIn.getPixels();

		int dim = width * height;
		int[] pixel_x = new int[dim];

		int[][] points = new int[n][n];

		for (int i = (n - 1) / 2; i < height - (n - 1) / 2; i++) {
			for (int j = (n - 1) / 2; j < width - (n - 1) / 2; j++) {
				for (int l = 0; l < n; l++) {
					for (int m = 0; m < n; m++) {
						points[l][m] = pixels[(i + (l -(n - 1) / 2)) * width
								+ (j + (m -(n - 1) / 2))];



					}
				}
				pixel_x[i * width + j] = (int) utility.convolutionFloat(
						makeMask(n, varianceA, varianceB), points);

			}

		}

		for (int i = 0; i < dim; i++) {

			pixels[i] = pixel_x[i];

		}
			imgOut.setPixels(pixels);
			threshold(imgOut);
			
			
	}
	
	
	public void threshold(PGM imgIn){
		int[] pix = imgIn.getPixels();
		for (int i = 0; i <pix.length; i++) {
			if (pix[i] > 0) {
				pix[i]=0;
			}else pix[i]=255;
		}
		
		imgIn.setPixels(pix);
	}
	
	
}
