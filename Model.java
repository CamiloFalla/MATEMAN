package pacman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Model extends JPanel implements ActionListener {
	
	private Dimension d;
    private final Font smallFont = new Font("Arial", Font.BOLD, 14);
    private final Font bigFont = new Font("Arial", Font.PLAIN, 24);
    private boolean inGame = false;
    private boolean dying = false;
    private boolean calculo = false;
    private boolean freeze = false;
	//Dimensiones del juego
	private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int MAX_ALIENS = 8;
    private final int MATEMAN_SPEED = 3;
	
	//Aliens rutinas
	private int N_ALIENS = 4;
    private int vidas, puntaje;
    private int[] dx, dy;
    private int[] alien_x, alien_y, alien_dx, alien_dy, alienSpeed;
	
	//Imagenes del juego
    private Image vida, alien, sleep;
    private Image suma, resta, multi, pregunta;
    private Image up, down, left, right;
	
	//Movimientos
	private int mateman_x, mateman_y, matemand_x, matemand_y;
    private int req_dx, req_dy;
	
	
	// Nomenclatura de tablero
	//0=azul no se permite avanzar en el
	//4=borde derecho
	//1=Borde izquierdo
	//8=Borde inferior
	//2=Borde Superior
	//16=Espacio para caminar de Pacman
	
    
    private final short  levelData[]= {
			19,26,26,26,26,26,18,26,18,26,26,26,26,26,22,
			21,0,0,0,0,0,21,0,21,0,0,0,0,0,21,
			17,18,26,18,26,18,20,0,17,18,26,18,26,18,20,
			17,20,0,21,0,17,20,0,17,20,0,21,0,17,20,
			17,20,0,21,0,25,20,0,17,28,0,21,0,17,20,
			17,20,0,21,0,0,17,18,20,0,0,21,0,17,20,
			17,20,0,17,18,26,16,16,16,26,18,20,0,17,20,
			17,16,18,16,20,0,17,16,20,0,17,16,18,16,20,
			17,24,16,16,20,0,17,16,20,0,17,16,16,24,20,
			21,0,17,16,20,0,17,24,20,0,17,16,20,0,21,
			21,0,17,24,20,0,21,0,21,0,17,24,20,0,21,
			21,0,21,0,17,18,20,0,17,18,20,0,21,0,21,
			21,0,21,0,25,24,16,18,16,24,28,0,21,0,21,
			17,18,20,0,0,0,17,16,20,0,0,0,17,18,20,
			25,24,24,26,26,26,24,24,24,26,26,26,24,24,28
	};
	
	//Parametros
	private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 4;

    private int currentSpeed = 1;
    private short[] screenData;
    private Timer timer;
		
	
    public Model() {

        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
    }
	//Carga Imagenes, debemos validar ruta de lugar de almacenamiento
	private void loadImages() {
		down=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/down.gif").getImage();
		up=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/up.gif").getImage();
		left=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/left.gif").getImage();
		right=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/right.gif").getImage();
		alien=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/alien.gif").getImage();
		vida=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/mateman.png").getImage();
		suma=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/suma.png").getImage();
		resta=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/resta.png").getImage();
		multi=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/multi.png").getImage();
		sleep=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/sleep.gif").getImage();
		pregunta=new ImageIcon("/Users/camilofalla/Desktop/Mateman/Images/images/pregunta.png").getImage();
	}
	private void initVariables() {
		
		screenData = new short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(600, 900);
        alien_x = new int[MAX_ALIENS];
        alien_dx = new int[MAX_ALIENS];
        alien_y = new int[MAX_ALIENS];
        alien_dy = new int[MAX_ALIENS];
        alienSpeed = new int[MAX_ALIENS];
        dx = new int[4];
        dy = new int[4];
        
        timer = new Timer(40, this);
        timer.start();
    }
	
	private void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else {

            
        	moveMateman();
            drawMateman(g2d);
            moveAliens(g2d);
            checkMaze();
        }
    }
	
	//MODO PAUSA O CONFIGURACION DE JUEGO
	private void showIntroScreen(Graphics2D g2d) {
		 
    	String start = "Presione Espacio para Iniciar";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (SCREEN_SIZE)/2+20, 480);
    }
	
	private void drawPuntaje(Graphics2D g) {
        g.setFont(smallFont);
        g.setColor(new Color(5, 181, 79));
        String s = "Puntaje: " + puntaje;
        g.drawString(s, SCREEN_SIZE / 2 + 200, SCREEN_SIZE + 146);

        for (int i = 0; i < vidas; i++) {
            g.drawImage(vida, i * 28 + 128, SCREEN_SIZE + 130, this);
        }
    }
	
	private void checkMaze() {

        int i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i]) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            puntaje += 50;

            if (N_ALIENS < MAX_ALIENS) {
                N_ALIENS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }
	
	private void death() {

    	vidas--;

        if (vidas == 0) {
            inGame = false;
        }

        continueLevel();
    }
	// Configuración para movimiento de los fantasmas
	private void moveAliens(Graphics2D g2d) {

        int pos;
        int count;

        for (int i = 0; i < N_ALIENS; i++) {
            if (alien_x[i] % BLOCK_SIZE == 0 && alien_y[i] % BLOCK_SIZE == 0) {
                pos = alien_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (alien_y[i] / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && alien_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && alien_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && alien_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && alien_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        alien_dx[i] = 0;
                        alien_dy[i] = 0;
                    } else {
                        alien_dx[i] = -alien_dx[i];
                        alien_dy[i] = -alien_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    alien_dx[i] = dx[count];
                    alien_dy[i] = dy[count];
                }

            }

            alien_x[i] = alien_x[i] + (alien_dx[i] * alienSpeed[i]);
            alien_y[i] = alien_y[i] + (alien_dy[i] * alienSpeed[i]);
            if (freeze) {
            	drawSleep(g2d, alien_x[i] + 1, alien_y[i] + 1);
            }else {
            	drawAlien(g2d, alien_x[i] + 1, alien_y[i] + 1);
            }
            
            

            if (mateman_x > (alien_x[i] - 12) && mateman_x < (alien_x[i] + 12)
                    && mateman_y > (alien_y[i] - 12) && mateman_y < (alien_y[i] + 12)
                    && inGame) {

                dying = true;
            }
        }
    }
	
	private void drawAlien(Graphics2D g2d, int x, int y) {
    	g2d.drawImage(alien, x+120, y+100, this);
    }
	
	private void drawSleep(Graphics2D g2d, int x, int y) {
    	g2d.drawImage(sleep, x+120, y+100, this);
    }
	//MATEMAN Moviendose
	
	private void moveMateman() {

        int pos;
        short ch;

        if (mateman_x % BLOCK_SIZE == 0 && mateman_y % BLOCK_SIZE == 0) {
            pos = mateman_x / BLOCK_SIZE + N_BLOCKS * (int) (mateman_y / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                puntaje++;
            }
            if (calculo) {
            	if (pos == 112) {
            		calculo=false;
            		freeze=true;
            		puntaje = puntaje +1000;
            	}
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    matemand_x = req_dx;
                    matemand_y = req_dy;
                }
            }

            // Check for standstill
            if ((matemand_x == -1 && matemand_y == 0 && (ch & 1) != 0)
                    || (matemand_x == 1 && matemand_y == 0 && (ch & 4) != 0)
                    || (matemand_x == 0 && matemand_y == -1 && (ch & 2) != 0)
                    || (matemand_x == 0 && matemand_y == 1 && (ch & 8) != 0)) {
                matemand_x = 0;
                matemand_y = 0;
            }
        } 
        mateman_x = mateman_x + MATEMAN_SPEED * matemand_x;
        mateman_y = mateman_y + MATEMAN_SPEED * matemand_y;
    }
	
	
	//Configurando la salida del PACMAN O COMO SE VE MOVIENDOSE
	
	
    private void drawMateman(Graphics2D g2d) {
    	int z=100;
    	int w=120;
        if (req_dx == -1) {
        	g2d.drawImage(left, mateman_x + 1+w, mateman_y + 1+z, this);
        } else if (req_dx == 1) {
        	g2d.drawImage(right, mateman_x + 1+w, mateman_y + 1+z, this);
        } else if (req_dy == -1) {
        	g2d.drawImage(up, mateman_x + 1+w, mateman_y + 1+z, this);
        } else {
        	g2d.drawImage(down, mateman_x + 1+w, mateman_y + 1+z, this);
        }
    }
    //Configuracion Tablero
    private void dibujaCalculo(Graphics g2d) {
    	
    	g2d.setFont(bigFont);
    	
        g2d.setColor(new Color(192, 192, 192));
        String s = "MATEMAN APRENDE JUGANDO";
        g2d.drawString(s, SCREEN_SIZE / 2 -60,  40);
        
        g2d.setFont(smallFont);
    	
        g2d.setColor(new Color(255, 0, 255));
        String p = "Oprime Enter para Ejercicio";
        g2d.drawString(p, SCREEN_SIZE / 2 -40,  70);

    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y, z, w;
        w=120;
        z=100;
        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(new Color(0,72,251));
                g2d.setStroke(new BasicStroke(5));
                
                if ((levelData[i] == 0)) { 
                	g2d.fillRect(x+w, y + z, BLOCK_SIZE, BLOCK_SIZE);
                 }

                if ((screenData[i] & 1) != 0) { 
                    g2d.drawLine(x+w, y+z, x+w, y+z + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) { 
                    g2d.drawLine(x+w, y+z, x+w + BLOCK_SIZE - 1, y+z);
                }

                if ((screenData[i] & 4) != 0) { 
                    g2d.drawLine(x +w+ BLOCK_SIZE - 1, y+z, x+w + BLOCK_SIZE - 1,
                            y +z+ BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) { 
                    g2d.drawLine(x+w, y+z + BLOCK_SIZE - 1, x+w+ BLOCK_SIZE - 1,
                            y +z+ BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) { 
                    g2d.setColor(new Color(255,255,255));
                    g2d.fillOval(x +w+ 10, y+z + 10, 6, 6);
               }

                i++;
            }
        }
    }
    
    private void drawSigno(Graphics2D g2d, int x, int y) {
    	g2d.drawImage(pregunta, x+120, y+500, this);
    }
    private void cargarEjercicio(Graphics2D g2d) {
    	short i = 0;
        int x, y, z, w;
        w=290;
        z=270;
        
        g2d.drawImage(pregunta, w, z, this);
        
        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {
            	
            }
        }
  
    }

	//Condiciones initGame para iniciar juego
    private void initGame() {

    	vidas = 3;
        puntaje = 0;
        initLevel();
        N_ALIENS = 4;
        currentSpeed = 2;
        calculo=false;
        freeze=false;
    }
    
    private void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
    }
	
	
    private void continueLevel() {

    	int dx = 1;
        int random;

        for (int i = 0; i < N_ALIENS; i++) {

            alien_y[i] = 4 * BLOCK_SIZE; //start position
            alien_x[i] = 4 * BLOCK_SIZE;
            alien_dy[i] = 0;
            alien_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            alienSpeed[i] = validSpeeds[random];
        }

        mateman_x = 7 * BLOCK_SIZE;  //start position
        mateman_y = 11 * BLOCK_SIZE;
        matemand_x = 0;	//reset direction move
        matemand_y = 0;
        req_dx = 0;		// reset direction controls
        req_dy = 0;
        dying = false;
    }

	//Arreglo de componente de Paint
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        dibujaCalculo(g2d);
        drawMaze(g2d);
        drawPuntaje(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }
        if (calculo){
        	cargarEjercicio(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
	
	///Inicia las teclas para mover el PACMAN
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_ENTER && timer.isRunning()) {
                	calculo = true;
                	freeze = false;
                }
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    initGame();
                }
            }
        }
    }
	
	
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
}
