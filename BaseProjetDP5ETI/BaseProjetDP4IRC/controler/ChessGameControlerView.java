package controler;

import gui.ChessGameGUI;

public interface ChessGameControlerView extends ChessGameControler {
	
	/**
	 * @param chessGUI
	 * 
	 * Fixe la view avec laquelle le controler dialogue
	 */
	public void setView (ChessGameGUI chessGUI);

	
	
}
