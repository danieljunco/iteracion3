package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;


public class ServletRFC10 extends ServletTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter respuesta = response.getWriter();
		try {
			ArrayList<String> tiposMaterial = ProdAndes.darInstancia().consultarNombrePedidos2();
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RFC10 - Consultar Pedidos 2");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> RFC10");
			respuesta.println("                            </li>");
			respuesta.println("                        </ol>");
			respuesta.println("                    </div>");
			respuesta.println("                </div>");
			
			respuesta.println("<ol class=\"breadcrumb\">");
			respuesta.println("                        <h2>");
			respuesta.println("                            Consulta");
			respuesta.println("                        </h2>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-6\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Tipo de Material</label>");
			respuesta.println("                            	<select class=\"form-control\" name=\"typeMaterial\" required>");
			respuesta.println("                            	<option value=\"\" disabled selected> Sel. Producto </option>");
			for (int i = 0; i < tiposMaterial.size(); i++) {
				String nombre = tiposMaterial.get(i);
				respuesta.println("                            	<option value=\""+nombre+"\">"+nombre+"</option>");
			}
			respuesta.println("                            	</select>");
			respuesta.println("                                <label>Costo</label>");
			respuesta.println("                                <input class=\"form-control\" name=\"costo\" placeholder=\"Ingrese costo\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                            </div>");
			respuesta.println("                        </ol>");
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}