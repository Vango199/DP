package model.business;


import java.util.List;

import model.piece.ChessPieceModelFactory;
import model.piece.PieceModel;
import nutsAndBolts.ActionType;
import nutsAndBolts.Couleur;
import nutsAndBolts.ModelCoord;

/**
 * @author francoise.perrin - Alain BECKER
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 * 
 * Cette classe est une imlémentation de l'échiquier (classe ChessModel)
 * Elle est chargée de la communication avec les Piece 
 * mais ne gère aucune logique métier
 * 
 * */
public class ChessImplementor implements ChessGameImplementor {

	private List<PieceModel> jeu;

	private PieceModel pieceToMove;	// La pièce à déplacer - sert en cas rollback
	private PieceModel pieceToCatch;// La pièce à prendre - sert en cas rollback
	private boolean hasPromote;	// true si on vient d'effectuer promotion du pion - sert en cas rollback


	/**
	 * Le constructeur de jeu fait appel � la fabrique 
	 * qui lui fournit une liste de pi�ces
	 */
	public ChessImplementor(){

		jeu = ChessPieceModelFactory.createPieceModelList();
	}

	@Override
	public boolean isPieceHere(ModelCoord coord, Couleur currentColor) {
		boolean ret = false;
		PieceModel piece = this.findPiece(coord) ;
		if(piece!= null && piece.getCouleur().equals(currentColor)){
			ret = true;
		}
		return ret;
	}

	@Override
	public String toString() {
		return "ChessImplementor [jeu=" + jeu + "]";
	}

	@Override
	public boolean isAlgoMoveOk(ModelCoord initCoord, ModelCoord finalCoord, ActionType type) {
		boolean isMoveOk = false;
		PieceModel pieceToMove = this.findPiece(initCoord);

		// verif déplacement autorisé 
		if (pieceToMove != null) {

			// appel de la méthode isAlgoMoveOk avec 3 paramètres en cas de pièce à prendre
			if (ActionType.TAKE.equals(type) ){
				isMoveOk = pieceToMove.isAlgoMoveOk(finalCoord, type) ;
			}
			// appel de la méthode isAlgoMoveOk avec 2 paramètres en cas de déplacement simple
			else {
				isMoveOk = pieceToMove.isAlgoMoveOk(finalCoord);
			}
		}
		return isMoveOk;
	}

	@Override
	public ActionType doCatch(ModelCoord coord) {

		ActionType isCaptureOk = ActionType.UNKNOWN;
		this.pieceToCatch = this.findPiece(coord);

		// verif déplacement autorisé 
		if (this.pieceToCatch != null ) {
			isCaptureOk = this.pieceToCatch.catchPiece() ? ActionType.TAKE : ActionType.UNKNOWN;
		}
		return isCaptureOk;
	}

	@Override
	public ActionType doMove(ModelCoord initCoord, ModelCoord finalCoord) {

		ActionType actionType = ActionType.UNKNOWN;

		this.pieceToMove = this.findPiece(initCoord);

		if (this.pieceToMove != null ) {
			actionType = this.pieceToMove.doMove(finalCoord)  ;
			this.hasPromote = ActionType.PROMOTION.equals(actionType) ? true : false;
		}
		return actionType;
	}

	@Override
	public boolean isItineraryEmpty(ModelCoord initCoord, ModelCoord finalCoord) {
		boolean ret = true;
		PieceModel pieceToMove = this.findPiece(initCoord);

		if (pieceToMove != null){

			// recherche de la trajectoire de la pièce à déplacer
			List<ModelCoord> itinerary = pieceToMove.getMoveItinerary(finalCoord);

			// on cherche s'il existe une piece de l'un des 2 jeux sur la trajectoire

			for (ModelCoord etape : itinerary) {
				// on sort du for s'il y a une piece sur l'itineraire
				// (inutile de chercher plusieurs collisions, 1 suffit)
				if (this.findPiece(etape)!=null ) { 
					ret = false;
					break; 
				}
			}
		}
		return ret;
	}

	@Override
	public ModelCoord getKingCoord(Couleur currentColor) {
		ModelCoord coord  = null;

		for (PieceModel piece : this.jeu){
			if ("Roi".equals(piece.getName()) && piece.getCouleur().equals(currentColor)){
				coord = new ModelCoord(piece.getCoord());
				break;
			}
		}
		return coord;
	}

	@Override
	public boolean undoLastCapture() {
		if (this.pieceToCatch != null){
			this.pieceToCatch.undoLastCatch();
			return true;
		}
		return false;
	}

	@Override
	public boolean undoLastMove() {
		if (this.pieceToMove != null){
			this.pieceToMove.undoLastMove();
			if (this.hasPromote) {

				// ToDo undoPawnPromotion()
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean pawnPromotion(ModelCoord promotionCoord, String promotionType) {
		boolean ret = false;

		if (this.pieceToMove != null ){
			if (this.pieceToMove.getCoord().equals(promotionCoord)) {

				// suppression de la pièce dans la liste
				PieceModel pieceToreMove = this.pieceToMove;
				jeu.remove(pieceToreMove);

				// création d'une nouvelle pièce
				PieceModel newPiece = ChessPieceModelFactory.createPiece(this.pieceToMove.getCouleur(), promotionType, promotionCoord) ;
				jeu.add(newPiece);
				this.pieceToMove = newPiece;
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * @param coord
	 * @return la référence vers la pièce cherchée, null sinon
	 */
	private PieceModel findPiece(ModelCoord coord){
		PieceModel pieceToFind = null;
		if (coord != null) {
			for (PieceModel piece : this.jeu){
				if (piece.getCoord().equals(coord)){
					pieceToFind = piece;
					break;
				}
			}
		}
		return pieceToFind;
	}
}

