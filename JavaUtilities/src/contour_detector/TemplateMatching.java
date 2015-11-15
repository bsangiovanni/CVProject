package contour_detector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

public class TemplateMatching {

	private PgmUtilities utility = new PgmUtilities();
	private ArrayList<Integer> convRes = new ArrayList<Integer>();
	
	
	private int[][] mask1 = { { 1, 1, 1 }, { 0, 0, 0 }, { 0, 0, 0 } };
	private int[][] mask2 = { { 0, 1, 1 }, { 0, 0, 1 }, { 0, 0, 0 } };
	private int[][] mask3 = { { 0, 0, 1 }, { 0, 0, 1 }, { 0, 0, 1 } };
	private int[][] mask4 = { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 1, 1 } };
	private int[][] mask5 = { { 0, 0, 0 }, { 0, 0, 0 }, { 1, 1, 1 } };
	private int[][] mask6 = { { 0, 0, 0 }, { 1, 0, 0 }, { 1, 1, 0 } };
	private int[][] mask7 = { { 1, 0, 0 }, { 1, 0, 0 }, { 1, 0, 0 } };
	private int[][] mask8 = { { 1, 1, 0 }, { 1, 0, 0 }, { 0, 0, 0 } };
	
	private int[][] ones = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };	//usefull to calculate the sum of every 3x3 pixel
	

	/**
	 * Method to make a Template Filter, in particular "3 over 9" filter
	 * @param imgIn
	 * @param imgOut
	 */
	public void applyFilter(PGM imgIn, PGM imgOut) {
		
		//3over9 masks: note that the ones turn clockwise around the central point 

		int[][] points = new int[3][3];

		int width = imgIn.getWidth();
		int height = imgIn.getHeight();
		int[] pixels = imgIn.getPixels();

		int dim = width * height;
		double[] pixel_x = new double[dim];

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
				int tmp = utility.convolution(ones, points);
				int tmpMax = Collections.max(convRes); //Max value of all convolutions 
				double tau = 0.88; //arbitrary value
				double th= (1-tau)/(2*tau+1); //threshold
				pixel_x[i * width + j] = 1.5 * ((float)tmpMax / (float)tmp-(float)1/3); //Formula to normalize pixel and to exclude monochromatic area
				if(pixel_x[i * width + j]<th){
					pixel_x[i * width + j]=0;	
				}
				convRes.clear();
			}
		}
		
		//Tanto bel codice duplicato... Normalizza la fase usando i float...
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
