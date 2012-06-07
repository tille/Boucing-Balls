import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics2D;
import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import org.jfugue.Player;


/* ---------------------------------------------------------------------------------------------------
 * Juego Cannon Balls
 * Jorman Bustos
 * Objetivo Tumbar Bolitas
 * Fundamentos de Programacion - Universidad EAFIT - 2009 2
 --------------------------------------------------------------------------------------------------- */

public class Juego extends JFrame {

	public static final int ANCHO = 450;
	public static final int ALTO = 650;
	private Techo techo;
	private Canyon canyon;
	private Bola bolaAMoverse; // La bola que se va a mover
	private ManejadorTeclado manejadorTeclado; // Clase para manejar los eventos del teclado
	private Lienzo lienzo; 	// Lienzo sobre el cual se pintan las entidades del juego
        public int scor=0; // Esta Variable Guarda el Puntaje del jugador
        int dificultad=2; // El nivel del juego siendo 2 el nivel 1
        int nivel=2;  // 
        Player player; // Objeto para reproducir un sonido
        private int f; //Esta variable obtiene cuantas posiciones hay pegadas al techo
        public boolean jugando=true; // Determina que el juego va continuar ejecutandose mientras sea true
        String nombre= "anomimo"; // El nombre del jugador si no ingresa nada se tomara como anonimo
        int baja=1;


/* ---------------------------------------------------------------------------------------------------
 * Constructor del Juego
 --------------------------------------------------------------------------------------------------- */

	public Juego() {
		super("Cannon Balls - By Tillegomezz ");
		setPreferredSize(new Dimension(ANCHO, ALTO));
		setLayout(null);

                String s = (String)JOptionPane.showInputDialog(null, "Escribe tu nombre:\n", "Nombre del Jugador", JOptionPane.PLAIN_MESSAGE);
                if ((s != null) && (s.length() > 0)) this.nombre=s;

                player=new Player();
		lienzo = new Lienzo();
		lienzo.setBounds(0, 0, ANCHO, ALTO);
		add(lienzo);

		pack();
		setResizable(false);
		setVisible(true);

		lienzo.crearEstrategia();
		addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e){ System.exit(0); }});

		manejadorTeclado = new ManejadorTeclado(this);
		addKeyListener(manejadorTeclado);
		requestFocus();
	}
        
/* ---------------------------------------------------------------------------------------------------
 --------------------------------------------------------------------------------------------------- */



/* ---------------------------------------------------------------------------------------------------
 * Este metodo sirve para agregar las bolas que se van a eliminar a un vector
 --------------------------------------------------------------------------------------------------- */
        public void eliminarBola(int x,int y,int color,Bola bola){
            techo.vectorEliminando[0]=new Condenadas(x,y,bola);
            techo.tamanovector=techo.tamanovector+1;
            techo.eliminando(x,y,this);
        }
/* ---------------------------------------------------------------------------------------------------
 --------------------------------------------------------------------------------------------------- */



/* ---------------------------------------------------------------------------------------------------
 * llena el techo con colores al azar dependiendo de la dificultad
 --------------------------------------------------------------------------------------------------- */
        public void techoLlenar(int p){
            if(p==1){
                baja=0;
                this.nivel++;
                this.dificultad=nivel;
            }else{
                this.nivel=2;
                this.dificultad=2;
                scor=0;
            }
            if(dificultad==2) techo=new Techo("Sprites/techo.png",this,scor);
            else if(dificultad==3) techo=new Techo("Sprites/techo1.png",this,scor);
            else if(dificultad==4) techo=new Techo("Sprites/techo2.png",this,scor);
            else if(dificultad==5) techo=new Techo("Sprites/techo3.png",this,scor);
            else techo=new Techo("Sprites/techo4.png",this,scor);
        }
 /* ---------------------------------------------------------------------------------------------------
 --------------------------------------------------------------------------------------------------- */



        
