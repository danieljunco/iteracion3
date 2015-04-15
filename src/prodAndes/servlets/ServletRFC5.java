package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.PedidoCliente;

public class ServletRFC5 extends ServletTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {
			PrintWriter respuesta = response.getWriter();
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("");
			respuesta.println("                        ");
			respuesta.println("");
			respuesta.println("                    </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ImprimirRFC5.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                            	<h2>Seleccionar Categoria</h2>");
			respuesta.println("                            	<select class=\"form-control\" name=\"catPedidos\" required>");
			respuesta.println("                            	<option value=\"\" disabled selected> Seleccione Categoria </option>");
			respuesta.println("                            	<option value=\"Pedidos Clientes\">Pedidos Clientes</option>");
			respuesta.println("                            	<option value=\"Pedidos Proveedores\">Pedidos Proveedores</option>");
			respuesta.println("                            	</select>");
			respuesta.println("                                ");
			respuesta.println("					</div>");
			respuesta.println("");
			respuesta.println("                            <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                            <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("");
			respuesta.println("                        </form>");
			respuesta.println("                    </div>");
			respuesta.println("                    ");
			respuesta.println("                </div>");
		} catch (Exception e) {
			// TODO: handle exception
		}	
	}

}
