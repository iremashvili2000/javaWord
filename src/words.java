import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;


public class words extends JFrame implements ActionListener {
	private static final long serialVersionUID=1L;
	private JTabbedPane tabPane;
	
	
	public static void main(String[] args) {
		new words().setVisible(true);
	}
	
	
	private words() {
		super("Words");
		setSize(1024,768);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitSafely();
			}
			
		});
		
		initialize();
	}
	
	private void initialize() {
		tabPane=new JTabbedPane();
		// default file when opened 
		WordDocument doc=new WordDocument(true);
		tabPane.addTab(doc.getName(), doc);
	
		
		JMenuBar bar=new JMenuBar();
		JMenu file=new JMenu("File");
		JMenuItem newDoc=new JMenuItem("New");
		JMenuItem open=new JMenuItem("Open");
		JMenuItem save=new JMenuItem("Save");
		JMenuItem saveas=new JMenuItem("Save as");
		JMenuItem exit=new JMenuItem("Exit");
		
		newDoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()| Event.SHIFT_MASK));
		
		
		
		JMenuItem[] items= {newDoc,open,save,saveas,exit};
		for(JMenuItem item : items) {
			item.addActionListener(this);
			
		}
		
		file.add(newDoc);
		file.add(open);
		file.add(save);
		file.add(saveas);
		file.addSeparator();
		file.add(exit);
		
		bar.add(file);
		
		add(tabPane);
		setJMenuBar(bar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().contentEquals("New")) {
			WordDocument doc=new WordDocument(true);
			tabPane.addTab(doc.getName(),doc);
			tabPane.setSelectedComponent(doc);
		}else if(e.getActionCommand().contentEquals("Open")) {
			open();
		}else if(e.getActionCommand().contentEquals("Save")) {
			save();
		}else if(e.getActionCommand().contentEquals("Save as")) {
			saveAs();
		}else if(e.getActionCommand().contentEquals("Exit")) {
			System.exit(0);
			
		}
		
	}
	private void  open() {
		JFileChooser chooser=new JFileChooser("./");
		
		int returned=chooser.showOpenDialog(this);
		
		if(returned==JFileChooser.APPROVE_OPTION) {
			File file=chooser.getSelectedFile();
			WordDocument doc=new WordDocument(file.getName(),file.getAbsolutePath(),new JTextArea());
			tabPane.addTab(file.getName(),doc);
			tabPane.setSelectedComponent(doc);
			
			
			try {
				BufferedReader br=new BufferedReader(new FileReader(file));
				String line;
				while((line=br.readLine())!=null) {
					doc.getText().append(line+ "\n");
				}
				
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		/*
	
		*/
	}
	
	private void save() {
	WordDocument doc =(WordDocument) tabPane.getSelectedComponent();
	
	if(doc.isNewDocument()){
		saveAs();
	}else {
	doc.save();
		}
	}
	
	private void  saveAs() {
	JFileChooser chooser=new JFileChooser("./");
	int returned =chooser.showSaveDialog(this);
	
	if(returned==JFileChooser.APPROVE_OPTION){
		File file=chooser.getSelectedFile();
		
		WordDocument doc=(WordDocument) tabPane.getSelectedComponent();
		if(doc.saveAs(file.getAbsolutePath())) {
		
		tabPane.setTitleAt(tabPane.getSelectedIndex(),file.getName());
		}
		
	}
		
	}
	
	private void exitSafely() {
		
		for(int i=0;i<tabPane.getTabCount();i++) {
			WordDocument doc=(WordDocument)tabPane.getComponentAt(i);
			
			if(doc.isUnsaved()) {
				int value=JOptionPane.showConfirmDialog(null,"Are you sure you wish to exit?\nYou lose all your unsaved changes! ","Warning",JOptionPane.YES_NO_OPTION);
				if(value==JOptionPane.NO_OPTION) {
					return;
				}else {
					dispose();
				}
			
			}
		}
		
		
		
		
	}
	
	
	
}
