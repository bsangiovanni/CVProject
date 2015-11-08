/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java_utilities.pgmutilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *
 * @author user
 */
public class PgmUtilities {
	static int numLines = 0;

	// ---------------------------------------------------------//
	// ------------- Create a new empty pgm image --------------//
	// ---------------------------------------------------------//
	public PGM newPGM(int width, int height, int max_val) {
		return new PGM(width, height, max_val);
	}

	// ----------------------------------------------------- //
	// Reads information from header file //
	// Allows for reading and writing in PGM P2 - P5 format //
	// Version 1.1 Piercarlo Dondi & Alessandro Gaggia //
	// ----------------------------------------------------- //

	// ******************* I/O FUNCTIONS *********************//

	// -------------------------------------------------------//
	// --------------- Skip Commented Lines ------------------//
	// -------------------------------------------------------//
	public String skipComments(BufferedReader br) throws IOException {
		boolean loop = true;
		String buffer = br.readLine();

		while (loop) {
			if (buffer.charAt(0) != '#')
				loop = false;
			else {
				buffer = br.readLine();
				numLines++;
			}
		}

		return buffer;
	}

	// ---------------------------------------------------------//
	// ------- Set to zero all the pixels of a pgm image -------//
	// ---------------------------------------------------------//
	public void resetPGM(PGM pgm) {
		int width = pgm.getWidth();
		int height = pgm.getHeight();
		int i;

		// set to zero all the pixels
		for (i = 0; i < width * height; i++) {
			pgm.getPixels()[i] = 0;
		}
	}

