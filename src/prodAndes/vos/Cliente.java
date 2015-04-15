package prodAndes.vos;

public class Cliente extends Usuario {

	public String registro;
	
	public String pedido;

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}
	
	public String getPedido(){
		return pedido;
	}
	
	public void setPedido(String pedido){
		this.pedido = pedido;
	}
}