/* ---------------------------------------------------------------------------------------------------
 * identifica la Posicion de la bola
 --------------------------------------------------------------------------------------------------- */
        public int identificarTipo(int x,int y){
            int bajando=dificultad-2;
            if(x==0 && y>bajando) return 1;
            else if(x==0 && y==bajando) return 2;
            else if(x>0 && y==bajando && x<8) return 3;
            else if(x==8 && y==bajando) return 4;
            else if(x==8 && y>bajando) return 5;
            else return 6;
        }
 /* ---------------------------------------------------------------------------------------------------
 --------------------------------------------------------------------------------------------------- */




/* ---------------------------------------------------------------------------------------------------
 * Agrega una bola a una posicion en el techo
 --------------------------------------------------------------------------------------------------- */
        public void agregarbola(int x,int y,Bola bola){
            techo.setagregar(x,y,bolaAMoverse);
        }
/* ---------------------------------------------------------------------------------------------------
 --------------------------------------------------------------------------------------------------- */

        
        


 /* ---------------------------------------------------------------------------------------------------
 * crea todas las entidades del juego
 --------------------------------------------------------------------------------------------------- */
	public void inicializarEnditades() {
		canyon = new Canyon(this);
		//bolaRoja = new Bola("Sprites/bolaRoja.png", new PuntoEntero(100, 100));
                if(dificultad==2) techo=new Techo("Sprites/techo.png",this,scor);
                else if(dificultad==3) techo=new Techo("Sprites/techo1.png",this,scor);
                else if(dificultad==4) techo=new Techo("Sprites/techo2.png",this,scor);
                else if(dificultad==5) techo=new Techo("Sprites/techo3.png",this,scor);
                else techo=new Techo("Sprites/techo4.png",this,scor);
                bolaAMoverse = canyon.generarBola();
	}
/* ---------------------------------------------------------------------------------------------------
 --------------------------------------------------------------------------------------------------- */



 /* ---------------------------------------------------------------------------------------------------
 * Evento tecla izquierda
 --------------------------------------------------------------------------------------------------- */
	public void presionarTeclaIzq() {
		canyon.rotarIzquierda();
	}
 /* ---------------------------------------------------------------------------------------------------
 --------------------------------------------------------------------------------------------------- */




 /* ---------------------------------------------------------------------------------------------------
 * Lo que sucede cuando se presiona la ttecla direccional derecha
 --------------------------------------------------------------------------------------------------- */
	public void presionarTeclaDer() {
		canyon.rotarDerecha();
	}
/* ---------------------------------------------------------------------------------------------------
 --------------------------------------------------------------------------------------------------- */





 /* ---------------------------------------------------------------------------------------------------
 * Determina Si Ha sido presionada la tecla espaciadora
 ---------------------------------------------------------------------------------------------------*/
	public void presionarTeclaEsp() {
                bolaAMoverse.punta=canyon.puntaX();
                bolaAMoverse.definirLlegada(this);
		PuntoEntero pe = canyon.getDxYDY();
		bolaAMoverse.setDeltaX(pe.getX()/10);
		bolaAMoverse.setDeltaY(pe.getY()/10);
                canyon.sprite = SpriteStore.get().getSprite("Sprites/CanonDisparo.png");
                boolean t=techo.muere();
                player.play("I[gunshot] G5");
	}
 /* ---------------------------------------------------------------------------------------------------
 ---------------------------------------------------------------------------------------------------*/


public void b(){
            if(baja==16){
                dificultad++;
                baja++;
                techo.sprite=SpriteStore.get().getSprite("Sprites/techo1.png");
                techo.mover();
            }
            else if(baja==32){
                dificultad++;
                baja++;
                techo.sprite=SpriteStore.get().getSprite("Sprites/techo2.png");
                techo.mover();
            }
            else if(baja==48){
                dificultad++;
                baja++;
                techo.sprite=SpriteStore.get().getSprite("Sprites/techo3.png");
                techo.mover();
            }
            else if(baja==64){
                dificultad++;
                baja++;
                techo.sprite=SpriteStore.get().getSprite("Sprites/techo4.png");
                techo.mover();
            }
}



