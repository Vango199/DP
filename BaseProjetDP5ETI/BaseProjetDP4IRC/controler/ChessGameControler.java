package controler;

import nutsAndBolts.Couleur;
import nutsAndBolts.GUICoord;

/**
 * @author francoiseperrin
 * 
 * Cette interface définit le comportement attendu de tous les controler
 * qui gèrent les communications entre la view et le model
 * qui ne se connaissent pas (DP Mediator) 
 * 
 * Cette interface est dérivée en 2 interfaces qui définissent les responsabilités 
 * côté view et côté model
 * Chacune étant implémentées par des controleur qui permettent d'éxécuter le progrramme
 * en mode local ou en mode client/server
 * 
 *
 */
public interface ChessGameControler {

	/**
	 * @param couleur
	 * 
	 * Cette méthode est appelée lors de la sélection d'une pièce dans une view .
	 * 
	 * @return true si la couleur de la pièce sélectionnée est de la couleur
	 * du joueur courant
	 */
	public boolean isPlayerOK(Couleur couleur);
	
	/**
	 * @param pieceToMoveCoord
	 * 
	 * Cette méthode est appelée après sélection d'une pièce dans une view .
	 * 
	 * Elle permet d'invoquer des méthodes du model (par exemple récupérer la liste 
	 * cases de destinations possibles d'une pièce sélectionnée)
	 * ou les méthodes de la view (par exemple, fixer la pièce qui doit être déplacée,
	 * allumer les cases de destinations possibles)  
	 */
	public void actionsWhenPieceIsSelectedOnGUI(GUICoord pieceCoord);

	
	/**
	 * @param pieceToMoveCoord
	 * @param targetCoord
	 * 
	 * Cette méthode est appelé lorsque la pièce a atteint sa destination dans une view.
	 * 
	 * Elle permet d'invoquer des méthodes du model (par exemple move() ou pawnPromotion())
	 * ou les méthodes de la view (par exemple, déplacer effectivement la pièce 
	 * ou la repositionner à sa place initiale)  
	 */
	public void actionsWhenPieceIsMovedOnGUI(GUICoord targetCoord);

	
}
