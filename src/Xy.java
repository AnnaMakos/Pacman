/**
 * 
 * @author Ania
 *
 */
public class Xy {
	/**
	 * Zmienna okreslajaca wspolrzedna obiektu klasy
	 */
	protected int x;
	protected int y;
	/**
	 * Flaga informujaca o mozliwosci przejscia w danym kierunku
	 */
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	/**
	 * Flaga informujaca o tym, czy dany obiekt jest na planszy jako kwadrat, przez
	 * ktory nie mozna przejsc
	 */
	protected boolean isSqaure = false;

	/**
	 * Konstruktor klasy Xy - przypisuje pobrane wartosci do elementow tworzonego
	 * obiektu - nadaje niektorym zmiennym wartosci domyslne
	 * 
	 * @param x
	 *            wspolrzedna x nowego obiektu
	 * @param y
	 *            wspolrzedna y nowego obiektu
	 */
	protected Xy(int x, int y) {
		this.x = x;
		this.y = y;
		left = right = up = down = true;
	}

}
