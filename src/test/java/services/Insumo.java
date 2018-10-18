package services;

public class Insumo {
	int id;
	String nombre;
	int cantidad;
	
	public Insumo(int id, String nombre, int cantidad) {
		this.id= id;
		this.nombre= nombre;
		this.cantidad= cantidad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cant) {
		this.cantidad = cant;
	}
	

}

