import javax.swing.*;

public class Bola extends Entidad {
	
	private double deltaX;
	 
	private double deltaY;
	 
	private int color;

        String [] arreglo;
	
	private Sprite sprite;
	
	public static final int LADO = 50;
        int x;

        boolean debug=true;
        boolean entrar=true;
        public int punta;
        public int subeHasta;

        public Posicion fila;
	
	public Bola(String ref, PuntoEntero posicion,int color) {
		super(posicion, LADO, LADO);
		this.sprite = SpriteStore.get().getSprite(ref);
                this.color=color;
	}

        public int getColor(){
            return color;
        }

	public Bola(String ref) {
		super(new PuntoEntero(), LADO, LADO);
		this.sprite = SpriteStore.get().getSprite(ref);
	}
	
	public void setDeltaX(double deltaX) {
		this.deltaX = deltaX;
	}

	public void setDeltaY(double deltaY) {
		this.deltaY = deltaY;
	}

	public void mover(){
            if(debug){
                 if( posicion.getY()-25 > 51 ){
                    posicion.setX((posicion.getX() + (int)deltaX));
                    posicion.setY((posicion.getY() + (int)deltaY));
                 }else{
                    // Juego.agregarbola(posicion.getX(),posicion.getY(),this);
                    debug=false;
                 }
            }
	}

        public void caer(){
                posicion.setY((int)(posicion.getY() + 1));
        }

        public void tales(Juego principal){
            if(debug){
                 int py=posicion.getY()+(int)deltaY;
                 int px=posicion.getX()+(int)deltaX;
                 if(px>=Juego.ANCHO) deltaX=-deltaX;
                 if(px<=0) deltaX=-deltaX;

                 if( posicion.getY()-25 > subeHasta){
                    posicion.setX((posicion.getX() + (int)deltaX));
                    posicion.setY((posicion.getY() + (int)deltaY));
                 }else{
                    //System.out.println("la bola se pego en la posicion"+x);
                    int a=(subeHasta/50)-1;
                    //System.out.println("y"+a);
                    principal.agregarbola(x,(subeHasta/50)-1,this);
                    principal.eliminarBola(x,(subeHasta/50)-1,color,this);
                    principal.baja++;
                    principal.canon();
                    debug=false;
                    principal.nuevaBola();
                 }
            }
	}
	
	public void dibujar(java.awt.Graphics g){
		sprite.draw(g, posicion.getX()-LADO/2,posicion.getY()-LADO/2);
	}
        

        public void definirLlegada(Juego principal){
            if(punta==197) x=0;
            else if(punta==204) x=1;
            else if(punta==211) x=2;
            else if(punta==218) x=3;
            else if(punta==225) x=4;
            else if(punta==232) x=5;
            else if(punta==239) x=6;
            else if(punta==246) x=7;
            else x=8;

            int u=principal.dificultad-3;
            for(int i=0; i<9 ; i++){
                //System.out.println("entro veces"+i);
                fila=principal.elementoMatriz(i,x);
                if(fila.getBola()!=null){
                    if(i>u) u=i;
                }
            }
            //System.out.println("la ultima posicion que se encontro en la columna"+x+" fue"+u);
            subeHasta=(u+2)*50;

            
            if(((subeHasta/50)-1)>8){
                
                int n = JOptionPane.showConfirmDialog(null, "Perdiste Baboso, Queres Seguir Tratando?","PERDISTE",JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION)  principal.techoLlenar(0);
                else   System.exit(0);

            }
        }

        public void cambiarimagen(String ref){
            this.sprite=SpriteStore.get().getSprite(ref);
        }
	
}
 
