package launcher.localLauncher;

/**
 * @author francoise.perrin
 * Lance l'exécution d'un jeu d'échec en mode local.
 *
 * les différents composants du jeu sont créés par un builder
 * 
 */
public class LauncherChessGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new ChessMVCLocal();
	}
}

