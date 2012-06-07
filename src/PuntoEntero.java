public class PuntoEntero {

	private int x;
	 
	private int y;

	/** Constructor de un punto con coordenadas enteras en 2D */
	public PuntoEntero(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public PuntoEntero() {
		x = y = 0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return ("x: " + x + " y: " + y + " " );
	}
	 
}
 
