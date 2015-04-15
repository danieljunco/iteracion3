package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.PedidoCliente;
import prodAndes.vos.Producto;
import prodAndes.vos.ReqPedidoCliente;
import prodAndes.vos.ReqPedidoProveedor;

public class ServletImprimirRFC5 extends ServletTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter respuesta = response.getWriter();
		String categoria = request.getParameter("catPedidos");
		System.out.println(categoria);
		if(categoria.equals("Pedidos Clientes")){
			try {
				imprimirTablasClientes(response.getWriter());
			} catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en pedidos");
			}
		}
		else {
			try {
				imprimirTablasProveedores(response.getWriter());
			} catch (Exception e) {
				e.printStackTrace();
				imprimirError(response, "Error en pedidos");
			}
		}
	}
	
	private void imprimirTablasClientes(PrintWriter respuesta) throws Exception{
		try{
			
		ArrayList<ReqPedidoCliente> pedidos = ProdAndes.darInstancia().consultarPedidosClientes();
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC5 - Consultar Pedidos");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC5");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Filtros");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Email</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"emailCliente2\" placeholder=\"Ingrese Email del cliente\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Fecha</label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaPedido\" >");
		respuesta.println("                            <br>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaEntrega\" >");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Nombre Producto</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"nombreProducto\" placeholder=\"Ingrese nombre\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                        </ol>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Pedidos Clientes</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Email Cliente</th>");
		respuesta.println("                                        <th>Nombre</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                        <th>Costo</th>");
		respuesta.println("                                        <th>Fecha Pedido</th>");
		respuesta.println("                                        <th>Fecha Entrega</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < pedidos.size(); i++) 
		{
			ReqPedidoCliente x = pedidos.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+x.getEmailCliente()+"</td>");
			respuesta.println("                                        <td>"+x.getNombreProducto()+"</td>");
			respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
			respuesta.println("                                        <td>"+x.getCosto()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaEntrega()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");

	} catch (Exception e) {
		// TODO: handle exception
	}
	}
	
	private void imprimirTablasProveedores(PrintWriter respuesta) throws Exception{
		try{
		
		ArrayList<ReqPedidoProveedor> pedidos = ProdAndes.darInstancia().consultarPedidosProveedor();
		respuesta.println("<div id=\"page-wrapper\">");
		respuesta.println("");
		respuesta.println("            <div class=\"container-fluid\">");
		respuesta.println("");
		respuesta.println("                <!-- Page Heading -->");
		respuesta.println("                <div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h1 class=\"page-header\">");
		respuesta.println("                            RFC5 - Consultar Pedidos");
		respuesta.println("                        </h1>");
		respuesta.println("                        <ol class=\"breadcrumb\">");
		respuesta.println("                            <li>");
		respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
		respuesta.println("                            </li>");
		respuesta.println("                            <li class=\"active\">");
		respuesta.println("                                <i class=\"fa fa-table\"></i> RFC5");
		respuesta.println("                            </li>");
		respuesta.println("                        </ol>");
		respuesta.println("                    </div>");
		respuesta.println("                </div>");
		
		respuesta.println("<ol class=\"breadcrumb\">");
		respuesta.println("                        <h2>");
		respuesta.println("                            Filtros");
		respuesta.println("                        </h2>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Estado</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"estado\" placeholder=\"Ingrese estado\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Fecha</label>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaPedidoProveedores\" >");
		respuesta.println("                            <br>");
		respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"fechaEntregaProveedores\" >");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                    <div class=\"col-lg-4\">");
		respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
		respuesta.println("");
		respuesta.println("                            <div class=\"form-group\">");
		respuesta.println("                                <label>Id Pedido</label>");
		respuesta.println("                                <input class=\"form-control\" name=\"idPedidoProveedores\" placeholder=\"Ingrese pedido\">");
		respuesta.println("                            </div>");
		respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
		respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
		respuesta.println("</form>");
		respuesta.println("                            </div>");
		respuesta.println("                            </div>");
		respuesta.println("                        </ol>");
		respuesta.println("<div class=\"row\">");
		respuesta.println("                    <div class=\"col-lg-12\">");
		respuesta.println("                        <h2>Tabla Pedidos Proveedores</h2>");
		respuesta.println("                        <div class=\"table-responsive\">");
		respuesta.println("                            <table class=\"table table-bordered table-hover\">");
		respuesta.println("                                <thead>");
		respuesta.println("                                    <tr>");
		respuesta.println("                                        <th>Id Pedido</th>");
		respuesta.println("                                        <th>Email</th>");
		respuesta.println("                                        <th>Fecha Pedido</th>");
		respuesta.println("                                        <th>Fecha Entrega</th>");
		respuesta.println("                                        <th>Estado</th>");
		respuesta.println("                                        <th>Cantidad</th>");
		respuesta.println("                                        <th>Material</th>");
		respuesta.println("                                    </tr>");
		respuesta.println("                                </thead>");
		respuesta.println("                                <tbody>");
		for (int i = 0; i < pedidos.size(); i++) 
		{
			ReqPedidoProveedor x = pedidos.get(i);
			respuesta.println("                                <tr>");
			respuesta.println("                                        <td>"+x.getIdPed()+"</td>");
			respuesta.println("                                        <td>"+x.getEmail()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaPedido()+"</td>");
			respuesta.println("                                        <td>"+x.getFechaEntrega()+"</td>");
			respuesta.println("                                        <td>"+x.getEstado()+"</td>");
			respuesta.println("                                        <td>"+x.getCantidad()+"</td>");
			respuesta.println("                                        <td>"+x.getNombreMaterial()+"</td>");
			respuesta.println("                                </tr>");

		}
		
		respuesta.println("                                </tbody>");
		respuesta.println("                            </table>");
		respuesta.println("                         </div>");
		respuesta.println("                      </div>");
		respuesta.println("                 </div>");

	} catch (Exception e) {
		// TODO: handle exception
	}
	}

}
