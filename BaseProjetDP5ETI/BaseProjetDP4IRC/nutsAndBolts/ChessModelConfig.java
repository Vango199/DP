package nutsAndBolts;

public class ChessModelConfig {
	private static int nbLigne;
	private static int nbColonne;
	private static Couleur beginColor;

	/**
	 * @param nbLigne
	 * @param nbColonne
	 * @param couleur
	 */

	private static ChessModelConfig instance = null;

	public static ChessModelConfig newInstance(int nbLigne, int nbColonne, 
			Couleur beginColor) {
		if (instance == null)
			instance = new ChessModelConfig( nbLigne,  nbColonne,
					beginColor);
		return instance;
	}
	private ChessModelConfig(int nbLigne, int nbColonne, 
			Couleur beginColor) {

		ChessModelConfig.nbLigne = nbLigne;
		ChessModelConfig.nbColonne = nbColonne;
		ChessModelConfig.beginColor = beginColor;
	}


	/**
	 * @return the nbLigne
	 */
	public static int getNbLigne() {
		return ChessModelConfig.nbLigne;
	}

	/**
	 * @return the nbColonne
	 */
	public static int getNbColonne() {
		return ChessModelConfig.nbColonne;
	}



	/**
	 * @return the beginColor
	 */
	public static Couleur getBeginColor() {
		return ChessModelConfig.beginColor;
	}

	/**
	 * @param x
	 * @param y
	 * @return true si les coordonnées passées en paramètre
	 * sont dans les limites du plateau
	 */
	public static boolean coordonnees_valides(ModelCoord coord){
		boolean ret = false;
		if (coord != null) {
			int x = coord.getCol() -'a';
			int y = 8 - coord.getLigne();
			ret = (x<= ChessModelConfig.nbColonne-1) && (x>=0) && (y<=ChessModelConfig.nbLigne) && (y>=0) ;
		}
		return ret;
	}
}
