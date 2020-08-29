import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class WordDocument extends JScrollPane implements DocumentListener{
	private static final long serialVersionUID=1L;
	
	private String name;
	private String path;
	private JTextArea text;
	private boolean  newDocument;
	private boolean unsaved;
	
	public WordDocument() {
		this("untitled","",new JTextArea());
	}
	
	public WordDocument(boolean newDocument) {
		this("untitled","",new JTextArea());
		this.newDocument=newDocument;
	}
	
	public WordDocument(String name,String path,JTextArea text) {
		super(text);
		this.name=name;
		this.path=path;
		this.text=text;
		this.text.getDocument().addDocumentListener(this);
	}
	public void save() {
		saveAs(path);
	}
	
	public boolean saveAs(String path) {
		
		
		try {
			File file=new File(path);
			
			if(file.exists()) {
				if(newDocument || newDocument && !this.path.equals(file.getAbsolutePath())) {
				int value=JOptionPane.showConfirmDialog(null,"Are you sure you want to overwrite this file? ","Warning",JOptionPane.YES_NO_OPTION);
				
				if(value==JOptionPane.NO_OPTION) {
					return false;
				}
			}
				
			}
			
			
			this.name=file.getName();
			BufferedWriter br=new BufferedWriter(new FileWriter(file));
			br.write(text.getText());
			
			
			br.close();
			// set our  path to this new file 
			this.path=file.getAbsolutePath();
			this.newDocument=false;
			this.unsaved=false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		update();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		update();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		update();
		
	}
	private void update() {
		unsaved=true;
	}
	
	public JTextArea getText() {
		return text;
	}
	
	
	@Override
	public String getName() {
		return name;
	}
	
	public boolean isNewDocument() {
		return newDocument;
	}
	public boolean isUnsaved() {
		return unsaved;
	}


}
