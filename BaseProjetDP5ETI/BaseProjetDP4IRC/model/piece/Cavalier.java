package model.piece;

import java.util.Collections;
import java.util.List;

import nutsAndBolts.Couleur;
import nutsAndBolts.ModelCoord;



/**
 * @author francoise.perrin - Alain BECKER
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD *
 */
public class Cavalier extends AbstractPiece  {
	

	/**
	 * @param couleur
	 * @param coord
	 */
	public Cavalier( Couleur couleur, ModelCoord coord) {
		super(couleur, coord);
	}
	
	@Override
	public boolean isAlgoMoveOk(ModelCoord finalCoord) {
	
		boolean ret = false;
		int xFinal = finalCoord.getCol() -'a';
		int yFinal = 8 - finalCoord.getLigne();
		
		if ((Math.abs(xFinal - this.getX()) + Math.abs(yFinal - this.getY())) == 3) {
			
			if ((Math.abs(xFinal - this.getX())<3) && (Math.abs(yFinal - this.getY())<3)) {
				ret  = true;
			}		
		}	
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see model.AbstractPiece#getMoveItinerary(int, int)
	 * dans le cas du cavalier, il n'y a pas d'itinéraire
	 * puisqu'il peut enjamber les autres pièces
	 */
	@Override
	public List<ModelCoord> getMoveItinerary(ModelCoord finalCoord) {
		List<ModelCoord> ret = Collections.emptyList(); 
		return ret;
	}

	
}
