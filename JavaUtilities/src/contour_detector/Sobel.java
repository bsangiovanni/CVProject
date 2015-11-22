package contour_detector;

import java.util.Arrays;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

public class Sobel implements IFilter {
	
	//This class implements the Sobel operator for contours detection
	
	private PgmUtilities utility = new PgmUtilities();

	/* Here we have verical and horizontal arrays */
	public final static int[][] SOBEL_X = { { -1, 0, 1 }, { -2, 0, 2 },
			{ -1, 0, 1 } };

	public final static int[][] SOBEL_Y = { { -1, -2, -1 }, { 0, 0, 0 },
			{ 1, 2, 1 } };
	
	//dimensional utilities
	
	private int width;
	private int height;
	private int dim;
	
	float[] sobelPhase;


	public void applyFilter(PGM imgIn, PGM imgOutModule) {

		this.width = imgIn.getWidth();
		this.height = imgIn.getHeight();
		this.dim = width * height;
		
		
		int[] pixels = imgIn.getPixels();
	
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

		sobelModule(imgOutModule, pixel_x, pixel_y);
		
		this.sobelPhase = sobelPhase(imgOutModule, pixel_x, pixel_y);

	}
		
	
	public float[] sobelPhase(PGM img, int[] pixel_x, int[] pixel_y){
		
		float[] phase = new float[dim];
		float temp_p;
		
		for (int i = 0; i < phase.length; i++) {
			
			/*
			 * Remember that atan2 returns a value between -pi and pi -> useful
			 * for the normalization
			 */
			temp_p = (float) Math.atan2((float) pixel_y[i], (float) pixel_x[i]);		//Sobel phase	
			phase[i] = temp_p;
		}		
		return phase;		
	}
	

	public void sobelModule(PGM img, int[] pixel_x, int[] pixel_y) {
			
		int[] pixels = img.getPixels();
		int temp_m;
		
		for (int i = 0; i < dim; i++) {
			temp_m = (int) Math.sqrt((pixel_x[i] * pixel_x[i])		//Sobel Module
					+ (pixel_y[i] * pixel_y[i]));

			pixels[i] = temp_m;
	
		}
		img.setPixels(pixels);
		
	}
	
	//This method generate the phase of Sobel 
	
	public PGM normalizeSobelPhase(PGM imgOut) {
		int[] phaseIn = new int[imgOut.getHeight() * imgOut.getWidth()];
		float[] copy = Arrays.copyOf(sobelPhase, imgOut.getHeight()*imgOut.getWidth());
		Arrays.sort(copy);
		float min=copy[0];
		float max=copy[imgOut.getHeight()*imgOut.getWidth()-1];
		for (int i = 0; i < sobelPhase.length; i++) {
			sobelPhase[i] = (float) (sobelPhase[i] + Math.PI);
//			phaseIn[i] = (int) phase[i];

			if(sobelPhase[i]<min){
				sobelPhase[i]=0;
			}
			if(sobelPhase[i]>max){
				sobelPhase[i]=255;
			}
			if(sobelPhase[i]>=min && sobelPhase[i]<=max){
				sobelPhase[i]=255*(sobelPhase[i]-min)/(max-min);
				phaseIn[i]=(int)sobelPhase[i];
			}
			
		}
		imgOut.setPixels(phaseIn);
		return imgOut;

	}



}