///////////////////////////////////////////////////////////////////////////////////////////////////////
// Este metodo no funciona triste pero cierto
        /*try {
            // Se obtiene un Clip de sonido
            Clip sonido = AudioSystem.getClip();
            
            // Se carga con un fichero wav
            sonido.open(AudioSystem.getAudioInputStream(new File("Sonidos/b.wav")));

            // Comienza la reproducción
            sonido.start();

            // Espera mientras se esté reproduciendo.
            while (sonido.isRunning())
                Thread.sleep(1000);

            // Se cierra el clip.
            sonido.close();
        } catch (Exception e) {
            System.out.println("" + e);
        }*/

///////////////////////////////////////////////////////////////////////////////////////////////////////





        
 /* ---------------------------------------------------------------------------------------------------
 * Cambia la imagen del cañon cuando este dispara y la pone normal cuando las bolas tocan el techo
  * este metodo espara la parte donde las pone normalitas.
 ---------------------------------------------------------------------------------------------------*/
        public void canon(){
                canyon.sprite = SpriteStore.get().getSprite("Sprites/CanonNormal.png");
        }
 /* ---------------------------------------------------------------------------------------------------
 ---------------------------------------------------------------------------------------------------*/




 /* ---------------------------------------------------------------------------------------------------
 * Genera una nueva bola para ser disparada cuando la anterior ya halla tocado el techo
 ---------------------------------------------------------------------------------------------------*/
        public void nuevaBola(){
                bolaAMoverse = canyon.generarBola();
        }
 /* ---------------------------------------------------------------------------------------------------
 ---------------------------------------------------------------------------------------------------*/




 /* ---------------------------------------------------------------------------------------------------
 * Retorna un posicion del techo esa posicion tiene una bola y unas coordenadas
 ---------------------------------------------------------------------------------------------------*/
        public Posicion elementoMatriz(int filas,int columnas){
                return techo.getPosicion(filas,columnas);
        }
 /* ---------------------------------------------------------------------------------------------------
 ---------------------------------------------------------------------------------------------------*/




 /* ---------------------------------------------------------------------------------------------------
 * Hay necesidad de explicarlo ???? jeje este ciclo maneja todo el juego TODO
 ---------------------------------------------------------------------------------------------------*/
	public void ciclo() {
		while (jugando) {
                    //try { Thread.sleep(4);} catch (Exception e){ }
                    
			Graphics2D g = (Graphics2D) lienzo.getStrategy().getDrawGraphics();
			g.setColor(Color.white);
			g.fillRect(0,0,ANCHO,ALTO);
                        canyon.pintarFondo(g);
			canyon.dibujar(g);
			bolaAMoverse.tales(this);
			bolaAMoverse.dibujar(g);
			techo.dibujar(g);
                        f=techo.verificar();
                        this.scor=techo.scor*dificultad;
                        if(this.dificultad==2) g.drawString( "Bienvenido: "  + nombre +"     Score: "+scor+"     Nivel: "+(nivel-1),10,25 );
                        else g.drawString( nombre + " Ahora estas Jugando Cannon Balls      Score: "+scor+"     Nivel: "+(nivel-1),10,25 );
                        if(f==0){
                            int n = JOptionPane.showConfirmDialog(null, "Hey Ganaste Bacano por Vos, Seguis Jugando?? , Aumentamos la Dificultad?","Ganaste",JOptionPane.YES_NO_OPTION);
                            if (n == JOptionPane.YES_OPTION)  techoLlenar(1);
                            else   System.exit(0);
                        }
                        b();
			g.dispose();
			lienzo.getStrategy().show();
		}
	}
 /* ---------------------------------------------------------------------------------------------------
 ---------------------------------------------------------------------------------------------------*/



 /* ---------------------------------------------------------------------------------------------------
 * Y este de aki ps el main principal donde comienza el desarrollo de la aplicacion donde se crea
 ---------------------------------------------------------------------------------------------------*/
	public static void main(String [] args) {
		Juego juego = new Juego();
		juego.inicializarEnditades();
		juego.ciclo();
	}
 /* ---------------------------------------------------------------------------------------------------
 ---------------------------------------------------------------------------------------------------*/



}
 

