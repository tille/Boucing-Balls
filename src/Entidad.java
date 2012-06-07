public abstract class Entidad {
 
	public Entidad(PuntoEntero posicion, int ancho, int alto) {
		this.posicion = posicion;
		this.ancho = ancho;
		this.alto = alto;
	}
	protected PuntoEntero posicion;
	 
	protected int ancho;
	 
	protected int alto;
	 
	public abstract void mover();
	public abstract void dibujar(java.awt.Graphics g);
	
	public void setPosicion(PuntoEntero posicion) {
		this.posicion = posicion;
	}
}
 
