package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FileMenu extends JMenu implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileList list;
	
	
	public FileMenu(FileList list) {
		super("File");
		list.addObserver(this);
		this.list=list;
		createMenu();
	}
	
	
	public void createMenu(){

		    for (String name : list.getNames()) {
				JMenuItem item = new JMenuItem(name);
				item.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String nome = "images/".concat(name);
						list.setFileName(nome);
						
					}
				});
				add(item);
			}
		    
	}
	
	public void cleanMenu(){
		removeAll();
	}
	


	@Override
	public void update(Observable arg0, Object arg1) {
		cleanMenu();
		createMenu();
		
	}



	
	

}
