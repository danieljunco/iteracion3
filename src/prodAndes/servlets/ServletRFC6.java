package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.Cliente;
import prodAndes.vos.Producto;

public class ServletRFC6 extends ServletTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter respuesta = response.getWriter();
		try {
			System.out.println("Entra loco");
			ArrayList<Cliente> clientes = ProdAndes.darInstancia().consultarClientes();
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RFC6 - Consultar Clientes");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> RFC6");
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
			respuesta.println("                                <input class=\"form-control\" name=\"emailCliente\" placeholder=\"Ingrese Email del cliente\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Nacionalidad</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"nacionalidad\" placeholder=\"Ingrese pais\">");
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
			respuesta.println("                                <input class=\"form-control\" name=\"idPedido\" placeholder=\"Ingrese pedido\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                            </div>");
			respuesta.println("                        </ol>");
			
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h2>Tabla Clientes</h2>");
			respuesta.println("                        <div class=\"table-responsive\">");
			respuesta.println("                            <table class=\"table table-bordered table-hover\">");
			respuesta.println("                                <thead>");
			respuesta.println("                                    <tr>");
			respuesta.println("                                        <th>Email</th>");
			respuesta.println("                                        <th>Nacionalidad</th>");
			respuesta.println("                                        <th>Direccion</th>");
			respuesta.println("                                        <th>Telefono</th>");
			respuesta.println("                                        <th>Ciudad</th>");
			respuesta.println("                                        <th>Numero Documento</th>");
			respuesta.println("                                        <th>Id Pedido</th>");
			respuesta.println("                                    </tr>");
			respuesta.println("                                </thead>");
			respuesta.println("                                <tbody>");
			for (int i = 0; i < clientes.size(); i++) 
			{
				Cliente x = clientes.get(i);
				respuesta.println("                                <tr>");
				respuesta.println("                                        <td>"+x.getCorreo()+"</td>");
				respuesta.println("                                        <td>"+x.getPais()+"</td>");
				respuesta.println("                                        <td>"+x.getDireccion()+"</td>");
				respuesta.println("                                        <td>"+x.getTelefono()+"</td>");
				respuesta.println("                                        <td>"+x.getCiudad()+"</td>");
				respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
				respuesta.println("                                        <td>"+x.getPedido()+"</td>");
				respuesta.println("                                </tr>");

			}
			
			respuesta.println("                                </tbody>");
			respuesta.println("                            </table>");
			respuesta.println("                         </div>");
			respuesta.println("                      </div>");
			respuesta.println("                 </div>");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
