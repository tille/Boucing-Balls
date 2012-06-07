import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Canyon {
 
	/** Base del canyon */
	private PuntoEntero base;
	/** Punta del canyon */
	private PuntoEntero punta;
	/** Longitud del canyon */
	public static final int LONGITUD = 60;
	/** Distancia del fondo de la pantalla */
	public static final int BORDE = 100;
	/** angulo: 0 es vertical */
	private double angulo = 0d;
	/** cuanto cambia el angulo cada que se presiona tecla derecha o izquierda */
	private double deltaAngulo = 7d * Math.PI / 180d;
	/** la bola que se va a disparar */
	private Bola bolaContinua;
        public Sprite sprite;
        private Sprite sprite2;
        private Juego principal;
        public  java.awt.Graphics dibujin;
	
	private boolean DEBUG = false;
	 
	public Canyon(Juego Principal) {
		base = new PuntoEntero(Juego.ANCHO / 2, Juego.ALTO - BORDE);
		punta = new PuntoEntero(base.getX(), base.getY() - LONGITUD);
                this.sprite = SpriteStore.get().getSprite("Sprites/CanonNormal.png");
                this.principal = Principal;
                this.sprite2=SpriteStore.get().getSprite("Sprites/Fondo.png");
	}
	 

	public void rotarDerecha() {
            if(angulo<0.4) angulo += deltaAngulo;
	}
	
	public void rotarIzquierda() {
            if(angulo>-0.4) angulo -= deltaAngulo;
	}
	
	public void dibujar(java.awt.Graphics g){
		g.setColor(Color.white);
		int x = (int) (Math.sin(angulo) * (double)LONGITUD);
		int y = (int) (Math.cos(angulo) * (double)LONGITUD);
		punta.setX(base.getX() + x);
		punta.setY(base.getY() - y);
		if(DEBUG) System.out.println("" + base + " " + punta + " ");
                if(DEBUG) System.out.println(angulo);
                dibujin=g;
                repaint(dibujin);
		//g.drawLine(base.getX(), base.getY(), punta.getX(), punta.getY());
                
	}

        public void pintarFondo(java.awt.Graphics g){
            g.drawImage(sprite2.getImagen(),0,0,principal);
        }

        public void repaint(java.awt.Graphics g){
                AffineTransform tx = AffineTransform.getRotateInstance(angulo,
                base.getX(),  base.getY());
                tx.translate(Juego.ANCHO / 2-25, Juego.ALTO-57-BORDE-25);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(sprite.getImagen(), tx, principal);
        }
	
	public Bola generarBola() {
		PuntoEntero punto = new PuntoEntero(base.getX(), base.getY()+40);

                int azar = new Double(Math.random() * (principal.nivel+2)).intValue();
                if(azar==0) bolaContinua = new Bola("Sprites/bolaRoja.png", punto,azar);
                else if(azar==1) bolaContinua = new Bola("Sprites/bolaVerde.png", punto,azar);
                else if(azar==2)bolaContinua = new Bola("Sprites/bolaMorado.png", punto,azar);
                else if(azar==3) bolaContinua = new Bola("Sprites/bolaNegra.png", punto,azar);
                else if(azar==4) bolaContinua = new Bola("Sprites/BolaAzulOscuro.png", punto,azar);
                else if(azar==5) bolaContinua = new Bola("Sprites/BolaAmarillo.png", punto,azar);
                else if(azar==6)bolaContinua = new Bola("Sprites/bolaAzul.png", punto,azar);


		return bolaContinua;
	}
	
	public PuntoEntero getDxYDY() {
		int dx = punta.getX() - base.getX();
		int dy = punta.getY() - base.getY();
		PuntoEntero pe = new PuntoEntero(dx, dy);
		return pe;
	}
        
        public int puntaX() {
		return punta.getX();
	}
	 
}
 
