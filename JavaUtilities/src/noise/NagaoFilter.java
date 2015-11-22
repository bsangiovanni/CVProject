package noise;

import java.util.ArrayList;
import java.util.Collections;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

public class NagaoFilter {
	
	//This class implements the Nagao Filter for de-noising

	private PgmUtilities utility = new PgmUtilities();

	ArrayList<Float> list = new ArrayList<Float>();
	ArrayList<int[][]> maskList = new ArrayList<int[][]>();
	
	
	private int width;
	private int height;
	private int dim;
	private int n=5;

	public void makeNagaoFilter(PGM imgIn, PGM imgOut) {

		this.width = imgIn.getWidth();
		this.height = imgIn.getHeight();
		this.dim = width * height;
		
		int[] pixels = imgIn.getPixels();
		double[] pixel_x = new double[dim];

		// First of all, initialize each sub-group
		int[][] points = new int[n][n];

		int[][] mask1 = { { 0, 0, 0, 0, 0 }, { 0, 1, 1, 1, 0 },
				{ 0, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0 }, { 0, 0, 0, 0, 0 } };
		maskList.add(mask1); // then, put them in an arraylist of masks
		int[][] mask2 = { { 0, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0 },
				{ 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
		maskList.add(mask2);
		int[][] mask3 = { { 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0 },
				{ 1, 1, 1, 0, 0 }, { 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
		maskList.add(mask3);
		int[][] mask4 = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 1 },
				{ 0, 0, 1, 1, 1 }, { 0, 0, 0, 1, 1 }, { 0, 0, 0, 0, 0 } };
		maskList.add(mask4);
		int[][] mask5 = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0 } };
		maskList.add(mask5);
		int[][] mask6 = { { 1, 1, 0, 0, 0 }, { 1, 1, 1, 0, 0 },
				{ 0, 1, 1, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
		maskList.add(mask6);
		int[][] mask7 = { { 0, 0, 0, 1, 1 }, { 0, 0, 1, 1, 1 },
				{ 0, 0, 1, 1, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
		maskList.add(mask7);
		int[][] mask8 = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 0, 1, 1, 0, 0 }, { 1, 1, 1, 0, 0 }, { 1, 1, 0, 0, 0 } };
		maskList.add(mask8);
		int[][] mask9 = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 1, 0 }, { 0, 0, 1, 1, 1 }, { 0, 0, 0, 1, 1 } };
		maskList.add(mask9);

		float[] mean = new float[9];

		for (int i = (n - 1) / 2; i < height - (n - 1) / 2; i++) {
			for (int j = (n - 1) / 2; j < width - (n - 1) / 2; j++) {
				for (int l = 0; l < n; l++) {
					for (int m = 0; m < n; m++) {
						points[l][m] = pixels[(i + (l - (n - 1) / 2)) * width
								+ (j + (m - (n - 1) / 2))];

					}
				}

				/*
				 * for each sub-group, calculate the mean value and put it in an
				 * array. The first value is 9 instead of 7, so it's separated
				 */

				mean[0] = (float) (utility.convolution(mask1, points))
						/ (float) 9;

				for (int j2 = 1; j2 < mean.length; j2++) {
					mean[j2] = (float) (utility.convolution(maskList.get(j2),
							points)) / (float) 7;

				}
				
				/*
				 * for each sub-group, calculate the variance value and put it in an arraylist.
				 * To find out the value, a comparison between mask and "1" it's made to find the 
				 * correspondent indices to assign to the points of the matrix 5x5. The first value it's different
				 * for the "9" instead of "7".
				 */

				float variance = 0;
				for (int k = 0; k < n; k++) {
					for (int k2 = 0; k2 < n; k2++) {
						if (maskList.get(0)[k][k2] == 1) {
							variance = variance
									+ ((points[k][k2] - mean[0]) * (points[k][k2] - mean[0]));
						}
					}
				}
				variance = variance / (float) 9;

				list.add(variance);

				for (int t = 1; t < maskList.size(); t++) {
					variance = 0;
					for (int k = 0; k < n; k++) {
						for (int k2 = 0; k2 < n; k2++) {
							if (maskList.get(t)[k][k2] == 1) {
								variance = variance
										+ ((points[k][k2] - mean[t]) * (points[k][k2] - mean[t]));
							}
						}
					}
					variance = variance / (float) 7;
					list.add(variance);

				}
				
				/*
				 * find out the mean value to assign to the central pixel in correspondence of the smallest variance of all sub-group
				 */

				pixel_x[i * width + j] = mean[list.indexOf(Collections
						.min(list))];
				list.clear(); //erase the list
			}

		}
		pixels= utility.normalizePhase(imgOut, pixel_x);

		imgOut.setPixels(pixels);
	}



}
