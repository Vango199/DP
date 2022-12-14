package model.piece;

import java.util.List;

import nutsAndBolts.ActionType;
import nutsAndBolts.Couleur;
import nutsAndBolts.ModelCoord;

/**
 * @author francoise.perrin - Alain BECKER
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD *
 * 
 * cette interface définit le comportement attendu des pièces 
 */
public interface PieceModel {

	/**
	 * @return coordonnées de la pièce
	 */
	public ModelCoord getCoord();

	/**
	 * @return couleur de la piece
	 */
	public Couleur getCouleur();

	/**
	 * @return le nom de la pièce (Tour, Cavalier, etc.)
	 * attention le nom de la pièce ne correspond pas forcément au nom de la classe
	 */
	public String getName();


	/**
	 * @param coord
	 * @return ActionType
	 */
	public ActionType doMove(ModelCoord coord);

	/** 
	 * @return true si piece effectivement captur�e
	 * Positionne x et y à -1
	 */
	public boolean catchPiece();

	/**
	 * @param coord
	 * @return  true si déplacement légal en fonction des algo
	 * de déplacement spécifique de chaque pièce
	 */
	public boolean isAlgoMoveOk(ModelCoord coord);

	/**
	 * @param coord
	 * @param type
	 * @return  true si déplacement légal en fonction des algo
	 * de déplacement spécifique de chaque pièce dans le cas d'un déplacement avec prise
	 */
	public boolean isAlgoMoveOk(ModelCoord coord, ActionType type);

	/**
	 * @param coord
	 * @return la liste des coordonnées des cases traversée en allant vers case de destination
	 */
	public List<ModelCoord> getMoveItinerary(ModelCoord coord);

	/**
	 * Effectue un rollback du dernier mouvement (en rétablissant les coordonnées
	 * initiales de la pièce, telles que mémorisées avant son déplacement).
	 */
	public boolean undoLastMove();

	/**
	 * Effectue un rollback de la capture (en rétablissant les coordonnées
	 * initiales de la pièce au moment de sa capture).
	 */
	public boolean undoLastCatch();

	
};

