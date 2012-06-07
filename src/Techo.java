import java.util.Arrays;
import javax.swing.*;

public class Techo extends Entidad {

	public final static int ALTURA = 75; // altura inicial del techo
	private double deltaY;
        public int dificultad=2;
        boolean g;
	private int posicionY;
	private Posicion [][] matrizPosiciones;
	private int [] valoresY;
	private int numFilas;
	private int numColumnas;
        public Condenadas [] vectorEliminando;
        public int tamanovector=0;
        private Juego principal;
	public Sprite sprite;
        public int scor;
        private JLabel Score;


	public Techo(String ref,Juego principal,int puntaje) {
		super(new PuntoEntero(10, 10), 10, 10);
                Score=new JLabel(""+scor);
                principal.add(Score);
                this.scor=puntaje;
		this.sprite = SpriteStore.get().getSprite(ref);
		numFilas = Juego.ALTO / Bola.LADO;
		numColumnas = Juego.ANCHO / Bola.LADO;
		matrizPosiciones = new Posicion[numFilas][numColumnas];
                vectorEliminando = new Condenadas[numFilas*numColumnas];
		int y = ALTURA;
		for (int i = 0; i < numFilas; i++) {
                int x = 25;
                    for (int j = 0; j < numColumnas; j++) {
			matrizPosiciones[i][j] = new Posicion(new PuntoEntero(x, y));
			x += Bola.LADO;
                    }
		y = y + Bola.LADO;
		}
		valoresY = new int[numFilas];
                int azar;
		for (int i = (principal.dificultad-2); i < (principal.dificultad+1); i++) {
                for (int j = 0; j < numColumnas; j++) {

                        azar = new Double(Math.random() * (principal.dificultad+2)).intValue();
                        PuntoEntero punto = new PuntoEntero(50, 90+40);
                        if(azar==0) matrizPosiciones[i][j].setBola(new Bola("Sprites/bolaRoja.png",punto,azar));
                        else if(azar==1) matrizPosiciones[i][j].setBola(new Bola("Sprites/bolaVerde.png",punto,azar));
                        else if(azar==2) matrizPosiciones[i][j].setBola(new Bola("Sprites/bolaMorado.png",punto,azar));
                        else if(azar==3) matrizPosiciones[i][j].setBola(new Bola("Sprites/bolaNegra.png",punto,azar));
                        else if(azar==4) matrizPosiciones[i][j].setBola(new Bola("Sprites/BolaAzulOscuro.png",punto,azar));
                        else if(azar==5) matrizPosiciones[i][j].setBola(new Bola("Sprites/BolaAmarillo.png",punto,azar));
                        else if(azar==6) matrizPosiciones[i][j].setBola(new Bola("Sprites/bolaAzul.png",punto,azar));
                }
                }
                llenarnull();
	}


	public void mover(){
            for (int y = numFilas-1; y>=0; y--) {
                for (int x = numColumnas-1; x>=0; x--) {
                    if(matrizPosiciones[y][x].getBola()!=null){
                        matrizPosiciones[y+1][x].setBola(matrizPosiciones[y][x].getBola());
                        matrizPosiciones[y][x].setBola2();
                    }
                }
            }

            for (int y = numFilas-1; y>=0; y--) {
                for (int x = numColumnas-1; x>=0; x--) {
                    if(matrizPosiciones[y][x].getBola()!=null && (y)>=8){
                        int n = JOptionPane.showConfirmDialog(null, "El Techo Bajo una Pocision y Acabas de Perder, Queres Seguir Tratando?","PERDISTE",JOptionPane.YES_NO_OPTION);
                        if (n == JOptionPane.YES_OPTION)  principal.techoLlenar(0);
                        else   System.exit(0);
                    }
                }
            }

	}

        public boolean muere(){
        for (int y = numFilas-1; y>=0; y--) {
                for (int x = numColumnas-1; x>=0; x--) {
                    if(matrizPosiciones[y][x].getBola()!=null && y>=8){
                        return true;
                    }
                }
            }
        return false;
        }

        public int verificar(){
            int r=0;
            for (int i = 0; i < numFilas; i++) {
                for (int j = 0; j < numColumnas; j++) {
                    Bola bola = matrizPosiciones[i][j].getBola();
                    if(bola!=null) r++;
                }
            }
            return r;
        }



        
        public void vaciar(){
            for (int i = 0; i < numFilas; i++) {
                for (int j = 0; j < numColumnas; j++) {
                    matrizPosiciones[i][j].setBola2();
                }
            }
        }




