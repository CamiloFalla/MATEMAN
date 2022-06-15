package pacman;

import javax.swing.JFrame;

public class Mateman extends JFrame{
	public Mateman() {
		add(new Model());
		
	}
	
	public static void main(String[] args) {
		Mateman pac = new Mateman();
		pac.setVisible(true);
		pac.setTitle("MateMan");
		pac.setSize(600,800);
		pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pac.setLocationRelativeTo(null);
		
	}
}
