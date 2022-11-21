package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controler.ChessGameControler;
import nutsAndBolts.ChessGUIConfig;
import nutsAndBolts.GUICoord;
import nutsAndBolts.TraceMessage;


public class ChessGUI extends JFrame implements ChessGameGUI {

	private static final long serialVersionUID = 1L;

	// le damier est une extension de JLayerdPane. Ses écouteurs invoqueront le controler
	private ChessGridGUI chessGridGUI ; 	
	// l'afficheur du suivi des coups est un JPanel
	private ChessTraceGUI chessTraceGUI;	
	// nb lignes et colonnes du damier
	private int length;						

	public ChessGUI(ChessGameControler chessGameControler) {
		super(ChessGUIConfig.getTitle());

		this.setLocation(ChessGUIConfig.getLocation());
		this.setPreferredSize(ChessGUIConfig.getDimension());
		this.pack();		
		this.setResizable(true);

		JComponent contentPane;			// panel principal 
		JComponent boardGameGUI;		// panel du damier + indices lignes et colonnes
		JComponent top, bottom ;		// panel avec indices colonnes
		JComponent west, east ;			// panel avec indices des lignes

		this.length = ChessGUIConfig.getNbLigne();
		this.chessGridGUI = new ChessGridGUI(chessGameControler );
		this.chessTraceGUI = new ChessTraceGUI();

		contentPane = new JPanel(new BorderLayout());	
		boardGameGUI = new JPanel(new BorderLayout());
		top = new JPanel();
		bottom = new JPanel();
		west = new JPanel();
		east = new JPanel();


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

}
