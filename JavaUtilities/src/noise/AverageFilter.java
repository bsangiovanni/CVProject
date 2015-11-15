package noise;

import java.util.Arrays;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

public class AverageFilter {

	private PgmUtilities utility = new PgmUtilities();
	
	private int width;
	private int height;
	private int dim;
	private int n=3;
	

	public void makeAverageFilter(PGM imgIn, PGM imgOut) {
		
		this.width = imgIn.getWidth();
		this.height = imgIn.getHeight();
		this.dim = width * height;
		double[] pixel_x = new double[dim];
		
		int[][] points = new int[n][n];
		int[][] ones = new int[n][n];
		int[] pixels = imgIn.getPixels();
		
		// generates nxn matrix of 1s
		for (int i = 0; i < ones.length; i++) {
			for (int j = 0; j < ones.length; j++) {
				ones[i][j]=1;
			}
		}
		
		for (int i = (n - 1) / 2; i < height - (n - 1) / 2; i++) {
			for (int j = (n - 1) / 2; j < width - (n - 1) / 2; j++) {
				for (int l = 0; l < n; l++) {
					for (int m = 0; m < n; m++) {
						points[l][m] = pixels[(i + (l - (n - 1) / 2)) * width
								+ (j + (m - (n - 1) / 2))];

					}
				}
				int tmp = utility.convolution(ones, points);		//makes the sum of every nxn area around each pixel
				pixel_x[i * width + j] = (float)tmp/(n*n);			//Average value to apply at the central pixel

			}
		}

		pixels = phaseNormalizer(pixel_x);
		 imgOut.setPixels(pixels);
	}


	private int[] phaseNormalizer(double[] pixel_x) {
		int[] phaseIn = new int[dim];
		double[] copy = Arrays.copyOf(pixel_x,
				dim);
		Arrays.sort(copy);
		float min = (float) copy[0];
		float max = (float) copy[dim - 1];
		for (int i = 0; i < pixel_x.length; i++) {
			if (pixel_x[i] < min) {
				pixel_x[i] = 0;
			}
			if (pixel_x[i] > max) {
				pixel_x[i] = 255;
			}
			if (pixel_x[i] >= min && pixel_x[i] <= max) {
				pixel_x[i] = 255 * (pixel_x[i] - min) / (max - min);
				phaseIn[i] = (int) pixel_x[i];

			}
			
		}
		return phaseIn;
	}

}
