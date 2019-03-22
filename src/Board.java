import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

/**
 * 
 * @author Ania
 *
 */
public class Board implements ImageObserver {
	/**
	 * Rozmiar kwadratu skladajacego sie na plansze gry. Zmienna uzywana rowniez w
	 * klasie GamePlay i Character
	 */
	protected int boardSize = 50;
	/**
	 * Wspolrzedna poczatku planszy gry. Wykorzystywana rowniez w GamePlay
	 */
	protected int boardX = 11, boardY = 81;
	/**
	 * Tablica przechowuje elementy klasy Xy. Kazdy element opisuje kwadrat na
	 * "szachownicy" - planszy gry. Uzywana rowniez w klasie GamePlay.
	 */
	protected Xy[][] tab;
	/**
	 * Zmienna okreslajaca rozmiar tablicy tab. Wykorzystywana rowniez w klasie
	 * GamePlay.
	 */
	protected int a, b;
	private Image board;
	private ImageIcon boardFile;
	private Variable v;

	/**
	 * Konstruktor klasy Board - wczytuje plik z grafika kwadratu planszy - tworzy
	 * obiekt Variable - nadaje wartosci zmiennym a, b - inicjalizuje tablice tab
	 */
	protected Board() {
		boardFile = new ImageIcon("kwadrat.png");
		board = boardFile.getImage();
		v = new Variable();

		b = (int) (677 - v.xR) / boardSize;
		a = (int) (509 - v.yR) / boardSize;
		tab = new Xy[a][b];
		int bX = boardX;
		int bY = boardY;
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				tab[i][j] = new Xy(bX, bY);
				bX += boardSize;
			}
			bX = boardX;
			bY += boardSize;
		}
	}

	/**
	 * Funkcja rysujaca tablice, wywolywana w GamePlay
	 * 
	 * @param g
	 *            element klay Grafika, na ktorym rysowany jest obraz
	 * @param nr
	 *            numer planszy, wybrany przez uzytkownika na stronie startowej
	 */
	protected void paintBoard(Graphics g, int nr) {
		// LEWA LINIA
		for (int i = 0; i < a; i++) {
			tab[i][0].left = false;
		}
		// PRAWA LINIA
		for (int i = 0; i < a; i++) {
			tab[i][b - 1].right = false;
		}
		// GORNA LINIA
		for (int i = 0; i < b; i++) {
			tab[0][i].up = false;
		}
		// DOLNA LINIA
		for (int i = 0; i < b; i++) {
			tab[a - 1][i].down = false;
		}

		if (nr == 1) {
			// najpierw rzad, potem wiersz
			for (int i = 4; i < 10; i++) {
				insertS(0, i, g);
			}
			for (int i = 2; i < 6; i++) {
				insertS(i, 7, g);
			}
			for (int i = 2; i < 4; i++) {
				insertS(i, 9, g);
			}
			for (int i = 5; i < 9; i++) {
				insertS(i, 9, g);
			}
			for (int i = 1; i < 8; i++) {
				insertS(i, 11, g);
			}
			for (int i = 3; i < 7; i++) {
				insertS(8, i, g);
			}
			insertS(7, 7, g);
			for (int i = 2; i < 8; i++) {
				insertS(i, 1, g);
			}
			for (int i = 3; i < 8; i++) {
				insertS(5, i, g);
			}
			for (int i = 3; i < 5; i++) {
				insertS(6, i, g);
			}
			insertS(3, 4, g);
			insertS(2, 0, g);
			insertS(1, 1, g);
			insertS(2, 2, g);
			insertS(1, 2, g);
			insertS(2, 3, g);
			insertS(2, 4, g);
			insertS(2, 5, g);
			insertS(3, 3, g);
			insertS(3, 1, g);
		} else if (nr == 2) {
			for (int i = 2; i < 11; i++) {
				insertS(4, i, g);
			}
		}

	}

	private void insertS(int x, int y, Graphics g) {
		g.drawImage(board, tab[x][y].x, tab[x][y].y, boardSize, boardSize, this);
		if (y < b - 1) {
			tab[x][y + 1].left = false;
		}
		if (y > 0) {
			tab[x][y - 1].right = false;
		}
		if (x < a - 1) {
			tab[x + 1][y].up = false;
		}
		if (x > 0) {
			tab[x - 1][y].down = false;
		}
		tab[x][y].isSqaure = true;
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}
}