	// ---------------------------------------------------------//
	// --------- Read Pixels From Different FileType -----------//
	// ---------------------------------------------------------//
	public PGM readPGM(String filename) {
		int width, height, max_val;
		boolean binary;

		PGM pgm;
		numLines = 3; // default number of lines of header

		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String buffer;
			// Read a line till \n or 64 char
			buffer = br.readLine();

			if ("P2".equals(buffer)) {
				binary = false;
				System.out.println("\nFORMAT: P2");
			} else if ("P5".equals(buffer)) {
				binary = true;
				System.out.println("\nFORMAT: P5");
			} else {
				System.err.println("ERROR: incorrect file format\n");
				in.close();
				return null;
			}

			// Jump commented lines
			buffer = skipComments(br);

			// Read width, height and max grayscale value
			StringTokenizer st = new StringTokenizer(buffer);
			width = Integer.parseInt(st.nextToken());
			height = Integer.parseInt(st.nextToken());

			buffer = br.readLine();
			max_val = Integer.parseInt(buffer);

			// Printing information on screen
			System.out.println("\nPGM Filename: " + filename
					+ "\nPGM Width & Height: " + width + "," + height
					+ "\nPGM Max Val & Type: " + max_val + ","
					+ (binary ? "P5" : "P2") + "\n");

			// Initialize PGM
			pgm = newPGM(width, height, max_val);

			// Reading Pixels
			if (binary) // P5 case
			{

				br.close();
				fstream = new FileInputStream(filename);
				in = new DataInputStream(fstream);

				int numLinesToSkip = numLines;
				System.out.println(numLinesToSkip);
				while (numLinesToSkip > 0) {
					char c;
					do {
						c = (char) (in.readUnsignedByte());
					} while (c != '\n');
					numLinesToSkip--;
				}

				int num;
				int x = 0;

				while ((num = in.read()) != -1) {
					pgm.getPixels()[x] = num;
					x++;
				}
			} else // P2 case
			{
				int i = 0;
				while ((buffer = br.readLine()) != null) {
					st = new StringTokenizer(buffer);
					while (st.hasMoreTokens()) {
						pgm.getPixels()[i] = Integer.parseInt(st.nextToken());
						i++;
					}
				}
			}

			// Ok close the file
			in.close();

			System.out.println("\nImage correctly loaded");

			return pgm;
		} catch (FileNotFoundException ex) {
			System.out.println("File not found.");
			return null;
		} catch (IOException ex) {
			System.out.println("IOException. Please check file.");
			return null;
		}
	}

	// ---------------------------------------------------------//
	// --- Write Pixels inside images for Different FileType ---//
	// ---------------------------------------------------------//
	public void writePGM(PGM pgm, String filename) {
		if (pgm == null) {
			System.err.println("Error! No data to write. Please Check.");
			return;
		}

		FileWriter fstream;
		try {
			fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);

			out.write("P2\n" + pgm.getWidth() + " " + pgm.getHeight() + "\n"
					+ pgm.getMax_val() + "\n");

			int i;
			int width = pgm.getWidth();
			int height = pgm.getHeight();

			// Write image
			for (i = 0; i < width * height; i++) {
				out.write(pgm.getPixels()[i] + "\n");
			}

			System.out.println("\nImage correctly written");

			// Ok close the file
			out.close();

		} catch (IOException ex) {
			System.err.println("\nIOException. Check input Data.");
		}
	}

	// *************** SOME BASIC OPERATIONS *****************//

	// -------------------------------------------------------//
	// ----- Invert Pixels GrayScale value inside images -----//
	// --------------- for Different FileType ----------------//
	// -------------------------------------------------------//
	public PGM invertPGM(PGM pgmIn) {
		if (pgmIn == null) {
			System.err.println("Error! No input data. Please Check.");
			return null;
		}

		PGM pgmOut = new PGM(pgmIn.getWidth(), pgmIn.getHeight(),
				pgmIn.getMax_val());
		int i, inv;
		int max = pgmIn.getMax_val();
		int width = pgmIn.getWidth();
		int height = pgmIn.getHeight();

		// Writing Pixels
		for (i = 0; i < width * height; i++) {
			// Invert GrayScale Value
			inv = max - pgmIn.getPixels()[i];
			pgmOut.getPixels()[i] = inv;
		}

		return pgmOut;
	}

	// -------------------------------------------------------//
	// ---------------- Flip Image Horizontally --------------//
	// -------------------------------------------------------//
	public PGM hflipPGM(PGM pgmIn) {
		if (pgmIn == null) {
			System.err.println("Error! No input data. Please Check.");
			return null;
		}

		PGM pgmOut = new PGM(pgmIn.getWidth(), pgmIn.getHeight(),
				pgmIn.getMax_val());

		int i, j;
		int hfp;

		int width = pgmIn.getWidth();
		int height = pgmIn.getHeight();

		int[] inputPixels = pgmIn.getPixels();
		int[] flipPixels = new int[width * height];

		// Modify Pixels
		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				// Flip GrayScale Value on width
				hfp = inputPixels[i * width + j];
				flipPixels[i * width + (width - j - 1)] = hfp;
			}
		}

		pgmOut.setPixels(flipPixels);

		return pgmOut;
	}

	// -------------------------------------------------------//
	// ------------------ Copy a PGM Image -------------------//
	// -------------------------------------------------------//
	public PGM copyPGM(PGM pgmIn) {
		if (pgmIn == null) {
			System.err.println("Error! No input data. Please Check.");
			return null;
		}
		PGM pgmOut = new PGM(pgmIn.getWidth(), pgmIn.getHeight(),
				pgmIn.getMax_val());

		int i;

		int width = pgmIn.getWidth();
		int height = pgmIn.getHeight();

		int[] inPixels = pgmIn.getPixels();
		int[] outPixels = new int[width * height];

		// Copy image
		for (i = 0; i < width * height; i++) {
			// Copy image
			outPixels[i] = inPixels[i];
		}

		pgmOut.setPixels(outPixels);

		return pgmOut;
	}

	// --------------------------------------------------------//
	// ------------------ Normalize Module---------------------//
	// --------------------------------------------------------//
	
	public PGM normalizeModule (int[] pixels, PGM imgOut){
		int[] copy = Arrays.copyOf(pixels, imgOut.getHeight()*imgOut.getWidth());
		Arrays.sort(copy);
		int min=copy[0];
		int max=copy[imgOut.getHeight()*imgOut.getWidth()-1];
		for (int i = 0; i < pixels.length; i++) {
			if(pixels[i]<min){
				pixels[i]=0;
			}
			if(pixels[i]>max){
				pixels[i]=255;
			}
			if(pixels[i]>=min && pixels[i]<=max){
				pixels[i]=255*(pixels[i]-min)/(max-min);
			}
		}
		imgOut.setPixels(pixels);
		return imgOut;
	}

	// --------------------------------------------------------//
	// ------------------ Equalizer Module---------------------//
	// --------------------------------------------------------//

	public PGM EqualizerModule(int[] histogram, PGM imgOut, int[] pixels) {
		// int[] pixels = new int[imgOut.getHeight() * imgOut.getWidth()];
		float sum = 0;

		int tmp;

		for (int i = 0; i < histogram.length; i++) {
			sum = histogram[i] + sum;
		}
		for (int i = 0; i < histogram.length; i++) {
			float a = 0;
			if (histogram[i] != 0) {
				tmp = i;
				for (int j = 0; j < i; j++) {
					a = histogram[j] + a;
				}
				for (int j = 0; j < pixels.length; j++) {
					if (pixels[j] == tmp) {
						pixels[j] = (int) (255 * (a / sum));

					}
				}
			}

		}
		imgOut.setPixels(pixels);
		return imgOut;
	}

	// --------------------------------------------------------//
	// ------------------ Normalize Phase----------------------//
	// --------------------------------------------------------//

	public PGM normalizePhase(float[] phase, PGM imgOut) {
		int[] phaseIn = new int[imgOut.getHeight() * imgOut.getWidth()];
		float[] copy = Arrays.copyOf(phase, imgOut.getHeight()*imgOut.getWidth());
		Arrays.sort(copy);
		float min=copy[0];
		float max=copy[imgOut.getHeight()*imgOut.getWidth()-1];
		for (int i = 0; i < phase.length; i++) {
			phase[i] = (float) (phase[i] + Math.PI);
//			phaseIn[i] = (int) phase[i];

			if(phase[i]<min){
				phase[i]=0;
			}
			if(phase[i]>max){
				phase[i]=255;
			}
			if(phase[i]>=min && phase[i]<=max){
				phase[i]=255*(phase[i]-min)/(max-min);
			}
			phaseIn[i]=(int)phase[i];
		}
		imgOut.setPixels(phaseIn);
		return imgOut;

	}

	// --------------------------------------------------------//
	// ------------------ Calculate Histogram -----------------//
	// --------------------------------------------------------//
	public int[] histogramPGM(PGM pgm) {
		if (pgm == null) {
			System.err.println("Error! No input data. Please Check.");
			return null;
		}

		int i, index;

		int[] inPixels = pgm.getPixels();
		int width = pgm.getWidth();
		int height = pgm.getHeight();
		int max_val = pgm.getMax_val();

		// if max_val is 255 each pixel of the image can have a value between
		// [0;255]
		// so histogram have a dimension of 256
		int[] histogram = new int[max_val + 1];

		for (i = 0; i < width * height; i++) {
			index = inPixels[i];
			histogram[index]++;
		}

		return histogram;
	}
	
	
	// --------------------------------------------------------//
	// ---------------------Convolution------------------------//
	// --------------------------------------------------------//
	
	public int convolution(int[][] filter_mask, int[][] points) {

		int result = filter_mask[0][0] * points[0][0] + filter_mask[0][1]
				* points[0][1] + filter_mask[0][2] * points[0][2]
				+ filter_mask[1][0] * points[1][0] + filter_mask[1][1]
				* points[1][1] + filter_mask[1][2] * points[1][2]
				+ filter_mask[2][0] * points[2][0] + filter_mask[2][1]
				* points[2][1] + filter_mask[2][2] * points[2][2];

		return result;
	}
	
	// --------------------------------------------------------//
	// ---------------------ConvolutionFloat-------------------//
	// --------------------------------------------------------//
	
	public float convolutionFloat(float[][] filter_mask, int[][] points) {
		
		float result=0;

			for (int i = 0; i < points.length; i++) {
				for (int j = 0; j < points.length; j++) {
					result=result + (filter_mask[i][j]*points[i][j]);
				}
				
			}
			

			return result;
		

			
		}

}
