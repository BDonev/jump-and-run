package bg.tusofia.fdiba.gamedev;

public class GameLauncher {

	/**
	 * Launches the game
	 * 
	 * @param args
	 *            Commandline args
	 */
	public static void main(String[] args) {
		Game game = Game.getInstance();
		game.start();
	}
}
