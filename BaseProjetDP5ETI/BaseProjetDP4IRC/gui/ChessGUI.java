package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import controler.ChessGameControler;
import nutsAndBolts.ChessGUIConfig;
import nutsAndBolts.GUICoord;
import nutsAndBolts.TraceMessage;


public class ChessGUI extends JFrame implements ChessGameGUI,  PropertyChangeListener{

	private static final long serialVersionUID = 1L;

	// le damier est une extension de JLayerdPane. Ses écouteurs invoqueront le controler
	private ChessGridGUI chessGridGUI ; 	
	// l'afficheur du suivi des coups est un JPanel
	private ChessTraceGUI chessTraceGUI;	
	// nb lignes et colonnes du damier
	private int length;		
	
	private int commandHistIndex =-1;
	private List<Command> commandHist = new ArrayList<>();

	JMenuItem itemUndo;
	JMenuItem itemRedo;
	ButtonGroup bg;		

	public ChessGUI(ChessGameControler chessGameControler) {
		super(ChessGUIConfig.getTitle());
		ChessGUIConfig.addPropertyChangeListener(this);
		this.setLocation(ChessGUIConfig.getLocation());
		this.setPreferredSize(ChessGUIConfig.getDimension());
		this.pack();		
		this.setResizable(true);

		JComponent contentPane;			// panel principal 
		JComponent boardGameGUI;		// panel du damier + indices lignes et colonnes
		JComponent top, bottom ;		// panel avec indices colonnes
		JComponent west, east ;			// panel avec indices des lignes
		JMenuBar menuBar;    			// barre des menus
		JMenu menuUndoRedo;				//Menu pour revenir en arrière/ en avant
		JMenu menuLightColor;			//Menu pour changer la couleur des cases de destinations possibles
						//Pour les boutons des couleurs

		

		//TODO declarer ts les items des menus ? 

		this.length = ChessGUIConfig.getNbLigne();
		this.chessGridGUI = new ChessGridGUI(chessGameControler );
		this.chessTraceGUI = new ChessTraceGUI();

		contentPane = new JPanel(new BorderLayout());	
		boardGameGUI = new JPanel(new BorderLayout());
		top = new JPanel();
		bottom = new JPanel();
		west = new JPanel();
		east = new JPanel();
		

		//Ajout des menus
		menuBar = new JMenuBar();
		menuUndoRedo= new JMenu("Undo/Redo");
		menuLightColor = new JMenu("color dest");
		bg = new ButtonGroup();

		//la liste des couleurs que peuvent prendre les cases de destination : 
		HashMap<String, Color> lightColors = new HashMap<String, Color>();
		lightColors.put("Blue", Color.blue);
		lightColors.put("Green", Color.green);
		lightColors.put("Orange", Color.orange);
		lightColors.put("Pink", Color.pink);



		for(String color : lightColors.keySet()){
			JRadioButtonMenuItem item = new JRadioButtonMenuItem(color);
			item.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					executeCommand(new CommandLightColor(lightColors.get(color)));
				}
			});
			if(lightColors.get(color)==ChessGUIConfig.getLightColor()){
				item.setSelected(true);
			}
			bg.add(item);
			menuLightColor.add(item);
			

		}
		

		itemUndo = new JMenuItem("Undo");
		itemUndo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				undo();
			}
		});
		itemUndo.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		itemUndo.setEnabled(false);

		itemRedo = new JMenuItem("Redo");
		itemRedo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				redo();
			}
		});
		itemRedo.setAccelerator(KeyStroke.getKeyStroke("ctrl Y"));
		itemRedo.setEnabled(false);
		
		menuUndoRedo.add(itemUndo);
		menuUndoRedo.add(itemRedo);

		menuBar.add(menuUndoRedo);
		menuBar.add(menuLightColor);

		

		///////////////////////////////////////////////////////////
		// Mise en forme de la fenêtre
		///////////////////////////////////////////////////////////


		///////////////////////////////////////////////////////////
		// Affichage valeurs des colonnes A -> H en haut et en bas
		///////////////////////////////////////////////////////////
		

		top.setBackground(Color.WHITE);
		top.setLayout(new GridLayout(1,8));
		for (char c = 'a'; c<='h'; c++){
			JLabel label = new JLabel(String.valueOf(c));
			label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			top.add(label);
		}


		bottom.setBackground(Color.WHITE);
		bottom.setLayout(new GridLayout(1,8));
		for (char c = 'a'; c<='h'; c++){
			JLabel label = new JLabel(String.valueOf(c));
			label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			bottom.add(label);
		}


		/////////////////////////////////////////////////////////////
		// Affichage valeurs des lignes 8 -> 1 à droite et à gauche
		/////////////////////////////////////////////////////////////

		west.setBackground(Color.WHITE);
		west.setLayout(new GridLayout(8,1));
		for (char c = '8'; c>='1'; c--){
			JLabel label = new JLabel(" "+String.valueOf(c)+" ");
			label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			west.add(label);
		}


		east.setBackground(Color.WHITE);
		east.setLayout(new GridLayout(8,1));
		for (char c = '8'; c>='1'; c--){
			JLabel label = new JLabel(" "+String.valueOf(c)+" ");
			label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			east.add(label);
		}


		////////////////////////////////////////////////////////
		// Affichage damier au centre
		////////////////////////////////////////////////////////


		boardGameGUI.add(west, BorderLayout.WEST);
		boardGameGUI.add(east, BorderLayout.EAST);
		boardGameGUI.add(top, BorderLayout.NORTH);
		boardGameGUI.add(bottom, BorderLayout.SOUTH);
		
		boardGameGUI.add(this.chessGridGUI, BorderLayout.CENTER);
		
		contentPane.add(this.chessTraceGUI, BorderLayout.EAST);
	
		contentPane.add(boardGameGUI, BorderLayout.CENTER);

	
		this.setContentPane(contentPane);
		this.setJMenuBar(menuBar);

	}

	private void executeCommand(Command command){
		command.execute();
		this.commandHist = this.commandHist.subList(0, commandHistIndex+1);
		this.commandHist.add(command);
		setCommandHistIndex(this.commandHistIndex+1);
	}

	private void undo(){
		
		if(this.commandHistIndex>=0){
			this.commandHist.get(commandHistIndex).compensate();
			setCommandHistIndex(this.commandHistIndex-1);
		}
		
	}

	private void redo(){
		
		if(this.commandHistIndex+1<commandHist.size()){
			setCommandHistIndex(this.commandHistIndex+1);
			this.commandHist.get(commandHistIndex).redo();
			
		}
		
	}

	private void setCommandHistIndex(int newIndex){
		this.commandHistIndex =newIndex;

		// System.out.println(this.commandHistIndex);

		if (this.commandHistIndex<0){
			this.itemUndo.setEnabled(false);
		}
		else{
			this.itemUndo.setEnabled(true);
		}
		if(this.commandHistIndex+1<commandHist.size()){
			this.itemRedo.setEnabled(true);	
		}
		else{
			this.itemRedo.setEnabled(false);
		}
	}

	



	@Override
	public void setMessage(TraceMessage traceMessage) {
		String message = null;
		GUICoord initCoord = traceMessage.getCoordInit();		
		GUICoord finalCoord = traceMessage.getCoordFinal();
		String coordInit = "-1", coordFinal = "-1";

		if (initCoord != null) {
			coordInit = "" + ((char)(initCoord.getX() + 'a')) + (this.length - initCoord.getY());
		}
		if (finalCoord != null) {
			coordFinal = "" + ((char)(finalCoord.getX() + 'a')) + (this.length - finalCoord.getY());
		}
		message = traceMessage.getCouleur() + " : " + coordInit + 
				" -> " + coordFinal + " : " + traceMessage.getActionType();
		this.chessTraceGUI.appendText(message + "\n");
	}

	@Override
	public void setPieceToMove(GUICoord gUICoord) {
		this.chessGridGUI.setPieceToMove( gUICoord);
	}

	@Override
	public void resetLight(List<GUICoord> gUICoords, boolean isLight) {
		this.chessGridGUI.resetLight( gUICoords,  isLight);
	}

	@Override
	public void movePiece(GUICoord targetCoord) {
		this.chessGridGUI.movePiece( targetCoord) ;
	}

	@Override
	public void undoMovePiece(GUICoord pieceToMoveInitCoord) {
		this.chessGridGUI.undoMovePiece( pieceToMoveInitCoord);
	}

	@Override
	public String getPromotionType() {
		return this.chessGridGUI.getPromotionType() ;
	}

	@Override
	public void promotePiece(GUICoord gUICoord, String promotionType) {
		this.chessGridGUI.promotePiece( gUICoord,  promotionType);
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Color newColor = (Color) evt.getNewValue();
		System.out.println(newColor.toString());
		System.out.println("notif");
		this.bg.clearSelection();
		for (Enumeration<AbstractButton> buttons = this.bg.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
			
            if (button.getText()==newColor.toString()) {
                button.setSelected(true);
				
            }
        }
		
	}

}
