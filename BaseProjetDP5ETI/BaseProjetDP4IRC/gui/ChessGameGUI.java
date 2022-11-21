package gui;


import java.util.List;

import nutsAndBolts.GUICoord;
import nutsAndBolts.TraceMessage;


/**
 * @author francoise.perrin
 *
 * Cette interface défini le comportement attendu par la view
 * 
 */

public interface ChessGameGUI {

	
	/**
	 * @param gUICoord
	 * 
	 * Cette méthode est appelée par le CONTROLER  en cas de 
	 * mousePressed() pour fixer la pièce à déplacer
	 */
	public void setPieceToMove(GUICoord gUICoord) ;

	/**
	 * @param gUICoords
	 * @param isLight
	 * 
	 * Cette méthode est appelée par le CONTROLER  en cas de 
	 * mousePressed() : isLight = true, ou de
	 * mouseReleased() : isLignt = false
	 * Elle éclaire ou remet dans leur couleur d'origine les cases concernées 
	 * par les destinations possibles de la pièce à déplacer
	 */
	public void resetLight(List<GUICoord> gUICoords, boolean isLight) ;

	/**
	 * @param coord
	 * 
	 * Cette méthode est appelée par le CONTROLER en cas de mouseReleased()
	 * uniquement dans le cas ou l'appel model.move() est OK
	 * la case de destination est recherchée :
	 * si elle contient une pièce, celle ci est supprimée
	 * puis la pièce est effectivement déplacée 
	 *  
	 * */
	public void movePiece(GUICoord targetCoord) ;

	/**
	 * @param message
	 * Cette méthode est appelée par le CONTROLER en cas de mouseReleased()
	 * pour afficher la trace des déplacements sur la view
	 */
	void setMessage(TraceMessage message);
	
	/**
	 * @param coord
	 * 
	 * Cette méthode est appelée par le CONTROLER  en cas de mouseReleased()
	 * lorsque le déplacement est illégal
	 * la case d'origine est recherchée :
	 * la pièce est replacée à ses coordonnées d'origine 
	 *  
	 * */
	public void undoMovePiece(GUICoord pieceToMoveInitCoord) ;

	/**
	 * @return promotionType
	 * Cette méthode est appelée par le CONTROLER  en cas de mouseReleased()
	 * et que le model a indiqué qu'on était dans un cas de promotion du pion
	 * elle demande en quoi l'utilisateur veut promouvoir le pion
	 */
	public String getPromotionType() ;

	/**
	 * @param gUICoord
	 * @param promotionType
	 * 
	 * Cette méthode est appelée par le CONTROLER  en cas de mouseReleased()
	 * et que lemodel a indiqué qu'on était dans un cas de promotion du pion
	 * Elle change l'image de la pièce en fonction du choix de l'utilisateur
	 */
	public void promotePiece(GUICoord gUICoord, String promotionType);

}

