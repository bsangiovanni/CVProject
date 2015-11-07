package contour_detector;

import java.util.ArrayList;
import java.util.Collections;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

public class TemplateMatching {

	private PgmUtilities utility = new PgmUtilities();
	private ArrayList<Integer> convRes = new ArrayList<Integer>();
	private ArrayList<int[][]> multMask = new ArrayList<int[][]>();

	public void makeFilter(PGM imgIn, PGM imgOut) {
		// int[] vect = new int[9];
		// for (int i = 0; i < vect.length-2; i++) {
		// vect[i]=1;
		// vect[i+1]=1;
		// vect[i+2]=1;
		// for (int j = 0; j < onemask.length; j++) {
		// for (int k = 0; k < onemask.length; k++) {
		// onemask[j][k]=vect[k+j*3];
		// }
		// }
		// for (int j = 0; j < onemask.length; j++) {
		// System.out.println(onemask[j][0]+" " + onemask[j][1]+ " " +
		// onemask[j][2]);
		// }
		// multMask.add(onemask);
		// for (int j = 0; j < vect.length; j++) {
		// vect[j]=0;
		// }
		// }
		int[][] mask1 = { { 1, 1, 1 }, { 0, 0, 0 }, { 0, 0, 0 } };
		multMask.add(mask1);
		int[][] mask2 = { { 0, 1, 1 }, { 0, 0, 1 }, { 0, 0, 0 } };
		multMask.add(mask2);
		int[][] mask3 = { { 0, 0, 1 }, { 0, 0, 1 }, { 0, 0, 1 } };
		multMask.add(mask3);
		int[][] mask4 = { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 1, 1 } };
		multMask.add(mask4);
		int[][] mask5 = { { 0, 0, 0 }, { 0, 0, 0 }, { 1, 1, 1 } };
		multMask.add(mask5);
		int[][] mask6 = { { 0, 0, 0 }, { 1, 0, 0 }, { 1, 1, 0 } };
		multMask.add(mask6);
		int[][] mask7 = { { 1, 0, 0 }, { 1, 0, 0 }, { 1, 0, 0 } };
		multMask.add(mask7);
		int[][] mask8 = { { 1, 1, 0 }, { 1, 0, 0 }, { 0, 0, 0 } };
		multMask.add(mask8);

		int[][] points = new int[3][3];

		int width = imgIn.getWidth();
		int height = imgIn.getHeight();
		int[] pixels = imgIn.getPixels();

		int dim = width * height;
		int[] pixel_x = new int[dim];

		for (int i = (3 - 1) / 2; i < height - (3 - 1) / 2; i++) {
			for (int j = (3 - 1) / 2; j < width - (3 - 1) / 2; j++) {
				for (int l = 0; l < 3; l++) {
					for (int m = 0; m < 3; m++) {
						points[l][m] = pixels[(i + (l - (3 - 1) / 2)) * width
								+ (j + (m - (3 - 1) / 2))];

					}
				}
				convRes.add(utility.convolution(mask1, points));
				convRes.add(utility.convolution(mask2, points));
				convRes.add(utility.convolution(mask3, points));
				convRes.add(utility.convolution(mask4, points));
				convRes.add(utility.convolution(mask5, points));
				convRes.add(utility.convolution(mask6, points));
				convRes.add(utility.convolution(mask7, points));
				convRes.add(utility.convolution(mask8, points));
				pixel_x[i * width + j] = (int) (1.5*(Collections.max(convRes)/points[1][1]-0.3));
				convRes.clear();
			}

		}
		for (int i = 0; i < dim; i++) {

			pixels[i] = pixel_x[i];

		}

		imgOut.setPixels(pixels);

	}

}
