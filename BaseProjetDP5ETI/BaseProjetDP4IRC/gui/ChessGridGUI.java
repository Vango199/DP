package gui;



import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controler.ChessGameControler;
import nutsAndBolts.ChessGUIConfig;
import nutsAndBolts.Couleur;
import nutsAndBolts.GUICoord;



/**
 * @author francoise.perrin
 *
 * cette classe implémente le damier de la view
 * elle a 2 responsabilités :
 * elle gére les affichages
 * elle invoque les méthodes du controler à travers ses écouteurs
 *  
 * 
 */

class ChessGridGUI extends JLayeredPane {

	private static final long serialVersionUID = 1L;

	private ChessGameControler chessGameControler;
	private Map<GUICoord, JPanel> mapCoordSquare;	// association Coord cases du damier / Square
	private JLabel pieceToMove;						// la pièce à déplacer
	private JLabel pieceToDrag;						// la pièce que l'on voit se déplacer (drag)
	private int xAdjustment;						// 
	private int yAdjustment;


	/**
	 * le constructeur construit les cases noires et blanches
	 * et positionne les images de pièces dessus
	 * @param chessGameControler 
	 * 
	 */
	ChessGridGUI(ChessGameControler chessGameControler) {
		super();
		this.mapCoordSquare = new HashMap<GUICoord, JPanel>();
		this.chessGameControler = chessGameControler;	
		this.fillGridGUI();		// remplissage damier
		this.addListener();		// écoute évènements Mouse et MouseMotion
	}


	//////////////////////////////////////////////////////////////////////////////
	//
	// Mise en forme du quadrillage et ajout des pièces sur le damier
	//
	//////////////////////////////////////////////////////////////////////////////

	/**
	 * Remplissage damier
	 */
	private void fillGridGUI() {
		int nbLigne = ChessGUIConfig.getNbLigne();
		int nbCol = ChessGUIConfig.getNbColonne();
		JPanel square = null;
		Color color = null;
		JLabel chessPieceGUI = null;

		this.setLayout(new GridLayout(nbLigne, nbCol));
		for (int ligne = 0; ligne<nbLigne; ligne++){
			for (int col = 0; col<nbCol; col++) {

				if((col%2==0 && ligne%2==0) || (col%2!=0 && ligne%2!=0)){
					color = ChessGUIConfig.getWhiteSquareColor();
				}
				else{
					color = ChessGUIConfig.getBlackSquareColor();
				}
				GUICoord gUICoord = new GUICoord(col, ligne);
				square = new ChessSquareGUI(color, gUICoord);

				// Si une pièce doit se trouver sur cette case :
				// fabrication de la pièceGUI
				chessPieceGUI = ChessPieceGUIFactory.createChessPieceGUI(gUICoord);

				// ajout de la pièceGUI sur le carre
				if (chessPieceGUI != null) {
					square.add(chessPieceGUI);
				}

				// ajout du carre sur le damier
				this.add(square);

				// MAJ Map de coordonnées pour retrouver facilement les pièces
				this.mapCoordSquare.put(gUICoord, (ChessSquareGUI) square);
			}
		}
		this.repaint();
		this.revalidate();
	}

	//////////////////////////////////////////////////////////////////////////////
	//
	// Gestion des évènements souris et du drag & drop
	//
	//////////////////////////////////////////////////////////////////////////////

