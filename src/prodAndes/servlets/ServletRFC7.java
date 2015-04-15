package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.Cliente;
import prodAndes.vos.Proveedor;

public class ServletRFC7 extends ServletTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request,HttpServletResponse response) throws IOException {		
		PrintWriter respuesta = response.getWriter();
		try {
			System.out.println("Entra loco");
			ArrayList<Proveedor> proveedores = ProdAndes.darInstancia().consultarProveedores();
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RFC7 - Consultar Proveedores");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> RFC7");
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
			respuesta.println("                                <input class=\"form-control\" name=\"emailProveedor\" placeholder=\"Ingrese Email del proveedor\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Numero Documento</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"documentoProveedor\" placeholder=\"Ingrese documento\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-4\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Materia que provee</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"matProvee\" placeholder=\"Ingrese Materia\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                            </div>");
			respuesta.println("                        </ol>");
			
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h2>Tabla Proveedores</h2>");
			respuesta.println("                        <div class=\"table-responsive\">");
			respuesta.println("                            <table class=\"table table-bordered table-hover\">");
			respuesta.println("                                <thead>");
			respuesta.println("                                    <tr>");
			respuesta.println("                                        <th>Email</th>");
			respuesta.println("                                        <th>Nacionalidad</th>");
			respuesta.println("                                        <th>Direccion</th>");
			respuesta.println("                                        <th>Numero Documento</th>");
			respuesta.println("                                        <th>Id Pedido</th>");
			respuesta.println("                                        <th>Estado</th>");
			respuesta.println("                                        <th>Materia Provee</th>");
			respuesta.println("                                    </tr>");
			respuesta.println("                                </thead>");
			respuesta.println("                                <tbody>");
			for (int i = 0; i < proveedores.size(); i++) 
			{
				Proveedor x = proveedores.get(i);
				respuesta.println("                                <tr>");
				respuesta.println("                                        <td>"+x.getCorreo()+"</td>");
				respuesta.println("                                        <td>"+x.getPais()+"</td>");
				respuesta.println("                                        <td>"+x.getDireccion()+"</td>");
				respuesta.println("                                        <td>"+x.getIdentificacion()+"</td>");
				respuesta.println("                                        <td>"+x.getIdPedido()+"</td>");
				respuesta.println("                                        <td>"+x.getEstadoPedido()+"</td>");
				respuesta.println("                                        <td>"+x.getNombreMateria()+"</td>");
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
