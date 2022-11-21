package controler.localControler;

import java.util.LinkedList;
import java.util.List;

import controler.AbstractControler;
import controler.ChessGameControlerModel;
import controler.ChessGameControlerView;
import gui.ChessGameGUI;
import model.business.ChessGameModel;
import nutsAndBolts.ActionType;
import nutsAndBolts.ChessGUIConfig;
import nutsAndBolts.Couleur;
import nutsAndBolts.GUICoord;
import nutsAndBolts.ModelCoord;
import nutsAndBolts.TraceMessage;



/**
 * @author francoise.perrin
 * 
 * Ce controleur propage les informations de la view au modèle (demande déplacement pièce...),
 * et du model à la view (qui les propage à son damier et à son afficheur de coups joués)
 * Il dispose ainsi d'une référence vers le model et vers la view
 *
 * Il sait convertir les coordonnées de la view vers celles du model et réciproquement
 * 
 * = Mise en oeuvre du DP Mediator
 * 
 */
public class ChessControlerLocal extends AbstractControler implements ChessGameControlerModel, ChessGameControlerView  {
	private ChessGameModel chessModel;
	private ChessGameGUI chessGUI;

	private GUICoord pieceToMoveCoord;
	private Couleur currentPlayer;
	private List<GUICoord> listPotentielTargetCoords ;
	private boolean isPieceToMove;

	public ChessControlerLocal() {
		super();
		this.currentPlayer = ChessGUIConfig.getBeginColor(); 
		this.listPotentielTargetCoords = null;
	}

	@Override
	public boolean isPlayerOK(Couleur couleur) {
		boolean ret = false;
		if (couleur.equals(this.currentPlayer)) {
			this.isPieceToMove = true;
			ret = true;
		}
		return ret;
	}

	@Override
	public void actionsWhenPieceIsSelectedOnGUI(GUICoord pieceToMoveCoord) {

		if (this.chessGUI != null && this.chessModel !=null) {
			
			// si une pièce a été sélectionnée
			if (this.isPieceToMove) {
				
				// fixe la pièce à déplacer sur la view et dans le controler
				this.pieceToMoveCoord = pieceToMoveCoord;
				this.chessGUI.setPieceToMove(pieceToMoveCoord);

				// Mise en évidence des cases vers lesquelles 
				// la pièce peut être déplacée 	
				List<ModelCoord> modelCoords = this.chessModel.getPieceListMoveOK(
						CoordToModelCoord(pieceToMoveCoord));
				if (modelCoords != null) {
					this.listPotentielTargetCoords = new LinkedList<GUICoord>();
					for (ModelCoord modelCoord : modelCoords) {
						this.listPotentielTargetCoords.add(ModelCoordToCoord(modelCoord));
					}
					this.chessGUI.resetLight(this.listPotentielTargetCoords, true);
				}
			}
		}
	}


	@Override
	public void actionsWhenPieceIsMovedOnGUI(GUICoord targetCoord) {

		if (this.chessGUI != null && this.chessModel !=null && this.isPieceToMove) {
							
			ActionType actionType = ActionType.UNKNOWN;

			// réinitialisation des couleurs d'origines des cases allumées 
			this.chessGUI.resetLight(this.listPotentielTargetCoords, false);

			// Invoque la methode de deplacement de l'echiquier	
			// qui retourne une info s'il est besoin de gérer la promotion du pion
			actionType = this.chessModel.move(
					CoordToModelCoord(this.pieceToMoveCoord), 
					CoordToModelCoord(targetCoord));

			this.chessGUI.setMessage(new TraceMessage(this.currentPlayer, 
					this.pieceToMoveCoord, targetCoord, actionType)); 

			// si déplacement OK avec ou sans capture, on déplace et on prend
			// effectivement la pièce sur le damier

			if (ActionType.ILLEGAL.equals(actionType)){
				this.chessGUI.undoMovePiece(this.pieceToMoveCoord);
			}
			else {
				// switch joueur
				this.currentPlayer = (Couleur.BLANC.equals(this.currentPlayer) ? Couleur.NOIR : Couleur.BLANC);

				// Déplacement effectif sur view
				this.chessGUI.movePiece(targetCoord);

				// en cas promotion du pion 
				if (ActionType.PROMOTION.equals(actionType) || ActionType.TAKEPROMOTION.equals(actionType)){

					String promotionType = this.chessGUI.getPromotionType();
					this.chessGUI.promotePiece(targetCoord, promotionType);
					this.chessModel.pawnPromotion(CoordToModelCoord(targetCoord), promotionType); 
				}
			}
			this.pieceToMoveCoord = null;
			this.isPieceToMove = false;
		}
	}

	@Override
	public void setView(ChessGameGUI chessGUI) {
		this.chessGUI = chessGUI;
	}

	@Override
	public void setModel(ChessGameModel chessModel) {
		this.chessModel = chessModel;
	}

}
