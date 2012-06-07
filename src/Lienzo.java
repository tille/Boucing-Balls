import java.awt.Canvas;
import java.awt.image.BufferStrategy;

/**
 * Area en la cual se pintan las entidades del juego
 * @author htrefftz
 *
 */
public class Lienzo extends Canvas {
	
	private BufferStrategy strategy;	
	
	public Lienzo() {
		super();
		//this.setPreferredSize(new Dimension(Juego.ANCHO, Juego.ALTO));
		setIgnoreRepaint(true);
	}
	
	public void crearEstrategia() {
		createBufferStrategy(2);
		strategy = getBufferStrategy();		
	}
	
	public BufferStrategy getStrategy() {
		return strategy;
	}

}
