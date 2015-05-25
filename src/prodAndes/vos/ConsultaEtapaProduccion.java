package prodAndes.vos;

import java.util.Date;

public class ConsultaEtapaProduccion {

	private String idEtapa;
	
	private String idProducto;
	
	private String idPedido;
	
	private String idMaterial;
	
	private int numEtapa;
	
	private int cantidad;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private String descripcion;
	
	private int costoFinal;

	public String getIdEtapa() {
		return idEtapa;
	}

	public void setIdEtapa(String idEtapa) {
		this.idEtapa = idEtapa;
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public String getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(String idMaterial) {
		this.idMaterial = idMaterial;
	}

	public int getNumEtapa() {
		return numEtapa;
	}

	public void setNumEtapa(int numEtapa) {
		this.numEtapa = numEtapa;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the costoFinal
	 */
	public int getCostoFinal() {
		return costoFinal;
	}

	/**
	 * @param costoFinal the costoFinal to set
	 */
	public void setCostoFinal(int costoFinal) {
		this.costoFinal = costoFinal;
	}
	
	
}
