package prodAndes.vos;

public class EstacionProduccion {

	private String codigo;
	
	private int capacidad;
	
	private String estado;
	
	private int ocupacion_actual;

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the capacidad
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * @param capacidad the capacidad to set
	 */
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the ocupacion_actual
	 */
	public int getOcupacion_actual() {
		return ocupacion_actual;
	}

	/**
	 * @param ocupacion_actual the ocupacion_actual to set
	 */
	public void setOcupacion_actual(int ocupacion_actual) {
		this.ocupacion_actual = ocupacion_actual;
	}
	
	
}
