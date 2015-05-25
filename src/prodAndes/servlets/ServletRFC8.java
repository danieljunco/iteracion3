package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.ConsultaEtapaProduccion;


public class ServletRFC8 extends ServletTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter respuesta = response.getWriter();
		try {
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RFC8 - Consultar Ejecucion De Etapas De Produccion 1");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> RFC8");
			respuesta.println("                            </li>");
			respuesta.println("                        </ol>");
			respuesta.println("                    </div>");
			respuesta.println("                </div>");
			
			respuesta.println("<ol class=\"breadcrumb\">");
			respuesta.println("                        <h2>");
			respuesta.println("                            Filtros - Seleccionar Uno");
			respuesta.println("                        </h2>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-2\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <input class=\"form-control\" name=\"codPedido\" placeholder=\"Pedido\" disabled>");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-2\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <input class=\"form-control\" name=\"codMaterial\" placeholder=\"Material\" disabled>");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-2\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <input class=\"form-control\" name=\"cant\" placeholder=\"Cantidad\" disabled>");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-2\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <input class=\"form-control\" name=\"codEtapa\" placeholder=\"Etapa\" disabled>");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-2\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletIteracion4.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <input class=\"form-control\" name=\"codProducto\" placeholder=\"Producto\" disabled>");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                            </div>");
			respuesta.println("                        </ol>");
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}