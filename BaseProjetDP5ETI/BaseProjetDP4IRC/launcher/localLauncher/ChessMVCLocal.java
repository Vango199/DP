package launcher.localLauncher;

public class ChessMVCLocal extends ChessMVC {
	
	
	public ChessMVCLocal() {
		super();
	}

	protected ChessGameBuilder createChessGameBuilder() {
		
		return new ChessBuilderLocal();
	}
}
