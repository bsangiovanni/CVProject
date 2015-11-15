package contour_detector;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

public class Gaussian implements IFilter{

	private PgmUtilities utility = new PgmUtilities();

	private int n = 11; //mask size
	private float varianceA = 1.25f; //must be 1.6 less than 2^ variance
	private float varianceB = 2f;

	
	public void setN(int n) {
		this.n = n;
	}

	public void setVarianceA(float varianceA) {
		this.varianceA = varianceA;
	}

	public void setVarianceB(float varianceB) {
		this.varianceB = varianceB;
	}
	
	
	/**
	 * Make the Gaussian Mask using the n value and variances and create the DoG mask with them 
	 * @param n
	 * @param varianceA
	 * @param varianceB
	 * @return
	 */
	public float[][] makeMask(int n, float varianceA, float varianceB) {
		float[][] mask1 = createGaussianMatrix(varianceA, n);
		float[][] mask2 = createGaussianMatrix(varianceB, n);
		
		//DoG mask
		float[][] maskTot = new float[n][n];

		for (int i = 0; i < maskTot.length; i++) {
			for (int j = 0; j < maskTot.length; j++) {
				maskTot[i][j] = (mask1[i][j] - mask2[i][j]);
			}
		}

		return maskTot;
	}

	
	
	/**
	 * Make the DoG Filter
	 * @param imgIn
	 * @param imgOut
	 */

	public void applyFilter(PGM imgIn, PGM imgOut) {
		int width = imgIn.getWidth();
		int height = imgIn.getHeight();
		int[] pixels = imgIn.getPixels();

		int dim = width * height;
		int[] pixel_x = new int[dim];

		int[][] points = new int[n][n];
		
		//Pixels to point assignment

		for (int i = (n - 1) / 2; i < height - (n - 1) / 2; i++) {
			for (int j = (n - 1) / 2; j < width - (n - 1) / 2; j++) {
				for (int l = 0; l < n; l++) {
					for (int m = 0; m < n; m++) {
						points[l][m] = pixels[(i + (l - (n - 1) / 2)) * width
								+ (j + (m - (n - 1) / 2))];

					}
				}
				//Give to the central point the exact result of Convolution Method
				float[][] gaussMask = makeMask(n, varianceA, varianceB);
				pixel_x[i * width + j] = (int) utility.convolutionFloat(
						gaussMask , points);

			}

		}
		//Final Pixel assignment
		for (int i = 0; i < dim; i++) {

			pixels[i] = pixel_x[i];

		}
		
		imgOut.setPixels(pixels);
		threshold(imgOut);

		//Contour Detector
		pixels = contourDetection(imgOut, 3);
		imgOut.setPixels(pixels);

	}
	
	/**
	 * Algorithm for contour detection
	 * @param img
	 * @param n
	 * @return
	 */

	private int[] contourDetection(PGM img, int n) {
		int width = img.getWidth();
		int height = img.getHeight();
		int[] pixels = img.getPixels();
		int[][] points = new int[n][n];
		
		int tmp = 0;
		for (int i = (n - 1) / 2; i < height - (n - 1) / 2; i++) {
			for (int j = (n - 1) / 2; j < width - (n - 1) / 2; j++) {
				tmp = 0;
				for (int l = 0; l < n; l++) {
					for (int m = 0; m < n; m++) {
						points[l][m] = pixels[(i + (l - (n - 1) / 2)) * width	//Want again the point in a smaller matrix
								+ (j + (m - (n - 1) / 2))];
						tmp = points[l][m] + tmp; 								//Calculates the sum of each 3x3 matrix
					}
				}
				if (tmp == 0 || tmp == (255 * n * n)) {									//Find the monochromatic area and "discard" it
					for (int l = 0; l < n; l=l+n) {
						for (int m = 0; m < n; m=m+n) {							//m=m+n to scanning another area, not the same pixels
							pixels[(i + (l - (n - 1) / 2)) * width
									+ (j + (m - (n - 1) / 2))] = 255;
						}
					}
				} 
				else {
					for (int l = 0; l < n; l=l+n) {								//Else, mark it if pixels are border
						for (int m = 0; m < n; m=m+n) {
							pixels[(i + (l - (n - 1) / 2)) * width
									+ (j + (m - (n - 1) / 2))] = 0;
						}
					}
				}

			}

		}
		
		return pixels;
	}
	

	/**
	 * Threshold to be used to separate pixel (black or white) 
	 * @param imgIn
	 */
	public void threshold(PGM imgIn) {
		int[] pix = imgIn.getPixels();
		for (int i = 0; i < pix.length; i++) {
			if (pix[i] > 0) {
				pix[i] = 0;
			} else
				pix[i] = 255;
		}

		imgIn.setPixels(pix);
	}
	
	
	/**
	 * Creates a gaussian matrix of dimension nxn
	 * @param variance
	 * @param n
	 * @return
	 */
	
	public float[][] createGaussianMatrix(float variance, int n) {
		int x;
		int y;
		float[][] mask = new float[n][n];
		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask.length; j++) {
				x = Math.abs((mask.length / 2) - i);
				y = Math.abs((mask.length / 2) - j);
				//Gaussian Formula
				float espo = -(((x * x) + (y * y)) / (variance * variance * 2.f));
				mask[i][j] = (float) ((1 / (2 * Math.PI * variance * variance)) * (Math 
						.exp(espo)));

			}
		}
		return mask;
	}

}
