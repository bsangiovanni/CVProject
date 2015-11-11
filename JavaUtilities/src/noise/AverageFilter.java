package noise;

import java.util.Arrays;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

public class AverageFilter {

	private PgmUtilities utility = new PgmUtilities();
	

	public void makeAverageFilter(PGM imgIn, PGM imgOut, int n) {
		int[][] points = new int[n][n];
		int[][] ones = new int[n][n];
		
		for (int i = 0; i < ones.length; i++) {
			for (int j = 0; j < ones.length; j++) {
				ones[i][j]=1;
			}
		}

		int width = imgIn.getWidth();
		int height = imgIn.getHeight();
		int[] pixels = imgIn.getPixels();

		int dim = width * height;
		double[] pixel_x = new double[dim];

		for (int i = (n - 1) / 2; i < height - (n - 1) / 2; i++) {
			for (int j = (n - 1) / 2; j < width - (n - 1) / 2; j++) {
				for (int l = 0; l < n; l++) {
					for (int m = 0; m < n; m++) {
						points[l][m] = pixels[(i + (l - (n - 1) / 2)) * width
								+ (j + (m - (n - 1) / 2))];

					}
				}
				int tmp = utility.convolution(ones, points);
				pixel_x[i * width + j] = (float)tmp/9;

			}
		}
		int[] phaseIn = new int[imgOut.getHeight() * imgOut.getWidth()];
		double[] copy = Arrays.copyOf(pixel_x,
				imgOut.getHeight() * imgOut.getWidth());
		Arrays.sort(copy);
		float min = (float) copy[0];
		float max = (float) copy[imgOut.getHeight() * imgOut.getWidth() - 1];
		for (int i = 0; i < pixel_x.length; i++) {
			if (pixel_x[i] < min) {
				pixel_x[i] = 0;
			}
			if (pixel_x[i] > max) {
				pixel_x[i] = 255;
			}
			if (pixel_x[i] >= min && pixel_x[i] <= max) {
				pixel_x[i] = 255 * (pixel_x[i] - min) / (max - min);

			}
			phaseIn[i] = (int) pixel_x[i];
		}
		
		for (int i = 0; i < dim; i++) {

			pixels[i] = phaseIn[i];

		}
		
		 imgOut.setPixels(pixels);
	}

}
