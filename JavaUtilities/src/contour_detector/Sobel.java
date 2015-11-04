package contour_detector;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

public class Sobel {
	
	
	private PgmUtilities utility = new PgmUtilities();

	/* Here we have verical and horizontal arrays */
	public final static int[][] SOBEL_X = { { -1, 0, 1 }, { -2, 0, 2 },
			{ -1, 0, 1 } };

	public final static int[][] SOBEL_Y = { { -1, -2, -1 }, { 0, 0, 0 },
			{ 1, 2, 1 } };


	public void makeSobel(PGM imgIn, PGM imgOutModule, float[] phase) {

		int width = imgIn.getWidth();
		int height = imgIn.getHeight();
		int[] pixels = imgIn.getPixels();
		int dim = width * height;

		int[] pixel_x = new int[dim];
		int[] pixel_y = new int[dim];

		int[][] points = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
		for (int i = 1; i < height - 1; i++) {
			for (int j = 1; j < width - 1; j++) {
				/* here we fill the points into the temporary matrix */
				/*
				 * stiamo riempiendo la matrice points[3][3], il riempimento si
				 * fa dall'elemento in alto a sinistra, rappresentato da (i-1) e
				 * ci si muove a destra/sinistra da j-1 fino a j+1
				 */

				points[0][0] = pixels[(i - 1) * width + (j - 1)];
				points[0][1] = pixels[(i - 1) * width + (j)];
				points[0][2] = pixels[(i - 1) * width + (j + 1)];
				points[1][0] = pixels[(i) * width + (j - 1)];
				points[1][1] = pixels[i * width + j]; /* central point */
				points[1][2] = pixels[i * width + (j + 1)];
				points[2][0] = pixels[(i + 1) * width + (j - 1)];
				points[2][1] = pixels[(i + 1) * width + (j)];
				points[2][2] = pixels[(i + 1) * width + (j + 1)];

				pixel_x[i * width + j] = utility.convolution(SOBEL_X, points);
				pixel_y[i * width + j] = utility.convolution(SOBEL_Y, points);
			}

		}
		
		
		

		int temp_m;
		float temp_p;

		for (int i = 0; i < dim; i++) {
			temp_m = (int) Math.sqrt((pixel_x[i] * pixel_x[i])
					+ (pixel_y[i] * pixel_y[i]));

			/*
			 * Remember that atan2 returns a value between -pi and pi -> useful
			 * for the normalization
			 */
			temp_p = (float) Math.atan2((float) pixel_y[i], (float) pixel_x[i]);
			
			pixels[i] = temp_m;
			imgOutModule.setPixels(pixels);
			phase[i] = temp_p;
			

		}

	}

	
	

}
