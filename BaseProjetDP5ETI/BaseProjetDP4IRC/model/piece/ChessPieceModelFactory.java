package model.piece;


import java.util.LinkedList;
import java.util.List;

import nutsAndBolts.Couleur;
import nutsAndBolts.ModelCoord;
import tools.Introspection;

/**
 * @author francoise.perrin
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 * 
 * Classe qui fabrique une liste de pieces de jeu d'echec
 * de la couleur pass�e en param�tre
 *
 */
public class ChessPieceModelFactory {

	static String chemin = "model.piece.";		// TODO - A VERIFIER //

	/**
	 * private pour ne pas instancier d'objets
	 */
	private ChessPieceModelFactory() {

	}
	enum ChessPiecePos {

		TOURBLANC("Tour", Couleur.BLANC, new ModelCoord[] {new ModelCoord('a',1), new ModelCoord('h',1)}),
		CAVALIERBLANC("Cavalier", Couleur.BLANC, new ModelCoord[] {new ModelCoord('b',1), new ModelCoord('g',1)}), 
		FOUBLANC("Fou", Couleur.BLANC, new ModelCoord[] {new ModelCoord('c',1), new ModelCoord('f',1)}), 
		REINEBLANC("Reine", Couleur.BLANC, new ModelCoord[] {new ModelCoord('d',1)}), 
		ROIBLANC("Roi", Couleur.BLANC, new ModelCoord[] {new ModelCoord('e',1)}),
		PIONBLANC("Pion", Couleur.BLANC, new ModelCoord[] {new ModelCoord('a',2), new ModelCoord('b',2), new ModelCoord('c',2), new ModelCoord('d',2),
				new ModelCoord('e',2), new ModelCoord('f',2), new ModelCoord('g',2), new ModelCoord('h',2)}),
//		PIONBLANC("PionBlanc", Couleur.BLANC, new ModelCoord[] {new ModelCoord('a',2), new ModelCoord('b',2), new ModelCoord('c',2), new ModelCoord('d',2),
//				new ModelCoord('e',2), new ModelCoord('f',2), new ModelCoord('g',2), new ModelCoord('h',2)}),
		
		TOURNOIR("Tour", Couleur.NOIR, new ModelCoord[] {new ModelCoord('a',8), new ModelCoord('h',8)}),
		CAVALIERNOIR("Cavalier", Couleur.NOIR, new ModelCoord[] {new ModelCoord('b',8), new ModelCoord('g',8)}), 
		FOUNOIR("Fou", Couleur.NOIR, new ModelCoord[] {new ModelCoord('c',8), new ModelCoord('f',8)}), 
		REINENOIR("Reine", Couleur.NOIR, new ModelCoord[] {new ModelCoord('d',8)}), 
		ROINOIR("Roi", Couleur.NOIR, new ModelCoord[] {new ModelCoord('e',8)}),
		PIONNOIR("Pion", Couleur.NOIR, new ModelCoord[] {new ModelCoord('a',7), new ModelCoord('b',7), new ModelCoord('c',7), new ModelCoord('d',7),
				new ModelCoord('e',7), new ModelCoord('f',7), new ModelCoord('g',7), new ModelCoord('h',7)})  
//		PIONNOIR("PionNoir", Couleur.NOIR, new ModelCoord[] {new ModelCoord('a',7), new ModelCoord('b',7), new ModelCoord('c',7), new ModelCoord('d',7),
//				new ModelCoord('e',7), new ModelCoord('f',7), new ModelCoord('g',7), new ModelCoord('h',7)})  
		; 

		public String nom;
		public Couleur couleur;
		public  ModelCoord[] modelCoords = new ModelCoord[8] ;   

		ChessPiecePos( String nom, Couleur couleur, ModelCoord[] modelCoords) { 
			this.nom = nom;
			this.couleur = couleur;
			this.modelCoords = modelCoords;
		} 
	}

	/**
	 * @param pieceCouleur
	 * @return liste de pi�ces de jeu d'�chec
	 */
	public static List<PieceModel> createPieceModelList(){
		String className;
		ModelCoord pieceModelCoord;
		Couleur pieceCouleur;
		List<PieceModel> pieceModel = new LinkedList<PieceModel>();

		for (int i = 0; i < ChessPiecePos.values().length; i++) {

			for (int j = 0; j < (ChessPiecePos.values()[i].modelCoords).length; j++) {
				className = chemin + ChessPiecePos.values()[i].nom;	// attention au chemin...
				pieceModelCoord = ChessPiecePos.values()[i].modelCoords[j];
				pieceCouleur = ChessPiecePos.values()[i].couleur;
				pieceModel.add((PieceModel) Introspection.newInstance (
						className, new Object[] {pieceCouleur, pieceModelCoord}));
			}
		}
		return pieceModel;
	}

	public static PieceModel createPiece(Couleur pieceCouleur, String type, ModelCoord pieceModelCoord){

		PieceModel piece = null;
		String className = chemin + type;	// attention au chemin		
		piece = (PieceModel) Introspection.newInstance(className,new Object[] {pieceCouleur, pieceModelCoord});
		return piece;
	}

	/**
	 * Tests unitaires
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < ChessPiecePos.values().length; i++) {
			System.out.print(ChessPiecePos.values()[i].name() + " \t"); 
			System.out.print(ChessPiecePos.values()[i].nom + " \t"); 
			for (int j = 0; j < (ChessPiecePos.values()[i].modelCoords).length; j++) {
				System.out.print(ChessPiecePos.values()[i].modelCoords[j] + " "); 				
			}
			System.out.println();
		}
		System.out.println(ChessPieceModelFactory.createPieceModelList()); 
		System.out.println(ChessPieceModelFactory.createPiece(Couleur.BLANC, "Tour", new ModelCoord('a', 8))); 
	}


}