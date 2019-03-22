import javax.swing.JFrame;
/**
 * 
 * @author Ania
 *
 */
public class Main {
/**
 * Funkcja Main wywoluje konstruktor klasy Main, ktory tworzy okno programu
 * @param args domyslny argument
 */
	public static void main(String[] args) {
		new Main();
	}
	
	private JFrame window;
	private GamePlay gamePlay;

	private Main() {
		gamePlay = new GamePlay();
		window = new JFrame();

		window.setBounds(25, 20, 678, 569);
		window.setResizable(false);
		window.setTitle("Pacman Anna Makoœ");
		window.add(gamePlay);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.setVisible(true);

	}
}
