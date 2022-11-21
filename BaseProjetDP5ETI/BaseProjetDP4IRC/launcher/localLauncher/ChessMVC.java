package launcher.localLauncher;

/**
 * @author francoise.perrin
 * Lance l'exécution d'un jeu d'échec.
 *
 * les échanges entre la view et le model
 * passent par le contrôleur (ChessGameControler)
 * 
 * Un Builder est utilisé pour créer les différents composants de l'application
 * Un builder différent est utilisé selon que l'on soit en mode local
 * ou en mode client/server
 * 
 * Un Factory Method permet de déléguer au classes dérivées
 * la création du Builder adéquat
 * C'est juste pour l'exemple, car pas pertinent dans ce cas d'utilisation...
 * 
 */
public abstract class ChessMVC {

	/**
	 * Constructeur qui met en oeuvre Factory Method
	 * et suppose que la classe soit dérivée
	 */
	public ChessMVC() {
		super();
		ChessGameBuilder chessGameBuilder = this.createChessGameBuilder();
		chessGameBuilder.buildModel();	
		chessGameBuilder.buildControler();
		chessGameBuilder.buildView();	
		chessGameBuilder.setMediator();
		chessGameBuilder.go();
	}
	
	protected abstract ChessGameBuilder createChessGameBuilder() ;

}