	/**
	 * Le damier est écouté par des écouteurs d'évènement Mouse et MouseMotion
	 * pour gérer le drag&drop
	 */
	private void addListener() {

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				// conversion des coordonnées pixel en Coord
				GUICoord pieceCoord = getCoord(e.getX(), e.getY());	

				// Si la pièce sélectionnée appartient bien au joueur courant
				ChessPieceGUI piece = (ChessPieceGUI) findPiece(pieceCoord);
				if (piece != null && chessGameControler.isPlayerOK(piece.getCouleur())) {

					// le controleur :
					// 		fixe la pièce à déplacer (PieceToMove) sur le damier
					// 		demande au damier d'allumer les cases de destinations possibles
					chessGameControler.actionsWhenPieceIsSelectedOnGUI(pieceCoord);

					setPieceToDrag(e.getX(), e.getY());
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {

				// suppression de la pièce à déplacer sur le Layeredpane
				removePieceToDrag();	// à faire en 1er pour remettre les indices OK sur Layerdpane

				// conversion des coordonnées pixel en Coord
				GUICoord  targetCoord = getCoord(e.getX(), e.getY()); 

				// Deplacement effectif et promotion éventuelle dans model	
				chessGameControler.actionsWhenPieceIsMovedOnGUI(targetCoord);
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				dragPieceToDrag(e.getX(), e.getY());
			}
		});
	}
	/**
	 * @param x en pixel
	 * @param y en pixel
	 * 
	 * Cette méthode est appelée par les écouteurs d'évènements souris
	 * @return les Coord de la pièce sélectionnée par l'utilisateur
	 */
	private GUICoord getCoord(int x, int y) {
		Component c =  this.findComponentAt(x, y);
		JPanel square = c instanceof JLabel ? (JPanel)c.getParent() : (JPanel)c;
		return ((ChessSquareGUI) square).getCoord();
	}

	/**
	 * @param x
	 * @param y
	 * 
	 * Cette méthode est appelée par l'écouteur en cas de mousePressed()
	 * la pièce qui va bouger est fixée (pieceToDrag) et est placée sur le 
	 * JLayerdPane pour que l'on puisse suivre son déplacement lors du mouseDragged()
	 * 
	 */
	private void setPieceToDrag(int x, int y) {
		Point pieceToDragLocation = null;
		JPanel square = null;

		Component c =  this.findComponentAt(x, y);

		// si l'utilisateur a selectionné une piece
		if (c instanceof JLabel) {

			this.pieceToDrag = (JLabel) c;

			square=(JPanel)pieceToDrag.getParent();
			pieceToDragLocation = square.getLocation();
			this.xAdjustment = pieceToDragLocation.x - x;
			this.yAdjustment = pieceToDragLocation.y - y;

			this.pieceToDrag.setLocation(x + xAdjustment, y + yAdjustment);

			this.add(this.pieceToDrag, JLayeredPane.DRAG_LAYER);
		}
	}
	/**
	 * @param x
	 * @param y
	 * 
	 * Cette méthode est appelée par l'écouteur en cas de mouseDragged()
	 * pour que l'on suive le trajet de l'image de pièce qui se déplace
	 * 
	 */
	private void dragPieceToDrag(int x, int y) {
		if (this.pieceToDrag != null) {	
			this.pieceToDrag.setLocation(x + xAdjustment, y + yAdjustment);
			this.pieceToDrag.setVisible(true);
		}
	}
	/**
	 * Cette méthode est appelée par l'écouteur en cas de mouseReleased()
	 * elle supprime du LayerdPane la pièce qui se déplace
	 */
	private void removePieceToDrag() {
		if (this.pieceToDrag != null){
			this.remove(this.pieceToDrag);
			this.pieceToDrag = null;
		}
	}


	//////////////////////////////////////////////////////////////////////////////
	//
	// Actions de la view en réaction des réponses du model
	// Toutes les méthodes suivantes sont invoquées par le controler
	// la plupart, en réponse aux codes de retour des méthodes du model
	//
	//////////////////////////////////////////////////////////////////////////////

	void setPieceToMove(GUICoord gUICoord) {
		this.pieceToMove = findPiece(gUICoord);
	}

	void resetLight(List<GUICoord> listGUICoords, boolean isLight) {
		JPanel square;

		if (listGUICoords != null) {
			for (GUICoord gUICoord : listGUICoords) {
				square = this.mapCoordSquare.get(gUICoord);
				((ChessSquareGUI) square).resetColor(isLight);
			}
		}
	}

	void movePiece(GUICoord targetCoord) {

		if (this.pieceToMove != null && targetCoord != null) {

			JPanel parentSquare = this.mapCoordSquare.get(targetCoord);

			// prise pièce si existe sur case arrivée
			if (parentSquare != null && parentSquare.getComponentCount()!=0){
				parentSquare.removeAll();
			}
			parentSquare.add(this.pieceToMove);
			this.pieceToMove = null;
		}
		this.repaint();
		this.revalidate();
	}

	void undoMovePiece(GUICoord pieceToMoveInitCoord) {

		if (this.pieceToMove != null && pieceToMoveInitCoord != null) {

			JPanel parentSquare = this.mapCoordSquare.get(pieceToMoveInitCoord);
			if (parentSquare != null ){
				parentSquare.add(this.pieceToMove);
				this.pieceToMove = null;
			}
		}
		this.repaint();
		this.revalidate();
	}

	public String getPromotionType() {

		String[] promotions = {"Reine", "Tour", "Fou", "Cavalier"};
		String promotionType = 
				(String)JOptionPane.showInputDialog(null, 
						"Veuillez indiquer en quelle pièce vous souhaitez promouvoir votre pion",
						"Promotion du pion",
						JOptionPane.QUESTION_MESSAGE,
						null,
						promotions,
						promotions[0]);
		return promotionType;
	}

	void promotePiece(GUICoord gUICoord, String promotionType) {

		JPanel parentSquare = this.mapCoordSquare.get(gUICoord);
		ChessPieceGUI chessPieceGUIOld = null;
		if (parentSquare != null && parentSquare.getComponentCount()!=0){
			chessPieceGUIOld = (ChessPieceGUI) parentSquare.getComponent(0);
			parentSquare.removeAll();
			Couleur couleur = chessPieceGUIOld.getCouleur();

			// fabrication de la nouvelle PieceGUI 
			// à partir des infos fournies par la pièce initiale
			JLabel chessPieceGUI = ChessPieceGUIFactory.createChessPieceGUI(promotionType, couleur);
			parentSquare.add(chessPieceGUI);
		}
		this.repaint();
		this.revalidate();
	}

	private JLabel findPiece(GUICoord gUICoord) {
		ChessPieceGUI chessPieceGUI = null;
		if (gUICoord != null) {
			JPanel parentSquare = this.mapCoordSquare.get(gUICoord);

			if (parentSquare != null && parentSquare.getComponentCount()!=0){
				chessPieceGUI = (ChessPieceGUI) parentSquare.getComponent(0);
			}
		}
		return chessPieceGUI;
	}
}

