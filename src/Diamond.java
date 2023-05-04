import java.awt.Rectangle;

/**
 * 
 * @author Ania
 *
 */
public class Diamond {

	/**
	 * Zmienna okreslajaca element tablicy, w ktorym diament znajduje sie, badz nie
	 * - uzywana rowniez w klasie GamePlay
	 */
	protected int i, j;
	/**
	 * Flaga informujaca o tym, czy diament zostal zjedzony przez figurke gracza czy
	 * nie. Uzywana rowniez w klasie GamePlay
	 */
	protected boolean eaten;

	/**
	 * Konstruktor klasy Diamond - przypisuje przekazane wartosci do elementow
	 * obiektu - ustawia flage eaten
	 * 
	 * @param rect
	 *            prostokat, ktory zostal utworzony jako diament
	 * @param i
	 *            okresla element tablicy, do ktorej przypisany zostaje objekt klasy
	 *            Diament
	 * @param j
	 *            okresla element tablicy, do ktorej przypisany zostaje objekt klasy
	 *            Diament
	 */
	protected Diamond(int i, int j) {
		this.i = i;
		this.j = j;
		eaten = false;
	}
}
