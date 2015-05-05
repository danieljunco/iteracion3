package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.PedidoMaterial2;
import prodAndes.vos.ReqPedidoProveedor;

public class ServletIteracion4 extends ServletTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter respuesta = response.getWriter();
		String identificadorMaterial = request.getParameter("identificadorMaterial");
		if(identificadorMaterial != null){
			try{
				imprimirTablasClientes(respuesta, identificadorMaterial);
			}
			catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en clientes");
			}
		}
		
	}
	
	private void imprimirTablasClientes(PrintWriter respuesta, String identificadorMaterial) throws Exception{
		ArrayList<PedidoMaterial2> pedidosMaterial = ProdAndes.darInstancia().consultarMaterial2(identificadorMaterial);
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC11 - Consultar Material 2");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC11");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Consulta");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Identificador Material</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"identificadorMaterial\" placeholder=\"Ingrese Identificador del Material\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                        </ol>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Pedidos Material 2</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Id Material</th>");
		respuesta.println("                                        <th>Id Pedido</th>");
		respuesta.println("                                        <th>Email Cliente</th>");
		respuesta.println("                                        <th>Fecha Pedido</th>");
		respuesta.println("                                        <th>Fecha Entrega</th>");
		respuesta.println("                                        <th>Estado</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < pedidosMaterial.size(); i++) 
		{
			PedidoMaterial2 x = pedidosMaterial.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+x.getIdMaterial()+"</td>");
			respuesta.println("                                        <td>"+x.getIdPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getEmailCliente()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaEntrega()+"</td>");
			respuesta.println("                                        <td>"+x.getEstado()+"</td>");
			respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");
	}

}
