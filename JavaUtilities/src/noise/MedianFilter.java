package noise;

import java.util.ArrayList;
import java.util.Collections;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;


public class MedianFilter {
	
	//This class implements the Median Filter for de-noising
	
	private PgmUtilities utility = new PgmUtilities();
	private int width;
	private int height;
	private int dim;
	private int n=3;

	public void makeMedianFilter(PGM imgIn, PGM imgOut) {

		int[][] ones = new int[n][n];

		for (int i = 0; i < ones.length; i++) {
			for (int j = 0; j < ones.length; j++) {
				ones[i][j] = 1;
			}
		}

		this.width = imgIn.getWidth();
		this.height = imgIn.getHeight();
		this.dim = width * height;

		int[] pixels = imgIn.getPixels();

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
		

		pixels= utility.normalizePhase(imgOut, pixel_x);

		imgOut.setPixels(pixels);
	}

}
