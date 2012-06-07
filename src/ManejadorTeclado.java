import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManejadorTeclado extends KeyAdapter{

	private boolean DEBUG = false;
	/** Referencia al juego, para poder invocar los metodos del juego */
	private Juego juego;
	 
	public ManejadorTeclado(Juego miJuego) {
		juego = miJuego;
	}
	 
	public void keyPressed(KeyEvent e) {
		if (DEBUG)
			System.out.println("En ManejadorTeclado: tecla presionada"+  e.getKeyCode());
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			juego.presionarTeclaIzq();
		}
                if (e.getKeyCode() == KeyEvent.VK_L) {
			juego.techoLlenar(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			juego.presionarTeclaDer();
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			juego.presionarTeclaEsp();
		}
	}
	 
}
 
