import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Ania
 *
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener, MouseListener {

	private ImageIcon pacmanFileUp, pacmanFileDown, pacmanFileRight, pacmanFileLeft;
	private Image pacmanUp, pacmanDown, pacmanRight, pacmanLeft;
	private ImageIcon diamondFile, heartFile;
	private Image diamond, heart;
	private Timer timer;
	private int delay = 1;
	private int pacmanMoveSize;
	private Board board;
	private Variable v;
	private MyColor color;
	private Move moveG1, moveG2, moveG3, moveG4;
	private int pacmanSize, pacmanX, pacmanY;
	private boolean action = false;
	private boolean upCan = true, downCan = true, rightCan = true, leftCan = true;
	private boolean goY = true, goX = true;
	private boolean justGoLeft = false, justGoRight = false, justGoUp = false, justGoDown = false;
	private boolean wantLeft = false, wantRight = true, wantUp = false, wantDown = false;
	private int jj, ii;
	private float diamSize;
	private List<Diamond> diamonds;
	private List<Rectangle> diamondsRect;
	private int score, maxScore;
	private int notEatenNumber;
	/**
	 * Wspolrzedna ostatniego elementu tablicy, opisujacej "szachownice" na ekranie.
	 * Odczytuje ja tez klasa Move.
	 */
	protected int lastX = 611, lastY = 481;
	private int heartNumber = 3;
	private int mouseX, mouseY;
	private boolean start = false;
	/**
	 * Tablica mowiaca o tym, ktora z opcji przy ekranie startowym zostala wybrana
	 * przez uzytkownika. Wykorzystuje ja rowniez klasa Character.
	 */
	protected boolean[][] startClick;
	private boolean gameOver = false;
	private boolean won = false;
	private boolean[] first;
	private Ellipse2D[] ghostAction;
	private int ghostsNumber = 1;

	/**
	 * Konstruktor klasy GamePlay - tworzy timer - ustawia wartosci inicjujace dla
	 * klas implementowanych - wywoluje konstruktory zaimplementowanych klas -
	 * ustawia wartosci dla niektorych zmiennych - nadaje wartosci tablicom
	 */
	protected GamePlay() {

		addMouseListener((MouseListener) this);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();

		ghostAction = new Ellipse2D[4];

		diamonds = new ArrayList<Diamond>();
		diamondsRect = new ArrayList<Rectangle>();

		board = new Board();
		v = new Variable();
		color = new MyColor();

		diamSize = board.boardSize / 4;
		pacmanSize = board.boardSize;
		pacmanX = board.boardX;
		pacmanY = board.boardY;

		startClick = new boolean[3][4];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (j == 0) {
					startClick[i][j] = true;
				} else {
					startClick[i][j] = false;
				}
			}
		}

		first = new boolean[4];
		for (int i = 0; i < 4; i++) {
			first[i] = true;
		}

	}

	/**
	 * Sluzy do rysowania grafiki na ekranie
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Font font1 = new Font("Courier New", 1, 60);
		Font font2 = new Font("Courier New", 1, 50);
		Font font3 = new Font("Courier New", 1, 100);
		Font font4 = new Font("Courier New", 1, 180);

		// TLO NA ZEWNATRZ
		g.setColor(color.myLightPink);
		g.fillRect(0, 0, v.a, v.b);

		// WYPELNIENIE DUZEJ RAMKI
		g.setColor(color.myBlack);
		g.fillRect(10, 80, v.a - 20 - v.xR, v.b - 20 - v.yR - 70);

		// WYPELNIENIE MALEJ RAMKI
		g.setColor(color.myGreen);
		g.fillRect(10, 10, v.a - 20 - v.xR, 60);

		// RAMKI
		g.setColor(color.myPink);
		g.drawRect(10, 10, v.a - 20 - v.xR, 60);
		g.drawRect(10, 80, v.a - 20 - v.xR, v.b - 20 - v.yR - 70);

		if (start) {

			// PACMAN IKONA
			if (startClick[2][0]) {
				pacmanMoveSize = 1;
			} else if (startClick[2][1]) {
				pacmanMoveSize = 2;
			} else if (startClick[2][2]) {
				pacmanMoveSize = 5;
			}
			pacmanFileRight = new ImageIcon(getClass().getClassLoader().getResource("pacman_right.png"));
			pacmanFileLeft = new ImageIcon(getClass().getClassLoader().getResource("pacman_left.png"));
			pacmanFileDown = new ImageIcon(getClass().getClassLoader().getResource("pacman_down.png"));
			pacmanFileUp = new ImageIcon(getClass().getClassLoader().getResource("pacman_up.png"));
			pacmanUp = pacmanFileUp.getImage();
			pacmanDown = pacmanFileDown.getImage();
			pacmanRight = pacmanFileRight.getImage();
			pacmanLeft = pacmanFileLeft.getImage();
			if (wantRight) {
				g.drawImage(pacmanRight, pacmanX, pacmanY, pacmanSize, pacmanSize, this);
			} else if (wantLeft) {
				g.drawImage(pacmanLeft, pacmanX, pacmanY, pacmanSize, pacmanSize, this);
			} else if (wantDown) {
				g.drawImage(pacmanDown, pacmanX, pacmanY, pacmanSize, pacmanSize, this);
			} else if (wantUp) {
				g.drawImage(pacmanUp, pacmanX, pacmanY, pacmanSize, pacmanSize, this);
			}

			if (startClick[1][0]) {
				board.paintBoard(g, 1);
				maxScore = 68;
			} else {
				board.paintBoard(g, 2);
				maxScore = 9 * 13 - 9;
			}

			// GHOSTS
			if (first[0]) {
				ImageIcon ghost1 = new ImageIcon(getClass().getClassLoader().getResource("ghost1.png"));
				moveG1 = new Move(ghost1, 611, 481);
				first[0] = false;
			}
			g.drawImage(moveG1.ch, moveG1.ghostX, moveG1.ghostY, board.boardSize, board.boardSize, this);

			if (!startClick[0][0]) {
				if (first[1]) {
					moveG2 = new Move(new ImageIcon(getClass().getClassLoader().getResource("ghost2.png")), 50, 450);
					first[1] = false;
				}
				g.drawImage(moveG2.ch, moveG2.ghostX, moveG2.ghostY, board.boardSize, board.boardSize, this);
			}

			if (startClick[0][2] || startClick[0][3]) {
				if (first[2]) {
					moveG3 = new Move(new ImageIcon(getClass().getClassLoader().getResource("ghost3.png")), 550, 100);
					first[2] = false;
				}
				g.drawImage(moveG3.ch, moveG3.ghostX, moveG3.ghostY, board.boardSize, board.boardSize, this);
			}

			if (startClick[0][3]) {
				if (first[3]) {
					moveG4 = new Move(new ImageIcon(getClass().getClassLoader().getResource("ghost4.png")), 300, 300);
					first[3] = false;
				}
				g.drawImage(moveG4.ch, moveG4.ghostX, moveG4.ghostY, board.boardSize, board.boardSize, this);
			}

			// DIAMENTY
			diamondFile = new ImageIcon(getClass().getClassLoader().getResource("diamond.png"));
			diamond = diamondFile.getImage();
			for (int i = 0; i < board.a; i++) {
				for (int j = 0; j < board.b; j++) {
					if (!board.tab[i][j].isSqaure) {
						g.drawImage(diamond, board.tab[i][j].x + (int) (diamSize * 1.5),
								board.tab[i][j].y + (int) (diamSize * 1.5), (int) diamSize, (int) diamSize, this);
						diamonds.add(new Diamond(
								new Rectangle(board.tab[i][j].x + (int) (diamSize * 1.5),
										board.tab[i][j].y + (int) (diamSize * 1.5), (int) diamSize, (int) diamSize),
								i, j));
						diamondsRect.add(new Rectangle(board.tab[i][j].x + (int) (diamSize * 1.5),
								board.tab[i][j].y + (int) (diamSize * 1.5), (int) diamSize, (int) diamSize));
						score++;
						notEatenNumber++;
					}
				}
			}

			// PUNKTY
			score = maxScore - notEatenNumber;
			notEatenNumber = 0;

			g.setColor(color.myLightPink);
			g.setFont(font2);
			g.drawString("WYNIK: " + score + "/" + maxScore, 220, 55);

			// SERCA -ZYCIA
			heartFile = new ImageIcon(getClass().getClassLoader().getResource("serce.png"));
			heart = heartFile.getImage();
			for (int i = 0; i < heartNumber; i++) {
				g.drawImage(heart, 55 * i + 15, 15, 50, 50, this);
			}

			// GAME OVER
			if (gameOver) {
				g.setFont(font4);
				g.setColor(color.myLightPink);
				g.drawString("KONIEC", 10, 300);
				g.drawString("GRY", 180, 410);
				g.setFont(font3);
				g.drawString("przegra³eœ", 50, 490);
			}

			if (won) {
				g.setFont(font4);
				g.setColor(color.myLightPink);
				g.drawString("BRAWO", 80, 300);
				g.setFont(font3);
				g.drawString("wygra³eœ", 100, 410);
			}

		} else {

			g.setColor(color.myPink);
			g.setFont(font1);
			g.drawString("PACMAN ANNA MAKOŒ", 40, 63);

			g.setFont(font2);

			g.setColor(color.myLightPink);
			g.drawString("Wybierz iloœæ wrogów", 30, 125);

			if (startClick[0][0]) {
				g.setColor(color.myLightPink);
				g.fillRect(50, 150, 80, 80);
				g.setColor(color.myPink);
				g.drawString("1", 75, 205);
			} else {
				g.setColor(color.myPink);
				g.fillRect(50, 150, 80, 80);
				g.setColor(color.myLightPink);
				g.drawString("1", 75, 205);
			}

			if (startClick[0][1]) {
				g.setColor(color.myLightPink);
				g.fillRect(140, 150, 80, 80);
				g.setColor(color.myPink);
				g.drawString("2", 165, 205);
			} else {
				g.setColor(color.myPink);
				g.fillRect(140, 150, 80, 80);
				g.setColor(color.myLightPink);
				g.drawString("2", 165, 205);
			}

			if (startClick[0][2]) {
				g.setColor(color.myLightPink);
				g.fillRect(230, 150, 80, 80);
				g.setColor(color.myPink);
				g.drawString("3", 255, 205);
			} else {
				g.setColor(color.myPink);
				g.fillRect(230, 150, 80, 80);
				g.setColor(color.myLightPink);
				g.drawString("3", 255, 205);
			}

			if (startClick[0][3]) {
				g.setColor(color.myLightPink);
				g.fillRect(320, 150, 80, 80);
				g.setColor(color.myPink);
				g.drawString("4", 345, 205);
			} else {
				g.setColor(color.myPink);
				g.fillRect(320, 150, 80, 80);
				g.setColor(color.myLightPink);
				g.drawString("4", 345, 205);
			}

			g.setColor(color.myLightPink);
			g.drawString("Wybierz numer planszy", 30, 275); // y + 150

			if (startClick[1][0]) {
				g.setColor(color.myLightPink);
				g.fillRect(50, 300, 80, 80);
				g.setColor(color.myPink);
				g.drawString("1", 75, 355);
			} else {
				g.setColor(color.myPink);
				g.fillRect(50, 300, 80, 80);
				g.setColor(color.myLightPink);
				g.drawString("1", 75, 355);
			}

			if (startClick[1][1]) {
				g.setColor(color.myLightPink);
				g.fillRect(140, 300, 80, 80);
				g.setColor(color.myPink);
				g.drawString("2", 165, 355);
			} else {
				g.setColor(color.myPink);
				g.fillRect(140, 300, 80, 80);
				g.setColor(color.myLightPink);
				g.drawString("2", 165, 355);
			}

			g.setColor(color.myLightPink);
			g.drawString("Wybierz tempo", 30, 425);

			if (startClick[2][0]) {
				g.setColor(color.myLightPink);
				g.fillRect(50, 450, 80, 80);
				g.setColor(color.myPink);
				g.drawString("1", 75, 505);
			} else {
				g.setColor(color.myPink);
				g.fillRect(50, 450, 80, 80);
				g.setColor(color.myLightPink);
				g.drawString("1", 75, 505);
			}

			if (startClick[2][1]) {
				g.setColor(color.myLightPink);
				g.fillRect(140, 450, 80, 80);
				g.setColor(color.myPink);
				g.drawString("2", 165, 505);
			} else {
				g.setColor(color.myPink);
				g.fillRect(140, 450, 80, 80);
				g.setColor(color.myLightPink);
				g.drawString("2", 165, 505);
			}

			if (startClick[2][2]) {
				g.setColor(color.myLightPink);
				g.fillRect(230, 450, 80, 80);
				g.setColor(color.myPink);
				g.drawString("3", 255, 505);
			} else {
				g.setColor(color.myPink);
				g.fillRect(230, 450, 80, 80);
				g.setColor(color.myLightPink);
				g.drawString("3", 255, 505);
			}

			g.setFont(font3);
			g.setColor(color.myLightPink);
			g.fillRect(445, 320, 200, 200);
			g.setColor(color.myPink);
			g.drawString("PL", 485, 420);
			g.drawString("AY", 485, 480);
		}
	}

	/**
	 * Funkcja regulujaca prace programu w zaleznosci od wcisnietego klawisza na
	 * klawiaturze
	 */
	@Override
	public void keyPressed(KeyEvent k) {
		if (start) {
			if (k.getKeyCode() == KeyEvent.VK_UP) {
				action = true;
				wantUp = true;
				wantDown = wantRight = wantLeft = false;
			}
			if (k.getKeyCode() == KeyEvent.VK_DOWN) {
				action = true;
				wantDown = true;
				wantUp = wantRight = wantLeft = false;
			}
			if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
				action = true;
				wantRight = true;
				wantDown = wantUp = wantLeft = false;
			}
			if (k.getKeyCode() == KeyEvent.VK_LEFT) {
				action = true;
				wantLeft = true;
				wantDown = wantRight = wantUp = false;
			}
			if (k.getKeyCode() == KeyEvent.VK_SPACE) {
				action = false;
			}
		}

	}

	private void move() {
		if ((justGoLeft || (wantLeft && goX)) && leftCan) {
			goX = true;
			rightCan = true;
			pacmanX -= pacmanMoveSize;
			justGoLeft = true;
			justGoRight = justGoUp = justGoDown = false;
			if (wantRight && rightCan) {
				justGoLeft = false;
				justGoRight = true;
			}
		}

		if ((justGoRight || (wantRight && goX)) && rightCan) {
			goX = true;
			leftCan = true;
			pacmanX += pacmanMoveSize;
			justGoRight = true;
			justGoLeft = justGoUp = justGoDown = false;
			if (wantLeft && leftCan) {
				justGoRight = false;
				justGoLeft = true;
			}
		}

		if ((justGoUp || (wantUp && goY)) && upCan) {
			goY = true;
			downCan = true;
			pacmanY -= pacmanMoveSize;
			justGoUp = true;
			justGoRight = justGoLeft = justGoDown = false;
			if (wantDown && downCan) {
				justGoUp = false;
				justGoDown = true;
			}
		}

		if ((justGoDown || (wantDown && goY)) && downCan) {
			goY = true;
			upCan = true;
			pacmanY += pacmanMoveSize;
			justGoDown = true;
			justGoRight = justGoUp = justGoLeft = false;
			if (wantUp && upCan) {
				justGoDown = false;
				justGoUp = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		;// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		;// TODO Auto-generated method stub

	}

	/**
	 * Funkcja wywolujaca funkcje rysujaca obraz oraz wszelki ruch w programie. -
	 * Opisuje zaleznosci miedzy elementami, miedzy innymi zderzenia.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (action) {
			repaint();

			move();

			// PACMAN
			for (int j = 0; j < board.b; j++) {
				if (pacmanX == board.tab[0][j].x) {
					goY = true;
					jj = j;
					j = board.b;
				} else {
					goY = false;
				}
			}

			for (int i = 0; i < board.a; i++) {
				if (pacmanY == board.tab[i][0].y) {
					goX = true;
					ii = i;
					i = board.a;
				} else {
					goX = false;
				}
			}

			downCan = board.tab[ii][jj].down;
			upCan = board.tab[ii][jj].up;
			rightCan = board.tab[ii][jj].right;
			leftCan = board.tab[ii][jj].left;

			Rectangle pacmanAction = new Rectangle(pacmanX, pacmanY, pacmanSize, pacmanSize);

			// DIAMENTY
			for (int i = 0; i < diamonds.size(); i++) {
				if (pacmanAction.intersects(diamondsRect.get(i)) && !diamonds.get(i).eaten) {
					board.tab[diamonds.get(i).i][diamonds.get(i).j].isSqaure = true;
					diamonds.get(i).eaten = true;
				}
			}

			// DUCHY
			moveG1.moving();
			ghostAction[0] = new Ellipse2D.Float(moveG1.ghostX, moveG1.ghostY, board.boardSize, board.boardSize);

			if (!startClick[0][0]) {
				moveG2.moving();
				ghostAction[1] = new Ellipse2D.Float(moveG2.ghostX, moveG2.ghostY, board.boardSize, board.boardSize);
				ghostsNumber = 2;
			}
			if (startClick[0][2] || startClick[0][3]) {
				moveG3.moving();
				ghostAction[2] = new Ellipse2D.Float(moveG3.ghostX, moveG3.ghostY, board.boardSize, board.boardSize);
				ghostsNumber = 3;
			}
			if (startClick[0][3]) {
				moveG4.moving();
				ghostAction[3] = new Ellipse2D.Float(moveG4.ghostX, moveG4.ghostY, board.boardSize, board.boardSize);
				ghostsNumber = 4;
			}
			for (int i = 0; i < ghostsNumber; i++) {
				if (ghostAction[i].intersects(pacmanAction)) {
					heartNumber--;
					action = false;
					pacmanX = board.boardX;
					pacmanY = board.boardY;
					wantRight = true;
					wantLeft = wantDown = wantUp = false;
					upCan = leftCan = false;
					if (heartNumber == 0) {
						gameOver = true;
						action = false;
					}
				}
			}

			// WYGRANA
			if (score == maxScore) {
				action = false;
				won = true;
			}

		}

	}

	/**
	 * Funkcja odbierajaca klikniecia myszka - w zaleznosci od opcji wybranej przez
	 * uzytkownika na ekranie, ustawia odpowiednie wartosci
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

		// PYTANIE O ILOSC WROGOW
		if (mouseX >= 50 && mouseX <= 130 && mouseY >= 150 && mouseY <= 230) {
			startClick[0][0] = true;
			startClick[0][1] = startClick[0][2] = startClick[0][3] = false;
		}
		if (mouseX >= 140 && mouseX <= 220 && mouseY >= 80 && mouseY <= 230) {
			startClick[0][1] = true;
			startClick[0][0] = startClick[0][2] = startClick[0][3] = false;
		}
		if (mouseX >= 230 && mouseX <= 310 && mouseY >= 80 && mouseY <= 230) {
			startClick[0][2] = true;
			startClick[0][1] = startClick[0][0] = startClick[0][3] = false;
		}
		if (mouseX >= 320 && mouseX <= 400 && mouseY >= 80 && mouseY <= 230) {
			startClick[0][3] = true;
			startClick[0][1] = startClick[0][2] = startClick[0][0] = false;
		}

		// PYTANIE O NUMER PLANSZY
		if (mouseX >= 50 && mouseX <= 130 && mouseY >= 300 && mouseY <= 380) {
			startClick[1][0] = true;
			startClick[1][1] = false;
		}
		if (mouseX >= 140 && mouseX <= 220 && mouseY >= 300 && mouseY <= 380) {
			startClick[1][1] = true;
			startClick[1][0] = false;
		}

		// PYTANIE O TEMPO
		if (mouseX >= 50 && mouseX <= 130 && mouseY >= 450 && mouseY <= 530) {
			startClick[2][0] = true;
			startClick[2][1] = startClick[2][2] = false;
		}
		if (mouseX >= 140 && mouseX <= 220 && mouseY >= 450 && mouseY <= 530) {
			startClick[2][1] = true;
			startClick[2][0] = startClick[2][2] = false;
		}
		if (mouseX >= 230 && mouseX <= 310 && mouseY >= 450 && mouseY <= 530) {
			startClick[2][2] = true;
			startClick[2][1] = startClick[2][0] = false;
		}

		// START
		if (mouseX >= 445 && mouseX <= 645 && mouseY >= 320 && mouseY <= 520) {
			start = true;
		}

		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
