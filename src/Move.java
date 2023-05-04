import javax.swing.*;
import java.util.Random;
/**
 * 
 * @author Ania
 *
 */
public class Move extends Character {

	private final GamePlay gamePlay;
	private final Board b;
	private final Random rand;
	private long startTime;
	private Direction where = Direction.UP;

	/**
	 * Konstruktor klasy Move - Wywoluje konstruktor klasy Character, po ktorej
	 * dziedziczy - wywoluje konstruktory innych klas - ustawia wartosc zmiennej
	 * startTime
	 *
	 * @param s nazwa pliku png, w ktorym znajduje sie grafika przypisana do
	 *          obiektu klasy Move
	 * @param x wspolrzedna x poczatkowa obiektu
	 * @param y wpolrzedna y poczatkowa obiektu
	 */
	protected Move(ImageIcon s, int x, int y, GamePlay gamePlay) {
		super(s, x, y, gamePlay);
		this.gamePlay = gamePlay;
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
			where = Direction.values()[rand.nextInt(4)];
			startTime = System.currentTimeMillis();
		}

		if (where == Direction.LEFT) {
			if (ghostX > b.boardX) {
				ghostX -= chMoveSize;
			} else {
				where = Direction.RIGHT;
			}
		} else if (where == Direction.UP) {
			if (ghostY > b.boardY) {
				ghostY -= chMoveSize;
			} else {
				where = Direction.DOWN;
			}
		} else if (where == Direction.RIGHT) {
			if (ghostX < gamePlay.lastX) {
				ghostX += chMoveSize;
			} else {
				where = Direction.LEFT;
			}
		} else if (where == Direction.DOWN) {
			if (ghostY < gamePlay.lastY) {
				ghostY += chMoveSize;
			} else {
				where = Direction.UP;
			}
		}

	}

	private enum Direction {
		UP, DOWN, RIGHT, LEFT
	}
}
