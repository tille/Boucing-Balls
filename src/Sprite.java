import java.awt.Graphics;
import java.awt.Image;
//import java.awt.geom.AffineTransform;

/**
 * Esta clase maneja los Sprites (im�genes) que se van a cargar en la aplicaci�n
 * 
 * Basado en el juego "SpaceInvaders", en versi�n de Kevin Glass
 * (disponible en http://www.cokeandcode.com/info/tut2d.html)
 * 
 * @author htrefftz
 *
 */
public class Sprite {
	private Image imagen;
	
	/**
	 * Crear un nuevo sprite
	 * 
	 * @param image Imagen para crear el sprite
	 */
	public Sprite(Image imagen) {
		this.imagen = imagen;
	}
	
	/**
	 * Retornar el ancho del sprite
	 * 
	 * @return Ancho en pixels del psrite
	 */
	public int getWidth() {
		return imagen.getWidth(null);
	}

	/**
	 * Retornar el alto del sprite
	 * 
	 * @return Retornar la altura en pixels del sprite
	 */
	public int getHeight() {
		return imagen.getHeight(null);
	}
	
	/**
	 * Dibujar el sprite
	 * 
	 * @param g En qu� contexto gr�fico dibujarlo
	 * @param x Coordenada en X para dibujar el sprite
	 * @param y Coordenada en Y para dibujar el sprite
	 */
	public void draw(Graphics g,int x,int y) {
		g.drawImage(imagen,x,y,null);
	}

        public Image getImagen() {
		return imagen;
	}

}
