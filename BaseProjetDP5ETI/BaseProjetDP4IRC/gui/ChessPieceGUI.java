package gui;



import javax.swing.ImageIcon;
import javax.swing.JLabel;

import nutsAndBolts.Couleur;

/**
 * @author francoise.perrin
 * 
 * Cette classe permet de donner une couleur et une image aux pièces
 *
 */
public class ChessPieceGUI extends JLabel {

	private static final long serialVersionUID = 1L;
	private Couleur couleur;

	public ChessPieceGUI(Couleur couleur, ImageIcon image ) {
		this.couleur = couleur;
		this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.setIcon(image);
	}

	public Couleur getCouleur() {
		return couleur;
	}

	@Override
	public String toString() {
		return "ChessPieceGUI [couleur=" + couleur + ", image=" + getIcon() + "]";
	}
	
}
