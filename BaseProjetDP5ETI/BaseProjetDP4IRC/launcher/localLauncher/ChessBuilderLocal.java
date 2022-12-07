package launcher.localLauncher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import controler.ChessGameControler;
import controler.ChessGameControlerModel;
import controler.ChessGameControlerView;
import controler.localControler.ChessControlerLocal;
import gui.ChessGUI;
import gui.ChessGameGUI;
import model.business.ChessGameModel;
import model.business.ChessModel;
import nutsAndBolts.ChessGUIConfig;
import nutsAndBolts.ChessModelConfig;
import nutsAndBolts.Couleur;

public class ChessBuilderLocal implements ChessGameBuilder {

	private ChessGameModel chessGameModel = null;
	private ChessGameControler chessGameControler = null;
	private ChessGameGUI chessGameGUI = null;
	
	public ChessBuilderLocal() {
		super();
		
		ChessModelConfig.newInstance(
				8, 							// nb lignes
				8, 							// nb colonnes
				Couleur.BLANC				// couleur du joueur qui débute la partie
				); 	
		
		ChessGUIConfig.newInstance(
				Couleur.BLANC,				// couleur joueur qui commence
				8, 							// nb lignes
				8, 							// nb colonnes
				new Color(139,69,0),  		// couleur des cases noires
				new Color(255,250,240), 	// couleur des cases blanches
				Color.GREEN,					// couleur mise en évidence cases destinations potentielles
				"Jeu d'échec",				// titre fenêtre
				new Dimension(1000, 800),	// dimension fenetre
				new Point(400, 10)			// localisation fenêtre
				);	
	}

	@Override
	public void buildModel() {
		
		this.chessGameModel = new ChessModel();
	}

	@Override
	public void buildControler() {
		
		this.chessGameControler = new ChessControlerLocal();
	}

	@Override
	public void buildView() {
		
		if (this.chessGameControler != null) {
			this.chessGameGUI = new ChessGUI(this.chessGameControler);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	// Initialisation de la view et du model avec lesquels le controler va communiquer
	// en fonction des actions effectuées sur le model, 
	// le controler invoquera des méthodes de la view pour qu'elle se mette à jour
	// et inversement
	//////////////////////////////////////////////////////////////////////////////

	@Override
	public void setMediator() {
		if(this.chessGameControler != null && 
				this.chessGameModel != null && 
				this.chessGameGUI != null) {
			
			((ChessGameControlerView) this.chessGameControler).setView(this.chessGameGUI);
			((ChessGameControlerModel) this.chessGameControler).setModel(this.chessGameModel);
		}
	}

	@Override
	public void go() {
		if (this.chessGameGUI != null) {
			((JFrame) this.chessGameGUI).setVisible(true); 
		}
	}

}
