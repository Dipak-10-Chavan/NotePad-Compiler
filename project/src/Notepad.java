package Notepad;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notepad {
	//frame
	JFrame frame;
	//main text area
	JTextArea textArea;
	//scroll bars
	JScrollPane scroll;
	//menubars
	JMenuBar menuBar;
	//elements of menubar
	JMenu fileMenu, languageMenu, formatMenu, commandPromptMenu;
	//filemenu items
	JMenuItem itemNew, itemNewWindow, itemOpen, itemSaveAs, itemSave, itemExit;
	//format menu item
	JMenuItem itemWordWrap, itemFont, itemFontSize;
	//comandprompt menu item
	JMenuItem openCmd;
	//language item
	JMenuItem java,cpp,html;
	FileDialog fd;
	//global variables for storing file name and path
	String openFileName =null ;
	String openPath = null ; 
	
	boolean wrap = false;
//	font names
	Font arial,newRoman,consolas;
//	default font style
	String fontStyle="Arial";
	
	public Notepad() {
		createFrame();
		createTextArea();
		createScrollBar();
		createMenuBar();
		createFileMenuItems();
		createFormatMenuItems();
		createComandPrompt();
		createLanguageMenu();
		
	}
	
	public  void createFrame() {
		//create new frame
		frame = new JFrame("Notepad");
		
		//set size of the frame 
		frame.setSize(500,500);
//		closing of frame
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//frame will create at middle of the screen
		frame.setLocationRelativeTo(null);
		
		//frame.setLayout(null); visibility
		
		frame.setVisible(true);
		
		//create icon
		Image icon = Toolkit.getDefaultToolkit().getImage("D:\\image\\notepad-icon-23.png");
		
		//set icon to the frame
		frame.setIconImage(icon);
	}
	
	public void createTextArea()
	{
	     textArea = new JTextArea();
//		create textarea inside frame
		frame.add(textArea);
//		default fontstyle and fontsize
		textArea.setFont(new Font("Arial",Font.PLAIN,20));
	}
	
	public void createScrollBar()
	{
		//create scrollbar on the text area and set the scrollbar will display when needed in vertical and horizontal
		scroll = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(scroll);
	}
	
	public void createMenuBar()
	{
		//create menubar 
		menuBar =  new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		//create menu bar options
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
//		create language menu
		languageMenu = new JMenu("Language");
		menuBar.add(languageMenu);
//		create format menu
		formatMenu = new JMenu("Format");
		menuBar.add(formatMenu);
//		create command prompt menu
		commandPromptMenu = new JMenu("Command Prompt");
		menuBar.add(commandPromptMenu);
		
	}
	
	public void createFileMenuItems()
	{
		//create item menu
		itemNew = new JMenuItem("New");
		itemNewWindow = new JMenuItem("New Window");
		itemOpen = new JMenuItem("Open");
		itemSaveAs = new JMenuItem("Save as");
		itemSave = new JMenuItem("Save");
		itemExit = new JMenuItem("Exit");
		
		//add items to fileMenu
		fileMenu.add(itemNew);
		fileMenu.add(itemNewWindow);
		fileMenu.add(itemOpen);
		fileMenu.add(itemSaveAs);
		fileMenu.add(itemSave);
		fileMenu.add(itemExit);
		
		
		
		//add action to the items
//		for new menuitem
		itemNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				textArea.setText("");
				frame.setTitle("Untitled Notepad");
				openPath = null;
				openFileName = null;
				
			}
		});
//		for new window menu item
		itemNewWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Notepad n1 = new Notepad();
				n1.frame.setTitle("Untitled");
				
			}
		});
//		for exit menu item
		itemExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
			}
		});
//		for open menu item
		itemOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				fd = new FileDialog(frame, "Open", FileDialog.LOAD);
				fd.setVisible(true);
				 String fileName = fd.getFile();
				String  path = fd.getDirectory();
				if(fileName != null)
				{
					textArea.setText("");
					openFileName = fileName;
					openPath=path;
					frame.setTitle(fileName);
					try (BufferedReader br = new BufferedReader(new FileReader(path+fileName))){
						String sentence = br.readLine();
						while(sentence!=null)
						{
							textArea.append(sentence+"\n");
							sentence = br.readLine();
						}
					} catch (FileNotFoundException e1) {
						System.out.println("File not found");
					} catch (IOException e1) {
						
						System.out.println("file reading success");
					}catch (NullPointerException npe) {
						
						
					}
				}
				
				
			}
		});
