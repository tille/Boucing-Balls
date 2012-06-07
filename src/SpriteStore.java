import java.util.HashMap;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;

/**
 * Esta clase se encarga de lo siguiente:
 * (i) Lee del disco duro las imagenes de los "sprites" (dibujos) a utilizar
 * (ii) Las guarda en una coleccion que se llama HashMap
 *      El HashMap es como un diccionario con tuplas <clave, valor>  
 *      La palabra (clave) es el nombre del sprite
 *      y la definicion (valor) es la imagen del sprite
 * (iii) El metodo getSprite devuelve la imagen
 * 
 * Basado en el juego "SpaceInvaders", en version de Kevin Glass
 * (disponible en http://www.cokeandcode.com/info/tut2d.html)

 * @author htrefftz
 *
 */

public class SpriteStore {
	// Este es un patron que se llama "singleton"
	// Hay solamente una instancia de esta clase
	private static SpriteStore single = new SpriteStore();
	
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	/**
	 * Se retorna una referencia de la unica instancia de esta clase
	 * @return single es el singleton de esta clase
	 */
	public static SpriteStore get() {
		return single;
	}
	
	/**
	 * Retorna el Sprite (dibujo) dado el nombre.
	 * Si no existe aun, se carga desde el disco
	 * @param ref nombre del sprite a retornar
	 * @return el sprite con el nombre que se recibe
	 */
	public Sprite getSprite(String ref) {
		// Si ya existe, retornarlo
		if (sprites.get(ref) != null) {
			return sprites.get(ref);
		}
		BufferedImage sourceImage = null;
		
		try {
			// buscar la imagen 
			URL url = this.getClass().getClassLoader().getResource(ref);
			
			if (url == null) {
				fail("No pude construir el URL: "+ref);
			}			
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			fail("Problemas en la lectura del archivo: "+ref);
		}
		
		// solicitar memoria grafica para almacenar la imagen
		GraphicsConfiguration gc = 
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = 
			gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
		
		// dibujar la imagen
		image.getGraphics().drawImage(sourceImage,0,0,null);
		
		// crear el sprite y agregarlo a la coleccion (HashMap)
		Sprite sprite = new Sprite(image);
		sprites.put(ref,sprite);
		
		return sprite;
		
	}
	
	/**
	 * Fallo, no se encontro la imagen para el sprite
	 * @param message
	 */
	private void fail(String message) {
		System.err.println(message);
		System.exit(1);
	}
}
