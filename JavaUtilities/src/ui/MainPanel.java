package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import noise.AverageFilter;
import noise.MedianFilter;
import noise.NagaoFilter;
import noise.Noise;
import contour_detector.Gaussian;
import contour_detector.Sobel;
import contour_detector.TemplateMatching;

public class MainPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private PgmUtilities pgmu = new PgmUtilities();
	private String filename;


	public MainPanel(FileList list) {
		super();
		
		

		
		JButton sobelButton = new JButton("Apply Sobel");
		sobelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filename = list.getFileName();
				PGM pgm = pgmu.readPGM(filename);
				PGM imgOut = pgmu.copyPGM(pgm);				
				Sobel sobel = new Sobel();

				sobel.applyFilter(pgm, imgOut);
				pgmu.normalizeModule(imgOut.getPixels(), imgOut);
				
				pgmu.writePGM(imgOut, filename.concat("_ModuloSobel.pgm"));
				
				pgmu.normalizePhase(sobel.getSobelPhase(), imgOut);

				pgmu.writePGM(imgOut, filename.concat("_FaseSobel.pgm"));
				
				list.clearList();
			}
		});

		add(sobelButton);
		
		
		JButton gaussButton = new JButton("Apply DoG");
		gaussButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filename = list.getFileName();
				PGM pgm = pgmu.readPGM(filename);
				PGM imgOut = pgmu.copyPGM(pgm);
				
				Gaussian gauss = new Gaussian();
				

				gauss.applyFilter(imgOut, imgOut);

				pgmu.writePGM(imgOut, filename.concat("_DoG.pgm"));
				list.clearList();
			}
		});

		add(gaussButton);

		JButton templateButton = new JButton("Apply 3/9");
		templateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filename = list.getFileName();
				PGM pgm = pgmu.readPGM(filename);
				PGM imgOut = pgmu.copyPGM(pgm);

				TemplateMatching template = new TemplateMatching();

				template.applyFilter(pgm, imgOut);

				pgmu.writePGM(imgOut, filename.concat("_TemplateMatching.pgm"));
				list.clearList();
			}
		});

		add(templateButton);
		
		JButton uniformNoiseButton = new JButton("Apply Uniform Noise");
		uniformNoiseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filename = list.getFileName();
				PGM pgm = pgmu.readPGM(filename);
				PGM imgOut = pgmu.copyPGM(pgm);
				
				Noise noise = new Noise();
				noise.addUniformNoise(pgm, imgOut);
				pgmu.writePGM(imgOut, filename.concat("_UN.pgm"));
				list.clearList();


			}
		});

		add(uniformNoiseButton);
		
		JButton SPNoiseButton = new JButton("Apply Salt and Pepper Noise");
		SPNoiseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filename = list.getFileName();
				PGM pgm = pgmu.readPGM(filename);
				PGM imgOut = pgmu.copyPGM(pgm);
				
				Noise noise = new Noise();
				noise.addSaltPepper(pgm, imgOut);
				pgmu.writePGM(imgOut, filename.concat("_SPN.pgm"));
				list.clearList();


			}
		});

		add(SPNoiseButton);

		JButton afButton = new JButton("Apply Average Filter");
		afButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filename = list.getFileName();
				PGM pgm = pgmu.readPGM(filename);
				PGM imgOut = pgmu.copyPGM(pgm);
				AverageFilter aFilter= new AverageFilter();
				aFilter.makeAverageFilter(imgOut, imgOut);
				aFilter.makeAverageFilter(imgOut, imgOut);
				pgmu.writePGM(imgOut, filename.concat("AF"));
				list.clearList();


			}
		});

		add(afButton);
		
		JButton mfButton = new JButton("Apply Median Filter");
		mfButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filename = list.getFileName();
				PGM pgm = pgmu.readPGM(filename);
				PGM imgOut = pgmu.copyPGM(pgm);
				MedianFilter mFilter= new MedianFilter();
				mFilter.makeMedianFilter(imgOut, imgOut);
				mFilter.makeMedianFilter(imgOut, imgOut);
				pgmu.writePGM(imgOut, filename.concat("MF"));
				list.clearList();


			}
		});

		add(mfButton);
		
		JButton nagaoButton = new JButton("Apply Nagao Filter");
		nagaoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filename = list.getFileName();
				PGM pgm = pgmu.readPGM(filename);
				PGM imgOut = pgmu.copyPGM(pgm);
				NagaoFilter nFilter = new NagaoFilter();
				nFilter.makeNagaoFilter(imgOut, imgOut);
				pgmu.writePGM(imgOut, filename.concat("NF"));
				list.clearList();


			}
		});

		add(nagaoButton);
		
	}
	
	
	
	
	
	

}
