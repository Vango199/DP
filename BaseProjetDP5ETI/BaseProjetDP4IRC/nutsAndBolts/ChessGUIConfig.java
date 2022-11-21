package nutsAndBolts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class ChessGUIConfig {
	private static Couleur beginColor;	// blanc dans jeu d'échec
	private static int nbLigne;
	private static int nbColonne;
	private static Color blackSquareColor, whiteSquareColor;
	private static Color lightColor;	// couleur mise en évidence cases destinations potentielles
	private static String title;
	private static Dimension dimension;
	private static Point location;
	
	
	private static ChessGUIConfig instance = null;
	
	
	public static ChessGUIConfig newInstance(
			Couleur beginColor,
			int nbLigne, 
			int nbColonne,  
			Color blackSquareColor, 
			Color whiteSquareColor, 
			Color lightColor,
			String title,
			Dimension dimension,  
			Point location) {
		if (instance == null)
			instance = new ChessGUIConfig( beginColor, nbLigne,  nbColonne,   
					blackSquareColor, whiteSquareColor, lightColor, title, dimension, location);
		return instance;
	}
	private ChessGUIConfig(
			Couleur beginColor,
			int nbLigne, 
			int nbColonne, 
			Color blackSquareColor, 
			Color whiteSquareColor, 
			Color lightColor,
			String title,
			Dimension dimension,  
			Point location) {
	
		ChessGUIConfig.beginColor = beginColor;
		ChessGUIConfig.nbLigne = nbLigne;
		ChessGUIConfig.nbColonne = nbColonne;
		ChessGUIConfig.blackSquareColor = blackSquareColor;
		ChessGUIConfig.whiteSquareColor = whiteSquareColor;
		ChessGUIConfig.lightColor = lightColor;
		ChessGUIConfig.dimension = dimension;
		ChessGUIConfig.location = location;
	
	}
	
	public static Couleur getBeginColor() {
		return ChessGUIConfig.beginColor;
	}
	
	public static int getNbLigne() {
		return ChessGUIConfig.nbLigne;
	}
	
	public static int getNbColonne() {
		return ChessGUIConfig.nbColonne;
	}
	
	public static Color getBlackSquareColor() {
		return ChessGUIConfig.blackSquareColor;
	}
	
	public static Color getWhiteSquareColor() {
		return ChessGUIConfig.whiteSquareColor;
	}
	
	public static Color getLightColor() {
		return ChessGUIConfig.lightColor;
	}
	
	
	public static String getTitle() {
		return ChessGUIConfig.title;
	}
	
	
	public static Dimension getDimension() {
		return ChessGUIConfig.dimension;
	}
	
	public static Point getLocation() {
		return ChessGUIConfig.location;
	}
	
	/**
	 * @param x
	 * @param y
	 * @return true si les coordonnées passées en paramètre
	 * sont dans les limites du plateau
	 */
	public static boolean coordonnees_valides(int x, int y){
		return ( (x<= ChessGUIConfig.nbColonne-1) && (x>=0) && (y<=ChessGUIConfig.nbLigne) && (y>=0) );
	}
	
}
