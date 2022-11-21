package gui;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import nutsAndBolts.Couleur;
import nutsAndBolts.GUICoord;

/**
 * @author francoise.perrin
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 * 

 *  
 */
public class ChessPieceGUIFactory {

	private static Map<String, List<GUICoord>> mapPieceInitCoords = new HashMap<String, List<GUICoord>>();
	private static Map<String, String> mapPieceImage = new HashMap<String, String> ();
	private static Map<Couleur, List<String>> mapPieceColor = new HashMap<Couleur, List<String>> ();

	static {	
		mapPieceInitCoords.put("TourBLANC", Arrays.asList(new GUICoord[] {new GUICoord(0,7), new GUICoord(7,7)}));
		mapPieceInitCoords.put("CavalierBLANC", Arrays.asList(new GUICoord[] {new GUICoord(1,7), new GUICoord(6,7)}));
		mapPieceInitCoords.put("FouBLANC", Arrays.asList(new GUICoord[] {new GUICoord(2,7), new GUICoord(5,7)}));
		mapPieceInitCoords.put("ReineBLANC", Arrays.asList(new GUICoord[] {new GUICoord(3,7)}));
		mapPieceInitCoords.put("RoiBLANC", Arrays.asList(new GUICoord[] {new GUICoord(4,7)}));
		mapPieceInitCoords.put("PionBLANC", Arrays.asList(new GUICoord[] {new GUICoord(0,6), new GUICoord(1,6), new GUICoord(2,6), new GUICoord(3,6),
				new GUICoord(4,6),  new GUICoord(5,6), new GUICoord(6,6), new GUICoord(7,6)}));
		mapPieceInitCoords.put("TourNOIR", Arrays.asList(new GUICoord[] {new GUICoord(0,0), new GUICoord(7,0)}));
		mapPieceInitCoords.put("CavalierNOIR", Arrays.asList(new GUICoord[] {new GUICoord(1,0), new GUICoord(6,0)}));
		mapPieceInitCoords.put("FouNOIR", Arrays.asList(new GUICoord[] {new GUICoord(2,0), new GUICoord(5,0)}));
		mapPieceInitCoords.put("ReineNOIR", Arrays.asList(new GUICoord[] {new GUICoord(3,0)}));
		mapPieceInitCoords.put("RoiNOIR", Arrays.asList(new GUICoord[] {new GUICoord(4,0)}));
		mapPieceInitCoords.put("PionNOIR", Arrays.asList(new GUICoord[] {new GUICoord(0,1), new GUICoord(1,1), new GUICoord(2,1), new GUICoord(3,1),
				new GUICoord(4,1), new GUICoord(5,1), new GUICoord(6,1), new GUICoord(7,1)}));
	}

	static {	
		mapPieceImage.put("TourBLANC", "tourBlancS.png");
		mapPieceImage.put("CavalierBLANC", "cavalierBlancS.png");
		mapPieceImage.put("FouBLANC",  "fouBlancS.png");
		mapPieceImage.put("ReineBLANC", "reineBlancS.png");
		mapPieceImage.put("RoiBLANC", "roiBlancS.png");
		mapPieceImage.put("PionBLANC", "pionBlancS.png");

		mapPieceImage.put("TourNOIR", "tourNoireS.png");
		mapPieceImage.put("CavalierNOIR", "cavalierNoirS.png");
		mapPieceImage.put("FouNOIR", "fouNoirS.png");
		mapPieceImage.put("ReineNOIR", "reineNoireS.png");
		mapPieceImage.put("RoiNOIR", "roiNoirS.png");
		mapPieceImage.put("PionNOIR", "pionNoirS.png")  ;
	}

	static {	
		mapPieceColor.put(Couleur.BLANC, Arrays.asList(new String[] {"TourBLANC","CavalierBLANC","FouBLANC","ReineBLANC","RoiBLANC","PionBLANC"}));
		mapPieceColor.put(Couleur.NOIR,Arrays.asList(new String[] {"TourNOIR","CavalierNOIR","FouNOIR","ReineNOIR","RoiNOIR","PionNOIR"}));
	}
	

	/**
	 * private pour ne pas instancier d'objets
	 */
	private ChessPieceGUIFactory() {

	}	

	public static JLabel createChessPieceGUI(GUICoord gUICoord) {
		JLabel chessPieceGUI = null;
		ImageIcon image = null;
		Couleur couleur = null;
		
		// Recherche type de la piece aux coordonnées coord
		Set<String> pieceTypeSet = mapPieceInitCoords.keySet();
		for (String pieceType : pieceTypeSet) {
			List<GUICoord> listCoords = mapPieceInitCoords.get(pieceType);
			if (listCoords!= null && listCoords.contains(gUICoord)) {
				
				// recheche de la couleur correspondante
				couleur = getCouleur(pieceType);
				
				// recherche du fichier de l'image correspondante et fabrication image
				image = createImage(pieceType);		
				
				// création pièce
				chessPieceGUI = new ChessPieceGUI( couleur,  image );
			}
		}
		
		return chessPieceGUI;
	}

	public static JLabel createChessPieceGUI(String promotionType, Couleur couleur) {
		return new ChessPieceGUI(couleur, createImage(promotionType + couleur.name()) );
	}

	private static ImageIcon createImage(String pieceType) {
		String pieceImageFile = null, nomImageFile = null;
		ImageIcon image = null;
	
		nomImageFile = mapPieceImage.get(pieceType);
		File g=new File("");
		pieceImageFile = g.getAbsolutePath()+"/images/" + nomImageFile;
		// System.out.println(pieceImageFile);
		image = new ImageIcon(pieceImageFile);
		return image;
	}
	
	private static Couleur getCouleur(String pieceType) {
		Couleur couleurPiece = null;
		
		Set<Couleur> couleurSet = mapPieceColor.keySet();
		for (Couleur couleur : couleurSet) {	
			List<String> listPieceType = mapPieceColor.get(couleur);
			if (listPieceType!= null && listPieceType.contains(pieceType)) {
				couleurPiece = couleur;
			}
		}
		return couleurPiece;
	}
	

	

	
}
