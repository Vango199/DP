package launcher.localLauncher;

/**
 * @author francoiseperrin
 *
 * Cette interface défini le comportement attendu des 
 * Buider de jeu d'échec
 * utilisables en mode local et en mode client/server
 * 
 * certaines méthodes pouvant dans certains cas ne rien faire
 * (pas de model pour les clients, pas de vue pour le server)
 * pour être conforme avec l'architecture des controler, on aurait
 * dû dériver cette interface en 2 sous interfaces, mais bon ...
 */
public interface ChessGameBuilder {
	
	public void buildModel() ;
	
	public void buildView() ;
	
	public void buildControler() ;

	public void setMediator() ;

	public void go();

}
