package matemanpck;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class tablero extends JPanel implements ActionListener {
	
	//Variables de Tablero
	private Dimension d;
	private final Font smallFont = new Font("Arial", Font.BOLD, 14);
	private final Font bigFont = new Font("Arial", Font.PLAIN, 24);
	private final int BLOCK_SIZE = 24;
	private final int N_BLOCKS = 15;
	private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
	private short[] screenData;
	
	//Imagenes del juego
    private Image vida, alien, sleep;
    private Image suma, resta, multi, pregunta;
    private Image up, down, left, right;
    
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
    // Centrado de dibujo
  	
  	static int z = 100;
  	static int w = 120;
  	private String operador1resuelto, operador2resuelto, respuestaresuelto, signoresuelto;
	//Variales de Trabajo
	
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
    
    private final List<Integer> tabdispon =Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,
			15,21,23,29,30,31,32,33,34,35,36,38,39,
			40,41,42,43,44,45,46,48,50,51,53,54,56
			,58,59,60,61,63,65,66,68,69,71,73,74,75,
			76,78,81,82,83,86,88,89,90,91,93,94,95,
			96,97,98,99,100,101,103,104,105,106,108,
			109,111,112,113,115,116,118,119,120,121,
			122,123,124,126,127,128,130,131,132,133,134,
			135,137,138,139,141,142,143,145,146,147,149,
			150,152,153,154,156,158,160,161,162,164,
			165,167,169,170,171,173,174,175,177,179,
			180,182,184,185,186,187,188,189,190,192,
			194,195,196,197,201,202,203,207,208,209,
			210,211,212,213,214,215,216,217,218,219,
			220,221,222,223,224);
    
	//Ubicacione de Alien Despierto
	private void drawAlien(Graphics2D g2d, int x, int y) {
    	g2d.drawImage(alien, x+120, y+100, this);
    }
	
	//Ubicacion de Alien Dormido
	private void drawSleep(Graphics2D g2d, int x, int y) {
    	g2d.drawImage(sleep, x+120, y+100, this);
    }

	// Iniciacion de Variables
	private void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else if(nivpre<10){

            
        	moveMateman();
            drawMateman(g2d);
            moveAliens(g2d);
            checkMaze();
         }else {
        	 initGame();
         }
    }
	 
	//MODO PAUSA O CONFIGURACION DE JUEGO
	private void showIntroScreen(Graphics2D g2d) {
		 
    	String start = "Presione Espacio para Iniciar";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (SCREEN_SIZE)/2+20, 480);
    }
	private void showendScreen(Graphics2D g2d) {
		 
    	String Finish = "HAS TERMINADO EL JUEGO VUELVE A EMPEZAR";
        g2d.setColor(Color.yellow);
        g2d.drawString(Finish, 180, 500);
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
	// con esto podemos generar graficos en 2D dependiendo del tamaño d definido
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

            if (nivpre<10) {
	            if(calculo) {
	            	cargarEjercicio(g2d);
	            }else {
	            	if (responder) {
	            		mostrarPregunta(g2d);
	            		cargapregunta(g2d);
	            		
	            	}else{
	            		sinEjercioactivado(g2d);
	            	}
	            	if(resolverbien) {
	            		showresolvistebien(g2d);
	                }
	            	if(resolvermal) {
	            		showresolvistemal(g2d);
	                }
	            }
            }else {
            	showendScreen(g2d);
            }

        } else {
        	if (vidas == 0) {
        		showIntroScreen(g2d);
        		mensajefinal(g2d);
        	}
            showIntroScreen(g2d);
        }
        

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
	//Configurando la salida del PACMAN O COMO SE VE MOVIENDOSE
	
	
    private void drawMateman(Graphics2D g2d) {
    
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
    }
    
    private void sinEjercioactivado(Graphics g2d) {
    	 g2d.setFont(smallFont);
     	
         g2d.setColor(new Color(255, 0, 255));
         String p = "Oprime Enter para Ejercicio";
         g2d.drawString(p, SCREEN_SIZE / 2 -30,  70);
    }
    private void mostrarPregunta(Graphics g2d) {
    	if (responder) {
	    	g2d.setFont(smallFont);
	     	
	        g2d.setColor(new Color(40, 255, 150));
	        String p = "¿Cuál es el signo que corresponde al resultado";
	        String q = "de la operación matemática?";
	        g2d.drawString(p, SCREEN_SIZE / 2 -35,  550);
	        g2d.drawString(q, SCREEN_SIZE / 2 -35,  570);
    	}
    }
	private void cargapreguntacuadrado(Graphics2D g2d) {
			g2d.setColor(new Color(255,0,0));
	        g2d.setStroke(new BasicStroke(1));
	        //g2d.fillRect(200, 600, BLOCK_SIZE, BLOCK_SIZE);
	        g2d.drawLine(285, 595, 315, 595);
	        g2d.drawLine(315, 595, 315, 625);
	        g2d.drawLine(315, 625, 285, 625);
	        g2d.drawLine(285, 625, 285, 595);
		
	} 
	private void showresolvistebien(Graphics2D g2d) {
		cargapreguntacuadrado(g2d);
		g2d.setFont(bigFont);
     	g2d.setColor(new Color(40, 255, 150));
        g2d.drawString(operador1resuelto, 250,  620);
        g2d.drawString(operador2resuelto, 330,  620);
        g2d.drawString("=", 365,  620);
        g2d.drawString(respuestaresuelto, 390,  620);
        
  
        g2d.setFont(smallFont);
     	g2d.setColor(new Color(255, 0, 255));
     	g2d.drawString("¡Genial! ¡Lo lograste!", 280,  570);
		if (signoresuelto=="-") {
			g2d.drawImage(resta, 290,600, this);
		}
		if (signoresuelto=="+") {
			g2d.drawImage(suma, 290,600, this);
		}
		if (signoresuelto=="x") {
			g2d.drawImage(multi, 290,600, this);
		}
		
	}
	private void showresolvistemal(Graphics2D g2d) {
		g2d.setFont(smallFont);
     	g2d.setColor(new Color(255, 0, 255));
     	g2d.drawString("Tú puedes lograrlo. ¡Vamos de nuevo!", 240,  570);
		
	}
	
	private void cargapregunta(Graphics2D g2d) {
		cargapreguntacuadrado(g2d);
		  
		g2d.setFont(bigFont);
     	g2d.setColor(new Color(40, 255, 150));
        g2d.drawString(preguntas[Listapreguntas[nivpre][0]][0], 250,  620);
        g2d.drawString(preguntas[Listapreguntas[nivpre][0]][2], 330,  620);
        g2d.drawString("=", 365,  620);
        g2d.drawString(preguntas[Listapreguntas[nivpre][0]][3], 390,  620);

  
        if (preguntas[Listapreguntas[nivpre][0]][1]=="x") {
        	g2d.drawImage(multi, BLOCK_SIZE *(Listapreguntas[nivpre][3]%N_BLOCKS) + 1+w, 
        			BLOCK_SIZE *(Listapreguntas[nivpre][3]/N_BLOCKS)  + 1+z, this);
        	g2d.drawImage(suma, BLOCK_SIZE *(Listapreguntas[nivpre][1]%N_BLOCKS) + 1+w, 
        			BLOCK_SIZE *(Listapreguntas[nivpre][1]/N_BLOCKS)  + 1+z, this);
        	g2d.drawImage(resta, BLOCK_SIZE *(Listapreguntas[nivpre][2]%N_BLOCKS) + 1+w, 
        			BLOCK_SIZE *(Listapreguntas[nivpre][2]/N_BLOCKS)  + 1+z, this);
        }
        if (preguntas[Listapreguntas[nivpre][0]][1]=="-") {
        	g2d.drawImage(resta, BLOCK_SIZE *(Listapreguntas[nivpre][3]%N_BLOCKS) + 1+w, 
        			BLOCK_SIZE *(Listapreguntas[nivpre][3]/N_BLOCKS)  + 1+z, this);
        	g2d.drawImage(suma, BLOCK_SIZE *(Listapreguntas[nivpre][1]%N_BLOCKS) + 1+w, 
        			BLOCK_SIZE *(Listapreguntas[nivpre][1]/N_BLOCKS)  + 1+z, this);
        	g2d.drawImage(multi, BLOCK_SIZE *(Listapreguntas[nivpre][2]%N_BLOCKS) + 1+w, 
        			BLOCK_SIZE *(Listapreguntas[nivpre][2]/N_BLOCKS)  + 1+z, this);
        }
        if (preguntas[Listapreguntas[nivpre][0]][1]=="+") {
        	g2d.drawImage(suma, BLOCK_SIZE *(Listapreguntas[nivpre][3]%N_BLOCKS) + 1+w, 
        			BLOCK_SIZE *(Listapreguntas[nivpre][3]/N_BLOCKS)  + 1+z, this);
        	g2d.drawImage(resta, BLOCK_SIZE *(Listapreguntas[nivpre][1]%N_BLOCKS) + 1+w, 
        			BLOCK_SIZE *(Listapreguntas[nivpre][1]/N_BLOCKS)  + 1+z, this);
        	g2d.drawImage(multi, BLOCK_SIZE *(Listapreguntas[nivpre][2]%N_BLOCKS) + 1+w, 
        			BLOCK_SIZE *(Listapreguntas[nivpre][2]/N_BLOCKS)  + 1+z, this);
        }

	}

	
	private void mensajefinal(Graphics2D g2d) {
		g2d.setFont(smallFont);
     	g2d.setColor(new Color(255, 0, 255));
     	g2d.drawString("¡Te has quedado sin vidas!", 220,  570);
     	g2d.drawString("Vuelve a iniciar", 255,  610);
		
	}	
    
    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;
 
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
    	g2d.drawImage(pregunta, x+w, y+500, this);
    }
    private void cargarEjercicio(Graphics2D g2d) {
        int x, y, z, w;
        w=290;
        z=270;
        
        g2d.drawImage(pregunta, w, z, this);
        
        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {
            	
            }
        }
        
  
    }


    public tablero() {

        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
    }
    // Hasta aqui va funciones relacionadas al tablero
    
    //Variables de inicio  configuración de arranque de juego
    
	private boolean inGame = false;
	private boolean dying = false;
	private boolean calculo = false;
	private boolean responder = false;
	private boolean freeze = false;
	private boolean resolverbien = false;
	private boolean resolvermal = false;
	
	
	private final int MAX_ALIENS = 8;
	private final int MATEMAN_SPEED = 3;
	private int N_ALIENS = 4;
	private int vidas, puntaje;
	private int[] dx, dy;
	private int[] alien_x, alien_y, alien_dx, alien_dy, alienSpeed;
	private int mateman_x, mateman_y, matemand_x, matemand_y;
    private int req_dx, req_dy;
	private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 4;
    private int currentSpeed = 1;
    private Timer timer;
    public int nivpre =0;
    List<Integer> ejemploLista = new ArrayList<Integer>();
	List<Integer> secuencia = new ArrayList<Integer>();
	private int[][] Listapreguntas;
	private int mensaje = 0;
	
	
    //Variables de Juego
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
        mensaje=0;
 
     }


	public static void sacarlista() {
		
		List<Integer> ejemploLista = new ArrayList<Integer>();
		List<Integer> secuencia = new ArrayList<Integer>();
        
		for (int x=0;x<10;x++) {
			ejemploLista.add(x);
		}
		
		int i=0;
		int saca=0;
		int valor=9;
		
		for (i=0;i<10;i++) {
			saca=(int)(Math.random()*valor);
			valor--;
			secuencia.add(i, ejemploLista.get(saca));;
			ejemploLista.remove(saca);
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

	//Condiciones initGame para iniciar juego
    private void initGame() {
    	
    	vidas = 3;
        puntaje = 0;
        initLevel();
        N_ALIENS = 4;
        currentSpeed = 2;
        calculo=false;
        freeze=false;
        responder=false;
        resolverbien=false;
        resolvermal=false;
        
        for (int x=0;x<10;x++) {
			ejemploLista.add(x);
		}
		
		int i=0;
		int saca=0;
		int valor=9;
		
		for (i=0;i<10;i++) {
			saca=(int)(Math.random()*valor);
			valor--;
			secuencia.add(i, ejemploLista.get(saca));;
			ejemploLista.remove(saca);
		}
		Listapreguntas = new int [10][4];
		for (i=0;i<10;i++) {
        	Listapreguntas[i][0]=secuencia.get(i);
        	Listapreguntas[i][1]=tabdispon.get((int)(Math.random()*165));
        	Listapreguntas[i][2]=tabdispon.get((int)(Math.random()*165));
        	Listapreguntas[i][3]=tabdispon.get((int)(Math.random()*165));
        	
    	}
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
            
            if (freeze) {
            	random = 1;
            	alienSpeed[i] = validSpeeds[0];
            } else {
            	random = (int) (Math.random() * (currentSpeed + 1));
            	if (random > currentSpeed) {
	                random = currentSpeed;
	            }
	            alienSpeed[i] = validSpeeds[random];
            }
        }

        mateman_x = 7 * BLOCK_SIZE;  //start position
        mateman_y = 11 * BLOCK_SIZE;
        matemand_x = 0;	//reset direction move
        matemand_y = 0;
        req_dx = 0;		// reset direction controls
        req_dy = 0;
        dying = false;
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
            		puntaje = puntaje +100;
            		responder=true;
            		resolverbien=false;
            		resolvermal=false;
            	}
            }
            if(responder) {
            	if (pos == Listapreguntas[nivpre][3]) {
            		operador1resuelto=preguntas[Listapreguntas[nivpre][0]][0];
            		operador2resuelto=preguntas[Listapreguntas[nivpre][0]][2];
            		signoresuelto=preguntas[Listapreguntas[nivpre][0]][1];
            		respuestaresuelto=preguntas[Listapreguntas[nivpre][0]][3];
            		puntaje = puntaje + 1000;
            		freeze=false;
            		responder=false;
            		resolverbien=true;
            		resolvermal=false;
            		nivpre++;
            	}
            	if (pos == Listapreguntas[nivpre][1] || pos == Listapreguntas[nivpre][2]) {
            		dying = true;
            		freeze=false;
            		responder=false;
            		resolverbien=false;
            		resolvermal=true;
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
	
	
//Preguntas Cargadas
	/* 9+9 = 18
	 * 27-18= 9
	 * 2x5=10
	 * 30+15=45
	 * 8-3=5
	 * 5x1=5
	 * 3+7=10
	 * 18-8=10
	 * 5x5=25
	 * 3+3=6
	 */
	String preguntas[][]= {{"9","+","9","18"},{"27","-","18","9"}
						,{"2","x","5","10"},{"30","+","15","45"}
						,{"8","-","3","5"},{"5","x","1","5"}
						,{"3","+","7","10"},{"18","-","8","10"}
						,{"5","x","5","25"},{"3","+","3","6"}};
	//Mostrar Pregunta
	


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
