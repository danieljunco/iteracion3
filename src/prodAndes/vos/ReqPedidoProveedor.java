package prodAndes.vos;

import java.util.Date;

public class ReqPedidoProveedor {
	private String idPed;
	private String email;
	private Date fechaPedido;
	private Date fechaEntrega;
	private String estado;
	private int cantidad;
	private String nombreMaterial;
	/**
	 * @return the idPed
	 */
	public String getIdPed() {
		return idPed;
	}
	/**
	 * @param idPed the idPed to set
	 */
	public void setIdPed(String idPed) {
		this.idPed = idPed;
	}
	/**
	 * @return the fechaPedido
	 */
	public Date getFechaPedido() {
		return fechaPedido;
	}
	/**
	 * @param fechaPedido the fechaPedido to set
	 */
	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the fechaEntrega
	 */
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	/**
	 * @param fechaEntrega the fechaEntrega to set
	 */
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
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
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the nombreMaterial
	 */
	public String getNombreMaterial() {
		return nombreMaterial;
	}
	/**
	 * @param nombreMaterial the nombreMaterial to set
	 */
	public void setNombreMaterial(String nombreMaterial) {
		this.nombreMaterial = nombreMaterial;
	}
	
	
	
}
