package prodAndes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prodAndes.fachada.ProdAndes;
import prodAndes.vos.EstacionProduccion;

public class ServletRF17 extends ServletTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void escribirContenido(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter respuesta = response.getWriter();
		try {
			ArrayList<EstacionProduccion> estaciones = ProdAndes.darInstancia().darEstacionesProduccion();
			respuesta.println("<div id=\"page-wrapper\">");
			respuesta.println("");
			respuesta.println("            <div class=\"container-fluid\">");
			respuesta.println("");
			respuesta.println("                <!-- Page Heading -->");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            RF17 - Reportar Cambio de Estado");
			respuesta.println("                        </h1>");
			respuesta.println("                        <ol class=\"breadcrumb\">");
			respuesta.println("                            <li>");
			respuesta.println("                                <i class=\"fa fa-dashboard\"></i>  <a href=\"index.htm\">Atras</a>");
			respuesta.println("                            </li>");
			respuesta.println("                            <li class=\"active\">");
			respuesta.println("                                <i class=\"fa fa-table\"></i> RF17");
			respuesta.println("                            </li>");
			respuesta.println("                        </ol>");
			respuesta.println("                    </div>");
			respuesta.println("                </div>");
			respuesta.println("                </div>");
			respuesta.println("                <div class=\"row\">");
			respuesta.println("                    <div class=\"col-lg-12\">");
			respuesta.println("                        <h1 class=\"page-header\">");
			respuesta.println("                            Estaciones de Produccion");
			respuesta.println("                        </h1>");
			respuesta.println("                </div>");
			respuesta.println("                </div>");
			
			for (int i = 0; i < estaciones.size(); i++) {
				EstacionProduccion x = estaciones.get(i);
				respuesta.println("                    <div class=\"col-lg-3\">");
				respuesta.println("<ol class=\"breadcrumb\">");
				respuesta.println("                    <form role=\"form\" action=\"ServletFiltrosTablas.htm\" method=\"get\">");
				respuesta.println("");
				respuesta.println("                            <div class=\"form-group\">");
				respuesta.println("                                <label>Id</label>");
				respuesta.println("                                <a name=\"codigo\">"+x.getCodigo()+"</a>");
				respuesta.println("                                <br>");
				respuesta.println("                                <label>Estado</label>");
				respuesta.println("                                <a name=\"estado\">"+x.getEstado()+"</a>");
				respuesta.println("                                <br>");
				respuesta.println("                                <label>Capacidad</label>");
				respuesta.println("                                <a name=\"capacidad\">"+x.getCapacidad()+"</a>");
				respuesta.println("                                <br>");	
				respuesta.println("                                <label>Ocupacion Actual</label>");
				respuesta.println("                                <a name=\"ocupacion\">"+x.getOcupacion_actual()+"</a>");
				respuesta.println("                            	   <button type=\"submit\" class=\"btn btn-primary\">Cambiar Estado</button>");
				respuesta.println("                            </div>");
				respuesta.println("</ol>");
				respuesta.println("</form>");
				respuesta.println("<br>");
				respuesta.println("</div>");

				
			}
			respuesta.println("                </div>");
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
