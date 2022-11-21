package tools.data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class ChessGUIConfig {
	private static Couleur beginColor;
	private static int nbLigne;
	private static int nbColonne;
	private static Color blackSquareColor, whiteSquareColor;
	private static String title;
	private static Dimension dimension;
	private static Point location;
	
	/**
	 * @param nbLigne
	 * @param nbColonne
	 * @param factoryName
	 * @param couleur
	 */
	
	private static ChessGUIConfig instance = null;
	
	public static ChessGUIConfig newInstance(
			Couleur beginColor,
			int nbLigne, 
			int nbColonne,  
			Color blackSquareColor, 
			Color whiteSquareColor, 
			String title,
			Dimension dimension,  
			Point location) {
		if (instance == null)
			instance = new ChessGUIConfig( beginColor, nbLigne,  nbColonne,   
					blackSquareColor, whiteSquareColor, title, dimension, location);
		return instance;
	}
	private ChessGUIConfig(
			Couleur beginColor,
			int nbLigne, 
			int nbColonne, 
			Color blackSquareColor, 
			Color whiteSquareColor, 
			String title,
			Dimension dimension,  
			Point location) {
	
		ChessGUIConfig.beginColor = beginColor;
		ChessGUIConfig.nbLigne = nbLigne;
		ChessGUIConfig.nbColonne = nbColonne;
		ChessGUIConfig.blackSquareColor = blackSquareColor;
		ChessGUIConfig.whiteSquareColor = whiteSquareColor;
		ChessGUIConfig.dimension = dimension;
		ChessGUIConfig.location = location;
	
	}
	/**
	 * @return the beginColor
	 */
	public static Couleur getBeginColor() {
		return ChessGUIConfig.beginColor;
	}
	
	/**
	 * @return the nbLigne
	 */
	public static int getNbLigne() {
		return ChessGUIConfig.nbLigne;
	}
	
	/**
	 * @return the nbColonne
	 */
	public static int getNbColonne() {
		return ChessGUIConfig.nbColonne;
	}
	/**
	 * @return the blackSquareColor
	 */
	public static Color getBlackSquareColor() {
		return ChessGUIConfig.blackSquareColor;
	}
	/**
	 * @return the whiteSquareColor
	 */
	public static Color getWhiteSquareColor() {
		return ChessGUIConfig.whiteSquareColor;
	}
	
	/**
	 * @return
	 */
	public static String getTitle() {
		return ChessGUIConfig.title;
	}
	
	/**
	 * @return
	 */
	public static Dimension getDimension() {
		return ChessGUIConfig.dimension;
	}
	/**
	 * @return
	 */
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
