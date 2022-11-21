package gui;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import nutsAndBolts.ChessGUIConfig;
import nutsAndBolts.GUICoord;

public class ChessSquareGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private Color color;
	private Color colorInit;
	private GUICoord gUICoord;

	public ChessSquareGUI(Color color, GUICoord gUICoord) {
		this.color = color;
		this.colorInit = this.color;
		this.gUICoord = gUICoord;
		
		this.setLayout(new BorderLayout());
		
		this.setBorder(BorderFactory.createLineBorder(ChessGUIConfig.getBlackSquareColor(), 1, false));
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(this.color);

		// définir un motif dégradé pour la couleur de la pièce
		Paint paint = new GradientPaint(0,0, getBackground(), getWidth(), getHeight(), Color.WHITE);
		((Graphics2D) g).setPaint(paint);
		
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	/**
	 * @param isLight
	 * positionne la couleur en fonction du booléen
	 */
	public void resetColor(boolean isLight) {
		Color colorLight = ChessGUIConfig.getLightColor();
		this.color = isLight ? colorLight : this.colorInit;
		repaint();
	}

	/**
	 * @return the coord
	 */
	public GUICoord getCoord() {
		return gUICoord;
	}
}