//		for save As menu item
		itemSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				fd = new FileDialog(frame,"Save As",FileDialog.SAVE);
				fd.setVisible(true);
			    String fileName = fd.getFile();
				String path = fd.getDirectory();
				if(fileName !=null  && path != null)
				{
					writeDataToFile(fileName, path);
				}
				
				
			}
		});
//		for Save
		itemSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(openFileName!=null && openPath!=null)
				{
					writeDataToFile(openFileName, openPath);
				}
				else {
					fd = new FileDialog(frame,"Save As",FileDialog.SAVE);
					fd.setVisible(true);
				    String fileName = fd.getFile();
					String path = fd.getDirectory();
					if(fileName !=null  && path != null)
					{
						writeDataToFile(fileName, path);
					}
				}
				
			}
		});
		
		
	}
	
	public void createFormatMenuItems()
	{
		//create item menu
		itemWordWrap = new JMenuItem("Word Wrap off");
		itemFont = new JMenuItem("Font");
		
		itemFontSize = new JMenuItem("Font Size");
		
		//add items to fileMenu
		formatMenu.add(itemWordWrap);
		//formatMenu.add(itemFont);
		//formatMenu.add(itemFontSize);
		
		
		// add actions to items
		
		itemWordWrap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!wrap)
				{
					textArea.setLineWrap(true);
					textArea.setWrapStyleWord(true);
					itemWordWrap.setText("Word Wrap off");
					wrap= true;
				}
				else {
					textArea.setLineWrap(false);
					textArea.setWrapStyleWord(false);
					itemWordWrap.setText("Word Wrap on");
					wrap = false;
				}
				
			}
		});
		
		//make font as menu 
		itemFont = new JMenu("Font");
		formatMenu.add(itemFont);
		
		//create and add items to font menu
		JMenuItem itemArial = new JMenuItem("Arial");
		itemFont.add(itemArial);
		JMenuItem itemTimesNewRoman = new JMenuItem("Times New Roman");
		itemFont.add(itemTimesNewRoman);
		JMenuItem itemConsolas = new JMenuItem("Consolas");
		itemFont.add(itemConsolas);
		//System.out.println(textArea.getFont());
		
//		for Arial font
		itemArial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontStyle("Arial");
				
			}
		});
//		For Times New Roman Font
		itemTimesNewRoman.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontStyle("Times New Roman");
				
			}
		});
//		for Font Consolas
		itemConsolas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontStyle("Consolas");
			}
		});
		//make fontsize as menu
		itemFontSize = new JMenu("Font Size");
		formatMenu.add(itemFontSize);
		
		//create and add items to font menu
		JMenuItem size10 = new JMenuItem("10");
		itemFontSize.add(size10);
		size10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(10);
			}
		});
		
		JMenuItem size12 = new JMenuItem("12");
		itemFontSize.add(size12);
		size12.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(12);
			}
		});
		
		JMenuItem size14 = new JMenuItem("14");
		itemFontSize.add(size14);
		size14.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(14);
			}
		});
		
		JMenuItem size16 = new JMenuItem("16");
		itemFontSize.add(size16);
		size16.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(16);
			}
		});
		
		JMenuItem size18 = new JMenuItem("18");
		itemFontSize.add(size18);
		size18.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(18);
			}
		});
		
		JMenuItem size20 = new JMenuItem("20");
		itemFontSize.add(size20);
		size20.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(20);
			}
		});
		
		JMenuItem size24 = new JMenuItem("24");
		itemFontSize.add(size24);
		size24.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(24);
			}
		});
		JMenuItem size28 = new JMenuItem("28");
		itemFontSize.add(size28);
		size28.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(28);
			}
		});
		
		JMenuItem size32 = new JMenuItem("32");
		itemFontSize.add(size32);
		size32.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(32);
			}
		});
	
		JMenuItem size36 = new JMenuItem("36");
		itemFontSize.add(size36);
		size36.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(36);
			}
		});
		
		JMenuItem size40 = new JMenuItem("40");
		itemFontSize.add(size40);
		
		size40.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(40);
			}
		});
		
		
		
	}
