import java.util.Random;
/**
 * 
 * @author Ania
 *
 */
public class Move extends Character {

	private Board b;
	private Random rand;
	private int where;
	private long startTime;
	private boolean up = false, down = false, right = false, left = true;

	/**
	 * Konstruktor klasy Move - Wywoluje konstruktor klasy Character, po ktorej
	 * dziedziczy - wywoluje konstruktory innych klas - ustawia wartosc zmiennej
	 * startTime
	 * 
	 * @param s
	 *            nazwa pliku png, w ktorym znajduje sie grafika przypisana do
	 *            obiektu klasy Move
	 * @param x
	 *            wspolrzedna x poczatkowa obiektu
	 * @param y
	 *            wpolrzedna y poczatkowa obiektu
	 */
	protected Move(String s, int x, int y) {
		super(s, x, y);
		rand = new Random();
		b = new Board();
		startTime = System.currentTimeMillis();
	}

	/**
	 * Funkcja regulujaca ruch elementu klasy Character - wywolywana w klasie
	 * GamePlay
	 */
	protected void moving() {

		if (System.currentTimeMillis() > startTime + moveDelay) {
			where = rand.nextInt(5);
			startTime = System.currentTimeMillis();
		}

		if (where == 0) { // up
			up = true;
			down = right = left = false;
		} else if (where == 1) { // down
			down = true;
			up = right = left = false;
		} else if (where == 2) { // right
			right = true;
			down = up = left = false;
		} else if (where == 3) { // left
			left = true;
			down = up = right = false;
		} // where == 4 - no change

		if (left) {
			if (ghostX > b.boardX) {
				ghostX -= chMoveSize;
			} else {
				left = false;
				right = true;
			}
		} else if (up) {
			if (ghostY > b.boardY) {
				ghostY -= chMoveSize;
			} else {
				up = false;
				down = true;
			}
		} else if (right) {
			if (ghostX < lastX) {
				ghostX += chMoveSize;
			} else {
				right = false;
				left = true;
			}
		} else if (down) {
			if (ghostY < lastY) {
				ghostY += chMoveSize;
			} else {
				down = false;
				up = true;
			}
		}

	}
}