        public void score(){
            //System.out.println(scor);
            Score.setName(""+scor);
            //principal.add(Score);
        }





	public void dibujar(java.awt.Graphics g){
		sprite.draw(g, 0, posicionY);
		for (int i = 0; i < numFilas; i++) {
			for (int j = 0; j < numColumnas; j++) {
				Bola bola = matrizPosiciones[i][j].getBola();
				if (bola != null) {
					bola.dibujar(g);
				}
			}
		}
	}




        public void setagregar(int x,int y,Bola bolita){
            this.matrizPosiciones[y][x].setBola(bolita);
        }




        public Posicion getPosicion(int filas,int columnas){
            return matrizPosiciones[filas][columnas];
        }




        public void llenarnull(){
            for(int i=0; i<vectorEliminando.length; i++){
                vectorEliminando[i]=null;
            }
        }




        public void bolassinsosten(Juego principal){
            this.principal=principal;
            boolean derecha;
            boolean izquierda;
            for (int i = 0; i < numFilas; i++) {
                for (int j = 0; j < numColumnas; j++) {
                    if(matrizPosiciones[i][j].getBola()!=null){
                        int tipo=principal.identificarTipo(j,i);
                        
                        if(tipo==1){
                            seCaen1(j,i);
                            if(g){
                                System.out.println("se debe caer");
                                //for(int y=0; y<10000; y++){ matrizPosiciones[i][j].getBola().caer(); }
                                matrizPosiciones[i][j].setBola2();
                            }
                        }else if(tipo==5){
                            seCaen5(j,i);
                            if(g){
                                System.out.println("se debe caer");
                               //for(int y=0; y<10000; y++){ matrizPosiciones[i][j].getBola().caer(); }
                               matrizPosiciones[i][j].setBola2();
                            }
                        }else if(tipo==6){
                            seCaen1(j,i);
                            derecha=g;
                            seCaen5(j,i);
                            izquierda=g;
                            if(derecha && izquierda){
                                System.out.println("se debe caer");
                               //for(int y=0; y<10000; y++){ matrizPosiciones[i][j].getBola().caer(); }
                               matrizPosiciones[i][j].setBola2();
                            }
                        }
                    }
                }
            }
        }



       public void seCaen1(int x,int y){
           if(x<9 && x>-1){
            if(x==8){
                if(matrizPosiciones[y-1][x].getBola()==null) this.g=true;
                else{
                    int tip=principal.identificarTipo(x, y-1);
                    if(tip==2 || tip==3 || tip==4) this.g=false;
                    else seCaen1(x,y-1);
                }
            }else if(matrizPosiciones[y-1][x].getBola()==null && matrizPosiciones[y][x+1].getBola()==null ) this.g=true;
            else if(matrizPosiciones[y-1][x].getBola()!=null){
                int tip=principal.identificarTipo(x, y-1);
                if(tip==2 || tip==3 || tip==4) this.g=false;
                else seCaen1(x,y-1);
            }else seCaen1(x+1,y);
           }else this.g=true;
       }





      public void seCaen5(int x,int y){
            if(matrizPosiciones[y-1][x].getBola()==null && matrizPosiciones[y][x-1].getBola()==null ) this.g=true;
            else if(matrizPosiciones[y-1][x].getBola()!=null){
                int tip=principal.identificarTipo(x, y-1);
                if(tip==2 || tip==3 || tip==4) this.g=false;
                else seCaen1(x,y-1);
            }else seCaen1(x-1,y);
       }






        public void eliminando(int x, int y,Juego principal){
            for(int i=0; i<vectorEliminando.length; i++){
                if(vectorEliminando[i]!=null){
                    //System.out.println("tamañovecotr"+tamañovector);
                    //System.out.println("entramos la primera vez con los siguientes valores x:"+ vectorEliminando[i].getX()+" y:"+ vectorEliminando[i].getY()+ " tipo: "+principal.identificarTipo(vectorEliminando[i].getX(),vectorEliminando[i].getY())+" color:"+vectorEliminando[i].getBola().getColor());
                    recorrido(vectorEliminando[i].getX(),vectorEliminando[i].getY(), principal.identificarTipo(vectorEliminando[i].getX(),vectorEliminando[i].getY()) , vectorEliminando[i].getBola().getColor(),i);
                }
            }
            bolassinsosten(principal);
            tamanovector=0;
            llenarnull();
        }





