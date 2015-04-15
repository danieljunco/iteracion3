package prodAndes.servlets;



import java.io.IOException;



import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



import prodAndes.fachada.ProdAndes;



public class ServletCancelarPedidos extends ServletTemplate {



/**

* 

*/

private static final long serialVersionUID = 1L;



@Override

public void escribirContenido(HttpServletRequest request,

HttpServletResponse response) throws IOException {

String idPed = request.getParameter("idPedido");

try {

System.out.println("Entra");

ProdAndes.darInstancia().cancelarPedidos(idPed);

imprimirExito(response, "Se cancelo el pedido del cliente con id: " + idPed);



} catch (Exception e) {

e.printStackTrace();

imprimirError(response, "Al cancelar el pedido #: " + idPed);

}

}



}

