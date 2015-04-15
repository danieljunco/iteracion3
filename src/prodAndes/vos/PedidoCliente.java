package prodAndes.vos;

import java.util.Date;


public class PedidoCliente {
	private String idPedido;
	
	private String cliente;
	
	private String estado;
	
	private Date fecha;
	
	private Date fechaEnt;
	
	private String emailCliente;

	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaEnt() {
		return fechaEnt;
	}

	public void setFechaEnt(Date fechaEnt) {
		this.fechaEnt = fechaEnt;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}
}