//	for language MenuItems
	public void createLanguageMenu()
	{
		java = new JMenuItem("Java");
		cpp = new JMenuItem("CPP");
		html = new JMenuItem("HTML");
		
		languageMenu.add(java);
		languageMenu.add(cpp);
		languageMenu.add(html);
		
		
		java.addActionListener(new ActionListener() {
//			for java 
			@Override
			public void actionPerformed(ActionEvent e) {
				

				
					textArea.setText("");
					frame.setTitle(openFileName);
					try (BufferedReader br = new BufferedReader(new FileReader("D:\\FileHandle\\java.java"))){
						String sentence = br.readLine();
						while(sentence!=null)
						{
							textArea.append(sentence+"\n");
							sentence = br.readLine();
						}
					} catch (FileNotFoundException e1) {
						System.out.println("File not found");
					} catch (IOException e1) {
						
						System.out.println("file reading success");
					}catch (NullPointerException npe) {
						
						
					}
				}
		});
//		for C++
		cpp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				

				
					textArea.setText("");
					frame.setTitle(openFileName);
					try (BufferedReader br = new BufferedReader(new FileReader("D:\\FileHandle\\cppFormat.cpp"))){
						String sentence = br.readLine();
						while(sentence!=null)
						{
							textArea.append(sentence+"\n");
							sentence = br.readLine();
						}
					} catch (FileNotFoundException e1) {
						System.out.println("File not found");
					} catch (IOException e1) {
						
						System.out.println("file reading success");
					}catch (NullPointerException npe) {
						
						
					}
				}
		});
//		for html
		html.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				

				
					textArea.setText("");
					frame.setTitle(openFileName);
					try (BufferedReader br = new BufferedReader(new FileReader("D:\\FileHandle\\htmlFormat.html"))){
						String sentence = br.readLine();
						while(sentence!=null)
						{
							textArea.append(sentence+"\n");
							sentence = br.readLine();
						}
					} catch (FileNotFoundException e1) {
						System.out.println("File not found");
					} catch (IOException e1) {
						
						System.out.println("file reading success");
					}catch (NullPointerException npe) {
						
						
					}
				}
		});
		
	}
//	for creating command prompt menu item
	public void createComandPrompt()
	{
		openCmd = new JMenuItem("Open Command prompt");
		commandPromptMenu.add(openCmd);
		
		openCmd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(openPath != null)
					{
						Runtime.getRuntime().exec(new String[] {"cmd","/k","start"},null,new File(openPath));
					}
					else {
						Runtime.getRuntime().exec(new String[] {"cmd","/k","start"},null,null);
					}
				} catch (IOException e1) {
					System.out.println("Couldn't lunch cmd");
				}
				
			}
		});
	}
//	for writing data to file
	public void writeDataToFile(String fileName, String path)
	{
		openFileName = fileName;
		openPath = path;
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path+fileName))){
			String text = textArea.getText();
			bw.write(text);
			frame.setTitle(fileName);
		} catch (FileNotFoundException fnf) {
			System.out.println("File not found!");
		} catch (IOException e1) {
			System.out.println("file write success");
		}
	}
	
//	for setting fontSize
	public void setFontSize(int size)
	{
		
		 arial = new Font("Arial",Font.PLAIN,size);
		 newRoman = new Font("Times New Roman",Font.PLAIN,size);
		 consolas = new Font("Consolas",Font.PLAIN,size);
		 setFontStyle(fontStyle);
		 
	}
//	for Setting FontStyle
	public void setFontStyle(String font)
	{
		
		fontStyle=font;
		switch(font)
		{
			case "Arial": {
				textArea.setFont(arial);
				break;
			}
			case "Times New Roman": {
				textArea.setFont(newRoman);
				break;
			}
			case "Consolas": {
				textArea.setFont(consolas);
				break;
			}
		}
	}
	
}