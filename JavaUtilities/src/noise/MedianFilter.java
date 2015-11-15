package noise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java_utilities.pgmutilities.PGM;


public class MedianFilter {

	public void makeMedianFilter(PGM imgIn, PGM imgOut, int n) {

		int[][] ones = new int[n][n];

		for (int i = 0; i < ones.length; i++) {
			for (int j = 0; j < ones.length; j++) {
				ones[i][j] = 1;
			}
		}

		int width = imgIn.getWidth();
		int height = imgIn.getHeight();
		int[] pixels = imgIn.getPixels();

		int dim = width * height;
		double[] pixel_x = new double[dim];

		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = (n - 1) / 2; i < height - (n - 1) / 2; i++) {
			for (int j = (n - 1) / 2; j < width - (n - 1) / 2; j++) {
				for (int l = 0; l < n; l++) {
					for (int m = 0; m < n; m++) {
						list.add(pixels[(i + (l - (n - 1) / 2)) * width
								+ (j + (m - (n - 1) / 2))]);

					}
				}
				
				Collections.sort(list);
				int sum = 0;
				for (int k = 1; k < list.size() - 1; k++) {
					sum = sum + list.get(k);

				}
				pixel_x[i * width + j] = (float) sum / list.size();	//Find out the median value, excluding the extremes
				list.clear();
			}

		}
		
		//Stessa storia stesso posto stesso bar
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
				phaseIn[i] = (int) pixel_x[i];

			}

		}

		for (int i = 0; i < dim; i++) {

			pixels[i] = phaseIn[i];

		}

		imgOut.setPixels(pixels);
	}

}
