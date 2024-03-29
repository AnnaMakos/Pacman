import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * 
 * @author Ania
 *
 */
public class Character {
	/**
	 * Ilosc pikseli, o ktore zwikesza sie lub zmniejsza polozenie obiektu - uzywane
	 * rowniez w klasie Move
	 */
	protected int chMoveSize;
	/**
	 * Grafika obiektu Character - uzywana rowniez w klasie GamePlay
	 */
	protected Image ch;
	/**
	 * Zmienna okreslajaca polozenie obiektu - uzywana rowniez w klasach GamePlay i
	 * Move
	 */
	protected int ghostX;
	protected int ghostY;
	/**
	 * Czas, po jakim zostanie wylosowany kolejny ruch obiektu klasy Character -
	 * wykorzystywany rowniez w klasie Move
	 */
	protected int moveDelay;

	/**
	 * Konstruktor klasy Character - wywoluje konstruktory klas - przypisuje
	 * wartosci pobrane do elementow obiektu - w zaleznosci od wartosci tablicy
	 * startClick z klasy GamePlay, po ktorej klasa Character dziedziczy, nadaje
	 * wartosci niektorym elementom obiektu
	 * 
	 * @param s
	 *            nazwa grafiki, ktora ma byc przypisana do obiektu
	 * @param x
	 *            wspolrzedna x poczatkowa obiektu
	 * @param y
	 *            wspolrzedna y poczatkowa obiektu
	 */
	protected Character(ImageIcon s, int x, int y, GamePlay gamePlay) {
		ch = s.getImage();
		this.ghostX = x;
		this.ghostY = y;

		if (gamePlay.startClick[2][0]) {
			chMoveSize = 1;
			moveDelay = 3000;
		} else if (gamePlay.startClick[2][1]) {
			chMoveSize = 2;
			moveDelay = 2000;
		} else if (gamePlay.startClick[2][2]) {
			chMoveSize = 5;
			moveDelay = 1000;
		}
	}
}
