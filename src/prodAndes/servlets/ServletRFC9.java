package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletRFC9 extends ServletTemplate{

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
			respuesta.println("                            RFC9 - Consultar Ejecucion De Etapas De Produccion 2");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Dashboard</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> RFC9");
			respuesta.println("                            </li>");
			respuesta.println("                        </ol>");
			respuesta.println("                    </div>");
			respuesta.println("                </div>");
			
			respuesta.println("<ol class=\"breadcrumb\">");
			respuesta.println("                        <h2>");
			respuesta.println("                            Rango de Tiempo");
			respuesta.println("                        </h2>");
			respuesta.println("<div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-6\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Rango</label>");
			respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"rango1\" placeholder=\"Ingrese Rango\">");
			respuesta.println("                            </div>");
			respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Consultar</button>");
			respuesta.println("                                <button type=\"reset\" class=\"btn btn-primary\">Reset</button>");
			respuesta.println("</form>");
			respuesta.println("                            </div>");
			respuesta.println("                    <div class=\"col-lg-6\">");
			respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
			respuesta.println("");
			respuesta.println("                            <div class=\"form-group\">");
			respuesta.println("                                <label>Rango</label>");
			respuesta.println("                                <input type=\"date\" class=\"form-control\" name=\"rango2\" placeholder=\"Ingrese Rango\">");
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