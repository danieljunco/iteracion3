package prodAndes.vos;

import java.util.Date;

public class PedidoMaterial2 {

	private String codEtapa;
	
	private String idProducto;
	
	private String idMaterial;
	
	private String idPedido;
	
	private String emailCliente;
	
	private Date fechaPedido;
	
	private Date fechaEntrega;
	
	private String estado;
	
	private int cantidad;
	
	private String idComponente;

	/**
	 * @return the idMaterial
	 */
	public String getIdMaterial() {
		return idMaterial;
	}

	/**
	 * @param idMaterial the idMaterial to set
	 */
	public void setIdMaterial(String idMaterial) {
		this.idMaterial = idMaterial;
	}

	/**
	 * @return the idPedido
	 */
	public String getIdPedido() {
		return idPedido;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	/**
	 * @return the emailCliente
	 */
	public String getEmailCliente() {
		return emailCliente;
	}

	/**
	 * @param emailCliente the emailCliente to set
	 */
	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
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
	 * @return the codEtapa
	 */
	public String getCodEtapa() {
		return codEtapa;
	}

	/**
	 * @param codEtapa the codEtapa to set
	 */
	public void setCodEtapa(String codEtapa) {
		this.codEtapa = codEtapa;
	}

	/**
	 * @return the idProducto
	 */
	public String getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * @return the idComponente
	 */
	public String getIdComponente() {
		return idComponente;
	}

	/**
	 * @param idComponente the idComponente to set
	 */
	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}
	
	
}
