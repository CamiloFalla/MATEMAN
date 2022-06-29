package matemanpck;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;


public class juego extends JFrame{
	public juego() {
			add(new tablero());
			
	}
		
	public static void main(String[] args) {
		juego pac = new juego();
		pac.setVisible(true);
		pac.setTitle("MateMan");
		pac.setSize(600,800);
		pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pac.setLocationRelativeTo(null);
			
	}
	// Con esta funion puedo poner random la frecuencia de las preguntas y los dibujos

}

