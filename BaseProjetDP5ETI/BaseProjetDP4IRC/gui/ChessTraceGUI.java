package gui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextArea;

class ChessTraceGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;

	ChessTraceGUI() {
		super();

		///////////////////////////////////////////////////////
		// texte d'affichage de la trace d'exécution à droite
		//////////////////////////////////////////////////////

		textArea = new JTextArea(50,10);
		textArea.setEditable(false);
		textArea.append("\t  Trace d'exécution" + "\n\n\n");
		textArea.setBackground(Color.WHITE);
		this.add(textArea);
	}

	void appendText(String text) {
		textArea.append(text + "\n");
		this.repaint();
		this.revalidate();
	}

}
