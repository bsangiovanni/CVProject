package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java_utilities.pgmutilities.PGM;
import java_utilities.pgmutilities.PgmUtilities;

import javax.swing.JButton;
import javax.swing.JLabel;
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
	
	private FileList list;
	private PgmUtilities pgmu = new PgmUtilities();
	private String filename;
	private PGM pgm;
	private PGM imgOut;
	


	public MainPanel(FileList list) {
		super();
		this.list=list;
		list.clearList();
		setLayout(new GridLayout(3, 1));
		contourPanel();
		noisePanel();
		noiseFilters();
		
		
	}
	
	public void contourPanel(){
		JPanel contourPanel = new JPanel();
		contourPanel.setLayout(new GridLayout(4,1));
		
		JLabel label = new JLabel(" --- Contour Detection ---");
		contourPanel.add(label);
		
		JButton sobelButton = new JButton("Apply Sobel");
		sobelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				readSetPGM();			
				Sobel sobel = new Sobel();
				sobel.applyFilter(pgm, imgOut);
				pgmu.normalizeModule(imgOut.getPixels(), imgOut);
				String[] nameext = filename.split("\\.");
				String name = nameext[0];
				pgmu.writePGM(imgOut, name.concat("_ModuloSobel.pgm"));				
				pgmu.normalizePhase(sobel.getSobelPhase(), imgOut);
				pgmu.writePGM(imgOut, name.concat("_FaseSobel.pgm"));				
				list.clearList();
			}
		});

		contourPanel.add(sobelButton);
		
		
		JButton gaussButton = new JButton("Apply DoG");
		gaussButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				readSetPGM();				
				Gaussian gauss = new Gaussian();			
				gauss.applyFilter(imgOut, imgOut);
				String[] nameext = filename.split("\\.");
				String name = nameext[0];
				pgmu.writePGM(imgOut, name.concat("_DoG.pgm"));
				list.clearList();
			}
		});

		contourPanel.add(gaussButton);

		JButton templateButton = new JButton("Apply 3/9");
		templateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				readSetPGM();

				TemplateMatching template = new TemplateMatching();

				template.applyFilter(pgm, imgOut);
				String[] nameext = filename.split("\\.");
				String name = nameext[0];
				pgmu.writePGM(imgOut, name.concat("_TemplateMatching.pgm"));
				list.clearList();
			}
		});

		contourPanel.add(templateButton);
		
		add(contourPanel);
	}
	
	public void noisePanel(){
		JPanel noisepanel = new JPanel();
		noisepanel.setLayout(new GridLayout(3,1));
		JLabel label = new JLabel(" --- Noise --- ");
		noisepanel.add(label);
		
		JButton uniformNoiseButton = new JButton("Apply Uniform Noise");
		uniformNoiseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				readSetPGM();				
				Noise noise = new Noise();
				noise.addUniformNoise(pgm, imgOut);
				String[] nameext = filename.split("\\.");
				String name = nameext[0];
				pgmu.writePGM(imgOut, name.concat("_UN.pgm"));
				list.clearList();


			}
		});

		noisepanel.add(uniformNoiseButton);
		
		JButton SPNoiseButton = new JButton("Apply Salt and Pepper Noise");
		SPNoiseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				readSetPGM();
				Noise noise = new Noise();
				noise.addSaltPepper(pgm, imgOut);
				String[] nameext = filename.split("\\.");
				String name = nameext[0];
				pgmu.writePGM(imgOut, name.concat("_SPN.pgm"));
				list.clearList();


			}
		});

		noisepanel.add(SPNoiseButton);
		
		add(noisepanel);
	}
	
	public void noiseFilters(){
		JPanel filterpanel = new JPanel();
		filterpanel.setLayout(new GridLayout(4,1));
		JLabel label = new JLabel(" --- Noise Filters --- ");
		filterpanel.add(label);
		
		JButton afButton = new JButton("Apply Average Filter");
		afButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				readSetPGM();
				AverageFilter aFilter= new AverageFilter();
				aFilter.makeAverageFilter(imgOut, imgOut);
				aFilter.makeAverageFilter(imgOut, imgOut);
				String[] nameext = filename.split("\\.");
				String name = nameext[0];
				pgmu.writePGM(imgOut, name.concat("AF.pgm"));
				list.clearList();


			}
		});

		filterpanel.add(afButton);
		
		JButton mfButton = new JButton("Apply Median Filter");
		mfButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				readSetPGM();
				MedianFilter mFilter= new MedianFilter();
				mFilter.makeMedianFilter(imgOut, imgOut);
				mFilter.makeMedianFilter(imgOut, imgOut);
				String[] nameext = filename.split("\\.");
				String name = nameext[0];
				pgmu.writePGM(imgOut, name.concat("MF.pgm"));
				list.clearList();


			}
		});

		filterpanel.add(mfButton);
		
		JButton nagaoButton = new JButton("Apply Nagao Filter");
		nagaoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				readSetPGM();
				NagaoFilter nFilter = new NagaoFilter();
				nFilter.makeNagaoFilter(imgOut, imgOut);
				String[] nameext = filename.split("\\.");
				String name = nameext[0];
				pgmu.writePGM(imgOut, name.concat("NF.pgm"));
				list.clearList();
			}
		});

		filterpanel.add(nagaoButton);
		
		add(filterpanel);
	}

	
	
	public void readSetPGM(){
		this.filename=list.getFileName();
		this.pgm = pgmu.readPGM(filename);
		this.imgOut = pgmu.copyPGM(pgm);	
	}


}