        public void recorrido(int x,int y,int t,int color,int i){
            if(tamanovector>2){
                for(int a=0; a<tamanovector; a++){
                    if(vectorEliminando[a]!=null){
                        this.scor=this.scor+5;
                        //System.out.println("Tu Puntaje Hasta Ahora Es:"+ scor);
                        matrizPosiciones[vectorEliminando[a].getY()][vectorEliminando[a].getX()].setBola2();
                    }
                   // vectorEliminando[a]=null;
                }
            }

            
            //////////////////////////////////////////////////////////////////////////////////
            
            if(t==1){
                if(matrizPosiciones[y][x+1].getBola()!=null && matrizPosiciones[y][x+1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y && vectorEliminando[b].getX()==x+1) c=1;
                    }
                    if(c==0){
                        vectorEliminando[tamanovector]=new Condenadas(x+1,y,matrizPosiciones[y][x+1].getBola());
                        this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y-1][x].getBola()!=null && matrizPosiciones[y-1][x].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y-1 && vectorEliminando[b].getX()==x) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x,y-1,matrizPosiciones[y-1][x].getBola());
                    //System.out.println("agregamos una bola al array osea que arribita de la que disparaste ahi una bola del mismo color");
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x].getBola()!=null && matrizPosiciones[y+1][x].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x,y+1,matrizPosiciones[y+1][x].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x+1].getBola()!=null && matrizPosiciones[y+1][x+1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x+1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x+1,y+1,matrizPosiciones[y+1][x+1].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y-1][x+1].getBola()!=null && matrizPosiciones[y-1][x+1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y-1 && vectorEliminando[b].getX()==x+1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x+1,y-1,matrizPosiciones[y-1][x+1].getBola());
                    this.tamanovector++;
                    }
                }
            }

            //////////////////////////////////////////////////////////////////////////////////

            if(t==2){
                if(matrizPosiciones[y][x+1].getBola()!=null && matrizPosiciones[y][x+1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y && vectorEliminando[b].getX()==x+1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x+1,y,matrizPosiciones[y][x+1].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x].getBola()!=null && matrizPosiciones[y+1][x].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x,y+1,matrizPosiciones[y+1][x].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x+1].getBola()!=null && matrizPosiciones[y+1][x+1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x+1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x+1,y+1,matrizPosiciones[y+1][x+1].getBola());
                    this.tamanovector++;
                    }
                }
            }

            //////////////////////////////////////////////////////////////////////////////////

            if(t==3){
                if(matrizPosiciones[y][x-1].getBola()!=null && matrizPosiciones[y][x-1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y && vectorEliminando[b].getX()==x-1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x-1,y,matrizPosiciones[y][x-1].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y][x+1].getBola()!=null && matrizPosiciones[y][x+1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y && vectorEliminando[b].getX()==x+1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x+1,y,matrizPosiciones[y][x+1].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x].getBola()!=null && matrizPosiciones[y+1][x].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x,y+1,matrizPosiciones[y+1][x].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x+1].getBola()!=null && matrizPosiciones[y+1][x+1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x+1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x+1,y+1,matrizPosiciones[y+1][x+1].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x-1].getBola()!=null && matrizPosiciones[y+1][x-1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x-1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x-1,y+1,matrizPosiciones[y+1][x-1].getBola());
                    this.tamanovector++;
                    }
                }
            }

            //////////////////////////////////////////////////////////////////////////////////

            if(t==4){
                if(matrizPosiciones[y][x-1].getBola()!=null && matrizPosiciones[y][x-1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y && vectorEliminando[b].getX()==x-1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x-1,y,matrizPosiciones[y][x-1].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x].getBola()!=null && matrizPosiciones[y+1][x].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x,y+1,matrizPosiciones[y+1][x].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x-1].getBola()!=null && matrizPosiciones[y+1][x-1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x-1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x-1,y+1,matrizPosiciones[y+1][x-1].getBola());
                    this.tamanovector++;
                    }
                }
            }

            ////////////////////////////////////////////////////////////////////////////////////////

            if(t==5){
                if(matrizPosiciones[y][x-1].getBola()!=null && matrizPosiciones[y][x-1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y && vectorEliminando[b].getX()==x-1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x-1,y,matrizPosiciones[y][x-1].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x].getBola()!=null && matrizPosiciones[y+1][x].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x,y+1,matrizPosiciones[y+1][x].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x-1].getBola()!=null && matrizPosiciones[y+1][x-1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x-1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x-1,y+1,matrizPosiciones[y+1][x-1].getBola());
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y-1][x].getBola()!=null && matrizPosiciones[y-1][x].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y-1 && vectorEliminando[b].getX()==x) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x,y-1,matrizPosiciones[y-1][x].getBola());
                    //System.out.println("agregamos una bola al array osea que arribita de la que disparaste ahi una bola del mismo color");
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y-1][x-1].getBola()!=null && matrizPosiciones[y-1][x-1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y-1 && vectorEliminando[b].getX()==x-1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x-1,y-1,matrizPosiciones[y-1][x-1].getBola());
                    this.tamanovector++;
                    }
                }
            }

            /////////////////////////////////////////////////////////////////////////////////////

            if(t==6){
                if(matrizPosiciones[y][x-1].getBola()!=null && matrizPosiciones[y][x-1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y && vectorEliminando[b].getX()==x-1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x-1,y,matrizPosiciones[y][x-1].getBola());
                    //System.out.println("agregamos una bola al array osea que a la izquierda de la que disparaste ahi una bola del mismo color agregada posicion:"+ tamañovector);
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x].getBola()!=null && matrizPosiciones[y+1][x].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x,y+1,matrizPosiciones[y+1][x].getBola());
                    //System.out.println("agregamos una bola al array osea que debajito de la que disparaste ahi una bola del mismo color agregada posicion:"+ tamañovector);
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x-1].getBola()!=null && matrizPosiciones[y+1][x-1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x-1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x-1,y+1,matrizPosiciones[y+1][x-1].getBola());
                    //System.out.println("agregamos una bola al array osea que abajo a la izquierda de la que disparaste ahi una bola del mismo color agregada posicion:"+ tamañovector);
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y-1][x].getBola()!=null && matrizPosiciones[y-1][x].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y-1 && vectorEliminando[b].getX()==x) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x,y-1,matrizPosiciones[y-1][x].getBola());
                    //System.out.println("agregamos una bola al array osea que arribita de la que disparaste ahi una bola del mismo color agregada posicion:"+ tamañovector);
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y-1][x-1].getBola()!=null && matrizPosiciones[y-1][x-1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y-1 && vectorEliminando[b].getX()==x-1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x-1,y-1,matrizPosiciones[y-1][x-1].getBola());
                    //System.out.println("agregamos una bola al array osea que arriba a la izquierda de la que disparaste ahi una bola del mismo color agregada posicion:"+ tamañovector);
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y-1][x+1].getBola()!=null && matrizPosiciones[y-1][x+1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y-1 && vectorEliminando[b].getX()==x+1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x+1,y-1,matrizPosiciones[y-1][x+1].getBola());
                    //System.out.println("agregamos una bola al array osea que arriba a la derecha de la que disparaste ahi una bola del mismo color agregada posicion:"+ tamañovector);
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y][x+1].getBola()!=null && matrizPosiciones[y][x+1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y && vectorEliminando[b].getX()==x+1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x+1,y,matrizPosiciones[y][x+1].getBola());
                    //System.out.println("agregamos una bola al array osea que a la derecha de la que disparaste ahi una bola del mismo color agregada posicion:"+ tamañovector);
                    this.tamanovector++;
                    }
                }
                if(matrizPosiciones[y+1][x+1].getBola()!=null && matrizPosiciones[y+1][x+1].getBola().getColor()==color){
                    int c=0;
                    for(int b=0; b<vectorEliminando.length; b++){
                        if(vectorEliminando[b]!=null) if( vectorEliminando[b].getY()==y+1 && vectorEliminando[b].getX()==x+1) c=1;
                    }
                    if(c==0){
                    vectorEliminando[tamanovector]=new Condenadas(x+1,y+1,matrizPosiciones[y+1][x+1].getBola());
                    //System.out.println("agregamos una bola al array osea que abajo a la derecha de la que disparaste ahi una bola del mismo color agregada posicion:"+ tamañovector);
                    this.tamanovector++;
                    }
                }
            }

        }

}


 
