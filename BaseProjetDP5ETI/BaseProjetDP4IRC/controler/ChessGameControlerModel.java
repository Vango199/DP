package controler;

import model.business.ChessGameModel;

public interface ChessGameControlerModel extends ChessGameControler {

	
	/**
	 * @param chessGameModel
	 * 
	 * Fixe le model avec lequel le controler dialogue
	 */
	public void setModel(ChessGameModel chessModel);
	
}
