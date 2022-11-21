package controler;

import nutsAndBolts.Couleur;
import nutsAndBolts.GUICoord;
import nutsAndBolts.ModelCoord;

/**
 * @author francoiseperrin
 * Cette classe contient les méthodes communes à tous les controler
 * 
 */
public abstract class AbstractControler implements ChessGameControler {

	public AbstractControler() {
		super();
	}
		
	public abstract boolean isPlayerOK(Couleur couleur);
	
	public abstract void actionsWhenPieceIsSelectedOnGUI(GUICoord pieceCoord);
	
	public abstract void actionsWhenPieceIsMovedOnGUI(GUICoord targetCoord);

	
	/**
	 * @param gUICoord
	 * @return GUICoord convertie en ModelCoord
	 */
	protected ModelCoord CoordToModelCoord(GUICoord gUICoord) {
		ModelCoord modelCoord = null;
		if(gUICoord != null) {
			char col = (char)(gUICoord.getX() + 'a');
			int ligne = 8 - gUICoord.getY();
			modelCoord = new ModelCoord(col, ligne);
		}
		return modelCoord;
	}

	/**
	 * @param modelCoord
	 * @return ModelCoord convertie en GUICoord
	 */
	protected GUICoord ModelCoordToCoord(ModelCoord modelCoord) {
		GUICoord gUICoord = null;
		if (modelCoord != null) {
			int x = modelCoord.getCol() -'a';
			int y = 8 - modelCoord.getLigne();
			gUICoord = new GUICoord(x, y);
		}
		return gUICoord;
	}
}
